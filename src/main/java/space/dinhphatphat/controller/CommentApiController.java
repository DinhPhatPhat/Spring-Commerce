package space.dinhphatphat.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import space.dinhphatphat.model.Comment;
import space.dinhphatphat.model.Story;
import space.dinhphatphat.model.User;
import space.dinhphatphat.service.CommentService;
import space.dinhphatphat.service.StoryService;

@RestController
@RequestMapping("api/comment")
public class CommentApiController {

    private final StoryService storyService;
    private final CommentService commentService;

    public CommentApiController(StoryService storyService, CommentService commentService) {
        this.storyService = storyService;
        this.commentService = commentService;
    }

    @PostMapping("add")
    public ResponseEntity<?> addComment (@RequestParam("content") String content, @RequestParam("meta") String meta, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bạn vui lòng đăng nhập để bình luận");
        }
        Story story = storyService.findByMeta(meta);
        if (story == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không tìm thấy bài viết");
        }
        if (content.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bạn nghĩ gì về câu chuyện?");
        }

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setStory(story);
        try {
            Comment createdComment = commentService.create(comment);
            if(createdComment != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
            }
            else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hiện không thể comment");
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hiện không thể comment");
        }

    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteComment (@RequestParam("id") int id, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bạn vui lòng đăng nhập");
        }
        Comment comment = commentService.findById(id);
        if (comment == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không tìm thấy bình luận");
        }
        if(comment.getUser().getId() != user.getId()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Không phải chủ bình luận");
        }
        try {
            commentService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(comment);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hiện không thể xóa");
        }

    }
}

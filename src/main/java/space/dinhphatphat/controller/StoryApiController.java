package space.dinhphatphat.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import space.dinhphatphat.model.Story;
import space.dinhphatphat.model.User;
import space.dinhphatphat.service.StoryService;

import java.io.IOException;
import java.util.List;

@Validated
@RestController
@RequestMapping("api/story")
public class StoryApiController {

    @Autowired
    StoryService storyService;

    @PostMapping("/find-top-3-stories")
    public List<Story> FindTop3NewsStories() {
        return storyService.findTop3ByOrderByCreatedAtDesc();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createStory(@Valid @ModelAttribute Story story, @RequestParam(required = false) MultipartFile image, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (image != null && image .getSize() > 3 * 1024 * 1024) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("File ảnh cần bé hơn 3MB");
        }
        try {
            story.setUser(user);
            Story createdStory = storyService.create(story, image);
            return ResponseEntity.status(HttpStatus.OK).body(createdStory);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Lỗi hệ thống, vui lòng thử lại sau");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateStory(@Valid @ModelAttribute Story story, @RequestParam(required = false) MultipartFile image , HttpSession session) throws IOException {
        User user = (User) session.getAttribute("user");
        if (user.getId() != story.getUser().getId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Truy cập trái phép");
        }
        if (image != null && image .getSize() > 3 * 1024 * 1024) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("File ảnh cần bé hơn 3MB");
        }
        try {
            Story updatedStory =  storyService.update(story, image);
            return ResponseEntity.status(HttpStatus.OK).body(updatedStory);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Lỗi hệ thống, vui lòng thử lại sau");
        }
    }
}

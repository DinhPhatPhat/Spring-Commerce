package space.dinhphatphat.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import space.dinhphatphat.model.Story;
import space.dinhphatphat.model.User;
import space.dinhphatphat.service.StoryService;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("api/story")
public class StoryApiController {

    @Autowired
    StoryService storyService;

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getStories(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "search", required = false) String search) {

        int pageSize = 6;
        Page<Story> storiesPage;

        if (search != null && !search.isEmpty()) {
            storiesPage = (Page<Story>) storyService.searchStories(search, PageRequest.of(page - 1, pageSize));
        } else {
            storiesPage = (Page<Story>) storyService.findAll(PageRequest.of(page - 1, pageSize, Sort.by("updatedAt").descending()));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("stories", storiesPage.getContent());
        response.put("totalPages", storiesPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/find-top-3-stories")
    public List<Story> FindTop3NewsStories() {
        return storyService.findTop3ByOrderByCreatedAtDesc();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createStory(@Valid @ModelAttribute Story story, BindingResult bindingResult, @RequestParam(required = false) MultipartFile image, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hãy đăng nhập");
        }
        ResponseEntity<?> errorResponse = checkBindingResult(user, bindingResult);
        if (errorResponse != null) {
            return errorResponse;
        }

        if (image != null && image .getSize() > 3 * 1024 * 1024) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("File ảnh cần bé hơn 3MB");
        }
        try {
            story.setUser(user);
            storyService.create(story, image);
            return ResponseEntity.status(HttpStatus.CREATED).body("Câu chuyện của bạn đã được chia sẽ");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Lỗi hệ thống, vui lòng thử lại sau");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateStory(@Validated @ModelAttribute Story story, BindingResult bindingResult,
                                         @RequestParam(required = false) MultipartFile image ,
                                         HttpSession session) throws IOException {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hãy đăng nhập");
        }

        ResponseEntity<?> errorResponse = checkBindingResult(user, bindingResult);
        if (errorResponse != null) {
            System.out.println("Binding error");
            return errorResponse;
        }

        Story existingStory = storyService.findById(story.getId());
        if (existingStory == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không tìm thấy story");
        }

        if (user.getId() != existingStory.getUser().getId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Truy cập trái phép");
        }
        if (image != null && image .getSize() > 3 * 1024 * 1024) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("File ảnh cần bé hơn 3MB");
        }
        try {
            existingStory.setTitle(story.getTitle());
            existingStory.setContent(story.getContent());
            storyService.update(existingStory, image);
            return ResponseEntity.status(HttpStatus.OK).body("Cập nhật thành công");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Lỗi hệ thống, vui lòng thử lại sau");
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStory(@PathVariable int id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hãy đăng nhập");
        }
        Story existingStory = storyService.findById(id);
        if (existingStory == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không tìm thấy story");
        }

        if (user.getId() != existingStory.getUser().getId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Truy cập trái phép");
        }
        try {
            storyService.deleteById(id);
            return  ResponseEntity.status(HttpStatus.OK).body("Đã xóa câu chuyện");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Lỗi hệ thống, vui lòng thử lại sau");
        }
    }

    private ResponseEntity<?> checkBindingResult(User user ,BindingResult bindingResult) {
        // BindingResult store valid error, then log and return to front-end
        if (bindingResult.hasErrors()) {
            List<String> fieldOrder = List.of("title", "content");
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .sorted(Comparator.comparingInt(e -> fieldOrder.indexOf(e.getField())))
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();

            return ResponseEntity.badRequest().body(errors.get(0));
        }
        return null;
    }
}

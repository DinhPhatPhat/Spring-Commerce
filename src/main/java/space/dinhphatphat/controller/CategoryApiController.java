package space.dinhphatphat.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.dinhphatphat.model.Category;
import space.dinhphatphat.model.User;
import space.dinhphatphat.service.CategoryService;
import space.dinhphatphat.service.UserService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/api/category")
public class CategoryApiController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@Validated @RequestBody Category category,
                               BindingResult bindingResult,
                               HttpSession session,
                               @CookieValue(value = "token", required = false) String token) {
        System.out.println(category.getName());
        User user = userService.getCurrentUser(session, token);
        if (user == null || user.getRole() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hãy đăng nhập với tư cách quản lý");
        }
        ResponseEntity<?> errorResponse = checkBindingResult(user, bindingResult);
        if (errorResponse != null) {
            return errorResponse;
        }
        try {
            Category existingCategory = categoryService.findByName(category.getName().trim());
            if (existingCategory!=null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tên danh mục đã tồn tại!");
            }
            categoryService.create(category);
            return ResponseEntity.status(HttpStatus.CREATED).body("Danh mục sản phẩm đã được tạo");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Lỗi hệ thống, vui lòng thử lại sau");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCategory(@Validated @RequestBody Category category,
                                            BindingResult bindingResult,
                                            HttpSession session,
                                            @CookieValue(value = "token", required = false) String token) {
        User user = userService.getCurrentUser(session, token);
        if (user == null || user.getRole() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hãy đăng nhập với tư cách quản lý");
        }
        ResponseEntity<?> errorResponse = checkBindingResult(user, bindingResult);
        if (errorResponse != null) {
            return errorResponse;
        }
        try {
            Category existingCategory = categoryService.findByName(category.getName().trim());
            if (existingCategory!=null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tên danh mục đã tồn tại!");
            }
            categoryService.update(category);
            return ResponseEntity.status(HttpStatus.OK).body("Danh mục sản phẩm đã được cập nhật");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Lỗi hệ thống, vui lòng thử lại sau");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable int id, HttpSession session,
                                                @CookieValue(value = "token", required = false) String token) {
        User user = userService.getCurrentUser(session, token);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hãy đăng nhập");
        }
        Category existingCategory = categoryService.findById(id);
        if (existingCategory == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không tìm thấy danh mục sản phẩm");
        }

        try {
            categoryService.deleteById(id);
            return  ResponseEntity.status(HttpStatus.OK).body("Đã xóa câu danh mục sản phẩm");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Lỗi hệ thống, vui lòng thử lại sau");
        }
    }
    @PostMapping("/findAll")
    public ResponseEntity<?> findAllCategory(){
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    private ResponseEntity<?> checkBindingResult(User user ,BindingResult bindingResult) {
        // BindingResult store valid error, then log and return to front-end
        if (bindingResult.hasErrors()) {
            List<String> fieldOrder = List.of("name");
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .sorted(Comparator.comparingInt(e -> fieldOrder.indexOf(e.getField())))
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();

            System.out.println(errors);
            return ResponseEntity.badRequest().body(errors.get(0));
        }
        return null;
    }

}

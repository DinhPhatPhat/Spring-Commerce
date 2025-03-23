package space.dinhphatphat.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.dinhphatphat.model.User;
import space.dinhphatphat.service.TokenService;
import space.dinhphatphat.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/api/user")
public class UserApiController {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    @PutMapping("update")
    public ResponseEntity<?> update(@Validated User user,
                                    BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error ->
                    System.out.println("Validation error: " + error.getDefaultMessage()));
        }
        try {
            User updatedUser = userService.update(user);
            return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body, HttpSession session) {
        String email = body.get("email");
        String password = body.get("password");

        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email không được để trống!");
        }
        if (password == null || password.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mật khẩu không được để trống!");
        }

        int checkLogin = userService.checkLogin(email, password);
        if (checkLogin == 0) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai email hoặc mật khẩu");
        }
        else if (checkLogin == 1) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Tài khoản chưa được kích hoạt, hãy kiểm tra email");
        }
        else {
            // Save user to session, user exist (checked login)
            User user = userService.findByEmail(email).get();
            session.setAttribute("user", user);
            return ResponseEntity.ok("Đăng nhập thành công");
        }
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@Validated @ModelAttribute User user, BindingResult bindingResult) {
        // BindingResult store valid error, then log and return to front-end
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        if(userService.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        User createdUser = userService.create(user);
        if (createdUser == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        Optional<User> userOptional = userService.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userService.forgotPassword(user);
            return ResponseEntity.ok().body("Đường link đổi mật khẩu đã được gửi, vui lòng kiểm tra email");
        }

        return ResponseEntity.badRequest().body("Email không tồn tại!");
    }



    @PostMapping("change-password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> body) {

        String email = body.get("email");
        String password = body.get("password");
        String password2 = body.get("password2");
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email không tồn tại");
        }
        if (password == null || password.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mật khẩu không được bỏ trống");
        }
        if (!password2.equals(password)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mật khẩu không trùng khớp");
        }

        User user = userService.findByEmail(email).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng");
        }

        user.setPassword(password);
        userService.update(user);
        tokenService.deleteAllByUser_Id(user.getId());
        return ResponseEntity.ok().body("Mật khẩu đã được thay đổi, hãy quay về đăng nhập");
    }

    @PostMapping("logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().body("Đăng xuất thành công");
    }

}

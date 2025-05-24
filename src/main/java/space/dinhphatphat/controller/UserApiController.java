package space.dinhphatphat.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import space.dinhphatphat.model.User;
import space.dinhphatphat.service.JwtService;
import space.dinhphatphat.service.TokenService;
import space.dinhphatphat.service.UserService;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@Validated
@RequestMapping("/api/user")
public class UserApiController {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    JwtService jwtService;

    @PutMapping("update")
    public ResponseEntity<?> update(@Validated(User.Update.class) @ModelAttribute User user,
                                    BindingResult bindingResult, @RequestParam(required = false) MultipartFile image,
                                    HttpSession httpSession, @CookieValue(value = "token", required = false) String token) {

        User logingUser = userService.getCurrentUser(httpSession,token);
        if (logingUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hãy đăng nhập");
        }

        ResponseEntity<?> errorResponse = checkBindingResult(user, bindingResult);
        if (errorResponse != null) {
            return errorResponse;
        }

        if (image != null && image.getSize() > 2 * 1024 * 1024) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("File ảnh cần bé hơn 2MB");
        }
        try {
            logingUser.setName(user.getName());
            logingUser.setPhoneNumber(user.getPhoneNumber());
            logingUser.setDateOfBirth(user.getDateOfBirth());
            logingUser.setBio(user.getBio());

            userService.update(logingUser, image);
            return ResponseEntity.status(HttpStatus.OK).body("Thông tin của bạn đã được cập nhật");
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body("Lỗi server");
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body, HttpSession session,
                                   HttpServletResponse response) {
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
            // Login success
            User user = userService.findByEmail(email).get();

            // Create session và save user information to session
            session.setAttribute("user", user);

            // Create token JWT
            String token = jwtService.generateToken(email);

            // Save token to cookie
            Cookie tokenCookie = new Cookie("token", token);
            tokenCookie.setHttpOnly(true);
            tokenCookie.setMaxAge(20 * 24 * 60 * 60); //20 days
            tokenCookie.setSecure(true);  // Send cookie to HTTPS
            tokenCookie.setPath("/");  // Apply for app

            response.addCookie(tokenCookie);  // Add cookie to response

            return ResponseEntity.ok("Đăng nhập thành công");
        }
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@Validated(User.Register.class) @ModelAttribute User user, BindingResult bindingResult) {
        ResponseEntity<?> errorResponse = checkBindingResult(user, bindingResult);
        if (errorResponse != null) {
            return errorResponse;
        }
        if(userService.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email đã tồn tại");
        }
        User createdUser = userService.create(user);
        if (createdUser == null) {
            return ResponseEntity.internalServerError().body("Lỗi server, thử lại sau");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Tạo tài khoản thành công, kiểm tra email để kích hoạt");
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
        if (password.length() < 6) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mật khẩu cần ít nhất 6 ký tự");
        }
        if (!password2.equals(password)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mật khẩu không trùng khớp");
        }

        User user = userService.findByEmail(email).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng");
        }

        user.setPassword(password);
        userService.update(user, null);
        tokenService.deleteAllByUser_Id(user.getId());
        return ResponseEntity.ok().body("Mật khẩu đã được thay đổi, hãy quay về đăng nhập");
    }

    @PostMapping("logout")
    public ResponseEntity<String> logout(HttpSession session, HttpServletResponse response) {
        // Delete session
        session.invalidate();
        // Delete cookie
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setSecure(true);

        response.addCookie(cookie);
        return ResponseEntity.ok().body("Đăng xuất thành công");
    }

    private ResponseEntity<?> checkBindingResult(User user ,BindingResult bindingResult) {
        // BindingResult store valid error, then log and return to front-end
        if (bindingResult.hasErrors()) {
            List<String> fieldOrder = List.of("name", "phoneNumber", "email", "password", "bio");
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .sorted(Comparator.comparingInt(e -> fieldOrder.indexOf(e.getField())))
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();

            return ResponseEntity.badRequest().body(errors.get(0));
        }
        return null;
    }
}

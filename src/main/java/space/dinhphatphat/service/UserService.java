package space.dinhphatphat.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import space.dinhphatphat.model.Token;
import space.dinhphatphat.model.User;
import space.dinhphatphat.repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private EmailService emailService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    public UserService(EmailService emailService) {
        this.emailService = emailService;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(int id){
        return userRepository.findById(id);
    }

    public Boolean existsById(int id){
        return userRepository.existsById(id);
    }

    public Boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public User create(User user){

        User createdUser = userRepository.save(user);
        System.out.println("User ID after save: " + createdUser.getId() + createdUser.getName());
        Token token = tokenService.create(createdUser, 0).orElseThrow(
                () -> {
                    userRepository.delete(user);
                    return new RuntimeException("Không thể tạo token, tạo tài khoản thất bại");
                }
        );
        // Send email to validate the account
        sendVerificationEmail(user, token.getToken());
        return user;
    }

    private void sendVerificationEmail(User user, String token){
        emailService.sendVerificationEmail(user.getEmail(), token);
    }

    private void sendChangePasswordEmail(User user, String token){
        emailService.sendChangePasswordEmail(user.getEmail(), token);
    }

    public User update(User user,MultipartFile image){
        try{
            if(image != null){
                String imagePath = upLoadImage(image, user.getId());
                user.setImagePath(imagePath);
            }
            return userRepository.save(user);
        }
        catch(Exception e){
            return null;
        }
    }

    public User findByEmailAndPassword(String email, String password){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent() && user.get().getPassword().equals(password)){
            return user.get();
        }
        return null;
    }

    public void forgotPassword(User user){

        Token token = tokenService.create(user, 1).orElseThrow(
                () -> new RuntimeException("Không thể tạo token, đổi mật khẩu thất bại")
        );
        sendChangePasswordEmail(user, token.getToken());
    }

    // Wrong email or password 0
    // Not verify yet: 1
    // Correct email and password, verified: 2
    public int checkLogin(String email, String password){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent() && user.get().getPassword().equals(password)){
            if(user.get().isActive()){
                return 2;
            }
            else{
                return 1;
            }
        }
        return 0;
    }

    public String upLoadImage(MultipartFile imageFile, int userId) throws IOException {

        String uploadDir = "uploads/image/user/";
        String accessPath = "/uploads/image/user/";

        return UploadService.upLoadImage(imageFile, userId, uploadDir, accessPath);
    }

    public User getCurrentUser(HttpSession session, String token) {
        // Check session
        User userFromSession = (User) session.getAttribute("user");
        if (userFromSession != null) {
            return userFromSession;
        }

        // If session is not exists, then check cookie
        if (token != null && jwtService.isTokenValid(token)) {
            // Encrypt email from token
            String email = jwtService.extractEmail(token);
            if (email != null) {
                // Take user from email
                User user = userRepository.findByEmail(email).orElse(null);
                // If user found, create session
                if (user != null) {
                    session.setAttribute("user", user);  // Create session
                    return user;
                }
            }
        }
        // If session and cookie are not exist, then return null
        return null;
    }


}

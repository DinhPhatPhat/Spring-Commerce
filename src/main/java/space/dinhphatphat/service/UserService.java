package space.dinhphatphat.service;

import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.dinhphatphat.model.Token;
import space.dinhphatphat.model.User;
import space.dinhphatphat.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final EmailService emailService;

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

    public User update(User user){
        return userRepository.findById(user.getId())
                .map(existingUser -> {
                    existingUser.setName(user.getName());
                    existingUser.setPhoneNumber(user.getPhoneNumber());
                    existingUser.setDateOfBirth(user.getDateOfBirth());
                    existingUser.setBio(user.getBio());

                    return userRepository.save(existingUser);
                })
                .orElseThrow( () -> new RuntimeException("User not found"));
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
}

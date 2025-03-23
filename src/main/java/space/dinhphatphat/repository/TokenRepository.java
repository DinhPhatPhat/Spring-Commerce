package space.dinhphatphat.repository;

import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.dinhphatphat.model.Token;
import space.dinhphatphat.model.User;

import java.util.List;
import java.util.Optional;
@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    boolean existsByUser_Email(@Email(message = "Email không hợp lệ") String userEmail);

    Optional<Token> findByUser_Email(String email);

    boolean existsByToken(String token);

    void deleteByToken(String token);

    Token findOneByToken(String token);

    List<Token> user(User user);

    void deleteAllByUser_Id(int userId);
}

package space.dinhphatphat.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import space.dinhphatphat.model.Token;
import space.dinhphatphat.model.User;
import space.dinhphatphat.repository.TokenRepository;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;


@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public boolean existByToken(String token){
        return tokenRepository.existsByToken(token);
    }
    public void delete(Token token){
        tokenRepository.deleteById(token.getId());
    }

    public Token findByToken(String token){
        return tokenRepository.findOneByToken(token);
    }
    public void deleteByToken(String token){
        tokenRepository.deleteByToken(token);
    }

    @Transactional
    public Optional<Token> create(User user, int type){

        Token token = new Token();
        token.setUser(user);
        String tokenString = UUID.randomUUID().toString();
        token.setToken(tokenString);
        token.setType(type);
        // If registering, don't set expire day, and no email existed before
        if (token.getType() == 0) {
            token.setExpiredAt(null);
        }

        // If changing password, delete all token existed before create the new one
        else {
            tokenRepository.deleteAllByUser_Id(user.getId());
        }
        tokenRepository.save(token);
        return Optional.of(token);
    }

    public Token checkToken(String token){

        if(token != null && token.length() == 36){
            Token optionalToken = findByToken(token);
            if(optionalToken != null && optionalToken.getExpiredAt() == null){
                return optionalToken;
            }
            if(optionalToken != null && optionalToken.getExpiredAt().after(new Timestamp(System.currentTimeMillis())) ) {
                return optionalToken;
            }
        }
        return null;
    }

    @Transactional
    public void deleteAllByUser_Id(int userId){
        tokenRepository.deleteAllByUser_Id(userId);
    }

}

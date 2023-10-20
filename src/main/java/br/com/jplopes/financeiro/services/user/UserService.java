package br.com.jplopes.financeiro.services.user;

import br.com.jplopes.financeiro.entities.user.User;
import br.com.jplopes.financeiro.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User singUp(User user){
        if(this.userRepository.findByEmail(user.getEmail()) != null){
            return null;
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encryptedPassword);
        return this.userRepository.save(user);
    }


}

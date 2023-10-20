package br.com.jplopes.financeiro.resources.user;

import br.com.jplopes.financeiro.entities.user.AuthDTO;
import br.com.jplopes.financeiro.entities.user.User;
import br.com.jplopes.financeiro.infra.security.TokenService;
import br.com.jplopes.financeiro.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping(value = "/signup")
    public ResponseEntity signUp(@RequestBody User user){

        if(this.userService.singUp(user) == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(this.userService.singUp(user));
    }

    @PostMapping(value = "/signin")
    public ResponseEntity signIn(@RequestBody AuthDTO authDTO  ){
       var usernamePassword = new UsernamePasswordAuthenticationToken(authDTO.email(), authDTO.password());
       var auth = this.authenticationManager.authenticate(usernamePassword);
       var token = this.tokenService.generateToken((User) auth.getPrincipal());
       return ResponseEntity.ok().body("token:" + token);
    }

}

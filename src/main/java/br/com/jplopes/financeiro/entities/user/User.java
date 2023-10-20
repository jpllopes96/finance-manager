package br.com.jplopes.financeiro.entities.user;

import br.com.jplopes.financeiro.entities.balance.Balance;
import br.com.jplopes.financeiro.entities.deal.Deal;
import br.com.jplopes.financeiro.entities.deal.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity(name = "tb_user")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //Se tive a propriedade role a classe enum com as role fazer isso para ver a permissão de cada role.
        // No exemplo abaixo o admin tem role de admin e tbm as role de USER normal.
//        if (this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"))
//        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

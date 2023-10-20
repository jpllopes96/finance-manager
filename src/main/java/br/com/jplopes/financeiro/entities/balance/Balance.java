package br.com.jplopes.financeiro.entities.balance;

import br.com.jplopes.financeiro.entities.deal.Type;
import br.com.jplopes.financeiro.entities.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double balance=0.00;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public void setBalance(Double value, Type type){
        if (type.getCode() == 0){
            balance = balance + value;
        }else if(type.getCode() == 1){
            balance = balance - value;
        }
    }
}

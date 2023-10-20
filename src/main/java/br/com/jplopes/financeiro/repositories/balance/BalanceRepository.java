package br.com.jplopes.financeiro.repositories.balance;

import br.com.jplopes.financeiro.entities.balance.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
    Balance findByUserId(Long id);

}

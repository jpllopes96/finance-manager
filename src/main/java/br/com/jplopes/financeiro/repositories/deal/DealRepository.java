package br.com.jplopes.financeiro.repositories.deal;

import br.com.jplopes.financeiro.entities.deal.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DealRepository extends JpaRepository<Deal, Long> {

    List<Deal> findAllByUserId(Long userId);

    //    @Query(value = "SELECT * FROM tb_deal where data BETWEEN :startDate AND :endDate")

    @Query(value = "SELECT * FROM tb_deal WHERE user_id = :userId AND data BETWEEN :startDate AND :endDate" , nativeQuery = true)
    List<Deal> getAllByUserBetweenDates(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate) ;

    List<Deal> findAllByUserIdAndDataBetween(Long id, LocalDateTime startDate, LocalDateTime endDate);

}

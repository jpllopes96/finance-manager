package br.com.jplopes.financeiro.services.deal;

import br.com.jplopes.financeiro.entities.balance.Balance;
import br.com.jplopes.financeiro.entities.deal.Deal;
import br.com.jplopes.financeiro.entities.deal.Type;
import br.com.jplopes.financeiro.entities.user.User;
import br.com.jplopes.financeiro.entities.user.UserDto;
import br.com.jplopes.financeiro.infra.security.TokenService;
import br.com.jplopes.financeiro.repositories.balance.BalanceRepository;
import br.com.jplopes.financeiro.repositories.deal.DealRepository;
import br.com.jplopes.financeiro.repositories.user.UserRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DealService {
    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private BalanceRepository balanceRepository;


    public User findById(Long id){
        Optional<User> obj = userRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("usuario não encontrado: ", id));
    }

    public Deal create(Deal deal) {
        User user = findById(getAuthenticatedUserId());
        Balance saldo = this.balanceRepository.findByUserId(user.getId());
        if (saldo == null) {
            saldo = new Balance();
        }
        saldo.setUser(user);
        saldo.setBalance(deal.getValor(), deal.getType());
        this.balanceRepository.save(saldo);
        deal.setUser(user);
        return this.dealRepository.save(deal);
    }

    public List<Deal> findByUserID(){
        List<Deal> deals = this.dealRepository.findAllByUserId(getAuthenticatedUserId());
        for(Deal d : deals){
            System.out.println(d);
        }

        return deals;
    }

    public List<Deal> getAllByUserBetweenDates(LocalDateTime startDate, LocalDateTime endDate){
        System.out.println(getAuthenticatedUserId());
        List<Deal> deals = this.dealRepository.getAllByUserBetweenDates(getAuthenticatedUserId(), startDate, endDate);

        return deals;
    }


    public void delete(Long id) {
        Optional<Deal> obj = this.dealRepository.findById(id);
        if(getAuthenticatedUserId() == obj.get().getUser().getId()){
            if (obj != null){
                Balance saldo = this.balanceRepository.findByUserId(obj.get().getUser().getId());
                if (saldo == null) {
                    saldo = new Balance();
                }
                if(obj.get().getType() == Type.ENTRADA){
                    saldo.setBalance(obj.get().getValor(), Type.SAIDA);
                }else {
                    saldo.setBalance(obj.get().getValor(), Type.ENTRADA);
                }
                this.balanceRepository.save(saldo);
                this.dealRepository.deleteById(id);
            }else {
                throw new DataIntegrityViolationException("Conta Inexistente");
            }
        }else{
            throw new DataIntegrityViolationException("Você não pode deletar conta de outra pessoa");
        }



    }

    private Long getAuthenticatedUserId(){
        String username = tokenService.getCurrentUser();
        User currentUser = (User) this.userRepository.findByEmail(username);
        return currentUser.getId();
    }
}

package br.com.jplopes.financeiro.resources.deal;

import br.com.jplopes.financeiro.entities.deal.Deal;
import br.com.jplopes.financeiro.services.deal.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/deal")
public class DealResource {

    @Autowired
    private DealService dealService;

    @PostMapping
    public ResponseEntity create(@RequestBody Deal deal){

        return ResponseEntity.ok().body(this.dealService.create(deal));
    }

    @GetMapping
    public ResponseEntity findByUserId(){

        return ResponseEntity.ok().body(this.dealService.findByUserID());
    }

    @GetMapping("/datas")
    public ResponseEntity getAllByUserBetweenDates(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate){
        return ResponseEntity.ok().body(this.dealService.getAllByUserBetweenDates(startDate, endDate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDeal(@PathVariable Long id){
       this.dealService.delete(id);
       return ResponseEntity.noContent().build();
    }
}

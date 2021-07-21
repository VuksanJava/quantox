package com.quantox.api.v1;

import com.quantox.MashineException;
import com.quantox.api.v1.dto.MaschineDto;
import com.quantox.service.MaschineService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/maschines")
@AllArgsConstructor

public class MaschineApi {

    private MaschineService maschineService;

    @GetMapping("")
    public List<MaschineDto> findAll(){
        return maschineService.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete (@PathVariable Long id){

        try {
            maschineService.delete(id);
            return ResponseEntity.ok("Destroy mashine");
        }catch (MashineException mashineException){
            return ResponseEntity.badRequest().body(mashineException.getMessage());
        }
    }


    @PostMapping("/{id}")
    public ResponseEntity start (@PathVariable Long id){
        try {
            maschineService.start(id);
            return ResponseEntity.ok("Start mashine");
        }catch (MashineException | InterruptedException mashineException){
            return ResponseEntity.badRequest().body(mashineException.getMessage());
        }



    }

}

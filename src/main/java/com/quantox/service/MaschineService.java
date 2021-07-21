package com.quantox.service;

import com.quantox.MashineException;
import com.quantox.api.v1.dto.MaschineDto;
import com.quantox.entity.Maschine;
import com.quantox.entity.Status;
import com.quantox.repository.MaschineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MaschineService {

    private MaschineRepository maschineRepository;

    public List<MaschineDto> findAll (){
        List<Maschine> mashines = maschineRepository.findAll();
        return  mashines.stream().filter(m -> m.getActive() == true)
                .map(m -> new MaschineDto(m.getUid(), m.getCreatedBy().getUsername(), m.getStatus(), m.getCreateAt(), m.getActive())).collect(Collectors.toList());

    }

    @Transactional
    public void delete (Long id){
        Maschine maschine = maschineRepository.findById(id).orElseThrow(()-> new MashineException("Mashine not exist"));
        if(maschine.getStatus()== Status.STOPPED)maschine.setActive(false);
    }


    public void start(Long id) throws InterruptedException {
        Maschine maschine = maschineRepository.findById(id).orElseThrow(()-> new MashineException("Mashine not exist"));
       if(maschine.getStatus()== Status.STOPPED){

           Runnable runnable1 = ()->{
               try {
                   Thread.sleep(15000);
                   maschine.setStatus(Status.RUNNING);
                   maschineRepository.save(maschine);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }

           };
           Thread thread = new Thread(runnable1);
           thread.start();
       }

    }
}

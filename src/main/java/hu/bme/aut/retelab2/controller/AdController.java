package hu.bme.aut.retelab2.controller;


import hu.bme.aut.retelab2.SecretGenerator;
import hu.bme.aut.retelab2.domain.Ad;
import hu.bme.aut.retelab2.domain.Note;
import hu.bme.aut.retelab2.repository.AdRepository;
import hu.bme.aut.retelab2.scheduler.ExpirationScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/ads")
public class AdController {
    @Autowired
    private AdRepository adRepository;

    @PostMapping
    public Ad create(@RequestBody Ad ad){
        ad.setId(null);
        ad.setCreationTimeStamp(null);
        ad.setSecret(SecretGenerator.generate());
        return  adRepository.save(ad);
    }
    @GetMapping
    public List<Ad> getByPrice(@RequestParam(required = false, defaultValue = "0") Long min, @RequestParam(required = false, defaultValue = "10000000") Long max){
        Logger logger = LoggerFactory.getLogger(AdController.class);
        logger.info(LocalDateTime.now().toString());
        List<Ad> result = adRepository.findByPriceBetween(min,max);
        result.forEach((a) -> a.setSecret(null));
        return result;
    }

    @PutMapping
    public ResponseEntity<Ad> update(@RequestBody Ad ad){
        Ad a = adRepository.findById(ad.getId()).get();
        if (a == null)
            return  ResponseEntity.notFound().build();
        try {
            a = adRepository.update(ad);
            return  ResponseEntity.ok(a);
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

    }


    @GetMapping("{tag}")
    public List<Ad> getByTag(@PathVariable String tag){
        List<Ad> result = adRepository.findByTag(tag);
        result.forEach((a) -> a.setSecret(null));

        return result;
    }
}

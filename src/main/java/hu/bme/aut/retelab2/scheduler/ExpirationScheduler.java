package hu.bme.aut.retelab2.scheduler;

import hu.bme.aut.retelab2.domain.Ad;
import hu.bme.aut.retelab2.repository.AdRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
@Component
public class ExpirationScheduler {
    @Autowired
    private AdRepository adRepository;

    @Scheduled(fixedDelay = 6000)
    public void removeExpired(){
        Logger logger = LoggerFactory.getLogger(ExpirationScheduler.class);
        List<Ad> expired = adRepository.findByExpirationNotNullAndExpirationBefore(LocalDateTime.now());
        adRepository.deleteAll(expired);
    }
}

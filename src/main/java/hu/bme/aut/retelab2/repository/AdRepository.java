package hu.bme.aut.retelab2.repository;

import hu.bme.aut.retelab2.domain.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface AdRepository extends  JpaRepository<Ad, Integer>{

    List<Ad> findByPriceBetween(long min, long max);
    default List<Ad> findByTag(String tag){
        List<Ad> result = new ArrayList<>();
        for (Ad a: findAll()
             ) {
            if(a.getTags().contains(tag))
                result.add(a);
        }
        return  result;
     }

     List<Ad> findByExpirationNotNullAndExpirationBefore(LocalDateTime time);
    @Transactional
    default Ad update(Ad ad) throws Exception{
        Ad stored = findById(ad.getId()).get() ;
        if(!ad.getSecret().equals(stored.getSecret()) )
            throw new Exception("Different secrets!");

        delete(stored);
        return  save(ad);

    }

}

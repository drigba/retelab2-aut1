package hu.bme.aut.retelab2.domain;

import lombok.Setter;
import  lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Ad {
    @Id
    @GeneratedValue
    private Integer id;

    private String title;
    private  String description;
    private  long price;

    @CreationTimestamp
    private Timestamp creationTimeStamp;

    private String secret;

    @ElementCollection
    private List<String> tags;

    private LocalDateTime expiration;


}

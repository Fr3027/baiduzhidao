package entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class SecondHand {
    @Id
    private Integer id;

    private String title;
    private String description;
    private Date time;
    private Integer uid;
}

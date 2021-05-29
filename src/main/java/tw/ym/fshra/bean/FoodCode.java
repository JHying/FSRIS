package tw.ym.fshra.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author H-yin on 2020.
 */
@Data
@Entity
@Table(name = "FOODCODE", schema = "dbo")
public class FoodCode implements Serializable {



    @Id
    @Column(name = "FOODID")
    private String foodid;

    @Id
    @Column(name = "SP_NAME")
    private String spName;

}

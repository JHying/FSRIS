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
@Table(name = "FOODCLASS", schema = "dbo")
public class FoodClass implements Serializable {

    @Id
    @Column(name = "FOODID")
    private String foodid;

    @Column(name = "FNAME_CH")
    private String fnameCh;

    @Column(name = "FBRANCH_ID")
    private String fbranchId;

    @Column(name = "FMAIN_ID")
    private String fmainId;

    @Column(name = "UNIT_DESC")
    private String unitDesc;

    @Column(name = "CSID")
    private String csId;


}

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
@Table(name = "CONSUMEITG_VIEW", schema = "dbo")
public class ConsumeItgView implements Serializable {

    @Id
    @Column(name = "FOODID")
    private String foodid;

    @Id
    @Column(name = "FNAME_CH")
    private String fnameCh;

    @Id
    @Column(name = "CSID")
    private String csid;

    @Id
    @Column(name = "CSNAME_CH")
    private String csNameCh;

    @Id
    @Column(name = "CSMAX_AVG")
    private Double csMax;

    @Id
    @Column(name = "CSMEAN_AVG")
    private Double csMean;

    @Id
    @Column(name = "CSMIN_AVG")
    private Double csMin;

    @Id
    @Column(name = "GENDER")
    private String gender;

}

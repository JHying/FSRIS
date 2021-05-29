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
@Table(name = "CONSUME_VIEW", schema = "dbo")
public class ConsumeView implements Serializable {

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
    @Column(name = "AGID")
    private String agid;

    @Id
    @Column(name = "CSNAME_CH")
    private String csNameCh;

    @Id
    @Column(name = "CSMAX_SUM")
    private Double csMax;

    @Id
    @Column(name = "CSMEAN_SUM")
    private Double csMean;

    @Id
    @Column(name = "CSMIN_SUM")
    private Double csMin;

    @Id
    @Column(name = "AGNAME_CH")
    private String agNameCh;

    @Id
    @Column(name = "AGE_MIN")
    private Integer ageMin;

    @Id
    @Column(name = "AGE_MAX")
    private Integer ageMax;

    @Id
    @Column(name = "BW_MIN")
    private Double bwMin;

    @Id
    @Column(name = "BW_MAX")
    private Double bwMax;

    @Id
    @Column(name = "BW_MEAN")
    private Double bwMean;

    @Id
    @Column(name = "BW_SD")
    private Double bwSD;

    @Id
    @Column(name = "SPE_TYPE")
    private String speType;

    @Id
    @Column(name = "GENDER")
    private String gender;

}

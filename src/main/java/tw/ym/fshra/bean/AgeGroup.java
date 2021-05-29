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
@Table(name = "AGE_GROUP", schema = "dbo")
public class AgeGroup implements Serializable {

    @Id
    @Column(name = "AGID")
    private String agid;

    @Column(name = "AGNAME_CH")
    private String agNameCh;

    @Column(name = "AGE_MIN")
    private Integer ageMin;

    @Column(name = "AGE_MAX")
    private Integer ageMax;

    @Column(name = "BW_MIN")
    private Double bwMin;

    @Column(name = "BW_MAX")
    private Double bwMax;

    @Column(name = "BW_MEAN")
    private Double bwMean;

    @Column(name = "BW_SD")
    private Double bwSD;

    @Column(name = "SPE_TYPE")
    private String speType;

    @Column(name = "GENDER")
    private String gender;

}

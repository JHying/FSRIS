package tw.ym.fshra.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author H-yin on 2020.
 */
@Data
@Entity
@Table(name = "CONCENTRATION", schema = "dbo")
public class Concentration implements Serializable {

    @Id
    @Column(name = "CONCID")
    private String concid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_TIME")
    private Date dtTime;

    @Column(name = "DT_CONC")
    private Double dtConc;

    @Column(name = "MDL")
    private Double mdl;

    @Column(name = "CONC_REF")
    private String concRef;

    @Column(name = "SP_NAME")
    private String spName;

    @Id
    @Column(name = "SUBSID")
    private String subsid;

}

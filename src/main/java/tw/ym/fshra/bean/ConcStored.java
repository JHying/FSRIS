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
@Table(name = "CONC_STORED", schema = "dbo")
public class ConcStored implements Serializable {

    @Id
    @Column(name = "FOODID")
    private String foodid;

    @Id
    @Column(name = "FBRANCH_ID")
    private String fbranchId;

    @Id
    @Column(name = "FMAIN_ID")
    private String fmainId;

    @Id
    @Column(name = "FNAME_CH")
    private String fnameCh;

    @Id
    @Column(name = "SUBSID")
    private String subsid;

    @Id
    @Column(name = "SMAIN_ID")
    private String smainId;

    @Id
    @Column(name = "SNAME")
    private String sName;

    @Id
    @Column(name = "SNAME_CH")
    private String sNameCh;

    @Id
    @Column(name = "INTRO")
    private String intro;

    @Id
    @Column(name = "INTRO_REF")
    private String introRef;

    @Id
    @Column(name = "MIN_MDL")
    private Double minMdl;

    @Id
    @Column(name = "CONC_COUNT")
    private int concCount;

    @Id
    @Column(name = "CONC_MAX")
    private Double concMax;

    @Id
    @Column(name = "CONC_MEAN")
    private Double concMean;

    @Id
    @Column(name = "CONC_MIN")
    private Double concMin;

    @Id
    @Column(name = "CONC_EDATE")
    private String concEdate;

    @Id
    @Column(name = "CONC_LDATE")
    private String concLdate;

    @Id
    @Column(name = "FAIL_RATE")
    private Double failRate;

}

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
@Entity
@Data
@Table(name = "SUBSHAZ_VIEW", schema = "dbo")
public class SubsHazView implements Serializable {

    @Id
    @Column(name = "SUBSID")
    private String subsid;

    @Id
    @Column(name = "SBRANCH_ID")
    private String sbranchId;

    @Id
    @Column(name = "SMAIN_ID")
    private String smainId;

    @Id
    @Column(name = "SNAME")
    private String sname;

    @Id
    @Column(name = "SNAME_CH")
    private String snameCh;

    @Id
    @Column(name = "ATDI_RULE")
    private String atdiRule;

    @Id
    @Column(name = "CONSUME_RULE")
    private String consumeRule;

    @Id
    @Column(name = "LEGAL_RULE")
    private String legalRule;

    @Id
    @Column(name = "BANNED")
    private String banned;

    @Id
    @Column(name = "POD")
    private Double pod;

    @Id
    @Column(name = "POD_NAME")
    private String podName;

    @Id
    @Column(name = "UF")
    private Double UF;

    @Id
    @Column(name = "MF")
    private Double MF;

    @Id
    @Column(name = "ADI")
    private Double adi;

    @Id
    @Column(name = "TDI")
    private Double tdi;

    @Id
    @Column(name = "ORAL_RfD")
    private Double oralRfD;

    @Id
    @Column(name = "POD_REF")
    private String podRef;

    @Id
    @Column(name = "ADI_REF")
    private String adiRef;

    @Id
    @Column(name = "TDI_REF")
    private String tdiRef;

    @Id
    @Column(name = "ORALRfD_REF")
    private String oralRfDref;

    @Id
    @Column(name = "TOXID")
    private String toxid;

    @Id
    @Column(name = "TNAME")
    private String tname;

    @Id
    @Column(name = "TNAME_CH")
    private String tnameCh;

    @Id
    @Column(name = "SPECIES")
    private String species;

    @Id
    @Column(name = "TS_SEX")
    private String tsSex;

    @Id
    @Column(name = "TS_REF")
    private String tsRef;

    @Id
    @Column(name = "SCORE_MAX")
    private int scoreMax;

}

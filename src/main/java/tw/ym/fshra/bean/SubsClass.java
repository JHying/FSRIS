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
@Table(name = "SUBSCLASS", schema = "dbo")
public class SubsClass implements Serializable {

    @Id
    @Column(name = "SUBSID")
    private String subsid;

    @Column(name = "SNAME")
    private String sname;

    @Column(name = "SNAME_CH")
    private String snameCh;

    @Column(name = "SBRANCH_ID")
    private String sbranchId;

    @Column(name = "SMAIN_ID")
    private String smainId;

    @Column(name = "ATDI_RULE")
    private String atdiRule;

    @Column(name = "CONSUME_RULE")
    private String consumeRule;

    @Column(name = "LEGAL_RULE")
    private String legalRule;

    @Column(name = "BANNED")
    private String banned;

    @Column(name = "RULE_REF")
    private String ruleRef;

    @Column(name = "CASRN")
    private String casrn;

    @Column(name = "INTRO")
    private String intro;

    @Column(name = "INTRO_REF")
    private String introRef;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "INTRO_UPDATED")
    private Date introUpdated;

}

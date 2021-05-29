package tw.ym.fshra.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author H-yin on 2020.
 */
@Entity
@Data
@Table(name = "PASS_STORED", schema = "dbo")
public class PassStored implements Serializable {

    @Id
    @Column(name = "FOODID")
    private String foodid;

    @Id
    @Column(name = "CONCID")
    private String concid;

    @Id
    @Column(name = "SP_NAME")
    private String spName;

    @Id
    @Column(name = "DT_CONC")
    private Double dtConc;

    @Id
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_TIME")
    private Date dtTime;

    @Id
    @Column(name = "MDL")
    private Double mdl;

    @Id
    @Column(name = "MRL")
    private Double mrl;

    @Id
    @Column(name = "RG_CLASS")
    private String rgClass;

    @Id
    @Column(name = "MRL_REF")
    private String mrlRef;

    @Id
    @Column(name = "CONC_REF")
    private String concRef;

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
    @Column(name = "PASS")
    private String pass;

}

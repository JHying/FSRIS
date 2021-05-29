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
@Table(name = "MRL_VIEW", schema = "dbo")
public class MrlView implements Serializable {



    @Id
    @Column(name = "FOODID")
    private String foodid;

    @Id
    @Column(name = "FNAME_CH")
    private String fNameCh;

    @Id
    @Column(name = "SUBSID")
    private String subsid;

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
    @Column(name = "RG_CLASS")
    private String rgClass;

    @Id
    @Column(name = "MRL")
    private Double mrl;

    @Id
    @Column(name = "MRL_REF")
    private String mrlRef;

    @Id
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MRL_UPDATED")
    private Date mrlUpdated;

}

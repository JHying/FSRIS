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
@Table(name = "MRLSLIST", schema = "dbo")
public class MrlsList implements Serializable {



    @Id
    @Column(name = "FOODID")
    private String foodid;

    @Id
    @Column(name = "SUBSID")
    private String subsid;

    @Id
    @Column(name = "RG_CLASS")
    private String rgClass;

    @Column(name = "MRL")
    private Double mrl;

    @Column(name = "MRL_REF")
    private String mrlRef;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MRL_UPDATED")
    private Date mrlUpdated;

}

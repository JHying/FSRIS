package tw.ym.fshra.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author H-yin on 2020.
 */
@Entity
@Data
@Table(name = "SUBSHAZARD", schema = "dbo")
public class SubsHazard implements Serializable {

    @Id
    @Column(name = "SUBSID")
    private String subsid;

    @Column(name = "POD")
    private Double pod;

    @Column(name = "POD_NAME")
    private String podName;

    @Column(name = "UF")
    private Double UF;

    @Column(name = "MF")
    private Double MF;

    @Column(name = "ADI")
    private Double adi;

    @Column(name = "TDI")
    private Double tdi;

    @Column(name = "ORAL_RfD")
    private Double oralRfD;

    @Column(name = "POD_REF")
    private String podRef;

    @Column(name = "ADI_REF")
    private String adiRef;

    @Column(name = "TDI_REF")
    private String tdiRef;

    @Column(name = "ORALRfD_REF")
    private String oralRfDref;

}

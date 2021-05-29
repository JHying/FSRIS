package tw.ym.fshra.bean;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author H-yin on 2020.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RISKSCORE", schema = "dbo")
public class RiskScore implements Serializable {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "RANK")
    private Integer rank;

    @Column(name = "SNAME_CH")
    private String subsNameCh;

    @Column(name = "SNAME")
    private String subsName;

    @Column(name = "SCORE_A")
    private Integer scoreA;

    @Column(name = "SCORE_B")
    private Integer scoreB;

    @Column(name = "SCORE_C")
    private Integer scoreC;

    @Column(name = "SCORE_D")
    private Integer scoreD;

    @Column(name = "TOTAL")
    private Integer total;

    @Column(name = "SUBSID")
    private String subsid;

    @Column(name = "SMAIN_ID")
    private String smainId;

}

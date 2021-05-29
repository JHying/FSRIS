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
@Table(name = "FOODFAIL", schema = "dbo")
public class FoodFail implements Serializable {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "RANK")
    private Integer rank;

    @Column(name = "FNAME_CH")
    private String foodNameCh;

    @Column(name = "SNAME_CH")
    private String subsNameCh;

    @Column(name = "SNAME")
    private String subsName;

    @Column(name = "FAIL_RATE")
    private Double failRate;

    @Column(name = "FOODID")
    private String foodid;

    @Column(name = "SUBSID")
    private String subsid;

    @Column(name = "SMAIN_ID")
    private String smainId;

}

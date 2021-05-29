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
@Table(name = "TOXSTUDY", schema = "dbo")
public class ToxStudy implements Serializable {

    @Id
    @Column(name = "TSID")
    private Long tsid;

    @Column(name = "SPECIES")
    private String species;

    @Column(name = "TS_SEX")
    private String tsSex;

    @Column(name = "TS_REF")
    private String tsRef;

    @Column(name = "SUBSID")
    private String subsid;

    @Column(name = "TOXID")
    private String toxid;

}

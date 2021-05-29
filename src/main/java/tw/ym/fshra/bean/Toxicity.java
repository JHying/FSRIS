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
@Table(name = "TOXICITY", schema = "dbo")
public class Toxicity implements Serializable {

    @Id
    @Column(name = "TOXID")
    private String toxid;

    @Column(name = "TNAME")
    private String tname;

    @Column(name = "TNAME_CH")
    private String tnameCh;

    @Column(name = "SCORE")
    private int score;

}

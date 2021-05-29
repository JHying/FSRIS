package tw.ym.fshra.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author H-yin on 2020.
 */
@Data
@NoArgsConstructor
public class FoodUnitRsp implements Serializable {

    @JsonProperty("UnitDesc")
    private String unitDesc;

}

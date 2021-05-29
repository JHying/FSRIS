package tw.ym.fshra.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.ym.fshra.config.JsonDateDeseriConfig;

import java.io.Serializable;
import java.util.Date;

/**
 * @author H-yin on 2020.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodFailConc implements Serializable {

    @JsonProperty("Concid")
    private String concid;

    @JsonProperty("DtConc")
    private String dtConc;

    @JsonProperty("DtTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonDeserialize(using = JsonDateDeseriConfig.class)
    private Date dtTime;

//    @JsonProperty("Mdl")
//    private Double mdl;

    @JsonProperty("Mrl")
    private String mrl;

    @JsonProperty("MrlRef")
    private String mrlRef;

    @JsonProperty("ConcRef")
    private String concRef;

    @JsonProperty("Pass")
    private String pass;

}

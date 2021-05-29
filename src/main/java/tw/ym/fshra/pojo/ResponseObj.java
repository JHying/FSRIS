package tw.ym.fshra.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseObj implements Serializable {

	private HttpStatus status; // success 或 error
	private List<String> errors; // message: 如果status是error，可以寫錯誤訊息，success的話，就給空值
	private Object result; //傳送的物件
	private String Msg;
	
}

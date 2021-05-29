package tw.ym.fshra.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * 透過 @Requestbody 直接反序列為 JavaBean 時，JavaBean 屬性的 @DateTimeFormat 會失效<br>
 * 此時需透過 JsonDeserializer 設定<br>
 * 使用方法 -- 於 JavaBean 屬性加上 @JsonDeserialize(using=JsonDateDeseriConfig.class)
 * 
 * @author H-yin
 *
 */
public class JsonDateDeseriConfig extends JsonDeserializer<Date> {

	public static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
	public static final SimpleDateFormat format2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public static final SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	public static final SimpleDateFormat format4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
		Date date = new Date();
		try {
			date = format.parse(jsonParser.getText());
		} catch (Exception e) {
		}
		try {
			date = format2.parse(jsonParser.getText());
		} catch (Exception e) {
		}
		try {
			date = format3.parse(jsonParser.getText());
		} catch (Exception e) {
		}
		try {
			date = format4.parse(jsonParser.getText());
		} catch (Exception e) {
		}
		return date;
	}
}

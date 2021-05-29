package tw.ym.fshra.util;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONtransfer {

	/**
	 * JSON 轉換成PrivateNotiReceiver
	 * 
	 * @param jsonStr  json 字串
	 * @param objectClass 寫入之目標物件
	 * @return 目標物件
	 */
	public static Object jsonToObject(String jsonStr, Class<?> objectClass) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Object nb = mapper.readValue(jsonStr, objectClass);
			return nb;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.error(">> JSON to '" + objectClass.getName() + "' exception: " + e.getMessage());
			return null;
		}
	}

	/**
	 * JSON 轉換成 map
	 * 
	 * @param jsonStr json 字串
	 * @return 目標 map
	 */
	public static Map<String, Object> jsonToMap(String jsonStr) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = mapper.readValue(jsonStr, new TypeReference<Map<String, Object>>() {
			});
			return map;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.error(">> " + jsonStr + " to map exception: " + e.getMessage());
			return new HashMap<String, Object>();
		}
	}

	/**
	 * 物件轉換成 JSON
	 * 
	 * @param obj 物件
	 * @return JSON 字串
	 */
	public static String objToJson(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonstr = mapper.writeValueAsString(obj);
			return jsonstr;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.error(">> " + obj.toString() + " transfer to JSON exception: " + e.getMessage());
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * map 轉換成 JSON
	 * 
	 * @param map map
	 * @return JSON 字串
	 */
	public static String MapToJson(Map<String, Object> map) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonstr = mapper.writeValueAsString(map);
			return jsonstr;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.error(">> " + map.toString() + " to JSON exception: " + e.getMessage());
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * JSONmap 轉 object
	 * 
	 * @param <T>    object
	 * @param map    JSONmap
	 * @param object object
	 * @return object
	 */
	@SuppressWarnings("unchecked")
	public static <T> T MapToObject(Map<String, Object> map, T object) {

		String json = MapToJson(map);
		object = (T) jsonToObject(json, object.getClass());

		return object;
	}
}

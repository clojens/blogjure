package blogjure.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionsUtil {
	
	/**
	 * Input:
	 * [{:entry_id=1, :cnt=2}, {:entry_id=2, :cnt=1}, {:entry_id=3, :cnt=1}]
	 * Output:
	 * {1=2, 2=1, 3=1}
	 * @param listOfMaps
	 * @param keyKey
	 * @param valKey
	 * @return
	 */
	public static Map<Object, Object> toMap(List<Map<?, ?>> listOfMaps, Object keyKey, Object valKey) {
		final Map<Object, Object> result = new HashMap<Object, Object>();
		for (Map<?, ?> each: listOfMaps) {
			result.put(":" + each.get(keyKey), each.get(valKey));
		}
		return result;
	}
	
	public static Object forKey(Object key, Map<?, ?> map) {
		return map.get(":" + key);
	}

}
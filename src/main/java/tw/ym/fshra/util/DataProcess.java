package tw.ym.fshra.util;

import org.apache.commons.collections.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author H-yin on 2020.
 */
public class DataProcess {

    /**
     * StrArray 轉 doubleList
     *
     * @param strArray 字串
     * @return doubleList
     */
    public static List<Double> strToDoubleList(String... strArray) {
        List<Double> dList = new ArrayList<>();
        for (String str : strArray) {
            dList.add(Double.parseDouble(str));
        }
        return dList;
    }

    /**
     * 去除重複元素
     *
     * @param objectList 欲過濾的list
     * @return 目標物件
     */
    public static List<String> getUniqList(List<String> objectList) {
        //去除重複元素
        List<String> uniqList = new ArrayList<>();
        for (String str : objectList) {
            if (Collections.frequency(uniqList, str) < 1) uniqList.add(str);
        }
        return uniqList;
    }

    /**
     * 將List轉成HashMap, 可以自定義Key,Value為整個物件
     *
     * @param list
     * @param keyMapper 取得key的method
     * @return
     */
    public static <K, T> Map<K, T> listToHashMap(List<T> list, Function<T, K> keyMapper) {

        if (CollectionUtils.isEmpty(list)) {
            return new HashMap<>();
        }
        return list.stream().collect(Collectors.toMap(keyMapper, Function.identity()));
    }

    /**
     * 將List轉成HashMap, 可自定義Key,Value
     *
     * @param list
     * @param keyMapper   取得key的method
     * @param valueMapper 取得value的method
     * @return
     */
    public static <K, V, T> Map<K, V> listToHashMap(List<T> list, Function<T, K> keyMapper,
                                                    Function<T, V> valueMapper) {

        if (CollectionUtils.isEmpty(list)) {
            return new HashMap<>();
        }

        return list.stream().collect(Collectors.toMap(keyMapper, valueMapper));
    }

    /**
     * 將List轉成LinkedHashMap, 可自定義Key,Value
     *
     * @param list
     * @param keyMapper   取得key的method
     * @param valueMapper 取得value的method
     * @return
     */
    public static <K, V, T> Map<K, V> listToLinkedHashMap(List<T> list, Function<T, K> keyMapper,
                                                          Function<T, V> valueMapper) {

        if (CollectionUtils.isEmpty(list)) {
            return new LinkedHashMap<>();
        }

        return list.stream().collect(Collectors.toMap(keyMapper, valueMapper, (o1, o2) -> o2, LinkedHashMap::new));
    }

    /**
     * 將List轉成HashMap , Value會依據keyMapper來分群
     *
     * @param list
     * @param keyMapper 取得key的method
     * @return
     */
    public static <K, T> Map<K, List<T>> listToMapGroupByArrayList(List<T> list, Function<T, K> keyMapper) {
        return list.stream().collect(Collectors.groupingBy(keyMapper, Collectors.toList()));
    }

    public static List<String> list2List(String list) {
        String[] paramArr;
        List<String> paramList;
        paramArr = list.trim().split(";");
        paramList = new ArrayList<>();
        for (int j = 0; j < paramArr.length; j++) {
            paramList.add(paramArr[j]);
        }
        return paramList;
    }

}

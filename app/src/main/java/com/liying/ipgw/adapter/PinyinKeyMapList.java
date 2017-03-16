package com.liying.ipgw.adapter;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public abstract class PinyinKeyMapList<T> {
    private HashMap<String, List<T>> map = new HashMap<>();
    private List<String> types = new ArrayList<>();
    private List<T> list = new ArrayList<>();

    public PinyinKeyMapList(List<T> list) {
        this.list = list;
        for (T t : list) {
            String str = getField(t);
            String first = getFirstChar(str);

            if (!types.contains(first)) {
                types.add(first);
            }

            List<T> keyList = map.get(first);
            if (keyList == null) {
                keyList = new ArrayList<T>();
                map.put(first, keyList);
            }
            keyList.add(t);
        }
        Collections.sort(types);
        for (Iterator<Map.Entry<String, List<T>>> it = map.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, List<T>> item = it.next();
            List<T> itList = item.getValue();
            Collections.sort(itList, new Comparator<T>() {
                @Override
                public int compare(T lhs, T rhs) {
                    return getField(lhs).compareTo(getField(rhs));
                }
            });

        }
    }

    public abstract String getField(T t);

    public HashMap<String, List<T>> getMap() {
        return map;
    }

    public PinyinKeyMapList setMap(HashMap<String, List<T>> map) {
        this.map = map;
        return this;
    }

    public List<String> getTypes() {
        return types;
    }

    public PinyinKeyMapList setTypes(List<String> types) {
        this.types = types;
        return this;
    }

    public List<T> getList() {
        return list;
    }

    public PinyinKeyMapList setList(List<T> list) {
        this.list = list;
        return this;
    }

    public List<T> getIndexList(int index) {
        return map.get(types.get(index));
    }

    public static String getFirstChar(String value) {
        value = value.trim();
        char firstChar = value.charAt(0);
        String first = null;
        String[] print = PinyinHelper.toHanyuPinyinStringArray(firstChar);

        if (print == null) {
            if ((firstChar >= 97 && firstChar <= 122)) {
                firstChar -= 32;
            }
            if (firstChar >= 65 && firstChar <= 90) {
                first = String.valueOf((char) firstChar);
            } else {
                first = "#";
            }
        } else {
            first = String.valueOf((char) (print[0].charAt(0) - 32));
        }
        if (first == null) {
            first = "#";
        }

        return first;
    }
}

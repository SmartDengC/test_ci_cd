package com.utils.basicConfig;

import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * @ClassName SearchMapByLike
 * @Version 1.0
 * @Author 邓聪
 * @Date 2019/11/29 0:36
 * @Description 模糊查询map中的数据
 * Modification User： 邓聪
 * Modification Date： 2019/11/29
 */
public class SearchMapByLike {
    public  List<Integer> getLikeByMap(Map<String, Integer> map, String keyLike){
        List<Integer> list=new Vector<>();
        for (Map.Entry<String, Integer> entity : map.entrySet()) {
            if(entity.getKey().indexOf(keyLike)>-1){
                list.add((Integer) entity.getValue());
            }
        }
        return list;
    }
}

package classDemo.__20000;import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.map.MapUtil;

import java.util.*;

public class CountArithmetic {

    public Map<String, Object> compute(Map<String,Object> data) {
        List<Map<String,Object>> list = MapUtil.get(data, "list", new TypeReference<List<Map<String,Object>>>() {
        });
        long sum = list.stream().mapToLong(Map::size).sum();
        Map<String, Object> result = MapUtil.newHashMap();
        // 计算总数
        result.put("res", sum);
        return result;
    }

//    public static void main(String[] args) {
//        com.zt.dev.algorithm.controller.CountArithmetic dataCollectArithmetic = new com.zt.dev.algorithm.controller.CountArithmetic();
//        List<Map<String,Object>> objects = new ArrayList<>();
//        objects.add(MapUtil.<String,Object>builder().put("a", 1).put("b", 2).put("c", 3).build());
//        objects.add(MapUtil.<String,Object>builder().put("a", 3).put("b", 4).build());
//        Map<String, Object> map = MapUtil.<String,Object>builder().put("list", objects).build();
//        Map<String, Object> compute = dataCollectArithmetic.compute(map);
//        System.out.println(compute);
//    }

}

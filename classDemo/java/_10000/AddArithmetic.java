package classDemo.__10000;import cn.hutool.core.map.MapUtil;
import java.util.Map;

interface TestInterface {
    Integer compute(Integer a, Integer b);
}

public class AddArithmetic {
    static {
        System.out.println("加载Class");
    }

    public Integer compute(Map<String, Object> map) {
        TestInterface testInterface = new TestInterface() {
            {
                System.out.println("加载Interface");
            }

            @Override
            public Integer compute(Integer a, Integer b) {
                return a + b;
            }
        };
        return testInterface.compute((Integer) map.get("a"), (Integer) map.get("b"));
    }


    public static void main(String[] args) {
        Integer compute = new AddArithmetic().compute(MapUtil.<String, Object>builder().put("a", 1).put("b", 2).build());
        System.out.println("compute = " + compute);
    }
}
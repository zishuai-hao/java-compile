import cn.hutool.core.map.MapUtil;
import com.hzs.JavaCompileUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class TestJavaCompile {
    public static final String SRC = System.getProperty("user.dir");


    @Test
    public void dynamicCompile() {

        Object o = JavaCompileUtil.execJava(SRC + "/classDemo/java/_10000/AddArithmetic.java",
                "compute",
                MapUtil.<String, Object>builder("a", 1).put("b", 2).build());
        System.out.println("AddArithmetic compute result = " + o);


        List<Map<String, Object>> objects = new ArrayList<>();
        objects.add(MapUtil.<String, Object>builder().put("a", 1).put("b", 2).put("c", 3).build());
        objects.add(MapUtil.<String, Object>builder().put("a", 3).put("b", 4).build());
        o = JavaCompileUtil.execJava(SRC + "/classDemo/java/_20000/CountArithmetic.java",
                "compute",
                MapUtil.<String, Object>builder().put("list", objects).build());
        System.out.println("CountArithmetic compute result = " + o);
    }

}

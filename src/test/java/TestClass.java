import com.koko.blog.entity.BlogTagEntity;
import com.koko.blog.utils.Constant;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

public class TestClass {
    @Test
    public void test() throws IOException {
        //当前类的绝对路径
            System.out.println(TestClass.class.getResource("/").getFile());
    }
}

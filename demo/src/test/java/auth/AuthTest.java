package auth;

import cn.eova.common.utils.util.AntPathMatcher;
import org.junit.Test;

/**
 *
 *
 * @author Jieven
 */
public class AuthTest {

    @Test
    public void uri() {
        AntPathMatcher pm = new AntPathMatcher();
        boolean match = pm.match("/excel/export/meta_product", "/excel/export/meta_product?type=csv&biz=meta_product");
        System.out.println(match);
    }
}

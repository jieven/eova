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
        boolean match = pm.match("/api/table/query/*", "/api/table/query/meta_product?page=1&limit=15&sort=&biz=meta_product");
        System.out.println(match);
    }
}

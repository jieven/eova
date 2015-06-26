import com.jfinal.core.JFinal;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jack.Zhou on 2015/6/17.
 */
public class EovaMainTest {

    @Test
    public void runWeb(){
        JFinal.start("src/main/webapp", 80, "/", 5);
    }
}
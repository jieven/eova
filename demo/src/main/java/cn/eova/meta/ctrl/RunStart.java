package cn.eova.meta.ctrl;

import cn.eova.service.ImportBiz;

/**
 *
 *
 * @author Jieven
 */
public class RunStart {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            ImportBiz ims = new ImportBiz();
//            ims.start("sys_hotel", "xxx");
        }
    }
}

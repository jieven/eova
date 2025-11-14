/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package sec;

import cn.eova.common.utils.EncryptUtil;

/**
 * @author Jieven
 */
public class InitPwd {

    // 生成默认密码的测试用例
    public static void main(String[] args) {
        System.out.println(EncryptUtil.getSM32("000000"));

    }
}

/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.ext.jfinal.directive;

import java.io.IOException;

import com.jfinal.kit.JsonKit;
import com.jfinal.template.Directive;
import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;

/**
 * 将目标对象转为JSON字符串
 * #json(xxxx)
 *
 * @author Jieven
 */
public class JsonDirective extends Directive {

    @Override
    public void exec(Env env, Scope scope, Writer writer) {
        Object value = exprList.eval(scope);
        if (value == null) {
            throw new IllegalArgumentException("Data object not found in scope.");
        }

        try {
            // toString 自动转了JSON
            writer.write(JsonKit.toJson(value));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
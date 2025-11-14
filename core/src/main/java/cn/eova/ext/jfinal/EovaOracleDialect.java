/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.ext.jfinal;

import com.jfinal.plugin.activerecord.dialect.OracleDialect;

/**
 * 拓展Oracle方言:个性化识别Number类型和Boolean类型
 *
 * @author Jieven
 *
 */
public class EovaOracleDialect extends OracleDialect {

    public EovaOracleDialect() {
        this.modelBuilder = OracleModelBuilder.me;
        this.recordBuilder = OracleRecordBuilder.me;
    }

}
package com.github.ibpm.common.param.core.arg.ext;

import com.github.ibpm.common.param.CommonParam;
import lombok.ToString;

@ToString
public class SqlArgParam implements CommonParam {

    protected String dbName;

    protected String sql;

    @Override
    public String validate() {
        if (sql == null || !sql.toLowerCase().startsWith("select ")) {
            return "7020";
        }
        return null;
    }

    public String getDbName() {
        return dbName;
    }

    public SqlArgParam setDbName(String dbName) {
        this.dbName = dbName;
        return this;
    }

    public String getSql() {
        return sql;
    }

    public SqlArgParam setSql(String sql) {
        this.sql = sql;
        return this;
    }
}

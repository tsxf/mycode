package com.mycode.framework.tx;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 蛮小江
 * 2018/5/11 14:39
 */
public class TransactionManager {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void begin() throws SQLException {
        getConnection().setAutoCommit(false);
    }

    public void rollback() throws SQLException {
        getConnection().rollback();
    }
}

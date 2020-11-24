package com.bambooJohn.transfer.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Description:
 * @Author: liangzhen
 * @Email: 1033855573@qq.com
 * @Date: 2020-11-24 8:56
 */
public class JdbcUtils {

    //c3p0数据库连接池对象属性
    private static final ComboPooledDataSource ds = new ComboPooledDataSource();

    private static ThreadLocal<Connection> tl = new ThreadLocal<>();

    //获取连接
    /*
        原本：直接从连接池中获取连接
        现在：
            1、直接获取当前线程绑定的连接对象
            2、如果连接对象是空的
                2.1 再去连接池中获取连接
                2.2 将此连接对象跟当前线程进行绑定
     */
    public static Connection getConnection() throws SQLException {

        Connection conn = tl.get();
        if(conn == null){
            conn = ds.getConnection();
            tl.set(conn);
        }
        return conn;
    }

    public static void release(AutoCloseable... ios){
        for (AutoCloseable io : ios) {
            if(io != null){
                try {
                    io.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void commitAndClose(Connection conn){
        try{
            if(conn != null){
                //提交事务
                conn.commit();
                //解绑当前线程绑定的连接对象
                tl.remove();

                //释放连接
                conn.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void rollbackAndClose(Connection conn){
        try {
            if(conn != null){
                //回滚事务
                conn.rollback();
                //解绑当前线程绑定的连接对象
                tl.remove();
                //释放连接
                conn.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}

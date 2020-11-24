package com.bambooJohn.transfer.dao;

import com.bambooJohn.transfer.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Description:
 *
 * 常规方案：
 *      1、方法添加一个参数 conn
 *      2、不能从连接池中获取连接，直接使用参数conn
 *      3、注意：dao层不能释放连接
 *
 * @Author: liangzhen
 * @Email: 1033855573@qq.com
 * @Date: 2020-11-24 8:55
 */
public class AccountDao {

    //转出
    public void out(String outUser,int money) throws SQLException {
        String sql = "update account set money = money - ? where name = ?";

        Connection conn = JdbcUtils.getConnection();
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1,money);
        pstm.setString(2,outUser);
        pstm.executeUpdate();
    //    JdbcUtils.release(pstm,conn);
    }

    //转入
    public void in(String inUser,int money) throws SQLException {
        String sql = "update account set money = mopney + ? where name = ?";

        Connection conn = JdbcUtils.getConnection();
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1,money);
        pstm.setString(2,inUser);
        pstm.executeUpdate();

    //    JdbcUtils.release(pstm,conn);
    }

}

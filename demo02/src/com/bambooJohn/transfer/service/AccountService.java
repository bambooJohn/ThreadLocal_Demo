package com.bambooJohn.transfer.service;

import com.bambooJohn.transfer.dao.AccountDao;
import com.bambooJohn.transfer.utils.JdbcUtils;

import java.sql.Connection;


/**
 * @Description:
 * 事务的使用注意事项：
 *  1、service层和dao层的连接对象保持一致
 *  2、每个线程的connection对象必须前后一致，线程隔离
 *
 * 常规解决方案：
 *  1、传参：将service层的connection对象直接传送到dao层
 *  2、加锁
 *
 * @Author: liangzhen
 * @Email: 1033855573@qq.com
 * @Date: 2020-11-24 8:55
 */
public class AccountService {

    public boolean transfer(String outUser,String inUser,int money){
        AccountDao ad = new AccountDao();
        Connection conn = null;
        try {
         //   synchronized (AccountService.class){
                //开启事务
                conn = JdbcUtils.getConnection();
                conn.setAutoCommit(false);
                //转出
                ad.out(outUser,money);
                int i = 1 / 0;
                //转入
                ad.in(inUser,money);

                JdbcUtils.commitAndClose(conn);
        //    }

        }catch (Exception e){
            e.printStackTrace();
            //2.或者失败回滚

            JdbcUtils.rollbackAndClose(conn);
            return false;
        }

        return true;
    }

}

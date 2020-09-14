package com.miaoqi.tx.demo2;

/**
 * 转账的Dao接口
 * @author miaoqi
 *
 */
public interface AccountDao {
    public void outMoney(String from, Double money);

    public void inMoney(String to, Double money);
}
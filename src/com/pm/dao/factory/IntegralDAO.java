package com.pm.dao.factory;

import com.pm.dao.datasource.Integral;
import com.pm.dao.datasource.Point;
import com.pm.dao.datasource.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/***
 * 包含用户表的数据库操作语句
 */
public class IntegralDAO {
    private Session session;

    public IntegralDAO(Session session) {
        this.session = session;
    }


    public Integral queryIDByUserName(int  userId) {
        Query query = session.createQuery("from Integral where userId = ?1");
        query.setParameter(1, userId);
        return (Integral) query.uniqueResult();
    }




    //插入积分
    public void insertIntegral(Integral b) {
        session.save(b);
    }

    //管理员修改用户密码
    public void editpwdUser(int id, int integral) {
        Query query = session.createQuery("update Integral set integral=?1 where userId=?2");
        query.setParameter(1, integral);
        query.setParameter(2, id);
        query.executeUpdate();
    }


}

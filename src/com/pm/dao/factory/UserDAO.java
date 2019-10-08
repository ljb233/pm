package com.pm.dao.factory;

import com.pm.dao.datasource.User;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionDelegatorBaseImpl;
import org.hibernate.query.Query;

import java.util.List;

/***
 * 包含用户表的数据库操作语句
 * @Auther: linyang
 */
public class UserDAO {
    private Session session;

    public UserDAO(Session session) {
        this.session = session;
    }



    /**
     * @param userName
     * @return User
     * @TODO:通过用户名查询该用户
     */
    public User queryIDByUserName(String userName) {
        Query query = session.createQuery("from User where userName = ?1");
        query.setParameter(1, userName);
        return (User) query.uniqueResult();
    }

    /**
     * @return AllUsers
     * @TODO:获取所有用户
     */
    public List<User> getAllUsers() {
        Query<User> query = session.createQuery("from User", User.class);
        return query.getResultList();
    }

    /**
     * @param b
     * @TODO:增加用户
     */
    public void insertUser(User b) {
        session.save(b);
    }

    /**
     * @param name
     * @param pwd
     * @return user
     * @TODO:登录
     */
    public User userLogin(String name, String pwd) {
        Query query = session.createQuery("from User where userName= ?1  and userPwd= ?2");
        query.setParameter(1, name);
        query.setParameter(2, pwd);
        User user = (User) query.uniqueResult();
        return user;
    }

    /**
     * @param id
     * @TODO:通过id冻结用户
     */
    public void frzzeeUser(int id) {
        Query query = session.createQuery("update User set isFreeze = 1 where id=?1");
        query.setParameter(1, id);
        query.executeUpdate();
    }

    /**
     * @param id
     * @TODO:通过id解冻用户
     */
    public void stopfrzzeeUser(int id) {
        Query query = session.createQuery("update User set isFreeze = 0 where id=?1");
        query.setParameter(1, id);
        query.executeUpdate();
    }

    /**
     * @param id
     * @param pwd
     * @TODO:重置用户密码
     */
    public void editpwdUser(int id, String pwd) {
        Query query = session.createQuery("update User set userPwd=?1 where id=?2");
        query.setParameter(1, pwd);
        query.setParameter(2, id);
        query.executeUpdate();
    }

}

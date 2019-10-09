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
     * @DO:通过用户名查询该用户
     * @param userName
     * @return User
     */
    public User queryIDByUserName(String userName) {
        Query query = session.createQuery("from User where userName = ?1");
        query.setParameter(1, userName);
        return (User) query.uniqueResult();
    }

    /**
     * @DO:获取所有用户
     * @return AllUsers
     */
    public List<User> getAllUsers() {
        Query<User> query = session.createQuery("from User", User.class);
        return query.getResultList();
    }

    /**
     * @DO:增加用户
     * @param b
     */
    public void insertUser(User b) {
        session.save(b);
    }

    /**
     * @TODO:登录
     * @param name
     * @param pwd
     * @return user
     */
    public User userLogin(String name, String pwd) {
        Query query = session.createQuery("from User where userName= ?1  and userPwd= ?2");
        query.setParameter(1, name);
        query.setParameter(2, pwd);
        User user = (User) query.uniqueResult();
        return user;
    }

    /**
     * @DO:通过id冻结用户
     * @param id
     */
    public void frzzeeUser(int id) {
        Query query = session.createQuery("update User set isFreeze = 1 where id=?1");
        query.setParameter(1, id);
        query.executeUpdate();
    }

    /**
     * @DO:通过id解冻用户
     * @param id
     */
    public void stopfrzzeeUser(int id) {
        Query query = session.createQuery("update User set isFreeze = 0 where id=?1");
        query.setParameter(1, id);
        query.executeUpdate();
    }

    /**
     * @DO:重置用户密码
     * @param id
     * @param pwd
     */
    public void editpwdUser(int id, String pwd) {
        Query query = session.createQuery("update User set userPwd=?1 where id=?2");
        query.setParameter(1, pwd);
        query.setParameter(2, id);
        query.executeUpdate();
    }
}

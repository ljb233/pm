package com.pm.process;

import com.pm.dao.datasource.User;
import com.pm.dao.factory.ManagerDAO;
import com.pm.dao.factory.PointDao;
import com.pm.dao.factory.UserDAO;
import com.pm.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import java.util.List;
/**
 * 用户处理过程
 *@Auther: linyang
 */
public class UserProcess {
    private Session session;
    private UserDAO userDAO;
    private ManagerDAO managerDAO;
    private PointDao pointDao;

    public UserProcess() {
        session = HibernateUtils.getSession();
        userDAO = new UserDAO(session);
        pointDao = new PointDao(session);
        managerDAO = new ManagerDAO(session);
    }

    /**
     * @TODO：获取所有用户
     * @return AllUsers
     */
    public List<User> getAllUsers() {
        try {
            return userDAO.getAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @TODO:登录
     * @param name
     * @param pwd
     * @return user
     */
    public User userLogin(String name, String pwd) {
        try {
            User user = userDAO.userLogin(name, pwd);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @TODO:冻结用户
     * @param id
     * @return boolean
     */
    public boolean frzzeeUserByID(int id) {
        Transaction transaction = session.beginTransaction();
        try {
            userDAO.frzzeeUser(id);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * @TODO:解冻用户
     * @param id
     * @return boolean
     */
    public boolean stopfrzzeeUserByID(int id) {
        Transaction transaction = session.beginTransaction();
        try {
            userDAO.stopfrzzeeUser(id);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * @TODO:重置用户密码
     * @param id
     * @param pwd
     * @return boolean
     */
    public boolean editpwdUser(int id, String pwd) {
        Transaction transaction = session.beginTransaction();
        try {
            userDAO.editpwdUser(id, pwd);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * @TODO:增加用户
     * @param user
     * @return boolean
     */
    public boolean insertUser(User user) {
        Transaction transaction = session.beginTransaction();
        User user2 = userDAO.queryIDByUserName(user.getUserName());
        if (user2 != null) {
            JOptionPane.showMessageDialog(null, "用户已存在");
        } else {
            userDAO.insertUser(user);
            transaction.commit();
            return true;
        }
        return false;
    }

    /**
     * @TODO:搜索用户
     * @param user
     * @return user2
     */
    public User SearchUser(User user) {
        User user2 = userDAO.queryIDByUserName(user.getUserName());
        return user2;
    }
}

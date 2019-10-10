package com.pm.process;

import com.pm.dao.datasource.Integral;
import com.pm.dao.datasource.User;
import com.pm.dao.factory.IntegralDAO;
import com.pm.dao.factory.ManagerDAO;
import com.pm.dao.factory.PointDao;
import com.pm.dao.factory.UserDAO;
import com.pm.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import java.util.List;

public class IntegralProcess {
    private Session session;
    private UserDAO userDAO;
    private IntegralDAO integralDAO;

    public IntegralProcess() {
        session = HibernateUtils.getSession();
        userDAO = new UserDAO(session);
        integralDAO = new IntegralDAO(session);
    }

    public int getIntegralByUserId(String username) {
        User user = userDAO.queryIDByUserName(username);
      Integral integral =  integralDAO.queryIDByUserName(user.getId());
      if(integral == null){
          return  0;
      }
        return integral.getIntegral();
    }


    /***
     * 签到增加积分
     */
    public void addIntegralByUsername(String username) {
        User user = userDAO.queryIDByUserName(username);
        Integral integral =  integralDAO.queryIDByUserName(user.getId());
        Transaction transaction = session.beginTransaction();
        if(integral !=null){
            integralDAO.editpwdUser(user.getId(),integral.getIntegral()+20);
            transaction.commit();
        }else{
            integral = new Integral();
            integral.setIntegral(20);
            integral.setUserId(user.getId());
            integralDAO.insertIntegral(integral);
            transaction.commit();
        }
        /***
         * 签到增加积分
         */

    }
    public void update(String username,int total) {
        User user = userDAO.queryIDByUserName(username);
        Integral integral =  integralDAO.queryIDByUserName(user.getId());
        Transaction transaction = session.beginTransaction();
        if(integral !=null){
            integralDAO.editpwdUser(user.getId(),total);
            transaction.commit();
        }

    }



}

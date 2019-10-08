package com.pm.process;

import com.pm.dao.factory.PointDao;
import com.pm.dao.factory.UserDAO;
import com.pm.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * 积分处理过程
 *@Auther: linyang
 */
public class PointProcess {
    private Session session;
    private PointDao pointDao;
    private UserDAO userDAO;

    public PointProcess() {
        session = HibernateUtils.getSession();
        pointDao = new PointDao(session);
        userDAO = new UserDAO(session);
    }

    /**
     * @TODO:积分增加处理
     * @param addValue
     * @param id
     * @return boolean
     */
    public boolean addPoints(int addValue, int id) {
        Transaction transaction = session.beginTransaction();
        try {
            int orgValue = 0;
            orgValue = pointDao.getPointByID(id).getPointValue();
            int updatetotal = addValue + orgValue;
            pointDao.addPoints(updatetotal, id);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * @TODO:通过用户id获取积分值
     * @param id
     * @return PointValue
     */
    public int getallpoint(int id) {
        try {
            return pointDao.getPointByID(id).getPointValue();
        } catch (Exception e) {
            e.printStackTrace();
            return Integer.parseInt(null);
        }
    }
}

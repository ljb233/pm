package com.pm.process;
import com.pm.dao.factory.PointDao;
import com.pm.dao.factory.UserDAO;
import com.pm.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PointProcess {
    private Session session;
    private PointDao pointDao;
    private UserDAO userDAO;

    public PointProcess() {
        session = HibernateUtils.getSession();
        pointDao = new PointDao(session);
        userDAO = new UserDAO(session);
    }

    public boolean maddpoints(int addValue, int id) {
        Transaction transaction = session.beginTransaction();
        try {
            int orgValue = 0;
            orgValue = pointDao.getPointByID(id).getPointValue();
            int updatetotal = addValue + orgValue;
            pointDao.maddpoints(updatetotal,id);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            System.out.println(e.getMessage());
            return false;
        }
    }

    public int getallpoint(int id){
        try {
            return pointDao.getPointByID(id).getPointValue();
        }catch (Exception e){
            e.printStackTrace();
            return Integer.parseInt(null);
        }
    }
}

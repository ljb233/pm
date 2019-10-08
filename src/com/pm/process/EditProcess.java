package com.pm.process;

import com.pm.dao.factory.EditDAO;
import com.pm.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class EditProcess {
    private Session session;
    private EditDAO editDAO;
    public EditProcess() {
        session = HibernateUtils.getSession();
        editDAO = new EditDAO(session);
    }

    /**
     * @TODO:重置用户密码
     * @param id
     * @param pwd
     * @return boolean
     */
    public boolean editpwd(int id, String pwd) {
        Transaction transaction = session.beginTransaction();
        try {
            editDAO.editpwd(id, pwd);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            System.out.println(e.getMessage());
            return false;
        }
    }
}

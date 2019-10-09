package com.pm.dao.factory;

import org.hibernate.Session;
import org.hibernate.query.Query;

public class EditDAO {
    private Session session;

    public EditDAO(Session session) {
        this.session = session;
    }
    /**
     * @param id
     * @param pwd
     * @TODO:修改用户密码
     */
    public void editpwd(int id, String pwd) {
        Query query = session.createQuery("update User set userPwd=?1 where id=?2");
        query.setParameter(1, pwd);
        query.setParameter(2, id);
        query.executeUpdate();
    }

    public void getId(){

    }
}

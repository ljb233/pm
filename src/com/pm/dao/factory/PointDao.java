package com.pm.dao.factory;

import com.pm.dao.datasource.Point;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 * 操作数据库中积分表
 *@Auther: linyang
 */
public class PointDao {
    private Session session;

    public PointDao(Session session) {
        this.session = session;
    }

    /**
     * @DO:通过用户id获得该用户的积分信息
     * @param id
     * @return Point
     */
    public Point getPointByID(int id) {
        Query query = session.createQuery("from Point where userId = ?1");
        query.setParameter(1, id);
        return (Point) query.uniqueResult();
    }

    /**
     * @DO:将增加后的积分插入到id对应的用户
     * @param updatetotal
     * @param id
     */
    public void addPoints(int updatetotal, int id) {
        Query query = session.createQuery("update Point set pointValue =?1 where userId=?2");
        query.setParameter(1, updatetotal);
        query.setParameter(2, id);
        query.executeUpdate();
    }

    /**
     * @DO:增加用户的同时，存入积分信息
     * @param b
     */
    public void insertUsertopoint(Point b) {
        session.save(b);
    }
}

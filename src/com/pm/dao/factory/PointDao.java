package com.pm.dao.factory;

import com.pm.dao.datasource.Point;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

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
     * 通过用户id获得该用户的积分信息
     * @param id
     * @return Point
     */
    public Point getPointByID(int id) {
        Query query = session.createQuery("from Point where userId = ?1");
        query.setParameter(1, id);
        return (Point) query.uniqueResult();
    }

    /**
     * 将增加后的积分插入到id对应的用户
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
     * 增加用户的同时，存入积分信息
     * @param b
     */
    public void insertUsertopoint(Point b) {
        session.save(b);
    }

    /**
     * 获取积分表
     * @return list
     */
    public List<Point> getAllPoints() {
        Query<Point> query = session.createQuery("from Point ", Point.class);
        return query.getResultList();
    }
}

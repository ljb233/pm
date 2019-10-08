package com.pm.process;

import com.pm.dao.datasource.Goods;
import com.pm.dao.factory.GoodsDAO;
import com.pm.dao.factory.ManagerDAO;
import com.pm.util.HibernateUtils;
import com.pm.util.Tools;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Hibernate;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class GoodsProcess {

    private Session session;
    private GoodsDAO goodsDAO;

    public GoodsProcess() {
        //获取session
        session = HibernateUtils.getSession();
        //初始化
        goodsDAO = new GoodsDAO(session);
    }

    /**
     * 获取所有的商品信息
     * @return 返回商品列表
     */
    public List<Goods> getGoods(){
        try {
            return goodsDAO.getAllGoods();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过商品ID查询
     * @param id 商品ID
     * @return 返回商品信息
     */
    public Goods getGoods(int id) {
        try {
            return goodsDAO.getGoodsByID(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过商品ID删除商品
     * @param id 商品ID
     * @return 返回商品信息
     */
    public boolean deleGoods(int id) {
        Transaction transaction = session.beginTransaction();
        try {
            goodsDAO.deleGoodsByID(id);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

    /**
     * 保存所有商品信息
     */
    public boolean saveGoods(Goods goods) {
        Transaction transaction = session.beginTransaction();
        try {
            goodsDAO.insertGoods(goods);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

    /**
     * 保存所有商品信息,并添加图片信息
     */
    public boolean saveGoods(Goods goods, byte[] imageByteArray) {
        Transaction transaction = session.beginTransaction();
        try {
            //转换图片信息为BOLB类型
            goods.setPicStream(Hibernate.getLobCreator(session).createBlob(imageByteArray));
            goodsDAO.insertGoods(goods);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * 更新所有商品信息
     */
    public boolean updateGoods(Goods goods) {
        Transaction transaction = session.beginTransaction();
        try {
            goodsDAO.updateGoods(goods);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

    /**
     * 关闭Session
     */
    void close(){
        session.close();
    }
}

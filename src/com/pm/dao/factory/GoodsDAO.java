package com.pm.dao.factory;

import com.pm.dao.datasource.Goods;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class GoodsDAO {
    private Session session;

    /**
     * 初始化
     * @param session session
     */
    public GoodsDAO(Session session){
        this.session = session;
    }

    /**
     * 获取所有商品信息
     * @return  返回所有商品信息
     */
    public List<Goods> getAllGoods(){
        Query<Goods> query = session.createQuery("from Goods",Goods.class);
        return query.getResultList();
    }

    /**
     * 获取指定ID商品信息
     * @param id id
     * @return 商品信息
     */
    public Goods getGoodsByID(int id){
        Query query = session.createQuery("from Goods where id = ?1");
        query.setParameter(1,id);
        return (Goods) query.getResultList();
    }

    /**
     * 指定ID删除商品
     * @param id id
     */
    public void deleGoodsByID(int id){
        Query query = session.createQuery("update Goods set isDele = 1 where id = ?1");
        query.setParameter(1,id);
        query.executeUpdate();
    }

    /**
     * 保存商品信息
     * @param goods 商品
     */
    public void insertGoods(Goods goods){
        session.save(goods);
    }

    /**
     * 更新商品信息
     * @param goods 商品
     */
    public void updateGoods(Goods goods) {
        Query query = session.createQuery(
                "update Goods set goodsId = ?1, " +
                        "goodsName = ?2, " +
                        "goodsPrice = ?3 where id = ?4");
        query.setParameter(1, goods.getGoodsId());
        query.setParameter(2, goods.getGoodsName());
        query.setParameter(3, goods.getGoodsPrice());
        query.setParameter(4, goods.getId());
        query.executeUpdate();
    }
}

package com.pm.dao.factory;

import com.pm.dao.datasource.VOrderinf;
import com.pm.dao.datasource.VOrderinfId;
import com.pm.util.HibernateUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderInfDAOTest {

    @Test
    void getOrderByUId() {
        OrderInfDAO od = new OrderInfDAO(HibernateUtils.getSession());
        VOrderinfId list = od.getOrderByOId1(1);
        //for (VOrderinf vo : list){

            System.out.println(list.getGoodsName());
        //}
    }
}
package com.pm.process;

import com.pm.dao.datasource.VOrderinfId;
import com.pm.dao.factory.OrderInfDAO;
import com.pm.util.HibernateUtils;
import org.hibernate.Session;


import java.util.List;

public class OrderInfProcess {
    private Session session;
    private OrderInfDAO orderInfDAO;

    public OrderInfProcess() {
        session = HibernateUtils.getSession();
        orderInfDAO = new OrderInfDAO(session);
    }

    public List<VOrderinfId> getAllOrderInf() {
        try {
            return orderInfDAO.getAllOrderInf();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public VOrderinfId getOrderInfByOId(int OId) {
        return orderInfDAO.getOrderByOId(OId);
    }

    public List<VOrderinfId> getUserInf() {
        try {
            return orderInfDAO.getAllOrderInfGroupByUserName();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public VOrderinfId getOrderInfByOId1(int OId) {
        return orderInfDAO.getOrderByOId1(OId);
    }

    public List<VOrderinfId> getOrderInfByUId(int uId) {
        List<VOrderinfId> list = orderInfDAO.getOrderByUId(uId);
        int j = 0;
        for (int i = 0; i < list.size(); i++) {

            if (list.get(j).getOsType().equals("已删除")) {
                list.remove(j);
                j--;
            }
            j++;
            //这里使用list.remove(O)会抛异常有时间再处理
        }
        return list;

    }

    public List<VOrderinfId> getAllOrderInf1() {
        try {
            return orderInfDAO.getAllOrderInf1();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //搜索功能
    public VOrderinfId searchOrder(VOrderinfId vOrderinfId){
        VOrderinfId vOrderinfId1 = orderInfDAO.getOrderByOId1(vOrderinfId.getOsId());
        return  vOrderinfId1;
    }
}

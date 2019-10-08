package com.pm.ui.user;

import com.pm.dao.datasource.*;
import com.pm.dao.factory.UserDAO;
import com.pm.process.GoodsProcess;
import com.pm.process.OrderInfProcess;
import com.pm.process.OrderProcess;
import com.pm.process.UserProcess;
import com.pm.ui.manager.PointsRecharge;
import org.hibernate.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.transaction.SystemException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

public class UOrder extends JFrame {
    private static final long serialVersionUID = 9527L;
    private JPanel jplPane1;
    private JPanel jplPane2;
    private JTable table;
    private JButton btnDetail;
    private JButton btnCancel;
    private JButton btnDelete;
    private Object[][] data;
    private User user;

    public UOrder(User user) {
        this.user = user;
        jplPane1 = new JPanel();
        jplPane2 = new JPanel();
        btnDetail = new JButton("详细信息");
        btnCancel = new JButton("取消订单");
        btnDelete = new JButton("删除订单");

        //设置表格
        String[] columnNames = {"ID", "订单号", "商品名", "状态"};
        table = new JTable(data, columnNames);
        table.setModel(new DefaultTableModel(data, columnNames));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setSize(300, 600);
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);

        //添加组件.
        jplPane1.add(Box.createGlue()); // 挤占用户管理按钮和窗口左侧空间
        jplPane1.add(btnDetail);
        jplPane1.add(Box.createHorizontalStrut(20));// 按钮之间的水平距离
        jplPane1.add(btnCancel);
        jplPane1.add(Box.createHorizontalStrut(20));
        jplPane1.add(btnDelete);
        jplPane1.add(Box.createGlue());
        jplPane2.add(scrollPane);

        add(jplPane1);
        add(jplPane2);
        setTitle("我的订单");// 标题
        setSize(600, 600);// 窗口大小
        setLayout(new GridLayout(3, 1));
        setResizable(false);
        setLocationRelativeTo(null);// 窗口居中
        this.setVisible(true);
    }

    public void go() {
        showData();
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UOrder.this.dispose();
                try {
                    String id = table.getValueAt(table.getSelectedRow(), 0).toString();
                    int ID = Integer.valueOf(id);
                    OrderProcess op = new OrderProcess();
                    if (op.cancelOrder(ID) == true) {

                        JOptionPane.showMessageDialog(null, "操作成功");
                    } else {


                        JOptionPane.showMessageDialog(null, "操作失败");
                    }
                    ;

                    showData();
                } catch (Exception el) {
                    JOptionPane.showMessageDialog(null,
                            "请正确操作",
                            "警告",
                            JOptionPane.ERROR_MESSAGE);
                }


            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UOrder.this.dispose();
                try {
                    String id = table.getValueAt(table.getSelectedRow(), 0).toString();
                    int ID = Integer.valueOf(id);
                    OrderProcess op = new OrderProcess();
                    if (op.deleteOrder(ID) == true) {
                        showData();
                        JOptionPane.showMessageDialog(null, "操作成功该订单已删除");
                    } else {
                        JOptionPane.showMessageDialog(null, "操作失败该订单不可删除");
                    }
                } catch (Exception el) {
                    JOptionPane.showMessageDialog(null,
                            "请正确操作",
                            "警告",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        btnDetail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UOrder.this.dispose();
                try {
                    String id = table.getValueAt(table.getSelectedRow(), 0).toString();
                    int ID = Integer.valueOf(id);
                    OrderInfProcess op = new OrderInfProcess();
                    ;
                    UOdetail UO = new UOdetail(op.getOrderInfByOId1(ID));
                    UO.go();
                } catch (Exception el) {
                    JOptionPane.showMessageDialog(null,
                            "请正确操作",
                            "警告",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        });
    }
    /***
     *显示所有用户信息

     */
    public void showData() {
        DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
        defaultTableModel.setRowCount(0);
        OrderInfProcess op = new OrderInfProcess();
        List<VOrderinfId> OrderList = op.getOrderInfByUId(user.getId());
        for (VOrderinfId order : OrderList) {
            Vector v = new Vector();
            v.add(order.getoId());
            v.add(order.getOrderId());
            v.add(order.getGoodsName());
            v.add(order.getOsType());
            defaultTableModel.addRow(v);
        }
    }
}

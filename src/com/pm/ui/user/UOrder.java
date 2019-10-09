package com.pm.ui.user;

import com.pm.dao.datasource.*;
import com.pm.process.OrderInfProcess;
import com.pm.process.OrderProcess;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

public class UOrder extends JDialog {
    private static final long serialVersionUID = 9527L;
    private JPanel jplPane1;
    private JPanel jplPane2;
    private JTable table;
    private JButton btnEdit;
    private Object[][] data;
    private User loginUser;

    public UOrder(User user) {

        loginUser = user;
        jplPane1 = new JPanel();
        jplPane2 = new JPanel();
        //设置表格
        String[] columnNames = {"ID", "订单号", "商品名", "状态"};
        table = new JTable(data, columnNames) {
            //禁止编辑单元格
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        table.setModel(new DefaultTableModel(data, columnNames));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setSize(300, 600);
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);

        table.getTableHeader().setReorderingAllowed(false);

        //添加组件.
        jplPane1.add(Box.createGlue()); // 挤占用户管理按钮和窗口左侧空间
        // 按钮之间的水平距离
        btnEdit = new JButton("修改密码");
        jplPane1.add(Box.createHorizontalStrut(20));
        jplPane1.add(btnEdit);
        jplPane1.add(Box.createGlue());
        jplPane2.add(scrollPane);

        add(jplPane1);
        add(jplPane2);

        setTitle("个人主页");// 标题
        setSize(500, 400);// 窗口大小
        setLayout(new GridLayout(3, 1));
        setResizable(false);
        setLocationRelativeTo(null);// 窗口居中
        this.setVisible(true);
    }

    public void go() {
        showData();

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {

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

        List<VOrderinfId> OrderList = op.getOrderInfByUId(loginUser.getId());
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


package com.pm.ui.user;

import com.pm.dao.datasource.*;
import com.pm.process.OrderInfProcess;
import com.pm.process.OrderProcess;
import com.pm.util.Page;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

public class UOrder extends JFrame {
    private static final long serialVersionUID = 9527L;
    private static final int TABLE_HEIGHT = 100;
    private JPanel jplPane1;
    private JPanel jplPane2;
    private JPanel jplPane3;
    private JTable table;
    private JButton btnCancel;
    private JButton btnDelete;
    private JButton firstPageButton;
    private JButton nextPageButton;
    private JButton previousPageButton;
    private JButton lastPageButton;

    private JFrame mainFrame;
    private Object[][] data;
    private User loginUser;

    private int currentPage;
    private final int firstPage = 1;
    private int lastPage;
    private int tableRows;
    private List<VOrderinfId> orderList;

    public UOrder(User user) {

        loginUser = user;
        jplPane1 = new JPanel();
        jplPane2 = new JPanel();
        jplPane3 = new JPanel();
        btnCancel = new JButton("取消订单");
        btnDelete = new JButton("删除订单");
        firstPageButton = new JButton("首页");
        previousPageButton = new JButton("上一页");
        nextPageButton = new JButton("下一页");
        lastPageButton = new JButton("末页");

        //设置表格
        String[] columnNames = {"ID", "订单号", "商品名", "状态"};
        table = new JTable(data, columnNames) {
            //禁止编辑单元格
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        ;
        table.setModel(new DefaultTableModel(data, columnNames));//设置自定义表格模型
        table.setRowHeight(TABLE_HEIGHT);//行高
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setSize(300, 600);
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//行单选
        table.getTableHeader().setReorderingAllowed(false);//不可移动
        //添加组件.
        jplPane1.add(Box.createGlue()); // 挤占用户管理按钮和窗口左侧空间
        // 按钮之间的水平距离
        jplPane1.add(btnCancel);
        jplPane1.add(Box.createHorizontalStrut(20));
        jplPane1.add(btnDelete);
        jplPane1.add(Box.createGlue());

        jplPane2.add(scrollPane);

        jplPane3.add(firstPageButton);
        jplPane3.add(previousPageButton);
        jplPane3.add(nextPageButton);
        jplPane3.add(lastPageButton);

        //加载组件
        mainFrame.add(jplPane1);
        mainFrame.add(jplPane2);
        mainFrame.add(jplPane3);
        mainFrame = new JFrame("我的订单");
        mainFrame.setSize(500, 550);// 窗口大小
        //setLayout(new GridLayout(3, 1));
        mainFrame.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);// 窗口居中
        mainFrame.setVisible(true);
    }

    private int getCurrentPage() {
        return currentPage;
    }

    private void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    private int getFirstPage() {
        return firstPage;
    }

    private int getLastPage() {
        return lastPage;
    }

    public void go() {
        initOrderInfList();

        initPageNumber();
        showData();
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //UOrder.this.dispose();
                try {
                    String id = table.getValueAt(table.getSelectedRow(), 0).toString();
                    int ID = Integer.valueOf(id);
                    OrderProcess op = new OrderProcess();
                    if (op.cancelOrder(ID) == true) {

                        JOptionPane.showMessageDialog(null, "操作成功");
                    } else {


                        JOptionPane.showMessageDialog(null, "操作失败该订单不可取消");
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
                //UOrder.this.dispose();
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

        //首页按钮的监听器，点击后设置首页并刷新table
        firstPageButton.addActionListener(e -> {
            setCurrentPage(firstPage);
            showData();
        });

        //上一页按钮的监听器
        previousPageButton.addActionListener(e -> {
            if(getCurrentPage() > getFirstPage()){
                setCurrentPage(currentPage - 1);
                showData();
            }else{
                setCurrentPage(getFirstPage());
                showData();
            }
        });

        //下一页按钮的监听器
        nextPageButton.addActionListener(e -> {
            if(getCurrentPage() < getLastPage()){
                setCurrentPage(currentPage + 1);
                showData();
            }else{
                setCurrentPage(lastPage);
                showData();
            }
        });

        //尾页按钮d的监听器
        lastPageButton.addActionListener(e -> {
            setCurrentPage(lastPage);
            showData();
        });
    }

    /**
     * 初始化页码
     */
    private void initPageNumber(){
        this.tableRows = 8;
        this.currentPage = firstPage;
        setLastPage();
    }

    /**
     * 计算尾页的页码
     */
    private void setLastPage(){
        if(this.orderList.size() % this.tableRows ==0){
            this.lastPage = this.orderList.size() / this.tableRows;
        }else{
            this.lastPage = this.orderList.size() / this.tableRows + 1;
        }
    }

    /**
     * 初始化订单列表
     */
    private void initOrderInfList(){
        OrderInfProcess orderInfProcess = new OrderInfProcess();
        this.orderList = orderInfProcess.getAllOrderInf();
    }

    /***
     *显示所有用户信息

     */
    public void showData() {
        DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
        defaultTableModel.setRowCount(0);
        OrderInfProcess op = new OrderInfProcess();

        //列表分页
        Page page = new Page<VOrderinfId>(tableRows);
        List<VOrderinfId> list =  page.cutList(currentPage, orderList);

        //List<VOrderinfId> list = op.getOrderInfByUId(loginUser.getId());
        for (VOrderinfId order : list) {
            Vector v = new Vector();
            v.add(order.getoId());
            v.add(order.getOrderId());
            v.add(order.getGoodsName());
            v.add(order.getOsType());
            defaultTableModel.addRow(v);
        }
    }
}


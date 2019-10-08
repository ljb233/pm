package com.pm.ui.manager;

import com.pm.dao.datasource.VOrderinfId;
import com.pm.process.OrderInfProcess;
import com.pm.util.Page;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;
import java.util.Vector;

public class MOrder {

    private JFrame jFrame;
    private JPanel jPanelTable,
            jPanelButton,
            jPanelButtonTop;
    private JTable jTable;
    private JScrollPane scrollPane;
    private JTextField searchText;
    private JButton topPageB,
            lastPageB,
            nextPageB,
            endPageB,
            editB,
            searchB;
    private int currentPage,
            firstPage,
            lastPage,
            tableRows;
    private List<VOrderinfId> orderList;

    public MOrder() {
        //初始化
        searchText = new JTextField(10);
        editB = new JButton("状态更改");
        searchB = new JButton("查询");
        topPageB = new JButton("首页");
        lastPageB = new JButton("上一页");
        nextPageB = new JButton("下一页");
        endPageB = new JButton("尾页");
        jPanelButtonTop = new JPanel();
        jPanelTable = new JPanel();
        jPanelButton = new JPanel();


        //订单表格
        String[] columnNames = {
                "订单ID",
                "下单时间",
                "完成时间",
                "订单状态",
                "购买商品",
                "下单用户"
        };

        jTable = new JTable(null, columnNames) {
            //禁止编辑单元格
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable.setModel(new DefaultTableModel(null,
                columnNames));
        jTable.setFillsViewportHeight(true);
        jTable.setAutoCreateRowSorter(true);
        //排序
        jTable.getTableHeader().setReorderingAllowed(false);
        //不可多选
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane = new JScrollPane(jTable);

        //顶部块
        jPanelButtonTop.add(searchText);
        jPanelButtonTop.add(searchB);

        //分页块
        jPanelButton.add(topPageB);
        jPanelButton.add(lastPageB);
        jPanelButton.add(nextPageB);
        jPanelButton.add(endPageB);
        jPanelButton.add(editB);

        //表格块
        jPanelTable.add(scrollPane);

        jFrame = new JFrame("订单信息");
        jFrame.setSize(500,
                400);
        jFrame.setLayout(new GridLayout(3,
                1));
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);

        jFrame.add(jPanelButtonTop);
        jFrame.add(jPanelTable);
        jFrame.add(jPanelButton);
        jFrame.setVisible(true);


    }

    //当前页
    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    //首页
    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    //尾页
    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public void go() {
        //方法调用
        setOrderList();
        initPageNumber();
        showData();

        //修改按钮功能
        editB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String orderid = jTable.getValueAt(jTable.getSelectedRow(),
                        0).toString();
                if (orderid.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "请选择订单!",
                            "警告",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    int OID = Integer.parseInt(orderid);
                    EditOrder editOrder = new EditOrder();
                    editOrder.go(OID);
                }
            }
        });
        //查询按钮功能
        searchB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        //首页按钮功能
        topPageB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setCurrentPage(firstPage);
                showData();
            }
        });
        //上一页按钮功能
        lastPageB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (getCurrentPage() > getFirstPage()) {
                    setCurrentPage(currentPage - 1);
                    showData();
                } else {
                    setCurrentPage(firstPage);
                    showData();
                }
            }
        });
        //下一页按钮功能
        nextPageB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (getCurrentPage() < getLastPage()) {
                    setCurrentPage(currentPage + 1);
                    showData();
                } else {
                    setCurrentPage(lastPage);
                    showData();
                }
            }
        });
        //尾页按钮功能
        endPageB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setCurrentPage(lastPage);
                showData();
            }
        });


    }

    //初始化分页
    public void initPageNumber() {
        this.firstPage = 1;
        this.tableRows = 5;
        this.currentPage = firstPage;

        if (this.orderList.size() % this.tableRows == 0) {
            this.lastPage = this.orderList.size() / this.tableRows;
        } else {
            this.lastPage = this.orderList.size() / this.tableRows + 1;
        }
    }

    public void setOrderList() {
        OrderInfProcess orderInfProcess = new OrderInfProcess();
        this.orderList = orderInfProcess.getAllOrderInf();
    }


    //显示订单信息
    public void showData() {

        DefaultTableModel defaultTableModel = (DefaultTableModel) jTable.getModel();
        defaultTableModel.setRowCount(0);

        //分页
        Page page = new Page(tableRows);
        List<VOrderinfId> list = page.cutList(currentPage, orderList);

        for (VOrderinfId vOrderinfId : list) {
            Vector v = new Vector();
            v.add(vOrderinfId.getoId());
            v.add(vOrderinfId.getCreateDate());
            v.add(vOrderinfId.getCompDate());
            v.add(vOrderinfId.getOsType());
            v.add(vOrderinfId.getGoodsName());
            v.add(vOrderinfId.getUserName());
            defaultTableModel.addRow(v);
        }

    }
}

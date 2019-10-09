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
import java.util.Collections;
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
            tableRows,
            intOId;
    private List<VOrderinfId> orderList;

    public MOrder() {
        //初始化
        searchText=new JTextField(10);
        editB = new JButton("详情");
        searchB = new JButton("查询");
        topPageB = new JButton("首页");
        lastPageB = new JButton("上一页");
        nextPageB = new JButton("下一页");
        endPageB = new JButton("尾页");
        jPanelButtonTop=new JPanel();
        jPanelTable = new JPanel();
        jPanelButton = new JPanel();


        //订单表格
        String[] columnNames={
                "订单ID",
                "下单时间",
                "完成时间",
                "订单状态",
                "购买商品",
                "下单用户"
        };

        jTable = new JTable(null,columnNames){
            //禁止编辑单元格
            @Override
            public boolean isCellEditable(int row,int column){
                return  false;
            }
        };
        jTable.setModel(new DefaultTableModel(null,
                columnNames));
        jTable.setFillsViewportHeight(true);
        //排序
        jTable.setAutoCreateRowSorter(true);
        //不可移动
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
        //尺寸
        jFrame.setSize(500,
                500);
        //布局
        jFrame.setLayout(new GridLayout(3,
                1));
        //jFrame.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        //窗口不可改变
        jFrame.setResizable(false);
        //居中
        jFrame.setLocationRelativeTo(null);

        //部署
        jFrame.add(jPanelButtonTop);
        jFrame.add(jPanelTable);
        jFrame.add(jPanelButton);
        //显示窗口
        jFrame.setVisible(true);


    }

    /**
     * 获取搜索框值
     */
    public void findOrderId(){
        intOId =Integer.parseInt(searchText.getText());
    }

    /**
     * 获取当前页
     * @return currentPage
     */
    public int getCurrentPage(){
        return currentPage;
    }

    /**
     * 设置当前页
     * @param currentPage
     */
    public void setCurrentPage(int currentPage){
        this.currentPage=currentPage;
    }

    /**
     *获取首页
     * @return firstPage
     */
    public int getFirstPage(){
        return firstPage;
    }

    /**
     * 设置首页
     * @param firstPage
     */
    public void setFirstPage(int firstPage){
        this.firstPage=firstPage;
    }

    /**
     * 获取尾页
     * @return lastPage
     */
    public int getLastPage(){
        return lastPage;
    }

    /**
     * 设置尾页
     * @param lastPage
     */
    public void setLastPage(int lastPage){
        this.lastPage=lastPage;
    }

    /**
     * 功能实现
     */
    public void go() {
        //方法调用
        setOrderList();
        initPageNumber();
        showData();

        /**
         * 修改按钮监听,
         */
        editB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //获取下拉框值,存入orderid
                String orderid = jTable.getValueAt(jTable.getSelectedRow(),
                        0).toString();
                //判断是否为空
                if(orderid.isEmpty()){
                    JOptionPane.showMessageDialog(null,
                            "请选择订单!",
                            "警告",
                            JOptionPane.WARNING_MESSAGE);
                }else {
                    //执行修改
                    int OID=Integer.parseInt(orderid);
                    Details details =new Details();
                    details.go(OID);

                    //刷新
                    JFrame frame = (JFrame) details.getFrame();
                    //编辑窗体监听,当关闭编辑窗体时刷新
                    frame.addWindowListener(new WindowListener() {
                        @Override
                        public void windowOpened(WindowEvent windowEvent) {

                        }

                        @Override
                        public void windowClosing(WindowEvent windowEvent) {

                        }

                        @Override
                        public void windowClosed(WindowEvent windowEvent) {
                            setOrderList();
                            showData();
                        }

                        @Override
                        public void windowIconified(WindowEvent windowEvent) {

                        }

                        @Override
                        public void windowDeiconified(WindowEvent windowEvent) {

                        }

                        @Override
                        public void windowActivated(WindowEvent windowEvent) {

                        }

                        @Override
                        public void windowDeactivated(WindowEvent windowEvent) {

                        }
                    });
                }

            }
        });
        /**
         * 搜索按钮监听
         */
        searchB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                findOrderId();
                VOrderinfId vOrderinfId = new VOrderinfId();
                OrderInfProcess orderInfProcess = new OrderInfProcess();
                vOrderinfId.setOsId(intOId);
                VOrderinfId vOrderinfId1 = orderInfProcess.searchOrder(vOrderinfId);

                if (vOrderinfId1!=null){
                    DefaultTableModel defaultTableModel = (DefaultTableModel)jTable.getModel();
                    defaultTableModel.setRowCount(0);

                    List<VOrderinfId> list = Collections.singletonList(vOrderinfId1);
                    for (VOrderinfId vOrderInf : list){
                        Vector v = new Vector();
                        if(vOrderinfId!=null){
                            v.add(vOrderInf.getoId());
                            v.add(vOrderInf.getCreateDate());
                            v.add(vOrderInf.getCompDate());
                            v.add(vOrderInf.getOsType());
                            v.add(vOrderInf.getGoodsName());
                            v.add(vOrderInf.getUserName());
                            defaultTableModel.addRow(v);
                        }
                    }
                }else {
                    JOptionPane.showMessageDialog(
                            null,
                            "订单不存在!",
                            "警告",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
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
                if (getCurrentPage()>getFirstPage()){
                    setCurrentPage(currentPage-1);
                    showData();
                }else {
                    setCurrentPage(firstPage);
                    showData();
                }
            }
        });
        //下一页按钮功能
        nextPageB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (getCurrentPage()<getLastPage()){
                    setCurrentPage(currentPage+1);
                    showData();
                }else {
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
    public void initPageNumber(){
        this.firstPage=1;
        this.tableRows=5;
        this.currentPage=firstPage;

        if(this.orderList.size()%this.tableRows==0){
            this.lastPage=this.orderList.size()/this.tableRows;
        }else {
            this.lastPage=this.orderList.size()/this.tableRows+1;
        }
    }

    public void setOrderList(){
        OrderInfProcess orderInfProcess=new OrderInfProcess();
        this.orderList=orderInfProcess.getAllOrderInf();
    }



    //显示订单信息
    public void showData(){

        DefaultTableModel defaultTableModel =(DefaultTableModel)jTable.getModel();
        defaultTableModel.setRowCount(0);

        //分页
        Page page= new Page(tableRows);
        List<VOrderinfId> list=page.cutList(currentPage,orderList);

        for (VOrderinfId vOrderinfId:list){
            Vector v = new Vector();
            if(vOrderinfId!=null){
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
}

package com.pm.ui.manager;

import com.pm.dao.datasource.Goods;
import com.pm.process.GoodsProcess;
import com.pm.util.Page;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

/**
 * 管理商品的UI
 */
public class MGoods {
    private JPanel jp1;
    private JPanel jp2;
    private JPanel jp3;

    private JTable table;

    private JButton addGoodsButton;
    private JButton editGoodsButton;
    private JButton deleGoodsButton;
    private JButton refreshButton;
    private JButton fristPageButton;
    private JButton nextPageButton;
    private JButton previousPageButton;
    private JButton lastPageButton;

    private JFrame mainFrame;

    private int currentPage;
    private final int firstPage = 1;
    private int lastPage;
    private int tableRows;
    private List<Goods> goodsList;

    public MGoods() {
        addGoodsButton = new JButton("上架商品");
        editGoodsButton = new JButton("修改商品信息");
        deleGoodsButton = new JButton("删除商品");
        refreshButton = new JButton("刷新");
        fristPageButton = new JButton("首页");
        previousPageButton = new JButton("上一页");
        nextPageButton = new JButton("下一页");
        lastPageButton = new JButton("末页");

        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();


        //设置表格
        String[] columnNames = {"ID", "商品编号", "商品名", "兑换价格", "商品图片"};

        table = new JTable(){
            //设置禁止编辑单元格
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        //设置自定义表格模型
        table.setModel(new CustomModel(null, columnNames));

        //table.setFillsViewportHeight(true);
        //设置行高
        table.setRowHeight(100);
        //列排序功能
        //table.setAutoCreateRowSorter(true);
        //设置表格列不可移动
        table.getTableHeader().setReorderingAllowed(false);
        //行单选
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //表格载入滑动面板
        JScrollPane scrollPane = new JScrollPane(table);


        //加载组件
        jp1.add(addGoodsButton);
        jp1.add(editGoodsButton);
        jp1.add(deleGoodsButton);
        jp1.add(refreshButton);

        jp2.add(scrollPane);

        jp3.add(fristPageButton);
        jp3.add(previousPageButton);
        jp3.add(nextPageButton);
        jp3.add(lastPageButton);

        //设置主面板属性
        mainFrame = new JFrame("商品管理");
        mainFrame.setSize(500,550);
        //使用流式布局
        mainFrame.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        mainFrame.setResizable(false);
        //居中显示
        mainFrame.setLocationRelativeTo(null);

        //加载组件
        mainFrame.add(jp1);
        mainFrame.add(jp2);
        mainFrame.add(jp3);
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
        initGoodsList();

        initPageNumber();

        showData();

        //刷新按钮的监听器
        refreshButton.addActionListener(e -> showData());
        //上架商品按钮的监听器
        addGoodsButton.addActionListener(e -> {
            AddGoods addGoods = new AddGoods();
            //跳转到添加商品的UI
            addGoods.go();

            //获取子窗口，添加窗口监听当子窗口关闭后刷新table数据
            JFrame frame = (JFrame) addGoods.getFrame();
            frame.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {

                }

                @Override
                public void windowClosing(WindowEvent e) {

                }

                @Override
                public void windowClosed(WindowEvent e) {
                    setCurrentPage(getLastPage());
                    showData();
                }

                @Override
                public void windowIconified(WindowEvent e) {

                }

                @Override
                public void windowDeiconified(WindowEvent e) {

                }

                @Override
                public void windowActivated(WindowEvent e) {

                }

                @Override
                public void windowDeactivated(WindowEvent e) {

                }
            });
        });

        //修改商品信息按钮的监听器
        editGoodsButton.addActionListener(e -> {
            //获取选中行的商品编号
            String id = table.getValueAt(table.getSelectedRow(),0).toString();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "请选择商品！",
                        "注意",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                int ID = Integer.parseInt(id);
                //获取商品ID后，跳转到修改UI
                EditGoods editGoods = new EditGoods();
                editGoods.go(ID);
                //todo 重新设置刷新监听到确定按钮
                //获取修改UI，添加窗口监听，当子窗口关闭后刷新table数据
                JFrame frame = (JFrame) editGoods.getFrame();
                frame.addWindowListener(new WindowListener() {
                    @Override
                    public void windowOpened(WindowEvent e) {

                    }

                    @Override
                    public void windowClosing(WindowEvent e) {

                    }

                    @Override
                    public void windowClosed(WindowEvent e) {
                        showData();
                    }

                    @Override
                    public void windowIconified(WindowEvent e) {

                    }

                    @Override
                    public void windowDeiconified(WindowEvent e) {

                    }

                    @Override
                    public void windowActivated(WindowEvent e) {

                    }

                    @Override
                    public void windowDeactivated(WindowEvent e) {

                    }
                });
            }
        });

        //删除商品信息按钮的监听器
        deleGoodsButton.addActionListener(e -> {

            GoodsProcess goodsProcess = new GoodsProcess();

            int result=JOptionPane.showConfirmDialog(null, "是否删除该商品？");
            if(result==0){

                //获取选中行的id
                String id = table.getValueAt(table.getSelectedRow(),0).toString();

                int ID = Integer.parseInt(id);
                boolean c = goodsProcess.deleGoods(ID);
                if (c) {
                    //刷新表格
                    showData();
                    JOptionPane.showMessageDialog(null,
                            "删除成功！",
                            "注意",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "删除失败！",
                            "注意",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //首页按钮的监听器，点击后设置首页并刷新table
        fristPageButton.addActionListener(e -> {
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

        //尾页按钮的监听器
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

        //计算尾页的页码
        if(this.goodsList.size() % this.tableRows ==0){
            this.lastPage = this.goodsList.size() / this.tableRows;
        }else{
            this.lastPage = this.goodsList.size() / this.tableRows + 1;
        }
    }

    private void initGoodsList(){
        GoodsProcess goodsProcess = new GoodsProcess();
        this.goodsList = goodsProcess.getGoods();
    }

    /**
     *显示所有商品信息
     */
    private void showData(){
        DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
        //清楚原有数据
        defaultTableModel.setRowCount(0);

        //列表分页
        Page page = new Page<Goods>(tableRows);
        List<Goods> list =  page.cutList(currentPage, goodsList);

        try {
            for(Goods goods : list){
                Vector v = new Vector();
                if (goods.getIsDele() == 0){
                    v.add(goods.getId());
                    v.add(goods.getGoodsId());
                    v.add(goods.getGoodsName());
                    v.add(goods.getGoodsPrice());
                    //获取图片数据，有就加载，无则加载默认图片
                    Blob blob = goods.getPicStream();
                    if(blob != null){
                        byte[] data = blob.getBytes(1,(int)blob.length());
                        v.add(new ImageIcon(data));
                    }else {
                        v.add(new ImageIcon("image/default.png"));
                    }
                    //添加一行数据
                    defaultTableModel.addRow(v);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //自定义table模型，让其可以加载图片内容
    static class CustomModel extends DefaultTableModel{

        CustomModel(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        //让table显示图片
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if(columnIndex == 4){
                return ImageIcon.class;
            }else {
                return super.getColumnClass(columnIndex);
            }
        }
    }
}

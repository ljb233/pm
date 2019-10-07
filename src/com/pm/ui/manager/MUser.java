package com.pm.ui.manager;

import com.pm.dao.datasource.User;
import com.pm.dao.datasource.Point;
import com.pm.dao.datasource.VOrderinfId;
import com.pm.process.OrderInfProcess;
import com.pm.process.UserProcess;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import com.pm.util.Page;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MUser extends JFrame {
    private static final long serialVersionUID = 9527L;
    private JPanel pane1;
    private JPanel pane2;
    private JPanel pane3;
    private JTable table;
    private JButton addpoint;
    private JButton adduser;
    private JButton freezeuser;
    private JButton stopfrzee;
    private JButton updatepwd;
    private JButton search;
    private JButton firstPageButton;
    private JButton nextPageButton;
    private JButton previousPageButton;
    private JButton lastPageButton;
    private int currentPage;
    private int fristPage;
    private int lastPage;
    private int tableRows;
    private JTextField searchfield;
    private Object[][] data;
    private List<User> userList;
    private List<Point> pointList;
    String strname;
    String status0 = "正常";
    String status1 = "已冻结";

    JTextField name;//文本

    public MUser() {
        super();
        pane1 = new JPanel();
        pane2 = new JPanel();
        pane3 = new JPanel();

        searchfield  = new JTextField("输入用户名查找",10);
        searchfield.setFont(new Font("标楷体",Font.TRUETYPE_FONT|Font.ITALIC,12));

        addpoint = new JButton("积分充值");
        adduser = new JButton("添加用户");
        freezeuser = new JButton("冻结用户");
        stopfrzee = new JButton("解冻用户");
        updatepwd = new JButton("重置密码");
        search = new JButton("go");
        firstPageButton = new JButton("首页");
        previousPageButton = new JButton("上一页");
        nextPageButton = new JButton("下一页");
        lastPageButton = new JButton("末页");

        pane1.add(searchfield);
        pane1.add(search);
        //设置表格
        setBounds(100,100,600,600);
        String[] columnNames = {"ID","用户名","账户状态","积分值"};
        table = new JTable(data, columnNames);
        //创建表格模型 （目的是操作表格）
        table.setModel(new DefaultTableModel(data, columnNames));    //创建表格并使表格模型与之关联
        //添加表格到滚动轴
        JScrollPane scrollpane = new JScrollPane();
        scrollpane.getViewport().add(table);

        pane2.add(Box.createGlue()); // 挤占用户管理按钮和窗口左侧空间
        pane2.add(addpoint);
        pane2.add(Box.createHorizontalStrut(20));// 按钮之间的水平距离
        pane2.add(adduser);
        pane2.add(Box.createHorizontalStrut(20));
        pane2.add(freezeuser);
        pane2.add(Box.createHorizontalStrut(20));
        pane2.add(stopfrzee);
        pane2.add(Box.createHorizontalStrut(20));
        pane2.add(updatepwd);
        pane2.add(Box.createGlue());
        pane2.add(scrollpane);

        pane3.add(firstPageButton);
        pane3.add(previousPageButton);
        pane3.add(nextPageButton);
        pane3.add(lastPageButton);

        add(pane1);
        add(pane2);
        add(pane3);

        setTitle("用户管理界面");// 标题
        //setSize(600, 600);// 窗口大小
        setLayout(new GridLayout(3, 1));
        //setResizable(false);
        setLocationRelativeTo(null);// 窗口居中
        this.setVisible(true);
    }
    public void findjtext() {
        strname = searchfield.getText().toString();
    }
    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }
    public int getFristPage() {
        return fristPage;
    }
    public void setFristPage(int fristPage) {
        this.fristPage = fristPage;
    }
    public int getLastPage() {
        return lastPage;
    }
    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public void go() {
        setUserList();
        initPageNumber();
        showData();
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findjtext();
                User user = new User();
                user.setUserName(strname);
                UserProcess userProcess = new UserProcess();
                User user2 = userProcess.searchUser(user);

                DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
                defaultTableModel.setRowCount(0);

                //列表分页
                Page page = new Page(tableRows);
                List<User> list =  page.cutList(currentPage, Collections.singletonList(user2));

                OrderInfProcess orderInfProcess = new OrderInfProcess();
                List<VOrderinfId> userInfList = orderInfProcess.getUserInf();

                //todo:在VOrderinfId视图中获取以下5个字段，显示在表格中
                for (User userInf : list) {
                    Vector v = new Vector();
                    v.add(userInf.getId());
                    v.add(userInf.getUserName());
                    v.add(userInf.getIsFreeze());
                    defaultTableModel.addRow(v);
                }
            }
        });

        adduser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddUser addUser = new AddUser();
                addUser.Main();
                showData();

                //刷新表格内容
                JFrame frame = (JFrame) addUser.getFrame();
                frame.addWindowListener(new WindowListener() {
                    @Override
                    public void windowOpened(WindowEvent e) {
                    }
                    @Override
                    public void windowClosing(WindowEvent e) {
                    }
                    @Override
                    public void windowClosed(WindowEvent e) {
                        setUserList();
                        initPageNumber();
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

        freezeuser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = null;
                try {
                    id = table.getValueAt(table.getSelectedRow(), 0).toString();
                    int ID = Integer.valueOf(id);
                    int n = JOptionPane.showConfirmDialog(null, "确认冻结吗?", "提示", JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION){
                        UserProcess userProcess = new UserProcess();
                        userProcess.frzzeeUserByID(ID);
                        setUserList();
                        initPageNumber();
                        showData();
                        JOptionPane.showMessageDialog(new JFrame(),"已冻结");
                    }else if(n == JOptionPane.NO_OPTION){
                        JOptionPane.showMessageDialog(new JFrame(),"已取消");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "请选中一个用户", "提示", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        stopfrzee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = null;
                try {
                    id = table.getValueAt(table.getSelectedRow(), 0).toString();
                    int ID = Integer.valueOf(id);
                    int n = JOptionPane.showConfirmDialog(null, "确认解冻吗?", "提示", JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION){
                        UserProcess userProcess = new UserProcess();
                        userProcess.stopfrzzeeUserByID(ID);
                        setUserList();
                        initPageNumber();
                        showData();
                        JOptionPane.showMessageDialog(new JFrame(),"已解冻");
                    }else if(n == JOptionPane.NO_OPTION){
                        JOptionPane.showMessageDialog(new JFrame(),"已取消");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "请选中一个用户", "提示", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        updatepwd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = null;
                try {
                    id = table.getValueAt(table.getSelectedRow(), 0).toString();
                    int ID = Integer.valueOf(id);
                    int n = JOptionPane.showConfirmDialog(null, "确认重置吗?", "提示", JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        String pwd = "123456";
                        UserProcess up = new UserProcess();
                        Boolean modify = up.editpwdUser(ID,pwd);
                        setUserList();
                        initPageNumber();
                        showData();
                        JOptionPane.showMessageDialog(new JFrame(),"已重置");
                    } else if (n == JOptionPane.NO_OPTION) {
                        JOptionPane.showMessageDialog(new JFrame(),"已取消");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "请选中一个用户", "提示", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        addpoint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = null;
                try {
                    id = table.getValueAt(table.getSelectedRow(), 0).toString();
                    int ID = Integer.valueOf(id);
                    int addValue=0;
                    PointsRecharge pointsRecharge = new PointsRecharge();
                    pointsRecharge.Main(addValue,ID);
                    //刷新表格内容
                    JFrame frame = (JFrame) pointsRecharge.getFrame();
                    frame.addWindowListener(new WindowListener() {
                        @Override
                        public void windowOpened(WindowEvent e) {
                        }
                        @Override
                        public void windowClosing(WindowEvent e) {
                        }
                        @Override
                        public void windowClosed(WindowEvent e) {
                            setUserList();
                            initPageNumber();
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
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "请选中一个用户", "提示", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        firstPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCurrentPage(fristPage);
                showData();
            }
        });

        previousPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getCurrentPage() > getFristPage()){
                    setCurrentPage(currentPage - 1);
                    showData();
                }else{
                    setCurrentPage(getFristPage());
                    showData();
                }
            }
        });

        nextPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getCurrentPage() < getLastPage()){
                    setCurrentPage(currentPage + 1);
                    showData();
                }else{
                    setCurrentPage(lastPage);
                    showData();
                }
            }
        });

        lastPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCurrentPage(lastPage);
                showData();
            }
        });
    }

    public void initPageNumber(){
        this.fristPage = 1;
        this.tableRows = 8;
        this.currentPage = fristPage;

        if(this.userList.size() % this.tableRows ==0){
            this.lastPage = this.userList.size() / this.tableRows;
        }else{
            this.lastPage = this.userList.size() / this.tableRows + 1;
        }
    }

    public void setUserList(){
        UserProcess userProcess = new UserProcess();
        this.userList = userProcess.getAllUsers();
    }
    /***
     *显示所有用户信息
     */
    public void showData() {
        DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
        defaultTableModel.setRowCount(0);

        //列表分页
        Page page = new Page(tableRows);
        List<User> list =  page.cutList(currentPage, userList);

        OrderInfProcess orderInfProcess = new OrderInfProcess();
        List<VOrderinfId> userInfList = orderInfProcess.getUserInf();

        //todo:在VOrderinfId视图中获取以下5个字段，显示在表格中
        for (User userInf : list) {
            Vector v = new Vector();
            v.add(userInf.getId());
            v.add(userInf.getUserName());
            if(userInf.getIsFreeze()==0){
                v.add(status0);
            }else {
                v.add(status1);
            }
            defaultTableModel.addRow(v);
        }
    }
    }





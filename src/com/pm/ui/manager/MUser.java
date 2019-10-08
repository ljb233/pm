package com.pm.ui.manager;

import com.pm.dao.datasource.User;
import com.pm.process.PointProcess;
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
/**
 * 管理员-用户管理-主界面
 *@Auther: linyang
 */
public class MUser extends JFrame {
    private JPanel pane1;
    private JPanel pane2;
    private JPanel pane3;
    private JTable table;
    private JButton btnAddpoint;
    private JButton btnAdduser;
    private JButton btnFreezeuser;
    private JButton btnStopfrzeeuser;
    private JButton btnUpdatepwd;
    private JButton btnSearch;
    private JButton btnFirstPageButton;
    private JButton btnNextPageButton;
    private JButton btnPreviousPageButton;
    private JButton btnLastPageButton;
    private int currentPage;
    private int fristPage;
    private int lastPage;
    private int tableRows;
    private JTextField searchfield;
    private Object[][] data;
    private List<User> userList;
    private String strname;
    private static final String NORMAL = "正常";
    private static final String FROZEN = "已冻结";

    public MUser() {
        super();
        pane1 = new JPanel();
        pane2 = new JPanel();
        pane3 = new JPanel();

        searchfield  = new JTextField("输入用户名查找",10);
        searchfield.setFont(new Font("标楷体",Font.TRUETYPE_FONT|Font.ITALIC,12));

        btnAddpoint = new JButton("积分充值");
        btnAdduser = new JButton("添加用户");
        btnFreezeuser = new JButton("冻结用户");
        btnStopfrzeeuser = new JButton("解冻用户");
        btnUpdatepwd = new JButton("重置密码");
        btnSearch = new JButton("go");
        btnFirstPageButton = new JButton("首页");
        btnPreviousPageButton = new JButton("上一页");
        btnNextPageButton = new JButton("下一页");
        btnLastPageButton = new JButton("末页");
        pane1.add(searchfield);
        pane1.add(btnSearch);
        //设置表格
        setBounds(100,100,600,620);
        String[] columnNames = {"ID","用户名","账户状态","积分值"};
        table = new JTable(data, columnNames);
        //创建表格模型 （目的是操作表格）
        table.setModel(new DefaultTableModel(data, columnNames));    //创建表格并使表格模型与之关联
        //添加表格到滚动轴
        JScrollPane scrollpane = new JScrollPane();
        scrollpane.getViewport().add(table);

        pane2.add(Box.createGlue()); // 挤占用户管理按钮和窗口左侧空间
        pane2.add(btnAddpoint);
        pane2.add(Box.createHorizontalStrut(20));// 按钮之间的水平距离
        pane2.add(btnAdduser);
        pane2.add(Box.createHorizontalStrut(20));
        pane2.add(btnFreezeuser);
        pane2.add(Box.createHorizontalStrut(20));
        pane2.add(btnStopfrzeeuser);
        pane2.add(Box.createHorizontalStrut(20));
        pane2.add(btnUpdatepwd);
        pane2.add(Box.createGlue());
        pane2.add(scrollpane);

        pane3.add(btnFirstPageButton);
        pane3.add(btnPreviousPageButton);
        pane3.add(btnNextPageButton);
        pane3.add(btnLastPageButton);

        add(pane1);
        add(pane2);
        add(pane3);

        setTitle("用户管理界面");// 标题
        setLayout(new GridLayout(3, 1));
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
        ShowData();
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findjtext();
                User user = new User();
                user.setUserName(strname);
                UserProcess userProcess = new UserProcess();
                User user2 = userProcess.SearchUser(user);

                if (user2 != null) {
                    DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
                    defaultTableModel.setRowCount(0);

                    List<User> list = Collections.singletonList(user2);
                    for (User userInf : list) {
                        Vector v = new Vector();
                        v.add(userInf.getId());
                        v.add(userInf.getUserName());
                        if (userInf.getIsFreeze() == 0) {
                            v.add(NORMAL);
                        } else {
                            v.add(FROZEN);
                        }
                        PointProcess pointProcess = new PointProcess();
                        v.add(pointProcess.getallpoint(userInf.getId()));
                        defaultTableModel.addRow(v);
                    }
                }else {
                    JOptionPane.showMessageDialog(null, "用户不存在！");
                }
            }
        });

        btnAdduser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddUser addUser = new AddUser();
                addUser.Main();
                ShowData();
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
                        ShowData();
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

        btnFreezeuser.addActionListener(new ActionListener() {
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
                        ShowData();
                        JOptionPane.showMessageDialog(new JFrame(),"已冻结");
                    }else if(n == JOptionPane.NO_OPTION){
                        JOptionPane.showMessageDialog(new JFrame(),"已取消");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "请选中一个用户", "提示", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnStopfrzeeuser.addActionListener(new ActionListener() {
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
                        ShowData();
                        JOptionPane.showMessageDialog(new JFrame(),"已解冻");
                    }else if(n == JOptionPane.NO_OPTION){
                        JOptionPane.showMessageDialog(new JFrame(),"已取消");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "请选中一个用户", "提示", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnUpdatepwd.addActionListener(new ActionListener() {
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
                        JOptionPane.showMessageDialog(new JFrame(),"已重置");
                    } else if (n == JOptionPane.NO_OPTION) {
                        JOptionPane.showMessageDialog(new JFrame(),"已取消");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "请选中一个用户", "提示", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnAddpoint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = null;
                try {
                    id = table.getValueAt(table.getSelectedRow(), 0).toString();
                    int ID = Integer.valueOf(id);
                    int addValue=0;
                    PointsRecharge pointsRecharge = new PointsRecharge();
                    pointsRecharge.Main(addValue,ID);
                    ShowData();
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
                            ShowData();
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

        btnFirstPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCurrentPage(fristPage);
                ShowData();
            }
        });

        btnPreviousPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getCurrentPage() > getFristPage()){
                    setCurrentPage(currentPage - 1);
                    ShowData();
                }else{
                    setCurrentPage(getFristPage());
                    ShowData();
                }
            }
        });

        btnNextPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getCurrentPage() < getLastPage()){
                    setCurrentPage(currentPage + 1);
                    ShowData();
                }else{
                    setCurrentPage(lastPage);
                    ShowData();
                }
            }
        });

        btnLastPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCurrentPage(lastPage);
                ShowData();
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
    public void ShowData() {
        DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
        defaultTableModel.setRowCount(0);
        //列表分页
        Page page = new Page(tableRows);
        List<User> list =  page.cutList(currentPage, userList);

        for (User userInf : list) {
            Vector v = new Vector();
            v.add(userInf.getId());
            v.add(userInf.getUserName());
            if(userInf.getIsFreeze()==0){
                v.add(NORMAL);
            }else {
                v.add(FROZEN);
            }
            PointProcess pointProcess = new PointProcess();
            v.add(pointProcess.getallpoint(userInf.getId()));
            defaultTableModel.addRow(v);
        }
    }
    }





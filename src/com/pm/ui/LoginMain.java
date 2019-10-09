package com.pm.ui;

import com.pm.dao.datasource.Manager;
import com.pm.dao.datasource.User;
import com.pm.process.ManagerProcess;
import com.pm.process.UserProcess;
import com.pm.ui.manager.ManagerMain;
import com.pm.ui.user.UserMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginMain extends JFrame {
    private String account = "";
    private String password = "";

    private JFrame mainFrame;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;

    private JTextField accountField;
    private JPasswordField passwordField;

    private JLabel accountLabel;
    private JLabel passwordLabel;

    private JButton loginButton;

    private JRadioButton mgRadioButton;
    private JRadioButton userRadioButton;

    public LoginMain() {
        //初始化窗口
        accountField = new JTextField(10);
        accountLabel = new JLabel("用户名");

        passwordField = new JPasswordField(10);
        passwordLabel = new JLabel("密码");

        userRadioButton = new JRadioButton("用户", true);
        mgRadioButton = new JRadioButton("管理员");

        loginButton = new JButton("登录");

        //将单选按钮绑定为一组
        ButtonGroup bg = new ButtonGroup();
        bg.add(userRadioButton);
        bg.add(mgRadioButton);

        //组合组件
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();

        panel1.add(accountLabel);
        panel1.add(accountField);
        panel2.add(passwordLabel);
        panel2.add(passwordField);
        panel3.add(userRadioButton);
        panel3.add(mgRadioButton);
        panel4.add(loginButton);

        mainFrame = new JFrame("积分管理系统登录入口");

        mainFrame.setSize(300, 200);
        mainFrame.setBounds(600, 200, 300, 220);
        mainFrame.setLayout(new GridLayout(4, 1));
        mainFrame.setResizable(false);
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        //添加组件
        mainFrame.add(panel1);
        mainFrame.add(panel2);
        mainFrame.add(panel3);
        mainFrame.add(panel4);
        mainFrame.setVisible(true);
    }

    public void Login() {
        //监听登录按钮
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //检查用户名与密码是否为空
                if (check()) {

                    //选择登录用户
                    if (userRadioButton.isSelected()) {
                        UserProcess up = new UserProcess();
                        User user = new User();
                        user = up.userLogin(account, password);
                        if (user != null) {
                            mainFrame.dispose();
                            //TODO 添加用户窗口
                            UserMain UM = new UserMain(user);
                            UM.go();
                            System.out.println("UY");
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "用户名或密码错误！",
                                    "注意",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        //查询用户信息
                        ManagerProcess mp = new ManagerProcess();
                        Manager manager = mp.ManagerLogin(account, password);
                        if (manager != null) {
                            mainFrame.dispose();
                            //TODO 添加管理员窗口
                           /* ManagerUI jframeMain = new ManagerUI();
                            jframeMain.Menu();*/

                            //将用户信息传入下个窗口
                            ManagerMain managerMain = new ManagerMain();
                            managerMain.go();


                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "用户名或密码错误！",
                                    "注意",
                                    JOptionPane.WARNING_MESSAGE);
                        }

                    }
                }
            }
        });
    }

    public boolean check() {
        account = accountField.getText();
        password = String.valueOf(passwordField.getPassword());

        if (account.equals("")) {
            JOptionPane.showMessageDialog(null,
                    "请输入用户名！",
                    "注意",
                    JOptionPane.ERROR_MESSAGE);
        } else if (password.equals("")) {
            JOptionPane.showMessageDialog(null,
                    "请输入密码！",
                    "注意",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            return true;
        }
        return false;
    }
}

        //将单选按钮绑定为一组
        ButtonGroup bg = new ButtonGroup();
        bg.add(userRadioButton);
        bg.add(mgRadioButton);

        //组合组件
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();

        panel1.add(accountLabel);
        panel1.add(accountField);
        panel2.add(passwordLabel);
        panel2.add(passwordField);
        panel3.add(userRadioButton);
        panel3.add(mgRadioButton);
        panel4.add(loginButton);


        setTitle("积分商城");
        setSize(300, 200);
        setBounds(600, 200, 300, 220);
        setLayout(new GridLayout(4, 1));
        setLocationRelativeTo(null);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        //添加组件
        add(panel1);
        add(panel2);
        add(panel3);
        add(panel4);
        setVisible(true);
    }

    void Login() {
        //监听登录按钮
        loginButton.addActionListener(e -> {

            //检查用户名与密码是否为空
            if (check()) {

                //选择登录用户
                if (userRadioButton.isSelected()) {
                    UserProcess up = new UserProcess();
                    User user = up.userLogin(account, password);
                    if (user == null) {
                        JOptionPane.showMessageDialog(null,
                                "用户名或密码错误！",
                                "注意",
                                JOptionPane.WARNING_MESSAGE);
                    } else if(user.getIsFreeze() == 1){
                        JOptionPane.showMessageDialog(null,
                                "用户已冻结！",
                                "注意",
                                JOptionPane.WARNING_MESSAGE);
                    }else {
                        //隐藏窗口
                        dispose();
                        UserMain UM = new UserMain(user);
                        UM.go();
                    }
                } else {
                    //查询用户信息
                    ManagerProcess mp = new ManagerProcess();
                    Manager manager = mp.ManagerLogin(account, password);
                    if (manager != null) {
                        //隐藏窗口
                        dispose();
                        ManagerMain managerMain = new ManagerMain();
                        managerMain.go();
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "用户名或密码错误！",
                                "注意",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
    }

    //文本框的检查，判断输入的数据格式是否都正确
    private boolean check() {
        account = accountField.getText();
        password = String.valueOf(passwordField.getPassword());

        if (account.equals("")) {
            JOptionPane.showMessageDialog(null,
                    "请输入用户名！",
                    "注意",
                    JOptionPane.ERROR_MESSAGE);
        } else if (password.equals("")) {
            JOptionPane.showMessageDialog(null,
                    "请输入密码！",
                    "注意",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            return true;
        }
        return false;
    }
}

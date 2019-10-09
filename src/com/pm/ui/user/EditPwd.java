package com.pm.ui.user;
import com.pm.dao.datasource.User;
import com.pm.dao.factory.UserDAO;
import com.pm.process.UserProcess;

import javax.swing.*;
import java.awt.*;


public class EditPwd extends JFrame {
    private String oldpwd = "";
    private String newpwd = "";
    private String repwd = "";

    private JFrame mainFrame;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;

    private JPasswordField oldpasswordField;
    private JPasswordField newpasswordField;
    private JPasswordField repasswordField;

    private JLabel oldpasswordLabel;
    private JLabel newpasswordLabel;
    private JLabel repasswordLabel;

    private JButton confirmButton;
    private JButton cancelButton;

    private User loginUser;


    public EditPwd(User user) {
        loginUser = user;
        //初始化窗口
        oldpasswordField = new JPasswordField(15);
        oldpasswordLabel = new JLabel("旧密码");

        newpasswordField = new JPasswordField(15);
        newpasswordLabel = new JLabel("新密码");

        repasswordField = new JPasswordField(15);
        repasswordLabel = new JLabel("确认密码");

        confirmButton = new JButton("确定");
        cancelButton = new JButton("取消");


        //组合组件
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();
        panel5 = new JPanel();

        panel1.add(oldpasswordLabel);
        panel1.add(oldpasswordField);
        panel2.add(newpasswordLabel);
        panel2.add(newpasswordField);
        panel3.add(repasswordLabel);
        panel3.add(repasswordField);
        panel4.add(confirmButton);
        panel5.add(cancelButton);

        //设置主窗口属性
        mainFrame = new JFrame("修改密码");
        mainFrame.setSize(300, 200);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainFrame.setResizable(false);


        //添加组件
        mainFrame.add(panel1);
        mainFrame.add(panel2);
        mainFrame.add(panel3);
        mainFrame.add(panel4);
        mainFrame.add(panel5);
        mainFrame.setVisible(true);
    }

    public void Edit() {
        //监听登录按钮
        confirmButton.addActionListener(e -> {
            //获取数据
            int id = loginUser.getId();
            String pwd = loginUser.getUserPwd();
            String oldpwd = new String(oldpasswordField.getPassword());
            String newpwd = new String(newpasswordField.getPassword());
            String repwd = new String(repasswordField.getPassword());

            if (check()) {
                if (oldpwd.equals(pwd)) {//旧密码与原密码是否一致
                    if (newpwd.equals(repwd)) {//两个新密码是否一致
                        if (!(newpwd.equals(pwd))) {
                            UserProcess userProcess = new UserProcess();
                            userProcess.editpwdUser(id,newpwd);
                            JOptionPane.showMessageDialog(null,
                                    "密码修改成功！",
                                    "友情提示",
                                    JOptionPane.INFORMATION_MESSAGE);
                            mainFrame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "您新密码与旧密码相同，请确认后重新输入！",
                                    "友情提示",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "您两次输入的新密码不一致，请确认后重新输入！",
                                "友情提示",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "您输入的原密码错误，请确认后重新输入！",
                            "友情提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        //取消按钮监听器
        cancelButton.addActionListener(e -> {
            mainFrame.dispose();
        });
    }


            public boolean check() {
                oldpwd = String.valueOf(oldpasswordField.getPassword());
                newpwd = String.valueOf(newpasswordField.getPassword());
                repwd = String.valueOf(repasswordField.getPassword());

                if (oldpwd.equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "请输入旧密码！",
                            "注意",
                            JOptionPane.ERROR_MESSAGE);
                } else if (newpwd.equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "请输入新密码！",
                            "注意",
                            JOptionPane.WARNING_MESSAGE);
                } else if (repwd.equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "请输入确认密码！",
                            "注意",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    return true;
                }
                return false;
            }

}
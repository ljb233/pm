package com.pm.ui.user;

import com.pm.dao.datasource.Manager;
import com.pm.dao.datasource.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UserMain extends JFrame{

    private JFrame mainFrame;
    private JButton uUserButton;
    private JButton uOrderButton;
    //private JButton uGoodsButton;
    public Manager manager;
    public User loginUser = null;


    public UserMain(User user){
        this.loginUser = user;
        uUserButton = new JButton("修改密码");
        uOrderButton = new JButton("个人订单");
        //uGoodsButton = new JButton("商品兑换");

        mainFrame = new JFrame(user.getUserName()+"");
        mainFrame.setSize(300,200);
        mainFrame.setLayout(new GridLayout(3,1));
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });

        //添加组件

        mainFrame.add(uUserButton);
        //mainFrame.add(uGoodsButton);
        mainFrame.add(uOrderButton);
        mainFrame.setVisible(true);
    }

    public void go(){
        uUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditPwd editPwd = new EditPwd(loginUser);
                editPwd.Edit();
            }
        });

      /* uGoodsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UOdetail uOdetail = new UOdetail(user);
                uOdetail.go();
            }
        });*/

        uOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UOrder uOrder = new UOrder(loginUser);
                uOrder.go();
            }
        });

    }

}

package com.pm.ui.manager;

import com.pm.dao.datasource.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ManagerMain extends JFrame{

    private JButton mUserButton;
    private JButton mOrderButton;
    private JButton mGoodsButton;

    public ManagerMain(){
        mUserButton = new JButton("用户管理");
        mOrderButton = new JButton("订单管理");
        mGoodsButton = new JButton("商品管理");

        //设置主窗口属性
        setTitle("管理员");
        setSize(300,200);
        setLayout(new GridLayout(3,1));
        //不可调整
        setResizable(false);
        //居中显示
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        //装载组件
        add(mUserButton);
        add(mGoodsButton);
        add(mOrderButton);
        setVisible(true);
    }

    public void go(){
        //用户管理监听器
        mUserButton.addActionListener(e -> {
            MUser mUser = new MUser();
            mUser.go();
        });
        //商品管理监听器
        mGoodsButton.addActionListener(e -> {
            MGoods mGoods = new MGoods();
            mGoods.go();
        });
        //订单管理监听器
        mOrderButton.addActionListener(e -> {
            MOrder morder = new MOrder();
            morder.go();
        });
    }
}

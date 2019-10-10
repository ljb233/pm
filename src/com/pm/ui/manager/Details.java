package com.pm.ui.manager;

import com.pm.dao.datasource.VOrderinfId;
import com.pm.process.OrderInfProcess;
import com.pm.process.OrderProcess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Details {

    private int OrderID,
            oID,
            orderType;
    private String orderId="";

    private JFrame jFrame;
    private JPanel jPanel1,
            jPanel2,
            jPanel3,
            jPanel4,
            jPanel5,
            jPanel6,
            jButtonPanel,
            ButtonPanel;

    private JTextField oIdText,
            createDateText,
            compDateText,
            osTypeText,
            goodsNameText,
            userNameText;
    private JLabel oIdLabel,
            createDateLabel,
            compDateLabel,
            osTypeLabel,
            goodsNameLabel,
            userNameLabel;
    //private ComboBox osTypeBox;
    private JButton //confirmButton,
            returnButton,
            deliverGoodsButton,
            cancelOrderButton;

    public Details(){
        //初始化
        oIdLabel = new JLabel("订单编号");
        createDateLabel = new JLabel("创建日期");
        compDateLabel = new JLabel("完成日期");
        osTypeLabel = new JLabel("订单状态");
        goodsNameLabel = new JLabel("商品名称");
        userNameLabel = new JLabel("用户名称");


        oIdText = new JTextField(13);
        oIdText.setEditable(false);

        createDateText = new JTextField(13);
        createDateText.setEditable(false);

        compDateText = new JTextField(13);
        compDateText.setEditable(false);

        osTypeText = new JTextField(13);
        osTypeText.setEditable(false);

        /*osTypeBox =new JComboBox();
        osTypeBox.addItem("--请选择--");
        osTypeBox.addItem("未支付");
        osTypeBox.addItem("已发货");
        osTypeBox.addItem("已完成");
        osTypeBox.addItem("无效");
        osTypeBox.addItem("已删除");
        osTypeBox.addItem("已取消");
        osTypeBox.addItem("未发货");*/


        goodsNameText = new JTextField(13);
        goodsNameText.setEditable(false);

        userNameText = new JTextField(13);
        userNameText.setEditable(false);

        //confirmButton = new JButton("确定");
        returnButton = new JButton("返回");
        deliverGoodsButton = new JButton("发货");
        cancelOrderButton = new JButton("取消订单");

        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jPanel3 = new JPanel();
        jPanel4 = new JPanel();
        jPanel5 = new JPanel();
        jPanel6 = new JPanel();
        jButtonPanel = new JPanel();
        ButtonPanel = new JPanel();

        jPanel1.add(oIdLabel);
        jPanel1.add(oIdText);
        jPanel2.add(createDateLabel);
        jPanel2.add(createDateText);
        jPanel3.add(compDateLabel);
        jPanel3.add(compDateText);
        jPanel4.add(osTypeLabel);
        jPanel4.add(osTypeText);
        jPanel5.add(goodsNameLabel);
        jPanel5.add(goodsNameText);
        jPanel6.add(userNameLabel);
        jPanel6.add(userNameText);
        jButtonPanel.add(deliverGoodsButton);
        jButtonPanel.add(cancelOrderButton);
        //ButtonPanel.add(confirmButton);
        ButtonPanel.add(returnButton);

        jFrame = new JFrame("订单详情");
        jFrame.setSize(400,400);
        jFrame.setLocationRelativeTo(null);
        jFrame.setLayout(new GridLayout(8,1));
        jFrame.setResizable(false);

        jFrame.add(jPanel1);
        jFrame.add(jPanel2);
        jFrame.add(jPanel3);
        jFrame.add(jPanel4);
        jFrame.add(jPanel5);
        jFrame.add(jPanel6);
        jFrame.add(jButtonPanel);
        jFrame.add(ButtonPanel);
        jFrame.setVisible(true);



    }


    public void go(int id) {
        this.OrderID = id;
        OrderInfProcess orderInfProcess = new OrderInfProcess();
        OrderProcess orderProcess = new OrderProcess();
        VOrderinfId vOrderinfId = orderInfProcess.getOrderInfByOId(OrderID);
        oIdText.setText(String.valueOf(vOrderinfId.getOrderId()));
        createDateText.setText(String.valueOf(vOrderinfId.getCreateDate()));
        compDateText.setText(String.valueOf(vOrderinfId.getCompDate()));
        osTypeText.setText(String.valueOf(vOrderinfId.getOsType()));
//        osTypeBox.setSelectedIndex(vOrderinfId.getOsId());
        goodsNameText.setText(vOrderinfId.getGoodsName());
        userNameText.setText(vOrderinfId.getUserName());
        orderType = vOrderinfId.getOsId();
        oID = vOrderinfId.getoId();
        deliverGoodsButton.addActionListener(e->{
            if (checkDeliver()){
                vOrderinfId.setoId(oID);
                //已发货状态osId==2
                vOrderinfId.setOsId(2);

                boolean c = orderProcess.updateOSType(vOrderinfId);

                if (c){
                    JOptionPane.showMessageDialog(null,
                            "修改成功!",
                            "注意",
                            JOptionPane.WARNING_MESSAGE);
                    jFrame.dispose();
                }else {
                    JOptionPane.showMessageDialog(null,
                            "修改失败!",
                            "注意",
                            JOptionPane.WARNING_MESSAGE);
                }
            }else {
                JOptionPane.showMessageDialog(null,
                        "不能发货,订单不符合要求!",
                        "注意",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        cancelOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (checkCancel()){
                    vOrderinfId.setoId(oID);
                    //已取消状态osId==6
                    vOrderinfId.setOsId(6);

                    boolean c = orderProcess.updateOSType(vOrderinfId);

                    if (c){
                        JOptionPane.showMessageDialog(null,
                                "修改成功!",
                                "注意",
                                JOptionPane.WARNING_MESSAGE);
                        jFrame.dispose();
                    }else {
                        JOptionPane.showMessageDialog(null,
                                "修改失败!",
                                "注意",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }else {
                    JOptionPane.showMessageDialog(null,
                            "不能取消,订单不符合要求!",
                            "注意",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        /*confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (check()){
                    vOrderinfId.setOrderId(orderId);
                    vOrderinfId.setOsId(osId);

                    OrderProcess orderProcess = new OrderProcess();
                    boolean c = orderProcess.updateOSType(vOrderinfId);

                    if (c){
                        JOptionPane.showMessageDialog(null,
                                "修改成功!",
                                "注意",
                                JOptionPane.WARNING_MESSAGE);
                        jFrame.dispose();
                    }else {
                        JOptionPane.showMessageDialog(null,
                                "修改失败!",
                                "注意",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });*/

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                jFrame.dispose();
            }
        });

    }

    public boolean checkDeliver(){

        if (orderType==7){
            return true;
        }
            return false;
    }

    public boolean checkCancel(){
        if (orderType==2||orderType==7){
            return true;
        }
            return false;
    }
    //主窗口获取
    public Object getFrame(){

        return this.jFrame;
    }
}

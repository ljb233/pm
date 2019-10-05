package com.pm.ui.manager;

import com.pm.dao.datasource.VOrderinfId;
import com.pm.process.OrderInfProcess;
import com.pm.process.OrderProcess;
import com.pm.util.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditOrder {

    private int OrderID,osId;
    private String orderId="";

    private JFrame jFrame;
    private JPanel jPanel1,
            jPanel2,
            jPanel3,
            jPanel4,
            jPanel5,
            jPanel6,
            ButtonPanel;

    private JTextField oIdText,
            createDateText,
            compDateText,
            goodsNameText,
            userNameText;
    private JComboBox osTypeBox;
    private JLabel oIdLabel,
            createDateLabel,
            compDateLabel,
            osTypeLabel,
            goodsNameLabel,
            userNameLabel;
    private JButton confirmButton,
            cancelButton;

    public EditOrder(){
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

        osTypeBox =new JComboBox();
        osTypeBox.addItem("--请选择--");
        osTypeBox.addItem("未支付");
        osTypeBox.addItem("已发货");
        osTypeBox.addItem("已完成");
        osTypeBox.addItem("无效");
        osTypeBox.addItem("已删除");
        osTypeBox.addItem("已取消");
        osTypeBox.addItem("未发货");

        goodsNameText = new JTextField(13);
        goodsNameText.setEditable(false);

        userNameText = new JTextField(13);
        userNameText.setEditable(false);

        confirmButton = new JButton("确定");
        cancelButton = new JButton("取消");

        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jPanel3 = new JPanel();
        jPanel4 = new JPanel();
        jPanel5 = new JPanel();
        jPanel6 = new JPanel();
        ButtonPanel = new JPanel();

        jPanel1.add(oIdLabel);
        jPanel1.add(oIdText);
        jPanel2.add(createDateLabel);
        jPanel2.add(createDateText);
        jPanel3.add(compDateLabel);
        jPanel3.add(compDateText);
        jPanel4.add(osTypeLabel);
        jPanel4.add(osTypeBox);
        jPanel5.add(goodsNameLabel);
        jPanel5.add(goodsNameText);
        jPanel6.add(userNameLabel);
        jPanel6.add(userNameText);
        ButtonPanel.add(confirmButton);
        ButtonPanel.add(cancelButton);

        jFrame = new JFrame("订单修改");
        jFrame.setSize(500,400);
        jFrame.setLocationRelativeTo(null);
        jFrame.setLayout(new GridLayout(7,1));
        jFrame.setResizable(false);

        jFrame.add(jPanel1);
        jFrame.add(jPanel2);
        jFrame.add(jPanel3);
        jFrame.add(jPanel4);
        jFrame.add(jPanel5);
        jFrame.add(jPanel6);
        jFrame.add(ButtonPanel);
        jFrame.setVisible(true);



    }


    public void go(int id) {
        this.OrderID = id;
        OrderInfProcess orderInfProcess = new OrderInfProcess();
        VOrderinfId vOrderinfId = orderInfProcess.getOrderInfByOId(OrderID);
        oIdText.setText(String.valueOf(vOrderinfId.getOrderId()));
        createDateText.setText(String.valueOf(vOrderinfId.getCreateDate()));
        compDateText.setText(String.valueOf(vOrderinfId.getCompDate()));
//        osTypeBox.setSelectedIndex(vOrderinfId.getOsId());
        goodsNameText.setText(vOrderinfId.getGoodsName());
        userNameText.setText(vOrderinfId.getUserName());

        confirmButton.addActionListener(new ActionListener() {
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
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                jFrame.dispose();
            }
        });

    }

    public boolean check(){
        orderId = oIdText.getText();
        osId = osTypeBox.getSelectedIndex();

        boolean c = new Tools().isALLIntger(orderId);

        if (c){
            return true;
        }
        return  false;
    }

    //主窗口获取
    public Object getFrame(){

        return 0;
    }
}

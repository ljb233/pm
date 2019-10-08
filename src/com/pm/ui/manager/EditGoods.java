package com.pm.ui.manager;

import com.pm.dao.datasource.Goods;
import com.pm.process.GoodsProcess;
import com.pm.util.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditGoods {
    private int ID;
    private String goodsId = "";
    private String goodsName = "";
    private int goodsPrice;
    private GoodsProcess goodsProcess;
    private Goods goods;

    private JFrame mainFrame;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;

    private JTextField idText;
    private JTextField goodsIdText;
    private JTextField goodsNameText;
    private JTextField goodsPriceText;

    private JLabel idLabel;
    private JLabel goodsIdLabel;
    private JLabel goodsNameLabel;
    private JLabel goodsPriceLabel;

    private JButton confirmButton;
    private JButton cancelButton;


    public EditGoods() {
        //初始化窗口
        idLabel = new JLabel("ID");
        idText = new JTextField();
        idText.setEditable(false);
        goodsIdLabel = new JLabel("商品编号(条形码编号)");
        goodsIdText = new JTextField(13);
        goodsNameLabel = new JLabel("商品名");
        goodsNameText = new JTextField(10);
        goodsPriceLabel = new JLabel("兑换价格");
        goodsPriceText = new JTextField(10);

        confirmButton = new JButton("确定");
        cancelButton = new JButton("取消");


        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();
        panel5 = new JPanel();

        //加载组件
        panel1.add(idLabel);
        panel1.add(idText);

        panel2.add(goodsIdLabel);
        panel2.add(goodsIdText);

        panel3.add(goodsNameLabel);
        panel3.add(goodsNameText);

        panel4.add(goodsPriceLabel);
        panel4.add(goodsPriceText);

        panel5.add(confirmButton);
        panel5.add(cancelButton);

        //设置主窗口属性
        mainFrame = new JFrame("修改商品信息");
        mainFrame.setSize(300,200);
        mainFrame.setBounds(600,200,300,220);
        mainFrame.setLayout(new GridLayout(5,1));
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);

        //加载组件
        mainFrame.add(panel1);
        mainFrame.add(panel2);
        mainFrame.add(panel3);
        mainFrame.add(panel4);
        mainFrame.add(panel5);
        mainFrame.setVisible(true);
    }

    public void go(int id){
        this.ID = id;
        callBackData();

        //确定按钮监听器
        confirmButton.addActionListener(e -> {
            if (check()){
                //装载新数据
                goods.setGoodsId(goodsId);
                goods.setGoodsName(goodsName);
                goods.setGoodsPrice(goodsPrice);

                //调用更新处理
                boolean c = goodsProcess.updateGoods(goods);

                if (c){
                    JOptionPane.showMessageDialog(null,
                            "修改成功！",
                            "注意",
                            JOptionPane.WARNING_MESSAGE);
                    mainFrame.dispose();
                }else{
                    JOptionPane.showMessageDialog(null,
                            "修改失败！",
                            "注意",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        //取消按钮监听器
        cancelButton.addActionListener(e -> mainFrame.dispose());

    }

    //回显数据
    private void callBackData(){
        goodsProcess = new GoodsProcess();
        goods = goodsProcess.getGoods(ID);
        idText.setText(String.valueOf(goods.getId()));
        goodsIdText.setText(goods.getGoodsId());
        goodsNameText.setText(goods.getGoodsName());
        goodsPriceText.setText(String.valueOf(goods.getGoodsPrice()));
    }

    //文本框的检查，判断输入的数据格式是否都正确
    private boolean check(){
        goodsId = goodsIdText.getText();
        goodsName = goodsNameText.getText();
        boolean c = new Tools().isALLIntger(goodsId);
        try {
            goodsPrice = Integer.parseInt(goodsPriceText.getText());
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,
                    "兑换价格错误！",
                    "注意",
                    JOptionPane.WARNING_MESSAGE);
        }

        if (goodsId.length() != 13 || !c) {
            JOptionPane.showMessageDialog(null,
                    "请输入正确的13位条形码！",
                    "注意",
                    JOptionPane.WARNING_MESSAGE);
        } else if (goodsName.equals("")) {
            JOptionPane.showMessageDialog(null,
                    "请输入商品名！",
                    "注意",
                    JOptionPane.WARNING_MESSAGE);
        } else if(goodsPrice < 0){
            JOptionPane.showMessageDialog(null,
                    "兑换价格错误！",
                    "注意",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            return true;
        }
        return false;
    }

    //获取主窗口属性
    Object getFrame(){
        return this.mainFrame;
    }
}

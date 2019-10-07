package com.pm.ui.manager;

import com.pm.dao.datasource.Goods;
import com.pm.process.GoodsProcess;
import com.pm.util.Tools;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class AddGoods{

    private String goodsId = "";
    private String goodsName = "";
    private int goodsPrice;
    private byte[] imageByteArray = null;
    private File selectedFile;

    private JFrame mainFrame;

    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;


    private JTextField goodsIdText;
    private JTextField goodsNameText;
    private JTextField goodsPriceText;

    private JLabel goodsIdLabel;
    private JLabel goodsNameLabel;
    private JLabel goodsPriceLabel;
    private JLabel imageNameLabel;
    private JLabel imageLabel;

    private JButton confirmButton;
    private JButton cancelButton;
    private JButton imageSelectButton;

    private JFileChooser chooser;

    public AddGoods() {
        //初始化窗口
        goodsIdLabel = new JLabel("商品编号(条形码编号)");
        goodsIdText = new JTextField(13);
        goodsNameLabel = new JLabel("商品名");
        goodsNameText = new JTextField(10);
        goodsPriceLabel = new JLabel("兑换价格");
        goodsPriceText = new JTextField(10);
        confirmButton = new JButton("确定");
        cancelButton = new JButton("取消");
        imageSelectButton = new JButton("选择图片");
        imageNameLabel = new JLabel("可选择商品图片");



        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel5 = new JPanel();
        imageLabel = new JLabel();

        //设置一个分割面板来显示商品图片和名字，使用上下分割布局
        JSplitPane jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        //上面为图片
        jSplitPane.setTopComponent(imageLabel);
        //下面为文件名字
        jSplitPane.setBottomComponent(imageNameLabel);
        //设置分割线大小为零，隐藏分割线
        jSplitPane.setDividerSize(0);

        //加载组件
        panel1.add(goodsIdLabel);
        panel1.add(goodsIdText);

        panel2.add(goodsNameLabel);
        panel2.add(goodsNameText);

        panel3.add(goodsPriceLabel);
        panel3.add(goodsPriceText);

        panel4.add(jSplitPane);
        panel4.add(imageSelectButton);

        panel5.add(confirmButton);
        panel5.add(cancelButton);

        //设置文件过滤器
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "选择PNG格式的图片", "png");
        //创建文件选择器，加载桌面路径
        chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        //加载过滤器
        chooser.setFileFilter(filter);

        //设置主窗口属性
        mainFrame = new JFrame("上架商品");
        mainFrame.setSize(300,400);
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

    public void go() {

        confirmButton.addActionListener(e -> {
            if (check()){
                Goods goods = new Goods();
                goods.setGoodsId(goodsId);
                goods.setGoodsName(goodsName);
                goods.setGoodsPrice(goodsPrice);

                GoodsProcess goodsProcess = new GoodsProcess();
                boolean c = goodsProcess.saveGoods(goods, imageByteArray);

                if (c){
                    JOptionPane.showMessageDialog(null,
                            "添加成功！",
                            "注意",
                            JOptionPane.WARNING_MESSAGE);
                    mainFrame.dispose();
                }else{
                    JOptionPane.showMessageDialog(null,
                            "添加失败！",
                            "注意",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(e -> {
            mainFrame.dispose();
        });

        //选择图片按钮监听器
        imageSelectButton.addActionListener(e -> {
            //显示文件选择器
            int returnValue = chooser.showOpenDialog(mainFrame);
            //在确定选择以后在主界面加载图片和名字，并将图片转换
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                if(selectedFile != null){
                    imageNameLabel.setText(selectedFile.getName());
                    imageLabel.setIcon(new ImageIcon(selectedFile.getAbsolutePath()));
                    imageByteArray = new Tools().imageToByte(selectedFile);
                }
            }
            //刷新组件
            panel4.updateUI();
        });
    }

    //文本框的检查，判断输入的数据格式是否都正确
    private boolean check(){
        goodsId = goodsIdText.getText();
        goodsName = goodsNameText.getText();
        //检查商品编号是否全为数字
        boolean c = new Tools().isALLIntger(goodsId);
        try {
            goodsPrice = Integer.parseInt(goodsPriceText.getText());
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,
                    "兑换价格错误！",
                    "注意",
                    JOptionPane.WARNING_MESSAGE);
            return false;
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

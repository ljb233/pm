package com.pm.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class Tools {

    //判断字符串是否全部是数字
    public boolean isALLIntger(String str){
        //使用正则判断
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public Date getNowDate() {
        //获取系统时间
        java.util.Date uDate = new java.util.Date();
        //设置时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return new java.sql.Date(sdf.parse(sdf.format(uDate)).getTime());
        }catch (ParseException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将图片文件转换为字节数组
     * @param file 待转图片
     * @return data 转换后的图片字节数组
     */
    public byte[] imageToByte(File file){
        byte[] data = null;
        try{
            //创建缓存
            BufferedImage bImage = ImageIO.read(file);
            //创建字节流
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            //写入
            ImageIO.write(bImage, "png", bos);
            //转换
            data = bos.toByteArray();
        }catch(Exception e){
            e.printStackTrace();
        }
        return data;
    }
}

package com.pm.ui.manager;

import com.pm.dao.datasource.Point;
import com.pm.dao.datasource.User;
import com.pm.dao.factory.PointDao;
import com.pm.dao.factory.UserDAO;
import com.pm.process.PointProcess;
import com.pm.process.UserProcess;
import com.pm.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * 增加用户界面以及处理逻辑
 *@Auther: linyang
 */
public class AddUser extends JFrame {
    JLabel tname;//标签
    JTextField name;//文本
    JButton btnadd;//按钮
    JPanel jp1, jp2;//面板

    JPanel jpmain = new JPanel();
    JFrame frame = new JFrame();

    String strname;
    private Session session;
    private  PointDao pointDao;
    private  UserDAO userDAO;

    /**
     * 开启事务
     */
    public AddUser() {
        session = HibernateUtils.getSession();
        userDAO = new UserDAO(session);
        pointDao = new PointDao(session);
    }

    /**
     * 主界面
     */
    public void Main() {

        jp1 = new JPanel();
        jp2 = new JPanel();

        tname = new JLabel("输入用户名:");
        name = new JTextField(10);

        jp1.add(tname);
        jp1.add(name);

        btnadd = new JButton("Add");
        jp2.add(btnadd);

        jpmain.setLayout(new GridLayout(1, 1));//网格式布局

        //将所有的面板加入主面板中
        //作用,在刷新界面时只需删除主面板即可
        jpmain.add(jp1);
        jpmain.add(jp2);

        //将主面板设置在界面上
        frame.add(jpmain);

        //设置窗体
        frame.setTitle("新增用户");//窗体标签
        frame.setSize(400, 150);//窗体大小
        frame.setLocationRelativeTo(null);//在屏幕中间显示(居中显示)
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//退出关闭JFrame
        frame.setVisible(true);//显示窗体
        frame.setResizable(false);//锁定窗体

        //设置按钮事件
        btnadd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findjtext();
                if (strname.equals("")) {
                    JOptionPane.showMessageDialog(null, "不能为空！", "提示", JOptionPane.WARNING_MESSAGE);
                } else {
                    //将输入框的值转入bean
                    User user = new User();
                    Point point = new Point();
                    //创建service对象,
                    UserProcess userProcess = new UserProcess();
                    PointProcess pointProcess = new PointProcess();
                    user.setUserName(strname);
                    user.setUserPwd("123456");
                    String userName = strname;

                    //执行添加用户方法,并将返回值存储在news里
                    boolean news = userProcess.insertUser(user);
                    Transaction transaction = session.beginTransaction();

                    int newid = 0;
                        newid = userDAO.queryIDByUserName(strname).getId();

                    point.setPointValue(0);
                    point.setUserId(newid);
                    point.setPtId(1);
                    pointDao.insertUsertopoint(point);
                    //未出现异常提交
                    transaction.commit();

                    //判断执行是否成功
                    if (news) {
                        JOptionPane.showMessageDialog(frame, "添加成功", "提示", JOptionPane.WARNING_MESSAGE);
                        frame.dispose();
                    }
                }
            }
        });
    }

    //获取输入框输入
    public void findjtext() {
        strname = name.getText().toString();
    }

    //获取主窗口属性
    public Object getFrame(){
        return this.frame;
    }

}
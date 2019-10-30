package chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;


/*class siliao implements Runnable, ActionListener {

	client c2=null;//
	
	public siliao(client c) {
		c2=c;
		JFrame jFrame = new JFrame();
        JPanel jp=new JPanel();
        jp.setSize(400,300);
        jp.setVisible(true);
        
        c.jTextArea.setEditable(false);
        JScrollPane js1 = new JScrollPane(c.jTextArea);
        js1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
       
        jp.add(js1,BorderLayout.NORTH);
        jp.add(c.jTextField,BorderLayout.SOUTH);
        jp.add(c.jButton,BorderLayout.SOUTH);
		
        jFrame.add(jp);
        c.jButton.addActionListener(this);
        c.jTextArea.setFont(new Font("宋体", Font.PLAIN,15));
        //jTextField.setBackground(Color.pink);
        jFrame.setSize(400,300);
        jFrame.setLocation(400,150);
        jFrame.setTitle("与" + c.userList.getSelectedValuesList().get(0) + "私聊中");
        jFrame.setVisible(true);
        jFrame.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {

            }

            public void windowClosing(WindowEvent e) {
                //flag = false;
                //JOptionPane.showMessageDialog(this, "您已成功退出！");
            	
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
	}
	
	
    public void run() {

    }


	@Override
	public void actionPerformed(ActionEvent e) {
        
		c2.handleSend();
		System.out.println(c2.isActive());
	}
    
}*/


public class client extends JFrame implements Runnable,ActionListener {

    //north——面板【north】说：我是一哥，这些小弟归我管理*************
	//负责软件美颜
	public JPanel north = new JPanel();
	
    public JMenuBar bar = new JMenuBar();
    public JMenu menu = new JMenu("软件");
    public JMenu menu2 = new JMenu("设置");
    public JMenuItem about = new JMenuItem("彩蛋");
    public JMenuItem exit = new JMenuItem("退出");
    public JMenuItem color_green = new JMenuItem("绿色");
    public JMenuItem color_yellow = new JMenuItem("黄色");
    public JMenuItem color_pink = new JMenuItem("粉色");
    
    
    //west——面板【west】说：那我吕明伟牛逼？*******************************
    //负责用户列表的工作
    public JPanel west = new JPanel();
    
    //创建并设置列表数据模型
    public DefaultListModel<String> dl = new DefaultListModel<String>();
    public JList<String> userList = new JList<String>(dl);
    //滚动面板加入用户列表（针对用户多的情况）
    public  JScrollPane listPane = new JScrollPane(userList);
    
    //center——【center】说：我宋志豪表示不服*****************************
    //负责文本交流管理
    public JPanel center = new JPanel();
    
    public JTextArea jta = new JTextArea(10,20);//这是文本域，就是聊天框
    public JScrollPane js = new JScrollPane(jta);
    public JPanel operPane = new JPanel();
    public JLabel input = new JLabel("请输入:");
    public JTextField jtf = new JTextField(24);//这是发送区域

   
    public JButton jbt = new JButton("发送消息");
    public JButton jbt1 = new JButton("私发消息");
    
    public BufferedReader br = null;
    public PrintStream ps = null;
    
    //用户名
    public String nickName = null;
    public JButton jButton = new JButton("发消息");
    public JTextArea jTextArea = new JTextArea(10,40);
    public JTextField jTextField = new JTextField(18);
    public String suser = new String();
    
    boolean flag = false;
    boolean flag_siliao=false;
//---------------------------------------------------------------------------------
    //构造函数
    public client() throws Exception{
    	
        //北边的布局--------------------------------
    	
        bar.add(menu);
        bar.add(menu2);
        menu.add(about);
        menu.add(exit);
        menu2.add(color_green);
        menu2.add(color_yellow);
        menu2.add(color_pink);
        
        about.addActionListener(this);
        exit.addActionListener(this);
        color_green.addActionListener(this);
        color_yellow.addActionListener(this);
        color_pink.addActionListener(this);
        
        BorderLayout bl = new BorderLayout();
        north.setLayout(bl);
        north.add(bar,BorderLayout.NORTH);
        add(north,BorderLayout.NORTH);
        
        //右边的布局---------------------------------
        
        //dimension是一个类，封装了一个组件的高度和宽度
        Dimension dim = new Dimension(100,150);
        west.setPreferredSize(dim);
        Dimension dim2 = new Dimension(100,300);
        listPane.setPreferredSize(dim2);
        /*Dimension dim3 = new Dimension(5,5);
        jbt1.setPreferredSize(dim3);*/
        
        
        BorderLayout bl2 = new BorderLayout();
        west.setLayout(bl2);
        west.add(listPane,BorderLayout.CENTER);//显示好友列表
        west.add(jbt1,BorderLayout.SOUTH);
        add(west,BorderLayout.EAST);
        userList.setFont(new Font("宋体",Font.BOLD,14));
        
        
        //中央布局---------------------------------------
        
        jta.setEditable(false);//你无法动这个控件一根毫毛
        //jtf.setBackground(Color.green);

        BorderLayout bl3 = new BorderLayout();
        center.setLayout(bl3);
        
        //流式布局
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
        operPane.setLayout(fl);
        operPane.add(input);
        operPane.add(jtf);
        operPane.add(jbt);
        //operPane.add(jbt1);
        center.add(js,BorderLayout.CENTER);
        center.add(operPane,BorderLayout.SOUTH);
        add(center,BorderLayout.CENTER);

        //设置垂直滚动条总是出现
        js.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        //鼠标事件，点击
        jbt.addActionListener(this);
        jbt1.addActionListener(this);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //this.setAlwaysOnTop(true);

        //界面
        nickName = JOptionPane.showInputDialog("输入用户名：");
    
        this.setTitle("【"+nickName+"】" + "的聊天室");
        this.setSize(600,400);
        this.setVisible(true);

        //客户端的连接
        Socket s = new Socket("127.0.0.1", 9999);
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        ps = new PrintStream(s.getOutputStream());
        
        new Thread(this).start();//线程启动
        
        //别的先不说，先给服务器发送我刚登录的名字
        ps.println("LOGIN#" + nickName);

        //键盘事件，实现当输完要发送的内容后，直接按【回车键】，实现发送
        //监听键盘相应的控件必须是获得焦点（focus）的情况下才能起作用
        //不然的话其他能响应键盘事件的控件也会跟着有反应动作
       jtf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {//当你按回车

                   ps.println("MSG#" + nickName + "#" +  jtf.getText());
                    
                    jtf.setText("");
                }
            }
        });

        jtf.setFocusable(true);//全场焦点发送框

        //监听系统关闭事件，退出时给服务器端发出指定消息
        //服务器得知道你要下线了OFFLINE
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ps.println("OFFLINE#" + nickName);
            }
        });

    }
    
    //线程要干什么呢
    public void run(){
        while (true){
            try{
            	
            	//随时保证你是假的
                if(flag_siliao) {
                    suser = userList.getSelectedValuesList().get(0);//得到这个用户的名字
                    
                    handleSec();//开启聊天,刚打开要清屏幕
                    
                    flag_siliao=false;
                }
            	
            	//*********************************************************************
                String msg = br.readLine();//死等服务器消息
                //System.out.println("服务器说："+msg);
                String[] strs = msg.split("#");
                
                
                //判断是否为服务器发来的登陆信息
                if(strs[0].equals("LOGIN")){
                    if(!strs[1].equals(nickName)){
                        jta.append("[系统提示]："+strs[1] + "上线\n");
                        dl.addElement(strs[1]);//添加用户
                        userList.repaint();//重绘组件
                    }
                }else if(strs[0].equals("MSG")){
                    if(!strs[1].equals(nickName)){
                        jta.append("【"+strs[1] + "】: " + strs[2] + "\n");
                    }else{
                        jta.append("[我]：" + strs[2] + "\n");
                    }
                }else if(strs[0].equals("USERS")){
                    dl.addElement(strs[1]);
                    userList.repaint();
                } else if(strs[0].equals("ALL")){
                    jta.append("[系统消息]：" + strs[1] + "\n");
                }else if(strs[0].equals("OFFLINE")){
                    jta.append("[系统提示]："+strs[1] + "下线\n");
                    dl.removeElement(strs[1]);//移除信息
                    userList.repaint();
                    //以下是私信部分
                }else if((strs[2].equals(nickName) || strs[1].equals(nickName)) && strs[0].equals("SMSG")){
                    if(!strs[1].equals(nickName)){
                        jTextArea.append(strs[1] + "说：" + strs[3] + "\n");
                    }else{
                        jTextArea.append("我说：" + strs[3] + "\n");
                    }
                }else if((strs[2].equals(nickName) || strs[1].equals(nickName))&& strs[0].equals("FSMSG")){//只有发信人和私信人能看（第一次）
                    if(strs[2].equals(nickName)){//如果被私信人是自己则显示系统消息
                        jTextArea.append(strs[1] + "说：" + strs[3] + "\n");
                        jta.append("[系统提示]：" + strs[1] + "私信" + "\n");
                    }else{//若自己为发信人
                       jTextArea.append( "我说：" + strs[3] + "\n");
                    }
                }
                
                
            }catch (Exception ex){
                ex.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(this, "您已被系统请出聊天室！");
                ps.println("OFFLINE#" + nickName);
                System.exit(0);
                //System.out.println("输入内容是空的吗");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object label = e.getSource();//得到组件的名称
        if(label==jbt){
        	//群发
            handleSend();
        }else if(label==jbt1 && !userList.isSelectionEmpty()){
        	//你第一步不是点击按钮而是选择列表元素
        	//未点击用户不执行
        	flag_siliao=true;
        	new Thread(this).start();//开启用户线程A

        }else if(label==jButton){
            handleSS();
        }else if(label==about){
            JOptionPane.showMessageDialog(this, "吕明伟牛逼");
        }else if(label==exit){
            JOptionPane.showMessageDialog(this, "您已成功退出！");
            ps.println("OFFLINE#" + nickName);
            System.exit(0);
        }else if(label==color_green){
        	jtf.setBackground(Color.green);
        }else if(label==color_yellow){
        	jtf.setBackground(Color.yellow);
        }else if(label==color_pink){
        	jtf.setBackground(Color.pink);
        } else{
            System.out.println("不知道你想干什么");
        }
    }

    //私聊消息按钮
    public void handleSS(){
   
    	if(!jTextField.getText().equals("")) {
    	
        if(flag){
            ps.println("SMSG#" + nickName + "#" + suser + "#" + jTextField.getText());
            jTextField.setText("");
        }else{
        	//首次私信格式为"FSMSG#  发信人  # 收信人 # 内容
            ps.println("FSMSG#" + nickName + "#" + suser + "#" + jTextField.getText());
            jTextField.setText("");
            flag = true;
        }
        }

    }//私聊中

    public void handleSend(){
        //发送信息时标识一下来源
    	if(!jtf.getText().equals("")) {
        ps.println("MSG#" + nickName + "#" +  jtf.getText());
        //发送完后，是输入框中内容为空
        jtf.setText("");}
    }//群聊

    //私聊
    public void handleSec(){
    	//new siliao(this);
        JFrame jFrame = new JFrame();
        JPanel jp=new JPanel();
        
        jp.setSize(400,300);
        jp.setVisible(true);
        
        jTextArea.setEditable(false);
        JScrollPane js1 = new JScrollPane(jTextArea);
        js1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
       
        jp.add(js1,BorderLayout.NORTH);
        jp.add(jTextField,BorderLayout.SOUTH);
        jp.add(jButton,BorderLayout.SOUTH);
        

         jFrame.add(jp);
        jButton.addActionListener(this);
        jTextArea.setFont(new Font("宋体", Font.PLAIN,15));

        jFrame.setSize(400,300);
        jFrame.setLocation(400,150);
        jFrame.setTitle("与" + userList.getSelectedValuesList().get(0) + "私聊中");
        jFrame.setVisible(true);
        jFrame.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {

            }

            public void windowClosing(WindowEvent e) {
            	jTextArea.setText("");
                flag = false;
                
                //System.out.println("您已成功退出！");
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }//私聊窗口

    public static void main(String[] args)throws Exception{
        new client();
    }
}
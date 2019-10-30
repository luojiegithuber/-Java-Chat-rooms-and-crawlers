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
        c.jTextArea.setFont(new Font("����", Font.PLAIN,15));
        //jTextField.setBackground(Color.pink);
        jFrame.setSize(400,300);
        jFrame.setLocation(400,150);
        jFrame.setTitle("��" + c.userList.getSelectedValuesList().get(0) + "˽����");
        jFrame.setVisible(true);
        jFrame.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {

            }

            public void windowClosing(WindowEvent e) {
                //flag = false;
                //JOptionPane.showMessageDialog(this, "���ѳɹ��˳���");
            	
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

    //north������塾north��˵������һ�磬��ЩС�ܹ��ҹ���*************
	//�����������
	public JPanel north = new JPanel();
	
    public JMenuBar bar = new JMenuBar();
    public JMenu menu = new JMenu("���");
    public JMenu menu2 = new JMenu("����");
    public JMenuItem about = new JMenuItem("�ʵ�");
    public JMenuItem exit = new JMenuItem("�˳�");
    public JMenuItem color_green = new JMenuItem("��ɫ");
    public JMenuItem color_yellow = new JMenuItem("��ɫ");
    public JMenuItem color_pink = new JMenuItem("��ɫ");
    
    
    //west������塾west��˵����������ΰţ�ƣ�*******************************
    //�����û��б�Ĺ���
    public JPanel west = new JPanel();
    
    //�����������б�����ģ��
    public DefaultListModel<String> dl = new DefaultListModel<String>();
    public JList<String> userList = new JList<String>(dl);
    //�����������û��б�����û���������
    public  JScrollPane listPane = new JScrollPane(userList);
    
    //center������center��˵������־����ʾ����*****************************
    //�����ı���������
    public JPanel center = new JPanel();
    
    public JTextArea jta = new JTextArea(10,20);//�����ı��򣬾��������
    public JScrollPane js = new JScrollPane(jta);
    public JPanel operPane = new JPanel();
    public JLabel input = new JLabel("������:");
    public JTextField jtf = new JTextField(24);//���Ƿ�������

   
    public JButton jbt = new JButton("������Ϣ");
    public JButton jbt1 = new JButton("˽����Ϣ");
    
    public BufferedReader br = null;
    public PrintStream ps = null;
    
    //�û���
    public String nickName = null;
    public JButton jButton = new JButton("����Ϣ");
    public JTextArea jTextArea = new JTextArea(10,40);
    public JTextField jTextField = new JTextField(18);
    public String suser = new String();
    
    boolean flag = false;
    boolean flag_siliao=false;
//---------------------------------------------------------------------------------
    //���캯��
    public client() throws Exception{
    	
        //���ߵĲ���--------------------------------
    	
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
        
        //�ұߵĲ���---------------------------------
        
        //dimension��һ���࣬��װ��һ������ĸ߶ȺͿ��
        Dimension dim = new Dimension(100,150);
        west.setPreferredSize(dim);
        Dimension dim2 = new Dimension(100,300);
        listPane.setPreferredSize(dim2);
        /*Dimension dim3 = new Dimension(5,5);
        jbt1.setPreferredSize(dim3);*/
        
        
        BorderLayout bl2 = new BorderLayout();
        west.setLayout(bl2);
        west.add(listPane,BorderLayout.CENTER);//��ʾ�����б�
        west.add(jbt1,BorderLayout.SOUTH);
        add(west,BorderLayout.EAST);
        userList.setFont(new Font("����",Font.BOLD,14));
        
        
        //���벼��---------------------------------------
        
        jta.setEditable(false);//���޷�������ؼ�һ����ë
        //jtf.setBackground(Color.green);

        BorderLayout bl3 = new BorderLayout();
        center.setLayout(bl3);
        
        //��ʽ����
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
        operPane.setLayout(fl);
        operPane.add(input);
        operPane.add(jtf);
        operPane.add(jbt);
        //operPane.add(jbt1);
        center.add(js,BorderLayout.CENTER);
        center.add(operPane,BorderLayout.SOUTH);
        add(center,BorderLayout.CENTER);

        //���ô�ֱ���������ǳ���
        js.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        //����¼������
        jbt.addActionListener(this);
        jbt1.addActionListener(this);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //this.setAlwaysOnTop(true);

        //����
        nickName = JOptionPane.showInputDialog("�����û�����");
    
        this.setTitle("��"+nickName+"��" + "��������");
        this.setSize(600,400);
        this.setVisible(true);

        //�ͻ��˵�����
        Socket s = new Socket("127.0.0.1", 9999);
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        ps = new PrintStream(s.getOutputStream());
        
        new Thread(this).start();//�߳�����
        
        //����Ȳ�˵���ȸ������������Ҹյ�¼������
        ps.println("LOGIN#" + nickName);

        //�����¼���ʵ�ֵ�����Ҫ���͵����ݺ�ֱ�Ӱ����س�������ʵ�ַ���
        //����������Ӧ�Ŀؼ������ǻ�ý��㣨focus��������²���������
        //��Ȼ�Ļ���������Ӧ�����¼��Ŀؼ�Ҳ������з�Ӧ����
       jtf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {//���㰴�س�

                   ps.println("MSG#" + nickName + "#" +  jtf.getText());
                    
                    jtf.setText("");
                }
            }
        });

        jtf.setFocusable(true);//ȫ�����㷢�Ϳ�

        //����ϵͳ�ر��¼����˳�ʱ���������˷���ָ����Ϣ
        //��������֪����Ҫ������OFFLINE
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ps.println("OFFLINE#" + nickName);
            }
        });

    }
    
    //�߳�Ҫ��ʲô��
    public void run(){
        while (true){
            try{
            	
            	//��ʱ��֤���Ǽٵ�
                if(flag_siliao) {
                    suser = userList.getSelectedValuesList().get(0);//�õ�����û�������
                    
                    handleSec();//��������,�մ�Ҫ����Ļ
                    
                    flag_siliao=false;
                }
            	
            	//*********************************************************************
                String msg = br.readLine();//���ȷ�������Ϣ
                //System.out.println("������˵��"+msg);
                String[] strs = msg.split("#");
                
                
                //�ж��Ƿ�Ϊ�����������ĵ�½��Ϣ
                if(strs[0].equals("LOGIN")){
                    if(!strs[1].equals(nickName)){
                        jta.append("[ϵͳ��ʾ]��"+strs[1] + "����\n");
                        dl.addElement(strs[1]);//����û�
                        userList.repaint();//�ػ����
                    }
                }else if(strs[0].equals("MSG")){
                    if(!strs[1].equals(nickName)){
                        jta.append("��"+strs[1] + "��: " + strs[2] + "\n");
                    }else{
                        jta.append("[��]��" + strs[2] + "\n");
                    }
                }else if(strs[0].equals("USERS")){
                    dl.addElement(strs[1]);
                    userList.repaint();
                } else if(strs[0].equals("ALL")){
                    jta.append("[ϵͳ��Ϣ]��" + strs[1] + "\n");
                }else if(strs[0].equals("OFFLINE")){
                    jta.append("[ϵͳ��ʾ]��"+strs[1] + "����\n");
                    dl.removeElement(strs[1]);//�Ƴ���Ϣ
                    userList.repaint();
                    //������˽�Ų���
                }else if((strs[2].equals(nickName) || strs[1].equals(nickName)) && strs[0].equals("SMSG")){
                    if(!strs[1].equals(nickName)){
                        jTextArea.append(strs[1] + "˵��" + strs[3] + "\n");
                    }else{
                        jTextArea.append("��˵��" + strs[3] + "\n");
                    }
                }else if((strs[2].equals(nickName) || strs[1].equals(nickName))&& strs[0].equals("FSMSG")){//ֻ�з����˺�˽�����ܿ�����һ�Σ�
                    if(strs[2].equals(nickName)){//�����˽�������Լ�����ʾϵͳ��Ϣ
                        jTextArea.append(strs[1] + "˵��" + strs[3] + "\n");
                        jta.append("[ϵͳ��ʾ]��" + strs[1] + "˽��" + "\n");
                    }else{//���Լ�Ϊ������
                       jTextArea.append( "��˵��" + strs[3] + "\n");
                    }
                }
                
                
            }catch (Exception ex){
                ex.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(this, "���ѱ�ϵͳ��������ң�");
                ps.println("OFFLINE#" + nickName);
                System.exit(0);
                //System.out.println("���������ǿյ���");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object label = e.getSource();//�õ����������
        if(label==jbt){
        	//Ⱥ��
            handleSend();
        }else if(label==jbt1 && !userList.isSelectionEmpty()){
        	//���һ�����ǵ����ť����ѡ���б�Ԫ��
        	//δ����û���ִ��
        	flag_siliao=true;
        	new Thread(this).start();//�����û��߳�A

        }else if(label==jButton){
            handleSS();
        }else if(label==about){
            JOptionPane.showMessageDialog(this, "����ΰţ��");
        }else if(label==exit){
            JOptionPane.showMessageDialog(this, "���ѳɹ��˳���");
            ps.println("OFFLINE#" + nickName);
            System.exit(0);
        }else if(label==color_green){
        	jtf.setBackground(Color.green);
        }else if(label==color_yellow){
        	jtf.setBackground(Color.yellow);
        }else if(label==color_pink){
        	jtf.setBackground(Color.pink);
        } else{
            System.out.println("��֪�������ʲô");
        }
    }

    //˽����Ϣ��ť
    public void handleSS(){
   
    	if(!jTextField.getText().equals("")) {
    	
        if(flag){
            ps.println("SMSG#" + nickName + "#" + suser + "#" + jTextField.getText());
            jTextField.setText("");
        }else{
        	//�״�˽�Ÿ�ʽΪ"FSMSG#  ������  # ������ # ����
            ps.println("FSMSG#" + nickName + "#" + suser + "#" + jTextField.getText());
            jTextField.setText("");
            flag = true;
        }
        }

    }//˽����

    public void handleSend(){
        //������Ϣʱ��ʶһ����Դ
    	if(!jtf.getText().equals("")) {
        ps.println("MSG#" + nickName + "#" +  jtf.getText());
        //��������������������Ϊ��
        jtf.setText("");}
    }//Ⱥ��

    //˽��
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
        jTextArea.setFont(new Font("����", Font.PLAIN,15));

        jFrame.setSize(400,300);
        jFrame.setLocation(400,150);
        jFrame.setTitle("��" + userList.getSelectedValuesList().get(0) + "˽����");
        jFrame.setVisible(true);
        jFrame.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {

            }

            public void windowClosing(WindowEvent e) {
            	jTextArea.setText("");
                flag = false;
                
                //System.out.println("���ѳɹ��˳���");
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
    }//˽�Ĵ���

    public static void main(String[] args)throws Exception{
        new client();
    }
}
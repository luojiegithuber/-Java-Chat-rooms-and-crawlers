package chat;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


class server extends JFrame implements Runnable, ListSelectionListener, ActionListener {
    private Socket s = null;
    private ServerSocket ss = null;
    private ArrayList<ChatThread> users = new ArrayList<ChatThread>();
    DefaultListModel<String> dl = new DefaultListModel<String>();
    private JList<String> userList = new JList<String>(dl);

    private JPanel jpl = new JPanel();
    private JButton jbt = new JButton("�߳�������");
    private JButton jbt1 = new JButton("Ⱥ����Ϣ");
    //Ⱥ����Ϣ������
    private JTextField jtf = new JTextField();


    public server() throws Exception{
        this.setTitle("��������");
        this.add(userList, "North");//���ڱ���
        this.add(jpl, "South");

        //����Ⱥ����Ϣ��������Ϊһ��
        jtf.setColumns(1);

        jtf.setBackground(Color.white);
        jpl.setLayout(new BorderLayout());
        jpl.add(jtf, BorderLayout.NORTH);
        jpl.add(jbt,BorderLayout.EAST);//�߳�������
        jpl.add(jbt1, BorderLayout.WEST);//Ⱥ����Ϣ

        //ʵ��Ⱥ��
        jbt1.addActionListener(this);
        //ʵ������
        jbt.addActionListener(this);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocation(400,100);
        this.setSize(300, 400);
        this.setVisible(true);
        this.setAlwaysOnTop(true);
        ss = new ServerSocket(9999);
        new Thread(this).start();
    }
    @Override
    public void run() {
        while(true){
            try{
                s = ss.accept();
                ChatThread ct = new ChatThread(s);
                users.add(ct);
                //����list����û���½��Ϣ
                ListModel<String> model = userList.getModel();
                for(int i = 0; i < model.getSize(); i++){
                    ct.ps.println("USERS#" + model.getElementAt(i));
                }
                ct.start();
            }catch (Exception ex){
                ex.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(this,"�������쳣��");
                System.exit(0);
            }
        }
    }

    //Listѡ���¼�����
    @Override
    public void valueChanged(ListSelectionEvent e) {

    }

    //Ⱥ����Ϣ��ť����¼�����
    @Override
    public void actionPerformed(ActionEvent e) {
        String label = e.getActionCommand();
        if(label.equals("Ⱥ����Ϣ")){
            handleAll();
        }else if(label.equals("�߳�������")){
            try {
                handleExpel();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void handleAll(){
        if(!jtf.getText().equals("")){
            sendMessage("ALL#" + jtf.getText());
            //��������������������Ϊ��
            jtf.setText("");
        }
    }//Ⱥ����Ϣ

    public void handleExpel() throws IOException {
        users.get(userList.getAnchorSelectionIndex()).s.close();//�ر��׽���

        sendMessage("OFFLINE#" + userList.getSelectedValuesList().get(0));
        dl.removeElement(userList.getSelectedValuesList().get(0));
        userList.repaint();
    }//����

    public class ChatThread extends Thread{
        Socket s = null;
        private BufferedReader br = null;
        private PrintStream ps = null;
        public boolean canRun = true;
        String nickName = null;
        public ChatThread(Socket s) throws Exception{
            this.s = s;
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            ps = new PrintStream(s.getOutputStream());
        }
        public void run(){
            while(canRun){
                try{
                    String msg = br.readLine();

                    String[] strs = msg.split("#");
                    if(strs[0].equals("LOGIN")){//�յ����Կͻ��˵�������Ϣ
                        nickName = strs[1];
                        dl.addElement(nickName);
                        sendMessage(msg);
                    }else if(strs[0].equals("MSG") || strs[0].equals("SMSG") || strs[0].equals("FSMSG")){
                        sendMessage(msg);
                    }else if(strs[0].equals("OFFLINE")){//�յ����Կͻ��˵�������Ϣ
                        sendMessage(msg);
                        //System.out.println(msg);
                        dl.removeElement(strs[1]);
                        // ����List�б�
                        userList.repaint();

                    
                    }
                    
                }catch (Exception ex){}
            }
        }
    }

    public void sendMessage(String msg){//���͸������û�
        for(ChatThread ct : users){
            ct.ps.println(msg);
        }
    }

    public static void main(String[] args)throws Exception{
        new server();
    }


}

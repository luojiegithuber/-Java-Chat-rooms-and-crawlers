package test;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import org.jsoup.nodes.Document;
public class Main extends JFrame implements ActionListener{
	private static String pageEncode="UTF8";//������Ҫ��������ҳ�ı���
	static String url;//��ַ
	JPanel jp=null;
	JButton jb1=null;
	JButton jb2=null;
	JButton jb3=null;
	JScrollPane scrollPane=null;
	JTextArea textArea=null;
	HashSet hs = new HashSet();
	
	
/***********************************************************************************************/	
	public static void main(String[] args) throws IOException {
		
		new Main();	
		//https://baike.baidu.com/item/%E7%99%BE%E5%BA%A6/6699?fromtitle=baidu&fromid=107002&fr=aladdin
		//https://www.bilibili.com/
		//url="https://baike.baidu.com/item/%E7%99%BE%E5%BA%A6/6699?fromtitle=baidu&fromid=107002&fr=aladdin";
		url=JOptionPane.showInputDialog("��������ַ");


	}

	
	
/*************************************************************************************************/	
	public  Main() {
		this.setSize(200, 200);
		this.setResizable(false);
		JPanel jp=new JPanel();
		
		textArea=new JTextArea();
		textArea.setColumns(30);
		textArea.setRows(10);
		textArea.setLineWrap(true);
		
		scrollPane=new JScrollPane();
		scrollPane.setViewportView(textArea);
		Dimension dime =textArea.getPreferredSize();
		scrollPane.setBounds(300,300,dime.width, dime.height);
		
		jb1=new JButton("��ʾ��ҳ����");
		jb2=new JButton("��ȡ���д�");
		jb3=new JButton("������ȡ");
		
		
	    jb1.addActionListener(this);
	    jb2.addActionListener(this);
	    jb3.addActionListener(this);

		jp.add(scrollPane);
		jp.add(jb1);
		jp.add(jb2);
		jp.add(jb3);

		this.add(jp);
		
		this.setTitle("����");
		this.setSize(600,400);
		this.setLocation(100,100);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
		add_words(readFile("D:\\java����\\���дʿ�.txt"));
		
	}


/********************************************************************************************/
	static String getcontext(String urlstring) throws IOException{
		Document doc = Jsoup.connect(urlstring).get();
				/*.data("query", "Java")//�������
				 .userAgent("Mozilla")//����agent
				 .cookie("auth", "token")//����cookie
				 .timeout(3000)//���ó�ʱʱ��
				 .post();//��post��������*/
        
        
        System.out.println(doc.text());
        return doc.text();

		}
/*********************************************************************************************/	
	static void gethtml(String urlstring) throws IOException{
		InputStream is = null;
		BufferedReader in = null;
		StringBuilder htmlSource= new StringBuilder();
	
		try {
			//1.��ȡ��ַ
			URL u = new URL(urlstring);
			//2.������
			URLConnection conn = u.openConnection();
			//3.��ȡ������
			is = conn.getInputStream();
			//4.��Դ����д���ڴ�(���ñ���)
			in = new BufferedReader(new InputStreamReader(is,"utf-8"));
			String str = "";
			while((str = in.readLine()) != null){
			htmlSource.append(str);
			}
			} catch (Exception e) {
			e.printStackTrace();
			}finally{
			//�ر�I/o
			try {
			if(in != null)in.close();
			if(is != null)is.close();
			} catch (IOException e) {
			e.printStackTrace();
			}
		
		}

		
	//System.out.println(htmlSource.toString());
	//�����ļ���
		String s=htmlSource.toString();
		byte[] sourceByte =s.getBytes();
		if(null!=sourceByte) {
			try {
				File file=new File("D:\\java����\\HTML.txt");
				FileOutputStream os = new FileOutputStream(file); //�ļ���������ڽ�����д���ļ�

				os.write(sourceByte);

				os.close(); //�ر��ļ������
			}catch (Exception e) {e.printStackTrace();}
		}
	}
	
/*********************************************************************************************************/	
	
    public void highLight(String keyWord) {
    	
    	Highlighter highLighter = textArea.getHighlighter();
    	String text = textArea.getText();
    	DefaultHighlighter.DefaultHighlightPainter p = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
    	//keyWord = "�ٶ�";
    	int pos = 0;
    	while ((pos = text.indexOf(keyWord, pos)) >= 0)
    	{
    	try{
    	highLighter.addHighlight(pos, pos + keyWord.length(), p);
    	pos += keyWord.length();
    	}
    	catch (BadLocationException e){
    	e.printStackTrace();
    	}
    	}

    }
	
    public String readFile(String pathname) {
         
         String line = null;
         String koko = null;
        //����·�������·�������ԣ�д���ļ�ʱ��ʾ���·��,��ȡ����·����input.txt�ļ�
        //��ֹ�ļ��������ȡʧ�ܣ���catch��׽���󲢴�ӡ��Ҳ����throw;
        //���ر��ļ��ᵼ����Դ��й¶����д�ļ���ͬ��
        //Java7��try-with-resources�������Źر��ļ����쳣ʱ�Զ��ر��ļ�����ϸ���https://stackoverflow.com/a/12665271
        try (FileReader reader = new FileReader(pathname);
             BufferedReader br = new BufferedReader(reader) // ����һ�����������ļ�����ת�ɼ�����ܶ���������
        ) { 
            while ((line = br.readLine()) != null) {
                // һ�ζ���һ������
            	koko=line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return koko;
    }

    public void add_words(String txt) { 
    	 
    	//�����ַ���
    	
    	String[] badword=txt.split(",");
    	
        for (String it : badword) {
       	 hs.add(it); ;
        }
    	
 
    	 } 
    	
    public void whlie_high() {
    	
   	 //����
   	 java.util.Iterator ite = hs.iterator(); 
   	 while(ite.hasNext()){ 
   	 String badword = (String)ite.next(); 
   	 highLight(badword); 
   	 }
    }

	/***********************************************************************************************************/


    //��ť
    public void actionPerformed (ActionEvent e) {
    	
    	if(e.getSource()==jb1) {
    		try {
    			
    		    textArea.setText(getcontext(url));
    			gethtml(url);
    			
    		} catch (Exception e1) {}

    	};
    	
    	if(e.getSource()==jb2) {
    		try {
    			whlie_high();
    		} catch (Exception e1) {}

    	};	
    	
    	if(e.getSource()==jb3) {
    		try {
            new WebGroup();
    		} catch (Exception e1) {}

    	};
    }
/*************************************************************************************/
}
	

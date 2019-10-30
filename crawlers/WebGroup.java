package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebGroup extends JFrame implements ActionListener{

	private static String pageEncode="UTF8";//������Ҫ��������ҳ�ı���
	static String url;//��ַ
	JPanel jp=null;
	JButton jb1=null;
	JButton jb2=null;
	JButton jb3=null;
	JScrollPane scrollPane=null;
	JTextArea textArea=null;
	HashSet hs = new HashSet();
	HashSet url_hs = new HashSet();

/*************************************************************************************************/	
	public  WebGroup() {
		this.setSize(300, 200);
		this.setResizable(false);
		JPanel jp=new JPanel();
		
		textArea=new JTextArea();
		textArea.setColumns(50);
		textArea.setRows(15);
		textArea.setLineWrap(true);
		
		scrollPane=new JScrollPane();
		scrollPane.setViewportView(textArea);
		Dimension dime =textArea.getPreferredSize();
		scrollPane.setBounds(300,300,dime.width, dime.height);
		
		jb1=new JButton("��ȡ������ַ");
		jb2=new JButton("��ȡ��ַ�ı�");
		jb3=new JButton("����������");
		
		
	    jb1.addActionListener(this);
	    jb2.addActionListener(this);
	    jb3.addActionListener(this);

		jp.add(scrollPane);
		jp.add(jb1);
		jp.add(jb2);
		jp.add(jb3);

		this.add(jp);
		
		this.setTitle("��������ȡ��");
		this.setSize(600,400);
		this.setLocation(200,200);
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
        
        
        //System.out.println(doc.text());
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
				File file=new File("D:\\java����\\1.txt");
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
    	DefaultHighlighter.DefaultHighlightPainter p = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
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
        // ����·�������·�������ԣ�д���ļ�ʱ��ʾ���·��,��ȡ����·����input.txt�ļ�
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

    //��ϣ�������д�
    public void add_words(String txt) { 
    	 
    	//�����ַ���
    	
    	String[] badword=txt.split(",");
    	
        for (String it : badword) {

       	 hs.add(it); 
        }
    	
 
    	 } 
    	
    
    
    public void whlie_high() {
    	
   	 //����
   	 java.util.Iterator ite = hs.iterator(); 
   	 while(ite.hasNext()){ 
   	 String str = (String)ite.next(); 
   	 highLight(str); 
   	 }
    }

	/***********************************************************************************************************/
   public void show_urls() {
	   	//����
	   	 java.util.Iterator ite = url_hs.iterator(); 
	   	 while(ite.hasNext()){ 
	   	 String str = (String)ite.next(); 
	   	 System.out.println(str); 
	   	 }
   }
   
   
   public void pachong_urls() {
	   	//����
	   	 java.util.Iterator ite = url_hs.iterator(); 
	   	 while(ite.hasNext()){ 
	   	 String str = (String)ite.next(); 
	     try {
	    	textArea.append("��"+str+"��\n");
			textArea.append(getcontext(str)+"\n");
			textArea.append("���д�:");
		   	 //����
		   	 java.util.Iterator ite2 = hs.iterator(); 
		   	 while(ite2.hasNext()){ 
		   	 String word = (String)ite2.next(); 
		   	 
		   	 if(getcontext(str).contains(word)) {
		   		 //��һ���������������дʣ�һ��һ���ԣ�
		   		 textArea.append(word+";"); 
		   	     }
		   	 }
			
		   	 
		   	textArea.append("\n\n");
			
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	   	 }
  }

   //���ı����ļ�
void  textarea_To_txt() throws Exception{  

    
	File fos=new File("D:\\java����\\������ַ�����дʼ�¼.txt");

	//fos.write(textArea.getText().getBytes());
    
	//fos.close();
    


	BufferedWriter bw = new BufferedWriter(new FileWriter(fos));
	String[] strs = textArea.getText().split("\n");
	for (String str : strs) {
	bw.write(str);
	bw.newLine();
	bw.flush();

	}

	
}
   
   
    /***********************************************************************************************************/
//��ȡ�ļ����ı�   
public void  readInStrings(File file) {
	    Reader reader = null;
	    try {
	        reader = new FileReader(file);
	        BufferedReader buf = new BufferedReader(reader);
	        int lines = 0;
	       String line = " ";
	       textArea.setText("");
	        while ((line= buf.readLine()) != null)
	        {
	            textArea.append(line+"\n");
	            url_hs.add(line); //˳�������ַ����ϣ
	            lines++;
	        }
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            reader.close();
	        } catch (IOException ex) {
	        }
	    }
	}
    //��ť
    public void actionPerformed (ActionEvent e) {
    	
    	//��ʾ��ַ
    	if(e.getSource()==jb1) {
    		try {
    			readInStrings(new File("D:\\java����\\������ַ.txt"));
    		} catch (Exception e1) {}

    	};
    	
    	if(e.getSource()==jb2) {
    		try {
    			
    			textArea.setText("");
    			//һ��һ�������õ��Ľ���ټ�����Ļ��
    			pachong_urls();
    			
    		} catch (Exception e1) {}

    	};	
    	
    	if(e.getSource()==jb3) {
    		try {
    			whlie_high();//����
    			textarea_To_txt();
    			//���浽�ı���
    		} catch (Exception e1) {}

    	};
    }
}


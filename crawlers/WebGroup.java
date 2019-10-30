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

	private static String pageEncode="UTF8";//定义需要操作的网页的编码
	static String url;//网址
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
		
		jb1=new JButton("爬取批量网址");
		jb2=new JButton("爬取网址文本");
		jb3=new JButton("高亮并保存");
		
		
	    jb1.addActionListener(this);
	    jb2.addActionListener(this);
	    jb3.addActionListener(this);

		jp.add(scrollPane);
		jp.add(jb1);
		jp.add(jb2);
		jp.add(jb3);

		this.add(jp);
		
		this.setTitle("【批量爬取】");
		this.setSize(600,400);
		this.setLocation(200,200);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
		add_words(readFile("D:\\java课设\\敏感词库.txt"));
		
		
	}


/********************************************************************************************/
	static String getcontext(String urlstring) throws IOException{
		Document doc = Jsoup.connect(urlstring).get();
				/*.data("query", "Java")//请求参数
				 .userAgent("Mozilla")//设置agent
				 .cookie("auth", "token")//设置cookie
				 .timeout(3000)//设置超时时间
				 .post();//用post方法访问*/
        
        
        //System.out.println(doc.text());
       return doc.text();

		}
/*********************************************************************************************/	
	static void gethtml(String urlstring) throws IOException{
		InputStream is = null;
		BufferedReader in = null;
		StringBuilder htmlSource= new StringBuilder();
	
		try {
			//1.获取网址
			URL u = new URL(urlstring);
			//2.打开连接
			URLConnection conn = u.openConnection();
			//3.获取输入流
			is = conn.getInputStream();
			//4.将源代码写入内存(设置编码)
			in = new BufferedReader(new InputStreamReader(is,"utf-8"));
			String str = "";
			while((str = in.readLine()) != null){
			htmlSource.append(str);
			}
			} catch (Exception e) {
			e.printStackTrace();
			}finally{
			//关闭I/o
			try {
			if(in != null)in.close();
			if(is != null)is.close();
			} catch (IOException e) {
			e.printStackTrace();
			}
		
		}

		
	//System.out.println(htmlSource.toString());
	//存入文件中
		String s=htmlSource.toString();
		byte[] sourceByte =s.getBytes();
		if(null!=sourceByte) {
			try {
				File file=new File("D:\\java课设\\1.txt");
				FileOutputStream os = new FileOutputStream(file); //文件输出流用于将数据写入文件

				os.write(sourceByte);

				os.close(); //关闭文件输出流
			}catch (Exception e) {e.printStackTrace();}
		}
	}
	
/*********************************************************************************************************/	
	
    public void highLight(String keyWord) {
    	
    	Highlighter highLighter = textArea.getHighlighter();
    	String text = textArea.getText();
    	DefaultHighlighter.DefaultHighlightPainter p = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
    	//keyWord = "百度";
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
        // 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
        //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
        //不关闭文件会导致资源的泄露，读写文件都同理
        //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
        try (FileReader reader = new FileReader(pathname);
             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) { 
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
            	koko=line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return koko;
    }

    //哈希加入敏感词
    public void add_words(String txt) { 
    	 
    	//处理字符串
    	
    	String[] badword=txt.split(",");
    	
        for (String it : badword) {

       	 hs.add(it); 
        }
    	
 
    	 } 
    	
    
    
    public void whlie_high() {
    	
   	 //遍历
   	 java.util.Iterator ite = hs.iterator(); 
   	 while(ite.hasNext()){ 
   	 String str = (String)ite.next(); 
   	 highLight(str); 
   	 }
    }

	/***********************************************************************************************************/
   public void show_urls() {
	   	//遍历
	   	 java.util.Iterator ite = url_hs.iterator(); 
	   	 while(ite.hasNext()){ 
	   	 String str = (String)ite.next(); 
	   	 System.out.println(str); 
	   	 }
   }
   
   
   public void pachong_urls() {
	   	//遍历
	   	 java.util.Iterator ite = url_hs.iterator(); 
	   	 while(ite.hasNext()){ 
	   	 String str = (String)ite.next(); 
	     try {
	    	textArea.append("【"+str+"】\n");
			textArea.append(getcontext(str)+"\n");
			textArea.append("敏感词:");
		   	 //遍历
		   	 java.util.Iterator ite2 = hs.iterator(); 
		   	 while(ite2.hasNext()){ 
		   	 String word = (String)ite2.next(); 
		   	 
		   	 if(getcontext(str).contains(word)) {
		   		 //这一大串文字若包含敏感词（一个一个试）
		   		 textArea.append(word+";"); 
		   	     }
		   	 }
			
		   	 
		   	textArea.append("\n\n");
			
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	   	 }
  }

   //从文本域到文件
void  textarea_To_txt() throws Exception{  

    
	File fos=new File("D:\\java课设\\批量网址的敏感词记录.txt");

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
//读取文件的文本   
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
	            url_hs.add(line); //顺便加入网址进哈希
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
    //按钮
    public void actionPerformed (ActionEvent e) {
    	
    	//显示网址
    	if(e.getSource()==jb1) {
    		try {
    			readInStrings(new File("D:\\java课设\\批量网址.txt"));
    		} catch (Exception e1) {}

    	};
    	
    	if(e.getSource()==jb2) {
    		try {
    			
    			textArea.setText("");
    			//一个一个爬，得到的结果再加在屏幕上
    			pachong_urls();
    			
    		} catch (Exception e1) {}

    	};	
    	
    	if(e.getSource()==jb3) {
    		try {
    			whlie_high();//高亮
    			textarea_To_txt();
    			//保存到文本里
    		} catch (Exception e1) {}

    	};
    }
}


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
	private static String pageEncode="UTF8";//定义需要操作的网页的编码
	static String url;//网址
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
		url=JOptionPane.showInputDialog("请输入网址");


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
		
		jb1=new JButton("显示网页内容");
		jb2=new JButton("提取敏感词");
		jb3=new JButton("批量爬取");
		
		
	    jb1.addActionListener(this);
	    jb2.addActionListener(this);
	    jb3.addActionListener(this);

		jp.add(scrollPane);
		jp.add(jb1);
		jp.add(jb2);
		jp.add(jb3);

		this.add(jp);
		
		this.setTitle("爬虫");
		this.setSize(600,400);
		this.setLocation(100,100);
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
        
        
        System.out.println(doc.text());
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
				File file=new File("D:\\java课设\\HTML.txt");
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
    	DefaultHighlighter.DefaultHighlightPainter p = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
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
        //绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
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

    public void add_words(String txt) { 
    	 
    	//处理字符串
    	
    	String[] badword=txt.split(",");
    	
        for (String it : badword) {
       	 hs.add(it); ;
        }
    	
 
    	 } 
    	
    public void whlie_high() {
    	
   	 //遍历
   	 java.util.Iterator ite = hs.iterator(); 
   	 while(ite.hasNext()){ 
   	 String badword = (String)ite.next(); 
   	 highLight(badword); 
   	 }
    }

	/***********************************************************************************************************/


    //按钮
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
	

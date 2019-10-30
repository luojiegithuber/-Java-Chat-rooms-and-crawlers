package test;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

public class TextAreaTest{
public static void main(String[] args)
{
JFrame frame = new JFrame();
JTextArea ta = new JTextArea(10, 20);
ta.setText("我想要打架，但是打架是不好的行为，这是一种暴力");
frame.add(new JScrollPane(ta));
Highlighter highLighter = ta.getHighlighter();
String text = ta.getText();
DefaultHighlighter.DefaultHighlightPainter p = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
String keyWord = "打架";
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
frame.pack();
frame.setVisible(true);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
}
//==============================取消所有高亮
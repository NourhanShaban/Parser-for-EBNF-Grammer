
package parser;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class SyntaxTree {
    private JFrame form;
    
    public SyntaxTree(JFrame f){
        this.form = f;
    }
    
    public int border = 1; // somk el panel elly hrsem 3leh.
    public Color color = Color.black;
    
    public void DrawLine(int x1, int y1, int x2, int y2){
        JPanel p1 = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(border));
                g2.setColor(color);
                g2.drawLine(x1, y1,x2 , y2);
            }
        };
        form.add(p1);
        p1.setBounds(0, 0, form.getWidth(), form.getHeight());
    }
    
    public void drawOval(int x, int y, int width, int height,String s){
       JPanel j1 = new JPanel(){
           @Override
           public void paintComponent(Graphics g){
               Graphics2D g2 = (Graphics2D)g;
               g2.setColor(Color.GRAY);
               g2.setStroke(new BasicStroke(border));
               g2.drawOval(x, y, width, height);
               g2.setColor(Color.black);
               g2.drawString(s, x+10, y+20);
           }
       };
       form.add(j1);
       j1.setBounds(0, 0, form.getWidth(), form.getHeight());
    }
    
    public void drawRectangle(int x, int y, int width, int height , String s){
       JPanel j1 = new JPanel(){
           @Override
           public void paintComponent(Graphics g){
               Graphics2D g2 = (Graphics2D)g;
               g2.setColor(Color.GRAY);
               g2.setStroke(new BasicStroke(border));
               g2.drawRect(x, y, width, height);
               g2.setColor(Color.black);
               g2.drawString(s, x+15, y+20);
           }
       };
       form.add(j1);
       j1.setBounds(0, 0, form.getWidth(), form.getHeight());
    }
    
    
}

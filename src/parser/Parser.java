/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.applet.Applet;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.util.LinkedList;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class Parser {

static Vector  <String> a=new Vector();
  static Vector  <String> b=new Vector();      
   static InputStream inputStream=null;
   static BufferedReader buf=null;
   static String line ;
      static int i=0;
     static BufferedWriter bwr;
	static BufferedWriter bw;
       static FileOutputStream fos;
      static TreeNode node;
      
     
      
   public static void fromFileToList()
   {
       
       try
       {
        
 
	
          InputStream inputStream = new FileInputStream("scanner_output.txt");
            BufferedReader buf = new BufferedReader(new InputStreamReader(inputStream));

            while ((line = buf.readLine()) != null) {
               int k=line.indexOf('&');
               a.add(line.substring(0,k));
               b.add(line.substring(k+1,line.length()));
                
            }
            a.add("");
            
         buf.close();
            
           
        
        }
       catch(Exception ee)
       {
         System.out.println("hello");
       }
   }

    public static boolean match(String s)
    {
        if(s.equals(a.get(i)))
        {
              
            i++;
        return true;
        }
        return false;
    }
            
   public static boolean verify (String s) throws IOException
   {
       if(match(s))
       {
          
           return true;
       }
      
           bw.write("invalid Symbol");
            bw.newLine();
       bw.close();
      
           System.exit(0);
           
       
       return false;
   }

   
    public static TreeNode Factor() throws IOException{
 TreeNode n= new TreeNode();
        if(match("("))
        {
             if(i==a.size()-1)
            {
                bw.write("Syntax error in factor");
                 bw.newLine();
                 bw.close();
              System.exit(0);  
            }
           n= exp();
        
                if(verify(")")){
                    bw.write("factor is found");
                     bw.newLine();
                }
                
            }
        
            
        else if(match("number")){
          bw.write("number is found");
          bw.newLine();
           int r= i-1;
           n.data="number"+" "+"("+b.get(r)+")";
           n.shape = "Oval";
            bw.write("Factor is found");
            bw.newLine();
            
        }
        
        else if(match("identifier")){
             bw.write("identifier is found");
             bw.newLine();
              int r= i-1;
        n.data="Identifier"+" "+"("+b.get(r)+")";
        n.shape = "Oval";
            bw.write("Factor is found");
            bw.newLine();
        }
        else
        {
            if(i==a.size()-1)
            {
              System.exit(0);  
            }
            else
            {
            bw.write("Syntax error in factor");
            bw.newLine();
            bw.close();
            System.exit(0);
            }
        }
        return n;
    }
    
    
    public static TreeNode term() throws IOException{
              TreeNode s=new TreeNode ();
            TreeNode f= Factor();
            try{
            while(match("*")||match("/") ){
               bw.write("Mulop is found");
               int r= i-1;
               s.data="op" +" "+"("+a.get(r)+")";
               s.shape = "Oval";
               s.left = f;
               
               bw.newLine();
               
              if(i==a.size()-1)
            {
                bw.write("Syntax error in factor");
                bw.newLine();
                bw.close();
              System.exit(0);  
            }
              TreeNode d=Factor();
              d.level=f.level;
              s.right = d; 
                f=s;
               
                }
            }
            catch(Exception ee)
            {
                
            }
            bw.write("Term is found");
            bw.newLine();
          return f;
            
            }
    public static TreeNode simple_Exp() throws IOException
    {    TreeNode s=new TreeNode ();
       TreeNode f= term();
        
        try{
            while(match("+")|| match("-")){
                bw.write("addop is found");
                bw.newLine();
                 int r= i-1;
                 s.data="op"+" "+"("+a.get(r)+")";
                 s.shape = "Oval";
               s.left = f;
                if(i==a.size()-1)
            {
               bw.write("Syntax error in simple_Exp");
               bw.newLine();
               bw.close();
              System.exit(0);  
            }
                TreeNode d=term();
              d.level=f.level;
              s.right = d; 
                f=s;
                    
               
                }
            }
            catch(Exception ee)
            {
                
            }
           bw.write("simple-exp is found");
           bw.newLine();
        
        return f;
    }
    
   public static TreeNode  exp () throws IOException
   {
        TreeNode s=new TreeNode ();
       TreeNode f=  simple_Exp();
   
      
       if(match("<")||match(">")||match("="))
       {   
            int r= i-1;
           s.data="op"+" "+"("+a.get(r)+")";
           s.shape = "Oval";
               s.left = f;
           bw.write("Comparsionop is found");
           bw.newLine();
            if(i==a.size()-1)
            {
                bw.write("Syntax error in exp");
                bw.newLine();
                bw.close();
              System.exit(0);  
            }
            TreeNode d=simple_Exp();
              d.level=f.level;
                s.right = d;
                f=s;
           
       }
       bw.write("exp is found");
       bw.newLine();
       return f;
   }
   
   public static TreeNode write_stmt() throws IOException
   {
       
       TreeNode s=new TreeNode ();
       if(match("write"))
       { int r= i-1;
           s.data="write";
           s.shape = "Rect";
           
    
           bw.write("write-word is found");
           bw.newLine();
            if(i==a.size()-1)
            {
                bw.write("Syntax error in write-stmt");
                bw.newLine();
                bw.close();
              System.exit(0);  
            }
          TreeNode d= exp();
          d.level=s.level--;
          s.middle = d;
           bw.write("write-stmt is found");
           bw.newLine();
       }
       return s;
   }
   public static TreeNode read_stmt() throws IOException
   {
       TreeNode d= new TreeNode ();
   
       if(match("read"))
       {
           d.data="read";
           d.shape = "Rect";
           bw.write("read-word is found");
           bw.newLine();
            if(i==a.size()-1)
            {
                bw.write("Syntax error in read_stmt");
                bw.newLine();
                bw.close();
              System.exit(0);  
            }
           verify("identifier");
            int r= i-1;
           d.data+= " " +"(" + b.get(r)+")";
       }
        bw.write("read_stmt is found");
        bw.newLine();
        return d;
   }
   
   public static TreeNode assign_stmt() throws IOException
   { TreeNode s=new TreeNode();
       if(match("identifier"))
       {
            int r= i-1;
           s.data="assign"+" "+"("+b.get(r)+")";
           s.shape = "Rect";
           bw.write("identifier is found");
           bw.newLine();
            if(i==a.size()-1)
            {
                bw.write("Syntax error in assign_stmt");
                bw.newLine();
                bw.close();
              System.exit(0);  
            }
          if( verify(":="))
          {  
              bw.write("assignment is found");
              bw.newLine();
              if(i==a.size()-1)
            {
                bw.write("Syntax error in assign_stmt");
                bw.newLine();
                bw.close();
              System.exit(0);  
            }
          TreeNode d= exp();
          d.level=s.level--;
          s.middle = d;
       }
       }
       bw.write("assign_stmt is found");
       bw.newLine();
       return s;
   }
   
   public static TreeNode repeat_stmt() throws IOException
   {
       
   TreeNode s=new TreeNode();
       if(match("repeat"))
       {
           s.data="repeat";
           s.shape = "Rect";
           int r= i;
           s.level=r--;
           
       
           bw.write("repeat-word is found");
           bw.newLine();
              
            if(i==a.size()-1)
            {
                bw.write("Syntax error in assign_stmt");
                bw.newLine();
                bw.close();
              System.exit(0);  
            }
           TreeNode d=stmt_sequence();
           d.level=s.level--;
           s.left = d;
           if(verify("until"))
           {
               
           
               bw.write("until-word is found");
               bw.newLine();
                if(i==a.size()-1)
            {
                bw.write("Syntax error in assign_stmt");
                bw.newLine();
                bw.close();
              System.exit(0);  
            }
              
               TreeNode e=exp();
               e.level=d.level;
               s.right = e;
           }
       }
       bw.write("repeat_stmt is found");
       bw.newLine();
      return s; 
   }
   public static TreeNode statement() throws IOException
   {
       
   TreeNode s=new TreeNode ();
       if(match("if"))
       {
           
           i--;
          s=if_stmt();
       }
       else if(match("repeat"))
       {
           
           i--;
          s= repeat_stmt();
       }
       else if(match("identifier"))
       {
          
           i--;
          s= assign_stmt();
       }
       
        else if(match("read"))
       {
        
           i--;
           s=read_stmt();
       }
        else if(match("write"))
       {
           
           i--;
           s=write_stmt();
       }
      
        
        
        
        else 
        {
            bw.write("Syntax error in statement");
            bw.newLine();
            bw.close();
            System.exit(0);
        }
     return s;
   }
   
   public static TreeNode if_stmt() throws IOException
   {
     TreeNode s=new TreeNode();
       if(match("if"))
       {
         s.data="if";
         s.shape = "Rect";
          int h= i;
         s.level= h--;
           bw.write("if-word is found");
           bw.newLine();
          TreeNode d= exp();
          d.level=s.level--;
          s.left = d;
          
            if(i==a.size()-1)
            {
               bw.write("Syntax error in if_stmt");
               bw.newLine();
               bw.close();
              System.exit(0);  
            }
           if(verify("then"))
           {
               bw.write("then-word is found");
               bw.newLine();
             TreeNode e=stmt_sequence (); 
             e.level=s.level--;
             s.middle = e;
           }
           
           if(match("else"))
           {
              bw.write("else-word is found");
              bw.newLine();
             TreeNode r=  stmt_sequence();
             r.level=s.level--;
             s.right = r;
           }
            if(i==a.size()-1)
            {
                bw.write("Syntax error in if_stmt");
                bw.newLine();
                bw.close();
              System.exit(0);  
            }
           
           if(verify("end"))
           {
               bw.write("end-word is found");
               bw.newLine();
               bw.write("if-stmt is found");
               bw.newLine();
           }
         
       }
       return s;
       
   }
   public static TreeNode stmt_sequence() throws IOException
   {
       
            TreeNode f = new TreeNode();
             f= statement();
     
      try{
            while(match(";") ){
                bw.write("semicolon is found");
           bw.newLine();
               
              
               TreeNode d=  statement(); 
               d.level=f.level;
               f.l1.add(d);
               
                }
            }
            catch(Exception ee)
            {
                
            }
      
      f.same = f.l1.get(0);
      for(int ii=1;ii<f.l1.size();ii++){
         f.l1.get(ii-1).same = f.l1.get(ii);
      }
      
            bw.write("stmt-sequence is found");
        bw.newLine();
       return f;
       
   }
   public static TreeNode program() throws IOException
   {
       
   node = stmt_sequence();
       bw.write("Program is found");
       bw.newLine();
       
      return node;
   }
           
    
    
        
        
        
    
    
    
   
    public static void main(String[] args) throws IOException {
       
        fromFileToList();
        File fout = new File("parser_output.txt");
         FileOutputStream fos = new FileOutputStream(fout);
 
	bw = new BufferedWriter(new OutputStreamWriter(fos));
              
                 TreeNode nn = program();
            
        bw.close();
    
       
        NewJFrame f = new NewJFrame();
        SyntaxTree t = new SyntaxTree(f);
        
        LinkedList <TreeNode> temp = new LinkedList<TreeNode>();//list
        nn.x = 100;
        nn.y = 50;
        temp.add(nn);
        int wid = 60;
        int height = 40;
        int L = 0;
        t.drawRectangle(temp.get(0).x, temp.get(0).y, wid, height,temp.get(0).data);
        int counter =0;
        while(counter != temp.size()){
            TreeNode T = temp.get(counter);
            if(T.left != null){
                TreeNode t2 = new TreeNode();
                t2 = T.left;
                if(T.left.same != null) {
                   t2.x = (T.x + L) - 250; 
                }
                else{
                t2.x = ( T.x + L ) - 160; 
                }
                t2.y = T.y + 50;
                if((t2.shape).equals("Rect")){
                    t.drawRectangle(t2.x, t2.y, wid, height, t2.data);
                     t.DrawLine(T.x, T.y+20, t2.x+60, t2.y+20);
                }
                else if((t2.shape).equals("Oval")){
                    t.drawOval(t2.x, t2.y, wid, height, t2.data);
                     t.DrawLine(T.x, T.y+20, t2.x+30, t2.y);
                }
               
                temp.add(t2);
                
            }
            if(T.right != null){
                TreeNode t2 = new TreeNode();
                t2 = T.right;
                if(T.left.same != null) {
                   t2.x = T.x + 230;
                   T.left.same.x = T.left.x + 280;
                }
                else{
                t2.x = (T.x - L) + 120; 
                }
                t2.y = T.y + 50;
                if((t2.shape).equals("Rect")){
                    t.drawRectangle(t2.x, t2.y, wid, height, t2.data);
                    t.DrawLine(T.x+60, T.y+20, t2.x, t2.y+20);
                }
                else if((t2.shape).equals("Oval")){
                    t.drawOval(t2.x, t2.y, wid, height, t2.data);
                    t.DrawLine(T.x+60, T.y+20, t2.x+30, t2.y);
                }
                
                
                temp.add(t2);
            }
            if(T.middle != null){
                TreeNode t2 = new TreeNode();
                t2 = T.middle;
                t2.x = T.x ; 
                t2.y = T.y + 160 ;
                if((t2.shape).equals("Rect")){
                    t.drawRectangle(t2.x, t2.y, wid, height, t2.data);
                    t.DrawLine(T.x+30, T.y+40, t2.x+30, t2.y);
                }
                else if((t2.shape).equals("Oval")){
                    t.drawOval(t2.x, t2.y, wid, height, t2.data);
                    t.DrawLine(T.x+30, T.y+40, t2.x+30, t2.y);
                }

                temp.add(t2);
            }
            if(T.same != null){
                TreeNode t2 = new TreeNode();
                t2 = T.same;
                if(t2.x ==0){
                t2.x = T.x + (400-L); 
                }
                t2.y = T.y;
                if((t2.shape).equals("Rect")){
                    t.drawRectangle(t2.x, t2.y, wid, height, t2.data);
                    t.DrawLine(T.x+60, T.y+20, t2.x, t2.y+20);
                }
                else if((t2.shape).equals("Oval")){
                    t.drawOval(t2.x, t2.y, wid, height, t2.data);
                    t.DrawLine(T.x+60, T.y+20, t2.x+30, t2.y);
                }
                
                
                temp.add(t2);
            }
            counter++;
            L = 40;
        }
        
         f.setVisible(true);
        
  

    
  
    

}
}

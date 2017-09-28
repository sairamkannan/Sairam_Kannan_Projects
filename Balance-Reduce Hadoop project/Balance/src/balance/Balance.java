
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package balance;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import java.io.File;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.awt.*;
import java.awt.Label;
import java.awt.event.*;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;


public class Balance implements ActionListener  {
public int TOTALSERVERS=0;
public int TOTALTASKS=0;
public static double  localcost=1.0;
public static int rmtasks=0;
server s[],spre[];// = new server[TOTALSERVERS];
task t[];
//task t[] = new task[TOTALTASKS];
public basenode bn[];// = new basenode[TOTALSERVERS+TOTALTASKS+1];
public basenode rg[];
public basenode flow[];
static Balance b;
int draw=0;
boolean btem=true;
JButton OK,Next,Finish,Server,Task,back,Exit,get,pre,Cal,linechart;
Label l,l1,fallocL[];
static int c=1;
JTextField tfser,tf1[],tftas,tf3[];
JFrame f1,f2,f3,fff,jf,jf1;
JFrame f;
JTextField taskP;
double sload[];
int server,ntask,taskn,tcount=0;    
boolean ntready=true;
     JTable jtb,jtb1;
    

/**
     * @param args the command line arguments
     */

public Balance(){
    
    
        
    f=new JFrame();
     f.setTitle("Enter the needed values");
     f.setBounds(400, 300, 30, 30);
    f.setSize(500,200);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Server =new JButton("server");
    Task =new JButton("task");
    OK =new JButton("OK");
    Exit=new JButton("quit");
    Cal=new JButton("calculate");
    tfser=new JTextField(5);
    tftas=new JTextField(5);
    l=new Label("Enter Server no:");
    l1=new Label("enter task no");
    OK.addActionListener(this);
    Server.addActionListener(this);
    Exit.addActionListener(this);
    Task.addActionListener(this);
    Cal.addActionListener(this);
    f.setLayout(new FlowLayout(FlowLayout.LEFT));
    f.add(l);
    f.add(tfser);
    f.add(Server);
    f.add(l1);
    f.add(tftas);
    
    f.add(Task);
    //f.add(OK);
    f.add(Cal);
    f.add(Exit);
    f.setVisible(true);
}
public Balance(int i ){
    
}

public void getTaskPreDetails(int t){
   f2=new JFrame();
    
    f2.setBounds(400, 300, 30, 30);
    f2.setSize(500,200);
    f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    System.out.println("inside preferred details function");
        back =new JButton("back");
        f2.setTitle("enter task preferences");
        f2.setLayout(new FlowLayout(FlowLayout.LEFT));
        tf3=new JTextField[t];
        //Label tl[]=new Label[ntask];
        back.addActionListener(this);
        Label ll=new Label("enter preferred servers for this task");
        f2.add(ll);
        for(int i=0;i<t;i++){
            tf3[i]=new JTextField(5);
            //f2.add(ll);
            f2.add(tf3[i]);
        }
    f2.add(back);
    f2.setVisible(true);
    
    

}
   public void  getTaskDetails(){
        
        f3=new JFrame();
        System.out.println("\n\ninside taskDetail()");
        f3.setTitle("enter prefered servers for task");
        f3.setBounds(400, 300, 30, 30);
        f3.setSize(500,200);
        f3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         get =new JButton("Get");
         pre =new JButton("pre");
        pre.addActionListener(this);
        get.addActionListener(this);
        JLabel taskMainla=new JLabel("enter prefered servers count for task"+c);
        taskP =new JTextField(5);
        
        f3.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        f3.add(taskMainla);
        f3.add(taskP); 
        f3.add(get);
        if(c>TOTALTASKS){
            f3.setVisible(false);
            f2.setVisible(false);
            f.setVisible(true);
    }
  else
   f3.setVisible(true);
        
   }
    
public void getServerLoadDetails(){
    
    
    f.setVisible(false);
    f1=new JFrame();
    f1.setTitle("enter server loads");
    f1.setBounds(400, 300, 30, 30);
    f1.setSize(500,200);
    f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f1.setLayout(new FlowLayout());
    Label load[] =new Label[TOTALSERVERS];
     tf1=new JTextField[TOTALSERVERS];
    Next =new JButton("Next");
    Next.addActionListener(this);
    //Label l1 =new Label("enter load of servers");
    //f1.add(l1);
    for(int i=0;i<TOTALSERVERS;i++){
    load[i]=new Label("Enter Load of server"+(i+1));
     f1.add(load[i]);
    tf1[i]=new JTextField(5);       
    f1.add(tf1[i]) ;
}
    f1.add(Next);
f1.setVisible(true);

}
   
@Override
    public void actionPerformed(ActionEvent e) {
     String str=e.getActionCommand();
    
    if(str.equals("calculate")){
      
        ntready=false;
       System.out.println("inside a cal"+ntready+ TOTALSERVERS+TOTALTASKS);
    }
    
    
    if(str.equals("task")){
       try{ 
       
            f.setVisible(false);
          getTaskDetails();
        }
    catch(Exception expe){
JOptionPane.showMessageDialog(new JFrame(), "Provide a vaild value", "Dialog",JOptionPane.ERROR_MESSAGE);
        f.setVisible(true);
    
    }
    }
    if(str.equals("quit")){
        System.out.println("\n\n inside exit");
            System.exit(0);
            
        }
    if(str.equals("server")){
            
       try{
        TOTALSERVERS = Integer.parseInt(tfser.getText());
            TOTALTASKS=Integer.parseInt(tftas.getText());
            t = new task[TOTALTASKS];
            
            s = new server[TOTALSERVERS];
        bn  = new basenode[TOTALSERVERS+TOTALTASKS+1];
        bn[TOTALSERVERS+TOTALTASKS]=new basenode(null,null,1);
        bn[TOTALSERVERS+TOTALTASKS].intnum=TOTALSERVERS+TOTALTASKS;
       for(int i=0;i<TOTALSERVERS;i++){
                s[i]=new server();
                s[i].num=i;
       }
        for(int i=0;i<TOTALTASKS;i++)
       {
           t[i]=new task();
           t[i].num=i;
           bn[i+TOTALSERVERS]= new basenode(null,t[i],i);
           bn[i+TOTALSERVERS].intnum=i+TOTALSERVERS;
       }
            getServerLoadDetails();
            
        
    }
    catch(Exception expe){
        f.setVisible(false);
JOptionPane.showMessageDialog(new JFrame(), "Provide a vaild value", "Dialog",JOptionPane.ERROR_MESSAGE);
        f.setVisible(true);
    
    }
    }
    
        if(str.equals("Next")){
            try{
            System.out.println("\n\nindise next");
            for(int i=0;i<TOTALSERVERS;i++)
        {
            
           s[i].getInitialLoad(Double.parseDouble(tf1[i].getText()));  
           bn[i]= new basenode(s[i],null,i);
           bn[i].intnum=i;
       }
            f.setVisible(true);
            f1.setVisible(false);
           // f2.setVisible(false);
        }
            catch(Exception expe){
                f1.setVisible(false);
JOptionPane.showMessageDialog(new JFrame(), "Provide a vaild value", "Dialog",JOptionPane.ERROR_MESSAGE);
        f1.setVisible(true);
    
    }
        }
        if(str.equals("pre")){
            f.setVisible(true);
            f1.setVisible(false);
            f2.setVisible(false);
            f3.setVisible(false);
        }
  if(str.equals("Get")){
      try{
      f.setVisible(false);
      f1.setVisible(false);
      f3.setVisible(false);
     
     
              
      taskn=Integer.parseInt(taskP.getText());
      if(Integer.parseInt(taskP.getText())>TOTALSERVERS){
            f3.setVisible(false);
            JOptionPane.showMessageDialog(new JFrame(), "cannot be more than "+TOTALSERVERS, "Dialog",JOptionPane.ERROR_MESSAGE);
            taskP.setText("");
            f3.setVisible(true);
            
        }
      else{
      System.out.println("insdie get action list with taskn value as"+taskn);
      tcount=c-1;
       t[tcount].getTPS(taskn); 
     System.out.print("in tcount");
     System.out.print("values of tcount :"+tcount);
     c++;
   getTaskPreDetails(taskn);
      }
      }
  catch(Exception expe){
                f3.setVisible(false);
JOptionPane.showMessageDialog(new JFrame(), "Provide a vaild value", "Dialog",JOptionPane.ERROR_MESSAGE);
        f3.setVisible(true);
    }
      }
        if(str.equals("back")){
            
      try{
          
          for(int i=0;i<t[tcount].tps;i++){
        if(Integer.parseInt(tf3[i].getText())>TOTALSERVERS){
            btem=false;
         if(btem==false){ f2.setVisible(false);
JOptionPane.showMessageDialog(new JFrame(), "value of "+(i+1)+"cannot be greater than "+TOTALSERVERS, "Dialog",JOptionPane.ERROR_MESSAGE);
    tf3[i].setText("");
    f2.setVisible(true);
    }
  }
        else btem=true;
          }
          if(btem==true){
      f.setVisible(false);
      f1.setVisible(false);
      f2.setVisible(false);
      
      
     for(int i=0;i<t[tcount].tps;i++){
      int ser;    
      ser=Integer.parseInt(tf3[i].getText());
      
      t[tcount].getPreferedServers(ser-1);
      }
      System.out.println("insise back");
      
            getTaskDetails();
        }
      }
      
      catch(Exception expe){
                f2.setVisible(false);
JOptionPane.showMessageDialog(new JFrame(), "Provide a vaild value", "Dialog",JOptionPane.ERROR_MESSAGE);
        f2.setVisible(true);
        }
        }
}
    public static void main(String[] args) throws IOException, Exception {
                        
//        b=new Balance();
//        while(b.ntready){
//            System.out.print("");
//        }
        
        
        b=new Balance(1);
        b.getDetails();
       System.out.println("inside main");
        b.balancephase(localcost);
        b.deletebn();
        
        server sfinal[]=new server[b.TOTALSERVERS];
        sfinal=b.reduce();
         b.opt(sfinal);
        // b.display();
       //b.displayfn();
    }
    
    public void display(){
        System.out.println("the edges created are....\n");
        for(int i=0;i<TOTALSERVERS+TOTALTASKS+1;i++){
            bn[i].display();
        }
    }
    
    public void balancephase(double localcost){
      createDP();
      createflow();
      createRG();
      ArrayList<basenode> plist = new ArrayList<basenode>();
      plist=null;
      for(int i=0;i<TOTALSERVERS;i++)
          s[i].cCurTasks=0;
      int tou=1;
      while(tou<=TOTALTASKS){
          for(int i=0;i<TOTALSERVERS+TOTALTASKS+1;i++){
              rg[i].visited=false;
          }
          while(plist==null){
              basenode smin=findMinUnvisitedServer(localcost);
              plist=constructTree(smin);  
              System.out.println(smin.name);
              //smin.s.cCurTasks++;
              if(plist==null){
//                   for(int i=0;i<TOTALSERVERS+TOTALTASKS+1;i++){
//                        rg[i].visited=false;
//                   }
                   smin.visited=true;
              }
              else if(plist!=null){
                  smin.s.cCurTasks+=1;
              }
              else{}
          }
          updateflow(plist);
          updateResidualGraph(plist);
          tou++;
          plist=null;
      }    
    }
    
    public void updateflow(ArrayList<basenode> plist){
        basenode ser=null,e=null;
        for(int j=0;j<plist.size()-1;j++){
            for(int i=0;i<TOTALSERVERS+TOTALTASKS+1;i++){
            
                if(plist.get(j).intnum==flow[i].intnum){
                  ser=flow[i];
                }
                if(plist.get(j+1).intnum==flow[i].intnum){
                e=flow[i];
                }
            }
            if(e.t!=null){
                if(e.t.fs==null){
                    e.t.fs=ser.s;
                }
                else if(e.t.fs!=null){
                    for(int i=0;i<TOTALSERVERS;i++){
                        if(e.t.fs==flow[i].s){
                            for(int k=0;k<flow[i].elist.size();k++){
                            if( flow[i].elist.get(k).e.name.equals(e.name)){
                                System.out.println("Removed edge:"+flow[i].elist.get(k));
                                flow[i].elist.remove(k);
                                e.indegree-=1;
                                e.t.fs=ser.s;
                 }}}}}
            }
            ser.addedge(ser, e);
            System.out.println(ser.elist.size()+ser.elist.toString()+e.indegree+e.toString());
        }
    }
    
    private void updateResidualGraph(ArrayList<basenode> plist) {
     rg=null;
     createRG();//basically we have to clear the RG and completely draw it... we are trying to improvise it using updation
     for(int i=0;i<TOTALSERVERS+TOTALTASKS+1;i++){
         
         for(int j=0;j<flow[i].elist.size();j++){for(int z=0;z<bn[i].elist.size();z++){
          if((bn[i].elist.get(z).e.intnum==flow[i].elist.get(j).e.intnum)){
              System.out.println(flow[i].elist.get(j).toString());
              String tmp=flow[i].elist.get(j).e.name;
              for(int k=0;k<TOTALSERVERS+TOTALTASKS+1;k++){
                  if(tmp.equals(rg[k].name))
                  {  rg[i].changedir(rg[i],rg[k]);            
                  System.out.println("after"+rg[i].elist +"          "+ rg[k].elist);break; 
                 }    
              }
          }   
         }}
     }
    }
    
    public ArrayList<basenode> constructTree(basenode smin){
        btree bt[];
        
        int num = smin.num;
        bt=new btree[TOTALSERVERS+TOTALTASKS+1];
        
        for(int i=0;i<TOTALSERVERS+TOTALTASKS+1;i++){
         bt[i]=new btree(rg[i]);
        }
      Queue<btree> q = new LinkedList<btree>();
      btree tmp;
      btree last = null;
      tmp=bt[num];
      bt[num].pre=null;
      q.add(tmp);
       while(!(q.isEmpty())){
       tmp = q.poll();
        for(int i=0;i<tmp.rg.elist.size();i++){
        for(int k=0;k<TOTALSERVERS+TOTALTASKS+1;k++){
             if(tmp.rg.elist.get(i).e.name.equals(bt[k].rg.name)  &&  bt[k].rg.visited==false){
                 bt[i].plist.add(bt[k]);
                 q.add(bt[k]);
                 bt[k].rg.visited=true;
                 bt[k].pre=tmp;
                 bt[k].heigh=tmp.heigh+1;
                 if(bt[k].rg.s==null && bt[k].rg.t==null){
                     q.clear();
                     last= bt[k];
                 }
             }
         }
        }
       }
       tmp=last;
       if(last==null){
           System.out.println("p is null");
           return null;
       }
       ArrayList<basenode>p= new ArrayList<basenode>(last.heigh+1);
       basenode[] ptmp=new basenode[last.heigh+1];
         while(tmp.rg!=smin){
            ptmp[tmp.heigh]=tmp.rg;
            tmp=tmp.pre;
        }
        ptmp[0]=tmp.rg;
        for(int i=0;i<=last.heigh;i++){
            p.add(ptmp[i]);
        }
            
        System.out.print("the augmented path for smin  "+smin.toString()+"is "+p.toString());
        System.out.println("path is "+p.toString());
        /*for(int i=0;i<TOTALSERVERS+TOTALTASKS+1;i++){
         for(int j=0;j<bt[i].bn.elist.size();j++){
         for(int k=0;k<TOTALSERVERS+TOTALTASKS+1;k++){
             if(bt[i].bn.elist.get(j).e==bt[k].bn){
                 bt[i].plist.add(bt[k]);
             }
                 
        }
        } // this code for creating a full copy of the basenode to btree... but this not required.. we have to create only for the basenode bmin
        }*/
    
        
        //call the node seaching algorithm for finding tree
        // mark the visited nodes after the creation of the p using the tree objects.
        return p;
    }
    
    
    
    public basenode findMinUnvisitedServer(double localcost){
        double minload=0;
        int sernum=-1;
        for(int i=0;i<TOTALSERVERS;i++){
            if(rg[i].visited==false){
                if(minload==0.0){
                    minload=rg[i].s.getLoad(localcost);
                    sernum=i;
                }
            
                else if(minload>s[i].getLoad(localcost)){
                    minload=s[i].getLoad(localcost);
                    sernum=i;
                }
            }
        }
        System.out.println(sernum);
        return rg[sernum];
    }
    
    public void createRG(){
        rg=new basenode[TOTALSERVERS+TOTALTASKS+1];
        
        for(int i=0;i<TOTALSERVERS+TOTALTASKS+1;i++){
            rg[i]=new basenode();
            rg[i].cel=bn[i].cel;
            rg[i].indegree=bn[i].indegree;
            rg[i].name=bn[i].name;
            rg[i].s=bn[i].s;
            rg[i].t=bn[i].t;
            rg[i].num=bn[i].num;
            rg[i].visited=bn[i].visited;
            rg[i].intnum=bn[i].intnum;
            if(!bn[i].elist.isEmpty()){
                int j=0;
                while(j<bn[i].elist.size()){
                    edge tmp=bn[i].elist.get(j);
                    rg[i].elist.add(new edge(tmp.s, tmp.e));
                    j++;
                }
            }System.out.print(rg[i].name);
        }
    }
    public void createflow(){
        flow=new basenode[TOTALSERVERS+TOTALTASKS+1];
        for(int i=0;i<TOTALSERVERS+TOTALTASKS+1;i++){
            flow[i]=new basenode();
            flow[i].cel=bn[i].cel;
            flow[i].indegree=0;
            flow[i].name=bn[i].name;
            flow[i].s=bn[i].s;
            flow[i].t=bn[i].t;
            flow[i].num=bn[i].num;
            flow[i].elist.clear();
            flow[i].visited=bn[i].visited;
            flow[i].intnum=bn[i].intnum;
        }
        
        
        for(int i=0;i<TOTALSERVERS+TOTALTASKS+1;i++){
            flow[i].elist.clear();
        }
    }
    
    public void createDP(){
        for(int i=0;i<TOTALTASKS;i++)
        {
            int count= t[i].tps;
            for(int j=0;j<count;j++)
            {
                int ser=t[i].putPreferedServers(j);
                bn[ser].addedge(bn[ser],bn[TOTALSERVERS+i]);
            }
            bn[TOTALSERVERS+i].addedge(bn[TOTALSERVERS+i], bn[TOTALSERVERS+TOTALTASKS]);
        }
   }
    
     public void getDetails()throws IOException, Exception{
         
         jf = new JFrame("Ouput Of Balance Phase");
         
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         jf1 = new JFrame("Ouput Of Reduce Phase");
        jf1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
         File file = new File("C:\\Users\\Sai\\Desktop\\25-50.xml");
        Serializer serializer = new Persister();
        Resources resources = serializer.read(Resources.class, file);
        Resource resource = resources.getResourceByName("total");
        
        //lc = Double.parseDouble(resource.getProperty("localhost"));
        //rf = Double.parseDouble(resource.getProperty("reomotefactor"));
        TOTALSERVERS = Integer.parseInt(resource.getProperty("total_servers"));
        System.out.println(TOTALSERVERS);
        TOTALTASKS=Integer.parseInt(resource.getProperty("total_tasks"));
        System.out.println(TOTALTASKS);
        System.out.println("enter initial load for servers");
        s = new server[TOTALSERVERS];
        bn  = new basenode[TOTALSERVERS+TOTALTASKS+1];
       for(int i=0;i<TOTALSERVERS;i++){
                s[i]=new server();
                s[i].num=i;
       }
       resource = resources.getResourceByName("server_details"); 
       for(int i=0;i<TOTALSERVERS;i++)
        {
            double li;
            li= Double.parseDouble(resource.getProperty(""+(i+1)));
            s[i].getInitialLoad(li);     
            System.out.println(i+"\t"+li);
            bn[i]= new basenode(s[i],null,i);
            bn[i].intnum=i;
       }
       
       System.out.println("enter task details");
       t = new task[TOTALTASKS];
       for(int i=0;i<TOTALTASKS;i++)
       {
           t[i]=new task();
           t[i].num=i;
           bn[i+TOTALSERVERS]= new basenode(null,t[i],i);
           bn[i+TOTALSERVERS].intnum=i+TOTALSERVERS;
           
       }
       
       for(int i =0;i<TOTALTASKS;i++)
       {
           System.out.println("enter total prefered servers for task " +(i+1));
           resource = resources.getResourceByName("task_"+(i+1)); 
           
           int totalta =0;
           totalta=Integer.parseInt(resource.getProperty("-"));
           t[i].getTPS(totalta);
           System.out.println("total pre servers  "+(i+1)+"\t"+totalta);
           System.out.println("enter server numbers 1 - "+(TOTALSERVERS));
           for(int j=0;j<totalta;j++)
           {
               int ser=Integer.parseInt(resource.getProperty(""+(j+1)));
               System.out.println("value of ser : "+ser);
               
               while(ser>TOTALSERVERS||ser<1){
                   System.out.println("Enter within the range 1-"+TOTALSERVERS);
                   ser=Integer.parseInt(br.readLine());
               }
               t[i].getPreferedServers(ser-1);
               System.out.println("value of t["+i+"].getPreferedServers(ser-1) "+t[i].sernum[j]);
           }  
       }
       bn[TOTALSERVERS+TOTALTASKS]=new basenode(null,null,1);
       bn[TOTALSERVERS+TOTALTASKS].intnum=TOTALSERVERS+TOTALTASKS;
    }

     public static  Balance getRef(){
        return b;
    }
    
     public void displayfn(){
        b.draw=-1;
       displayNode.main(null);
       b.draw++;
       displayNode.main(null);
       for(int i=0;i<b.TOTALSERVERS;i++){
           b.draw++;
           displayNode.main(null);
       }
    }
     
     public server[] reduce(){
        
         int totalrmtasks=0;
         System.out.println("in reduce phase");
         ArrayList<task> P = new ArrayList<task>();
         createspre();
         while(true){
             server smax=MaxLoadActiveServer();
              task ta= RandomTask(smax);
             
             P.add(ta);
             smax.removeTask(ta);
             Double Maxload= calMaxload(1);
             System.out.println("Mexp:"+Maxload);
             allocateTask(ta,P);
             Double pMaxload= calMaxload(2);
             System.out.println("pMaxload > Maxload: "+pMaxload+">"+Maxload);
             if(pMaxload>Maxload)
             {
                 if(pMaxload>=calMaxload(3)){
                     return spre;
                 }
                 else { 
                     return s;
                 }
             }
             for(int i=0;i<TOTALSERVERS;i++){
                System.out.println(s[i]+"\n\tbalance taskss"+s[i].BallocT+""+"\n\treduce tasks"+s[i].remTasks+ s[i].balanceload + "   "+s[i].getTotalload());
             }
                copyStoSpre();
         }
     }

    private balance.server MaxLoadActiveServer() {
        double tmp=0;
        int cc=0;
        for(int i=0;i<TOTALSERVERS;i++){
            if(tmp==0.0&&s[i].isActive()){
                tmp=s[i].getTotalload();
                cc=i;
            }
            if(s[i].isActive()&& tmp<s[i].getTotalload()){
                tmp=s[i].getTotalload();
                cc=i;
            }
        }
       System.out.println("smax :"+s[cc]);
        return s[cc];
    }

    private task RandomTask(balance.server smax) {
        System.out.println("Random task:"+smax.BallocT.get(smax.BallocT.size()-1));
        return smax.BallocT.get(smax.BallocT.size()-1);
    }

   private Double calMaxload(int par) {
   double temp=-1;
   int cc=0;
   if(par==1){
       for(int i=0;i<TOTALSERVERS;i++){
       if(temp<s[i].getbalanceLoad(localcost) && s[i].isActive()){
           temp=s[i].getbalanceLoad(localcost);
           cc=i;
      }
      }
       System.out.println("server :"+s[cc]);
       return s[cc].getTotalload();
   }
   if(par==2){
       for(int i=0;i<TOTALSERVERS;i++){
       if(temp<s[i].getTotalload() && s[i].isActive()){
           temp=s[i].getTotalload();
           cc=i;
      }
      }
       System.out.println("server :"+s[cc]);
       return s[cc].getTotalload();
   }
   if(par==3){
       for(int i=0;i<TOTALSERVERS;i++){
       if(temp<spre[i].getTotalload() && spre[i].isActive()){
           temp=spre[i].getTotalload();
           cc=i;
      }
   }
   }System.out.println("sPre makespan"+spre[cc].getTotalload()+"of"+s[cc]);   
    return spre[cc].getTotalload();
    }

    private void allocateTask(task t, ArrayList<task> P) {
        server smin=getminServer(); 
        smin.addRemoteTask(t);
        calculateRemoteTasks(P);
        System.out.println("t"+t+"\t"+"and p is "+P);
    }

    private balance.server getminServer() {
        
        double temp=s[0].getTotalload();
        int cc=0;
        for(int i=0;i<TOTALSERVERS;i++){
        if(temp>s[i].getTotalload()){
           temp=s[i].getTotalload();
           cc=i;
        }
    }System.out.println("smin"+s[cc]);
    return s[cc];
    }

    private void calculateRemoteTasks(ArrayList<task> P) {
        rmtasks=0;
        for(int i=0;i<TOTALSERVERS;i++){
            rmtasks+=s[i].remTasks.size();
        }
        if(P.size()==rmtasks)
            System.out.println("rmtask==P.size"+P.size());
        else
            System.out.println("rmtask="+rmtasks+"P.size()"+P.size());
    }

    private void createspre() {
        System.out.println("spre created");
        copyStoSpre();
        
        
    }

    private void copyStoSpre() {
        spre=null;
        spre=new server[TOTALSERVERS];
        for(int i=0;i<TOTALSERVERS;i++){
            spre[i]= new server();
        }
        for(int i=0;i<TOTALSERVERS;i++){
            int k=0;
            for(int j=0;j<s[i].BallocT.size();j++){
                spre[i].BallocT.add(s[i].BallocT.get(j));
            }
            for(int j=0;j<s[i].remTasks.size();j++){
                spre[i].remTasks.add(s[i].remTasks.get(j));
             }
            if(s[i].BallocT.isEmpty())
                spre[i].BallocT.clear();
            if(s[i].remTasks.isEmpty())
                spre[i].remTasks.clear();
            spre[i].initialload=s[i].initialload;
            spre[i].balanceload=s[i].balanceload;
            spre[i].cBalTasks=s[i].cBalTasks;
            spre[i].cRemTasks=s[i].cRemTasks;
            spre[i].cCurTasks=s[i].cCurTasks;
            spre[i].balancefinal=s[i].balancefinal;
            spre[i].num=s[i].num;
            spre[i].totalload=s[i].totalload;
            System.out.println("copied s to spre "+i +""+ s[i]);
        }
    }

    private void copySpretoS() {
        for(int i=0;i<TOTALSERVERS;i++){
            for(int j=0;j<spre[i].BallocT.size();j++){
                s[i].BallocT.add(spre[i].BallocT.get(j));
            }
            for(int j=0;j<s[i].remTasks.size();j++){
                s[i].remTasks.add(spre[i].remTasks.get(j));
             }
            if(spre[i].BallocT.isEmpty())
                s[i].BallocT.clear();
            if(spre[i].remTasks.isEmpty())
                s[i].remTasks.clear();
            s[i].initialload=spre[i].initialload;
            s[i].balanceload=spre[i].balanceload;
            s[i].cBalTasks=spre[i].cBalTasks;
            s[i].cRemTasks=spre[i].cRemTasks;
            s[i].cCurTasks=spre[i].cCurTasks;    
            s[i].num=spre[i].num;
            s[i].totalload=spre[i].totalload;
            System.out.println("copied spre to s");
        }
        
    }
    private void deletebn() {
       System.out.println("final balanced tasks ");
       for(int i=0;i<TOTALSERVERS;i++){
           System.out.print("\n\n"+flow[i].s.toString()+":");
           for(int j=0;j<flow[i].elist.size();j++){
               flow[i].s.BallocT.add(flow[i].elist.get(j).e.t);
             //  flow[i].s.BallocTF.add(flow[i].elist.get(j).e.t);
               System.out.print("\t"+flow[i].elist.get(j).e.t +" s["+i+"].BallocT "+ s[i].BallocT );
               System.out.println();
           }
           s[i].cBalTasks=s[i].BallocT.size();
           s[i].calculatebalanceLoad(1.0);
           System.out.print("     s[i].initialload"+s[i].initialload+"    s[i].balanceload:"+s[i].balanceload);
           s[i].balancefinal=s[i].balanceload;
           System.out.println("\n");
           //System.out.print("\n\n balance TF s["+i+"].BallocTF "+ s[i].BallocTF );
           
       }
   
       String col[]={"Server","Initial Load","Balanced Allocations","Balanced Load","Active"};
        String row[][]=new String[TOTALSERVERS][col.length];
       
        for(int i=0;i<TOTALSERVERS;i++){
            for(int j=0;j<col.length;j++){
                if(j==0){ 
                    row[i][j]=s[i].toString();
                    System.out.println("row first col  "+row[i][j]);
                }
                   if(j==1){
                       row[i][j]=""+s[i].initialload;
                 
                   }
                   if(j==2){
                       
                       if(s[i].BallocT.toString().equals("[]")) 
                           row[i][j]="NULL";
                      else
                           row[i][j]=s[i].BallocT.toString();
                           
                       System.out.println("row ["+i+"]["+j+"] =" +row[i][j]);

                    }
               if(j==3){
                    row[i][j]=""+s[i].balanceload;
                  
               }
               if(j==4){
                   if(s[i].BallocT.isEmpty())
                       row[i][j]="False";
                       else
                       row[i][j]="True";
               }
                
            }
            }
        
     jtb=new JTable(row,col){ 
           
            @Override
    public Component prepareRenderer (TableCellRenderer renderer,  int index_row, int index_col){  
        Component comp = super.prepareRenderer(renderer, index_row, index_col);  
          
        comp.setBackground(getBackground());
	int modelRow = convertRowIndexToModel(index_row);

         if(getValueAt(modelRow, 4).equals(("False"))){
             
           comp.setBackground(Color.RED);
           // comp.setForeground(Color.WHITE);
          
        }
         else {
        
       // comp.setForeground(Color.BLACK);
        if( index_col ==3 ){  
                comp.setBackground(new Color(0.8f, 0.8f, 1.0f));  
                } 
        
        if( index_col ==2 ){  
                comp.setBackground(Color.YELLOW);  
                } 
        
        if(index_col==4){  
                      comp.setBackground(Color.WHITE);
                      
                } 
        
        if(index_col==1){  
                      comp.setBackground(Color.ORANGE);  
                }   
        if(index_col==0){
            comp.setBackground(Color.WHITE);
        }
            
         }
          return comp;  
            }
     };
    
    DefaultTableModel df =new DefaultTableModel(row, col){
            @Override
          
        public boolean isCellEditable(int nRow, int nCol) {
    return false;
  }
     };
    jtb.setModel(df);
   
    
    double mksp=0;
    for(int i=0;i<TOTALSERVERS;i++){
        
        if(s[i].initialload!=s[i].balanceload&&s[i].balanceload>mksp)
            mksp=s[i].balanceload;
    }
    
    JLabel MSP =new JLabel("Make Span at the end of Balance Phase is "+mksp);
    
    JPanel jp = new JPanel(new BorderLayout(200, -200));
       
         jp.add(new JScrollPane(jtb),BorderLayout.NORTH);
        jp.add(MSP,BorderLayout.CENTER);
        jf.add(jp);
    jf.setBounds(400, 300, 30, 30);
        jf.setSize(600,400);
        
    }

    private void opt(balance.server[] sfinal) {
       
        String col1[]={"Server","Balanced Allocations"," Remote Allocations ","Initial Load","Final Load","Active"};
        String row1[][]=new String[TOTALSERVERS][col1.length];
         
        double rmksp=0;
    for(int i=0;i<TOTALSERVERS;i++){
      
        if(!sfinal[i].BallocT.isEmpty()||!sfinal[i].remTasks.isEmpty()){
            if(sfinal[i].totalload>rmksp){
            rmksp=sfinal[i].totalload;
            
            }
    }
    }
    
        DecimalFormat df1 =new DecimalFormat("##.##");
        rmksp= Double.parseDouble(df1.format(rmksp));
        
        
        for(int i=0;i<TOTALSERVERS;i++){
            for(int j=0;j<col1.length;j++){
                if(j==0){ 
                    row1[i][j]=sfinal[i].toString();
                    System.out.println("row first col  "+row1[i][j]);
                }
                   if(j==1){
                       
                       if(sfinal[i].BallocT.toString().equals("[]")) 
                           row1[i][j]="NULL";
                      else
                           row1[i][j]=sfinal[i].BallocT.toString();
                           
                       System.out.println("row ["+i+"]["+j+"] =" +row1[i][j]);  
                     }
                   
                   if(j==2){
                       
                       if(sfinal[i].remTasks.toString().equals("[]"))
                            row1[i][j]="NULL";
                       else 
                            row1[i][j]=sfinal[i].remTasks.toString();
                       System.out.println("value of remote task at i="+i+" is "+row1[i][j]);
                       
                       
//                       row1[i][j]=""+sfinal[i].balancefinal;
                   }
                   if(j==3){
                       row1[i][j]=""+sfinal[i].initialload;
                   }
               if(j==4){
               DecimalFormat df = new DecimalFormat("#.#####");
               
                 // sfinal[i].totalload= (double) Math.round(sfinal[i].totalload * 100.0) / 100.0;
                   row1[i][j]=df.format(sfinal[i].totalload);
                   System.out.println("final load value at i="+i+" "+row1[i][j]);
               }
               
               if(j==5){
                   if(sfinal[i].BallocT.isEmpty()&&sfinal[i].remTasks.isEmpty())
                       row1[i][j]="False";
                       else
                       row1[i][j]="True";
               }
               
               
            }
            }
        
        
    JLabel MSP =new JLabel("Make Span at the end of Reduce Phase is "+rmksp);
        
    
    
       
        jtb1=new JTable(row1,col1){ 
           
            @Override
    public Component prepareRenderer (TableCellRenderer renderer,  int index_row, int index_col){  
        Component comp = super.prepareRenderer(renderer, index_row, index_col);  
          
        
        comp.setBackground(getBackground());
	int modelRow = convertRowIndexToModel(index_row);

         if(getValueAt(modelRow, 5).equals(("False"))){
             
            comp.setBackground(Color.RED);
          
        }
         else {
              
        if( index_col==2 ){  
        
                comp.setBackground(Color.CYAN);  
                } 
        if(index_col==3){
            comp.setBackground(Color.ORANGE);
        }
        if(index_col==4){
            comp.setBackground(Color.GREEN);
        }
        if(index_col==5){
            comp.setBackground(Color.WHITE);
        }
        
        if(index_col==1){  
                      comp.setBackground(Color.YELLOW);  
                }   
        if(index_col==0){
            comp.setBackground(Color.WHITE);
        }
         }
            return comp;  
    }  
};
        DefaultTableModel df =new DefaultTableModel(row1, col1){
            @Override
          
        public boolean isCellEditable(int nRow, int nCol) {
    return false;
  }
     };
    jtb1.setModel(df);
    
      JPanel jp = new JPanel(new BorderLayout(20, -220));
       
         jp.add(new JScrollPane(jtb1),BorderLayout.NORTH);
        jp.add(MSP,BorderLayout.CENTER);
        
        jf1.add(jp);
         jf1.setBounds(400, 300, 30, 30);
        jf1.setSize(600,400);
       // f.setVisible(false);
        jf.setVisible(true);
        jf1.setVisible(true);
        
       // graph();
         BalanceLineChart blc = new BalanceLineChart("Balance Reduce Line Chart",sfinal);
        blc.pack();
        RefineryUtilities.centerFrameOnScreen(blc);
        blc.setVisible(true);
        
    }
    
    
    //***********************************************************************************************
    /*
    line chart code and class
    */
    public final class BalanceLineChart extends ApplicationFrame {
        server s[];
        
        public BalanceLineChart(final String title,server s[]) {
        super(title);
        this.s=s;
        final CategoryDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(chartPanel);
    }
        
        
    
    public CategoryDataset createDataset() {
        
         String series1 = "Initial Load";
         String series2 = "Balanced Load";
         String series3 = "Final Load";
    
         DefaultCategoryDataset dataset = new DefaultCategoryDataset();
System.out.println("\n\n dataset creation ");
        for(int i=0;i<TOTALSERVERS;i++){
        dataset.addValue(s[i].initialload, series1, s[i].toString());
        System.out.println("s["+i+"].initload "+s[i].initialload+"series1 :"+series1+" s["+i+" ].tostring :"+s[i].toString());
        
        
        }
        for(int i=0;i<TOTALSERVERS;i++){
            //if(s[i].initialload!=s[i].totalload){
        dataset.addValue(s[i].balancefinal, series2, s[i].toString());
        System.out.println("s["+i+"].balancefinal"+s[i].balancefinal+"series2 :"+series2+" s["+i+" ].tostring :"+s[i].toString());
        
        }
        for(int i=0;i<TOTALSERVERS;i++){
          //  if(s[i].initialload!=s[i].balancefinal){
        dataset.addValue(s[i].totalload, series3, s[i].toString());
        System.out.println("s["+i+"].totalload "+s[i].totalload+"series3 :"+series3+" s["+i+" ].tostring :"+s[i].toString());
        
        }
        return dataset;
                
    }
    
    public JFreeChart createChart(final CategoryDataset dataset) {
       
        // create the chart...
               final JFreeChart chart = ChartFactory.createLineChart(
            "Balance Reduce Algorithm",       // chart title
            "Servers",                    // domain axis label
            "Load",                   // range axis label
            dataset,                   // data
            PlotOrientation.VERTICAL,  // orientation
            true,                      // include legend
            true,                      // tooltips
            false                      // urls
        );
    
        chart.setBackgroundPaint(Color.white);

        final CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.BLACK);
        plot.getRenderer().setSeriesPaint(0, Color.ORANGE);
        plot.getRenderer().setSeriesPaint(1, Color.RED);
        plot.getRenderer().setSeriesPaint(2, Color.CYAN);
        // customise the range axis...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);
 
        // customise the renderer...
        final LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
//        renderer.setDrawShapes(true);

        renderer.setSeriesStroke(
            0, new BasicStroke(
                2.0f, BasicStroke.JOIN_MITER, BasicStroke.JOIN_MITER,
                1.0f, new float[] {6.0f, 0.0f}, 0.0f
            )
        );
        renderer.setSeriesStroke(
            1, new BasicStroke(
                2.0f, BasicStroke.JOIN_MITER, BasicStroke.JOIN_MITER,
                1.0f, new float[] {6.0f, 0.0f}, 0.0f
            )
        );
        renderer.setSeriesStroke(
            2, new BasicStroke(
                2.0f, BasicStroke.JOIN_MITER, BasicStroke.JOIN_MITER,
                1.0f, new float[] {6.0f, 0.0f}, 0.0f
            )
        );
        // OPTIONAL CUSTOMISATION COMPLETED.
        
        return chart;
    }
    
    }    
}


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package balance;

import java.util.ArrayList;

public class basenode {
    int num;
    int intnum;
    server s=null;
    task t =null;
    String name;
    //edge eList[];
    ArrayList<edge> elist = new ArrayList<edge>();
    int cel=0;
    boolean visited=false;
    int indegree=0;
    //basenode cend;
    //basenode cstart;
    public basenode(){
         
    }
    
    public basenode(server s,task t,int num)
    {
        this.s=s;
        this.t = t;
        this.num= num;
        if(this.s==null&&this.t==null)
            name="nT";
        else if(this.s==null)
            name="t"+(num+1);
        else
            name="s"+(num+1);
        
    }
    
    
    public basenode(basenode bn){
        this.s=bn.s;
        this.t=bn.t;
        this.num=bn.num;
        this.cel=bn.cel;
        this.elist=bn.elist;
        this.name=bn.name;
        this.visited=bn.visited;
    }
    
   
    public void  addServer(server ser,int num)
    {
        this.s = ser;
        this.num = num;
    }
    
    public void addTask(task tas,int num){
        this.t=tas;
       this.num=num;
    }
    public void addedge(basenode s,basenode e){
        elist.add(new edge(s,e));
        e.indegree+=1;
    }
   
    public void changedir(basenode s,basenode e){
        for(int i=0;i<elist.size();i++){
         if(elist.get(i).getEnd().intnum==e.intnum){
            e.addedge(e, s);
            s.elist.remove(i);
            s.indegree-=1;
            break;
          }
        }
       }
    public char getbase(){
        if(t==null&&s==null)
            return 'n';
        else if(t==null)
            return 's';
        return 't';
    }
    public int getnum()
    {
        return num;
    }
    
    public void display(){
        for(int i=0;i<elist.size();i++){
           System.out.println(elist.get(i).toString());
        }
    }
    public void updateforflow(){
        
    }
    @Override
    public String toString(){
        return name;
    }
}


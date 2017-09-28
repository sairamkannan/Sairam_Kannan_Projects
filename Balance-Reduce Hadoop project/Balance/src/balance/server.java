/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package balance;

import java.util.ArrayList;


public class server {
     double initialload;
     double balanceload;
     double balancefinal;
     double totalload;
     int cCurTasks=0;  //s.num in algorithm
     int cBalTasks=0;
     int cRemTasks=0;
     int num;
     ArrayList<task> BallocT = new ArrayList<task>();
    // ArrayList<task> BallocTF = new ArrayList<task>();
     ArrayList<task> remTasks = new ArrayList<task>();
     
    public server(){
        initialload=0;
        balanceload=0;
    }
    public void getInitialLoad(double il){
        initialload = il;
        balanceload=initialload;
    }
    public double putInitialLoad(){
        return initialload;
    }
    public double putLoad(){
        return balanceload;
    }
    public double getLoad(double localcost){
        balanceload=initialload+(localcost*cCurTasks);
        return balanceload;
    }

    public double getbalanceLoad(double localcost){
        balanceload=initialload+(localcost*cBalTasks);
        return balanceload;
    }        
    public void calculatebalanceLoad(double localcost){
        balanceload=initialload+(localcost*BallocT.size());
        
        
    }    
    public Double getTotalload(){
        calculatebalanceLoad(1.0);
        double rl=calculateRemoteLoad();
        totalload=rl+balanceload;
        return(rl+balanceload);
    }
    public double calculateRemoteLoad(){
         Balance b=Balance.getRef();
        double rl = 1+(0.1 * Balance.rmtasks);
        rl=rl*remTasks.size();
        return rl;
    }

    void removeTask(task t) {
        int tasktoberm=BallocT.lastIndexOf(t);
        
        System.out.println("task removed"+BallocT.get(tasktoberm).toString());
        cCurTasks-=1;
        cBalTasks-=1;
        BallocT.remove(t);
        calculatebalanceLoad(1.0);
        System.out.println("total load after removal"+getTotalload());
    }
    
    public boolean isActive(){
        if(BallocT.isEmpty())
            return false;
        else
            return true;
    }

    void addRemoteTask(task t) {
        boolean add = remTasks.add(t);
        System.out.println(add);
        cCurTasks+=1;
        cRemTasks+=1;
        getTotalload();
        System.out.println("task added "+this.toString()+"--"+t);
        System.out.println(remTasks);
       
    }
    
    @Override
    public String toString(){
        String str="s"+(num+1)+"";
       return str;
    }

    private void calculatetotalload() {
        calculatebalanceLoad(1.0);
        double rl=calculateRemoteLoad();
        totalload=rl+balanceload;
    }
}

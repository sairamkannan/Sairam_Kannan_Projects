/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package balance;


public class task {
    float localcost;
   int tps;
   int sernum[];
   int i =0;
   int num=0;
   server fs;
   
  public void getTPS(int tp){
      tps=tp;
      sernum= new int[tps];     
  }
  public int putTPS(){
      return tps;
  }
   public void getPreferedServers(int num ){
       sernum[i]=num;
       i++;
       
   }
   public int putPreferedServers(int i)
   {
       return sernum[i];
       
   }
    @Override
   public String toString(){
       String str="t"+(num+1)+"";
       return str;
   }
   
   
    
    
            
}

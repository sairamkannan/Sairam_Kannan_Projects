/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package balance;

import java.util.ArrayList;

public class edge {
    basenode s;
    basenode e;
    int weight; 
    
    public edge(basenode s,basenode e){
        this.s=s;
        this.e =e;
        weight=1;
        
    }
    public edge()
    {
        this.s=null;
        this.e=null;
        weight=1;
        
    }
    public void addedge(basenode s,basenode e){
        this.s=s;
        this.e=s;
    }
    
    public basenode getBeg(){
        return s;
    }
    
    public basenode getEnd(){
        return e;
    }
            
    public void dirChange()
    { 
        basenode temp;
        temp=s;
        s=e;
        e=temp;
        String str=toString(); 
        System.out.println(str);  
    }
    @Override
    public String toString(){
        String str= "Edge :"+s.name+"-->"+e.name;
        return str;
        
    }
}
class btree{
    basenode rg;
    ArrayList<btree> plist = new ArrayList<btree>();
    btree pre;
    int heigh=0;
    public btree(basenode bn){
        this.rg=bn;
    }
    @Override
    public String toString(){
        return rg.toString();
    }

//    void calculatebtree(basenode[] ba) {
//         
//         Balance b=Balance.getRef();
//        for(int i =0;i<bn.elist.size();i++){
//            for(int j=0;j< b.TOTALSERVERS+b.TOTALTASKS+1;j++){
//                if(bn.elist.get(i).e == ba[j]){
//                    plist.add(bt[j]);
//                }
//            }
//                
//        }
//    }
}

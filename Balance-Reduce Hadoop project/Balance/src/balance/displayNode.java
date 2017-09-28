package balance;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javax.swing.*;
import org.jgraph.*;
import org.jgraph.graph.*;
import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;
import org.jgrapht.graph.DefaultEdge;

public class displayNode
    extends JApplet
{
    
    private static final long serialVersionUID = 3256444702936019250L;
    private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
    private static final Dimension DEFAULT_SIZE = new Dimension(1000,800);
    static Balance b;
   
    private JGraphModelAdapter<String, DefaultEdge> jgAdapter;
    //basenode bn[];
    
    public static void main(String [] args)
    {   
       
        displayNode applet = new displayNode();
        applet.init();

        JFrame frame = new JFrame();
        frame.getContentPane().add(applet);
        
        b=Balance.getRef();
        if(b.draw==-1)
            frame.setTitle("data placement graph");
        else if(b.draw==0)
            frame.setTitle("flow graph");
        else
            frame.setTitle("BFS tree for server"+(b.draw));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

   
    public void init()
    {
        // create a JGraphT graph
        ListenableGraph<String, DefaultEdge> g =
            new ListenableDirectedMultigraph<String, DefaultEdge>(
                DefaultEdge.class);

        // create a visualization using JGraph, via an adapter
        jgAdapter = new JGraphModelAdapter<String, DefaultEdge>(g);

        JGraph jgraph = new JGraph(jgAdapter);

        adjustDisplaySettings(jgraph);
        getContentPane().add(jgraph);
        resize(DEFAULT_SIZE);
        b=Balance.getRef();
      //  bn=b.bn;
        if(b.draw==-1){
            for(int i=0;i<(b.TOTALSERVERS+b.TOTALTASKS);i++){
            g.addVertex(b.bn[i].name);
            //System.out.print(b.bn[i].name);
            if(i<b.TOTALSERVERS){
                 positionVertexAt(b.bn[i].name,100+(75*i),650);
            }else if(i<b.TOTALSERVERS+b.TOTALTASKS){
                positionVertexAt(b.bn[i].name,100+(50*i),400);
            }
            }
        for(int i=0;i<(b.TOTALSERVERS);i++)
            for(int j=0;j<b.bn[i].elist.size();j++){
                g.addEdge(b.bn[i].elist.get(j).getBeg().name,b.bn[i].elist.get(j).getEnd().name);
            
            }
        }
        else if(b.draw ==0){
            for(int i=0;i<(b.TOTALSERVERS+b.TOTALTASKS+1);i++){
            g.addVertex(b.bn[i].name);
            //System.out.print(b.bn[i].name);
            if(i<b.TOTALSERVERS){
                 positionVertexAt(b.bn[i].name,100+(75*i),650);
            }else if(i<b.TOTALSERVERS+b.TOTALTASKS){
                positionVertexAt(b.bn[i].name,100+(85*i),400);
            }
            else
                positionVertexAt(b.bn[i].name,300,100);
                
          }
        for(int i=0;i<(b.TOTALSERVERS+b.TOTALTASKS+1);i++)
            for(int j=0;j<b.bn[i].elist.size();j++){
                g.addEdge(b.bn[i].elist.get(j).getBeg().name,b.bn[i].elist.get(j).getEnd().name);
            
            }
            
        }
        else{
            g.addVertex(b.bn[b.draw-1].name);
            positionVertexAt(b.bn[b.draw-1].name, 400, 100);
            for(int i=b.TOTALSERVERS;i<(b.TOTALSERVERS+b.TOTALTASKS+1);i++)
            {
                g.addVertex(b.bn[i].name);
                positionVertexAt(b.bn[i].name, 100+(85*i), 400);
                if(i==b.TOTALSERVERS+b.TOTALTASKS)
                    positionVertexAt(b.bn[i].name, 500, 600);
            }
            int i=b.draw-1;
            ArrayList <basenode> edgel= new ArrayList<basenode>();
                for(int j=0;j<b.bn[i].elist.size();j++){
                g.addEdge(b.bn[i].elist.get(j).getBeg().name,b.bn[i].elist.get(j).getEnd().name);
                
                edgel.add(b.bn[i].elist.get(j).getEnd());
            }
            g.addEdge(edgel.get(0).name,"nT");
        }            
        
}

    private void adjustDisplaySettings(JGraph jg)
    {
        jg.setPreferredSize(DEFAULT_SIZE);

        Color c = DEFAULT_BG_COLOR;
        String colorStr = null;

        try {
            colorStr = getParameter("bgcolor");
        } catch (Exception e) {
        }

        if (colorStr != null) {
            c = Color.decode(colorStr);
        }

        jg.setBackground(c);
    }

    @SuppressWarnings("unchecked") 
    private void positionVertexAt(Object vertex, int x, int y)
    {
        DefaultGraphCell cell = jgAdapter.getVertexCell(vertex);
        AttributeMap attr = cell.getAttributes();
        Rectangle2D bounds = GraphConstants.getBounds(attr);

        Rectangle2D newBounds =
            new Rectangle2D.Double(
                x,
                y,
                bounds.getWidth(),
                bounds.getHeight());

        GraphConstants.setBounds(attr, newBounds);
AttributeMap cellAttr = new AttributeMap();
        cellAttr.put(cell, attr);
        jgAdapter.edit(cellAttr, null, null, null);
    }

    private static class ListenableDirectedMultigraph<V, E>
        extends DefaultListenableGraph<V, E>
        implements DirectedGraph<V, E>
    {
        private static final long serialVersionUID = 1L;

        ListenableDirectedMultigraph(Class<E> edgeClass)
        {
            super(new DirectedMultigraph<V, E>(edgeClass));
        }
    }
}

package balance;

import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class Demo {
    static Double lc,rf;
    public static void main(String[] args) throws Exception {
        File file = new File("/home/hduser/Desktop/resources.xml");
        Serializer serializer = new Persister();
        Resources resources = serializer.read(Resources.class, file);

        Resource resource = resources.getResourceByName("localcost");
        
        lc = Double.parseDouble(resource.getProperty("localhost"));
        rf = Double.parseDouble(resource.getProperty("reomotefactor"));
        
    }
    public static Double getlc(){
        return lc;
    }
    public static Double getrf(){
        return rf;
        
    }
}
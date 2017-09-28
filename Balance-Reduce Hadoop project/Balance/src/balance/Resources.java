/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package balance;



import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.util.Dictionary;

@Root(name="resources")
public class Resources {
    @ElementList(entry = "resource", inline = true) 
    private Dictionary<Resource> resources = new Dictionary<Resource>();

    public Resources(@ElementList(entry = "resource", inline = true) Dictionary<Resource> resources) {
        this.resources = resources;
}

    public Resource getResourceByName(String name){
        return resources.get(name);
    }
}
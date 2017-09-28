/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package balance;



import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.util.Entry;

@Root
public class Property implements Entry{
    @Attribute(name="name") private String name;
    @Attribute(name="value") private String value;

    public Property(@Attribute(name="name") String name, @Attribute(name="value") String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
package ops;

import javax.xml.bind.annotation.*;

/**
 * Created by jose on 25/02/16.
 */

@XmlRootElement(name = "OPERATION")
@XmlAccessorType(XmlAccessType.FIELD)
public class Op {

    @XmlElement(name = "KEY")
    private String key;
    @XmlElement(name = "NAME")
    private String className;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}

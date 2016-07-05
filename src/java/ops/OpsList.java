package ops;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by jose on 25/02/16.
 */

@XmlRootElement(name = "OPERATIONS")
@XmlAccessorType(XmlAccessType.FIELD)
public class OpsList {

    @XmlElement(name = "OPERATION")
    private List<Op> operations = null;

    public List<Op> getOperations() {
        return operations;
    }

    public void setOperations(List operations) {
        this.operations = operations;
    }
}

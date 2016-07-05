package factories;

import java.io.File;
import java.util.HashMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import ops.Global_op;
import ops.OpsList;

/**
 * Created by jose on 25/02/16.
 */

public class OpsFactory {

    private static HashMap<String, String> opsMap = null;
    private static volatile OpsFactory instance = null;

    public synchronized static OpsFactory getInstance() {
        if (instance == null) {
            instance = new OpsFactory();
        }
        return instance;
    }

    public OpsList populateOps(String path) {
        File xml_ops = new File(path);
        try {
            JAXBContext context = JAXBContext.newInstance(OpsList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            OpsList opsList = (OpsList) unmarshaller.unmarshal(xml_ops);
            opsMap = new HashMap<>();
            opsList.getOperations().stream().forEach((op) -> {
                opsMap.put(op.getKey(), op.getClassName());
            });
        } catch (Exception e) {
        }
        return null;
    }

    public Global_op reflexOp(String k) {
        try {
            if (opsMap.containsKey(k)) {
                return (Global_op) Class.forName("ops." + opsMap.get(k)).newInstance();
            }
        } catch (Exception e) {
        }
        return null;
    }

}

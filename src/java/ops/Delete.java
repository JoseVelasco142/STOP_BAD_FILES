package ops;

import hibernate.DataModel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import workers.Core;

/**
 *
 * @author root
 */

public class Delete extends Global_op {
    private String uuid;
    private int idFile;
    private Path filePath;
    
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response){
        try {
            uuid = request.getParameter("id").trim();
            idFile = DataModel.getInstance().getIdFile(uuid);
            DataModel.getInstance().removeFile(idFile);
            filePath = Paths.get(Core.STOREPATH + uuid);
            Files.deleteIfExists(filePath);
        } catch (Exception e) {
        }
    }
}

package ops;

import com.google.gson.Gson;
import hibernate.DataModel;
import hibernate.SambaFile;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author jose
 */

public class FileList extends Global_op {

    private List<SambaFile> fileList;
    private HttpSession session;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            session = request.getSession(false);
            fileList = DataModel.getInstance().getFileList(session.getAttribute("uidNumber").toString());
            response.getOutputStream().print(new Gson().toJson(fileList));
        } catch (IOException e) {
        }

    }
}

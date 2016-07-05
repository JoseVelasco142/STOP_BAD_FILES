
package ops;

import com.google.gson.Gson;
import hibernate.DataModel;
import hibernate.SambaFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jose
 */
public class Information extends Global_op {
    private SambaFile file;
    
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String uuid = request.getParameter("id").trim();
        try {
            file = DataModel.getInstance().getFileInfo(uuid);
            response.getOutputStream().print(new Gson().toJson(file));
        } catch(Exception e){
        }
    }
    
}

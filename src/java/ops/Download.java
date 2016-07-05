package ops;

import hibernate.DataModel;
import java.io.File;
import java.io.FileInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import workers.Core;

/**
 * Created by jose on 25/02/16.
 */

public class Download extends Global_op {

    private File file;
    private String uuid;
    private String filePath;
    
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            uuid = request.getParameter("id").trim();
            file = new File(DataModel.getInstance().getFileName(uuid));
            filePath = Core.STOREPATH + uuid;
            new File(filePath).renameTo(file);
            FileInputStream inStream = new FileInputStream(file);
            OutputStream outStream = response.getOutputStream();
            String mimeType = request.getServletContext().getMimeType(filePath);
            if (mimeType == null) 
                mimeType = "application/octet-stream";
            response.setContentType(mimeType);
            response.setContentLength((int) file.length());
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            inStream.close();
            outStream.close();
        } catch (Exception e) {
        }
    }
}

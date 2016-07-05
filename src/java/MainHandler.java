
import factories.OpsFactory;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ops.Global_op;

/**
 *
 * @author jose
 */
public class MainHandler extends HttpServlet {

    @Override
    public void init(){
        try {
            super.init();
            OpsFactory.getInstance().populateOps(getServletContext().getRealPath("/WEB-INF/ops.xml"));
        } catch (Exception e) {
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
        String operation = request.getParameter("op");
            if (operation != null) {
                Global_op global_op = OpsFactory.getInstance().reflexOp(operation);
                global_op.execute(request, response);
            }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}

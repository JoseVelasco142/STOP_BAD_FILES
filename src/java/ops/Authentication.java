package ops;

import factories.LdapConnector;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jose
 */
public class Authentication extends Global_op {
    private Boolean out;
    private String uid;
    private String password;
    private String uidNumber;
    

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            out = Boolean.valueOf(request.getParameter("out"));
            if (!out) {
                uid = request.getParameter("username").trim();
                password = request.getParameter("password").trim();
                if (NoEmptyData()) {
                    if (auth()) {
                        uidNumber = LdapConnector.getInstance().getUidNumber(uid);
                        request.getSession(true).setAttribute("logged", true);
                        request.getSession(true).setAttribute("uid", uid);
                        request.getSession(true).setAttribute("uidNumber", uidNumber);
                        request.getRequestDispatcher("/views/Main.jsp").forward(request, response);
                    } else 
                        response.getWriter().write("" + Global_op.NO);
                } else
                    response.getWriter().write("" + Global_op.EMPTY); 
            } else 
                request.getSession().invalidate();
        } catch (ServletException | IOException e) {
        }
    }
    
    private boolean NoEmptyData(){
        return !(uid).isEmpty() && !(password).isEmpty();
    }
    
    private boolean auth(){
        return LdapConnector.getInstance().login(uid, password);
    }

}

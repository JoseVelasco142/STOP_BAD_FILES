package ops;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jose on 25/02/16.
 */
public abstract class Global_op {

    public static final int NO = 1;
    public static final int EMPTY = 2;

    public abstract void execute(HttpServletRequest request, HttpServletResponse response);

}

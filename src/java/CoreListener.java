
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import workers.Core;

/**
 *
 * @author jose
 */
@WebListener
public class CoreListener implements ServletContextListener {

    Core core = Core.getInstance();
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            core.init();
        } catch (Exception ex) {
            Logger.getLogger(CoreListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("ServletContextListener destroyed");
    }

}

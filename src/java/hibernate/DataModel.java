package hibernate;

import factories.HibernateUtil;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Created by jose on 25/02/16.
 */
public class DataModel {

    private static Session session = null;

    public static DataModel getInstance() {
        return DataModelHolder.INSTANCE;
    }

    private DataModel() {
    }

    //MASTER SESSION
    public boolean OpenConnection() {
        try {
            session = HibernateUtil.getInstance().openSession();
            return session != null;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean CloseConnection() {
        try {
            session.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //SAVE NEW FILE
    public void saveNewFile(String filename, String size, String uidNumber, Date datetime, String uuid) {
        if (OpenConnection()) {
            try {
                session.beginTransaction();
                session.save(new SambaFile(filename, size, uidNumber, datetime, uuid));
                session.getTransaction().commit();
            } finally {
                CloseConnection();
            }
        }
    }

    //REMOVE ONE FILE
    public void removeFile(int id) {
        if (OpenConnection()) {
            try {
                session.beginTransaction();
                SambaFile tempFile = (SambaFile) session.load(SambaFile.class, id);
                session.delete(tempFile);
                session.getTransaction().commit();
            } finally {
                CloseConnection();
            }
        }
    }

    //GETTER PARAM
    public String getFileName(String uuid) {
        if (OpenConnection()) {
            try {
                SambaFile file = (SambaFile) session.createQuery("from SambaFile where UUID = '" + uuid + "'").uniqueResult();
                return file.getFilename();
            } finally {
                CloseConnection();
            }
        }
        return null;
    }

    public int getIdFile(String uuid) {
        if (OpenConnection()) {
            try {
                SambaFile file = (SambaFile) session.createQuery("from SambaFile where UUID = '" + uuid + "'").uniqueResult();
                return file.getIdFile();
            } finally {
                CloseConnection();
            }
        }
        return -1;
    }

    //GETTER FILES
    public List getFileList(String owner) {
        String query;
        if (OpenConnection()) {
            query = "from SambaFile where owner = '" + owner + "'";
            if ("root".equals(owner) || "0".equals(owner)) 
                query = "from SambaFile";
            try {
                return session.createQuery(query).list();
            } finally {
                CloseConnection();
            }
        }
        return null;
    }

    //GETTER INFO FILE
    public SambaFile getFileInfo(String uuid) {
        if (OpenConnection()) {
            try {
                return (SambaFile) session.createQuery("from SambaFile where UUID = '" + uuid + "'").uniqueResult();
            } finally {
                CloseConnection();
            }
        }
        return null;
    }

    //GETTER INFO FILE
    public void saveScanReport(String uuid, int state, String report) {
        OpenConnection();
        try {
            session.beginTransaction();
            Query query = session.createQuery("update SambaFile set state= :state, report = :report where UUID = :uuid");
            query.setParameter("uuid", uuid);
            query.setParameter("state", state);
            query.setParameter("report", report);
            query.executeUpdate();
            session.getTransaction().commit();
        } finally {
            CloseConnection();
        }
    }

    private static class DataModelHolder {
        private static final DataModel INSTANCE = new DataModel();
    }

}

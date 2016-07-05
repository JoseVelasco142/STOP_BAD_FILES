package hibernate;

import java.io.Serializable;
import java.util.Date;
import workers.Core;

/**
 * Created by jose on 25/02/16.
 */
public class SambaFile implements Serializable {

    private int idFile;
    private String filename;
    private String size;
    private int state;
    private String owner;
    private Date datetime;
    private String UUID;
    private String report;
    private String path;

    public SambaFile() {
    }

    // NEWFILE CONTRUCTOR
    public SambaFile(String filename, String size, String owner, Date datetime, String UUID) {
        this.filename = filename;
        this.size = size;
        this.state = Core.NEW;
        this.owner = owner;
        this.datetime = datetime;
        this.UUID = UUID;
        this.report = "NOT SCANNED";
    }

    // FILESCAN CONSTRUCTOR
    public SambaFile(String UUID) {
        this.UUID = UUID;
    }

    public int getIdFile() {
        return idFile;
    }

    public void setIdFile(int idFile) {
        this.idFile = idFile;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String uidNumber) {
        this.owner = uidNumber;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String path) {
        this.UUID = path;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}

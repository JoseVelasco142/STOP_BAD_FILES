package workers;

import hibernate.SambaFile;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author jose
 */
public class Core {

    private static Core instance = null;
    public static ScheduledExecutorService schedulerExecutor = null;
    public static final int mappingInterval = 60;
    private static ExecutorService actionsExecutor = null;
    private static FileMapper fileMapper = null;
    private static List<SambaFile> filesToScan = null;
    private static ReentrantLock filesScanLock = null;
    private static final String COREPATH = File.separator + "StopBadFiles" + File.separator;
    public static final String SAMBAPATH = COREPATH + "samba" + File.separator;
    public static final String STOREPATH = COREPATH + "store" + File.separator;
    public static final int NEW = 0;
    public static final int CLEAN = 1;
    public static final int INFECTED = 2;
    public static final int EMPTY = 3;

    protected Core() {
        schedulerExecutor = Executors.newSingleThreadScheduledExecutor();
        actionsExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        fileMapper = new FileMapper();
        filesToScan = new ArrayList<>();
        filesScanLock = new ReentrantLock();
    }

    public static Core getInstance() {
        synchronized (Core.class) {
            if (instance == null) {
                instance = new Core();
            }
        }
        return instance;
    }

    public void init() {
            schedulerExecutor.schedule(fileMapper, mappingInterval, TimeUnit.SECONDS);
    }

    public void ScanFiles() {
        if(filesToScan.size()>0){
            filesToScan.stream().forEach((_item) -> {
                actionsExecutor.execute(new Scanner());
            }); 
        }
    }

    public boolean ListIsEmpty() {
        return filesToScan.isEmpty();
    }

    public void addFileToScan(SambaFile file) {
        filesToScan.add(file);
    }

    public SambaFile getOneToScan() {
        filesScanLock.lock();
        try {
            synchronized (Core.class) {
                SambaFile smbFile = filesToScan.get((int) (Math.random() * filesToScan.size()));
                filesToScan.remove(smbFile);
                return smbFile;
            }
        } finally {
            filesScanLock.unlock();
        }
    }
}

package workers;

import java.io.File;
import java.util.Date;
import java.util.UUID;
import hibernate.DataModel;
import hibernate.SambaFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;

/**
 * @author jose
 * 
 * Durante la extracción de los atributos de los fichero y tras reiteradas ejecuciones de la aplicación recibía
 * excepciones sin impacto funcional (que hasta donde pude averiguar se deben a las hebras que se quedan en ejecución
 * en Tomcat). Debido a estas excepciones la ejecución de la hebra programa se detenía tal y como especifica el javadoc,
 * una de las soluciones que encontré era realizar en el 'finaly' dentro del método run() una reinvocación de la tarea 
 * programada. Sinceramente no me convence...
 * 
 */

public class FileMapper implements Runnable {
    
    private String filename;
    private String size;
    private String uid;
    private Date date;
    private String uuid;
    private Path sambaPath;
    private String extension;
    private Path storePath;
    
    public FileMapper() {
    }

    public void listDirectory(String path, int level) {
        File directory = new File(path);
        File[] firstLevel = directory.listFiles();
        if (firstLevel != null && firstLevel.length > 0) {
            for (File file : firstLevel) {
                if (file.isDirectory()) 
                    listDirectory(file.getAbsolutePath(), level + 1);
                else {
                    try {
                        filename = file.getName();
                        size = Long.toString(file.length());
                        uid = Files.getOwner(file.toPath()).getName();
                        date = new Date(file.lastModified());
                        uuid = UUID.randomUUID().toString();
                        extension = getExtension(filename);
                        sambaPath = Paths.get(file.getAbsolutePath());
                        storePath = Paths.get(Core.STOREPATH + uuid + "." + extension);
                        DataModel.getInstance().saveNewFile(filename, size, uid, date, uuid + "." + extension);
                        Files.move(sambaPath, storePath, StandardCopyOption.REPLACE_EXISTING);
                        Core.getInstance().addFileToScan(new SambaFile(uuid + "." + extension));
                    } catch (Exception e){
                    }
                }
            }
        }
        Core.getInstance().ScanFiles();
    }

    public static String getExtension(String filename) {
        int extensionPos = filename.lastIndexOf('.');
        int lastUnixPos = filename.lastIndexOf('/');
        int lastWindowsPos = filename.lastIndexOf('\\');
        int lastSeparator = Math.max(lastUnixPos, lastWindowsPos);
        int index = lastSeparator > extensionPos ? -1 : extensionPos;
        if (index == -1) 
            return "";
        else 
            return filename.substring(index + 1);
    }

    @Override
    public void run() {
        try {
            listDirectory(Core.SAMBAPATH, 0);
        } catch (Exception e){
        } finally {
            Core.schedulerExecutor.schedule(this, Core.mappingInterval, TimeUnit.SECONDS);
        }
    }
}

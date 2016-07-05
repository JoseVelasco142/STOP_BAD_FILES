package workers;

import hibernate.DataModel;
import hibernate.SambaFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author jose
 * 
 * Sé que sería mucho mas simple pasar el path del directorio a clamscan y recoger en el buffer el 
 * restado de todos los ficheros pero me pareció buena la idea para jugar con el pool de hebras y el acceso concurrente 
 * 
 */

public class Scanner implements Runnable {

    private SambaFile file;
    
    @Override
    public void run() {
        try {
            if (!Core.getInstance().ListIsEmpty()) {
                file = Core.getInstance().getOneToScan();
                Runtime rtm = Runtime.getRuntime();
                Process process = rtm.exec("clamscan " + Core.STOREPATH + file.getUUID());
                process.waitFor();
                try (BufferedReader buffer = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = buffer.readLine()) != null) {
                        String pathFile = line.split(":")[0];
                        String report = line.split(":")[1];
                        if (pathFile.equals(Core.STOREPATH + file.getUUID())) {
                            switch (report) {
                                case " OK":
                                    DataModel.getInstance().saveScanReport(file.getUUID(), Core.CLEAN, "No threats found");
                                    break;
                                case " Empty file":
                                    DataModel.getInstance().saveScanReport(file.getUUID(), Core.EMPTY, report);
                                    break;
                                default:
                                    DataModel.getInstance().saveScanReport(file.getUUID(), Core.INFECTED, report);
                                    break;
                            }
                        }
                    }
                }
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Scanner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

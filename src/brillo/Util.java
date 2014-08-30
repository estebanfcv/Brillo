package brillo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JOptionPane;

/**
 *
 * @author estebanfcv
 */
public class Util {

    public static void cerrarProcesos(BufferedInputStream bf, InputStream is, Process process) {
        try {
            if (bf != null) {
                bf.close();
            }
            if (is != null) {
                is.close();
            }
            if (process != null) {
                process.destroy();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static int ObtenerBrilloMaximo() {
        String[] brilloActual = {"sh", "-c", "cat /sys/class/backlight/intel_backlight/max_brightness"};
        Process process = null;
        int brillo = 0;
        InputStream is = null;
        BufferedInputStream bf = null;
        try {
            process = Runtime.getRuntime().exec(brilloActual);
            is = process.getInputStream();
            bf = new BufferedInputStream(is);
            byte[] contents = new byte[1024];
            int bytesRead;
            String strFileContents;
            if ((bytesRead = bf.read(contents)) != -1) {
                strFileContents = new String(contents, 0, bytesRead);
                brillo = Integer.parseInt(strFileContents.trim());
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            cerrarProcesos(bf, is, process);
        }
        return brillo;
    }
}

package brillo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static brillo.Util.cerrarProcesos;
import static brillo.Constantes.inicializarBrilloMaximo;

/**
 *
 * @author estebanfcv
 */
public class Brillo {

    public static int brillo = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String[] opciones = {"+", "-"};
        int opcion;
        do {
            inicializarBrilloMaximo();
            if (Constantes.brilloMaximo == 0) {
                JOptionPane.showMessageDialog(null, "No se pudo accesar a la propiedad brillo del equipo");
                return;
            }
            opcion = JOptionPane.showOptionDialog(null, valorBrillo(), "Brillo", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, new ImageIcon(new Constantes().icono), opciones, opciones[0]);
            if (opcion == 0) {
                subirBrillo();
            } else if (opcion == 1) {
                bajarBrillo();
            }
        } while (opcion != -1);
    }

    private static String valorBrillo() {
        String[] brilloActual = {"sh", "-c", "cat /sys/class/backlight/intel_backlight/brightness"};
        Process process = null;
        InputStream is = null;
        BufferedInputStream bf = null;
        try {
            process = Runtime.getRuntime().exec(brilloActual);
            is = process.getInputStream();
            bf = new BufferedInputStream(is);
            byte[] contents = new byte[1024];
            int bytesRead;
            if ((bytesRead = bf.read(contents)) != -1) {
                brillo = Integer.parseInt(new String(contents, 0, bytesRead).trim());
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            cerrarProcesos(bf, is, process);
        }
        return String.valueOf(((brillo * 100) / Constantes.brilloMaximo)) + "%";
    }

    public static void subirBrillo() {
        if (brillo >= Constantes.brilloMaximo) {
            JOptionPane.showMessageDialog(null, "El brillo esta al máximo");
            return;
        }
        String[] comando = {"sh", "-c", "echo " + (brillo + 500) + " > /sys/class/backlight/intel_backlight/brightness"};
        try {
            Runtime.getRuntime().exec(comando);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public static void bajarBrillo() {
        if (brillo <= 312) {
            JOptionPane.showMessageDialog(null, "El brillo esta al mínimo");
            return;
        }
        String[] comando = {"sh", "-c", "echo " + (brillo - 500) + " > /sys/class/backlight/intel_backlight/brightness"};
        try {
            Runtime.getRuntime().exec(comando);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brillo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static brillo.Util.cerrarProcesos;

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
        String[] opciones = {"Subir", "Bajar"};
        int opcion;
        do {
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
            int bytesRead = 0;
            String strFileContents;
            while ((bytesRead = bf.read(contents)) != -1) {
                strFileContents = new String(contents, 0, bytesRead);
                brillo = Integer.parseInt(strFileContents.trim());
            }
            return porcentajeBrillo(brillo);
        } catch (IOException ex) {
            ex.printStackTrace();
            return porcentajeBrillo(brillo);
        } finally {
            cerrarProcesos(bf, is, process);
        }
    }

    private static String porcentajeBrillo(int brillo) {
        //7812 brillo maximo
        //312 brillo minimo
        if (brillo == 0) {
            return "0%";
        }
        float x = (brillo * 100) / Constantes.brilloMaximo;
        return String.valueOf(Math.round(x)) + "%";
    }

    public static void subirBrillo() {
        if (brillo == 7812) {
            JOptionPane.showMessageDialog(null, "El brillo esta al máximo");
            return;
        }
        String[] command = {"sh", "-c", "echo " + (brillo + 500) + " > /sys/class/backlight/intel_backlight/brightness"};
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException ex) {
            Logger.getLogger(Brillo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void bajarBrillo() {
        if (brillo == 312) {
            JOptionPane.showMessageDialog(null, "El brillo esta al mínimo");
            return;
        }
        String[] command = {"sh", "-c", "echo " + (brillo - 500) + " > /sys/class/backlight/intel_backlight/brightness"};
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException ex) {
            Logger.getLogger(Brillo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
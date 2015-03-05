package brillo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
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
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
            if ((bytesRead = bf.read(contents)) != -1) {
                brillo = Integer.parseInt(new String(contents, 0, bytesRead).trim());
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            cerrarProcesos(bf, is, process);
        }
        return brillo;
    }

    public static String obtenerDatos() {
        String[] cmd = {"/bin/bash", "-c", "echo Zelda090| sudo -S " + "cat /etc/rc.local"};
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            String s;
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void crearArchivo(int valor) {
        try {
            BufferedReader b = new BufferedReader(new FileReader("/etc/rc.local"));
            File f = new File("/etc/rc.local");
            FileOutputStream fos = new FileOutputStream(f);
            String s;
            String texto = "";
            while ((s = b.readLine()) != null) {
                if (s.contains("echo")) {
                    s = s.replace(obtenerSoloNumero(s), String.valueOf(valor));
                }
                texto += s + "\n";
            }
            byte[] contentInBytes = texto.getBytes();
            fos.write(contentInBytes);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String obtenerSoloNumero(String s) {
        String numero = "";
        for (int i = 0; i < s.length(); i++) {
            char x = s.charAt(i);
            if (x >= 48 && x <= 57) {
                numero += s.charAt(i);
            }
        }
        return numero;
    }
}

package brillo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author estebanfcv
 */
public class Util {

    public static void cerrarProcesos(BufferedInputStream bf, InputStream is, Process process, BufferedReader br) {
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
            if (br != null) {
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int obtenerBrilloMaximo() {
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
            ex.printStackTrace();
        } finally {
            cerrarProcesos(bf, is, process, null);
        }
        return brillo;
    }

    public static int obtenerBrilloActual(int BRILLO_MAXIMO) {
        int actual = 0;
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
                actual = new Integer(new String(contents, 0, bytesRead).trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarProcesos(bf, is, process, null);
        }
        return Math.round((float) actual * 100 / (float) BRILLO_MAXIMO);
    }

    public static void cambiarBrillo(int BRILLO_MAXIMO, int valor) {
        String[] comando = {"sh", "-c", "echo " + ((BRILLO_MAXIMO * valor) / 100) + " > /sys/class/backlight/intel_backlight/brightness"};
        try {
            Runtime.getRuntime().exec(comando);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void guardarBrilloActual(int valor) {
        BufferedReader b = null;
        try {
            b = new BufferedReader(new FileReader("/etc/rc.local"));
            String cadenaOriginal = "";
            String cadenaFinal = "";
            while ((cadenaOriginal = b.readLine()) != null) {
                if (cadenaOriginal.contains("echo")) {
                    cadenaFinal = cadenaOriginal.replace(obtenerSoloNumero(cadenaOriginal), String.valueOf(valor));
                    break;
                }
            }
            cadenaOriginal = cadenaOriginal.replace("/", "\\/");
            cadenaFinal = cadenaFinal.replace("/", "\\/");
            if (!cadenaOriginal.isEmpty() && !cadenaFinal.isEmpty()) {
                String[] sed = {"/bin/bash", "-c", "echo Zelda090| sudo -S " + "sed  -i  's/" + cadenaOriginal + "/" + cadenaFinal + "/g' /etc/rc.local"};
                new ProcessBuilder(sed).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarProcesos(null, null, null,b);
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

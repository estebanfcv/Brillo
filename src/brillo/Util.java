/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brillo;

import java.io.BufferedInputStream;
import java.io.InputStream;

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
            e.printStackTrace();
        }
    }

}

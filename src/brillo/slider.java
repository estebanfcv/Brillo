package brillo;

import static brillo.Util.cerrarProcesos;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author estebanfcv
 */
public class slider extends javax.swing.JFrame {

    public final int BRILLO;

    /**
     * Creates new form slider
     */
    public slider() {
        BRILLO = obtenerBrilloMaximo();
        initComponents();
        JSBrillo.setValue(obtenerBrilloActual());
    }

    private int obtenerBrilloMaximo() {
        int maximo = 0;
        String[] brilloMaximo = {"sh", "-c", "cat /sys/class/backlight/intel_backlight/max_brightness"};
        Process process = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try {
            process = Runtime.getRuntime().exec(brilloMaximo);
            is = process.getInputStream();
            bis = new BufferedInputStream(is);
            byte[] contents = new byte[1024];
            int bytesRead;
            if ((bytesRead = bis.read(contents)) != -1) {
                maximo = new Integer(new String(contents, 0, bytesRead).trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarProcesos(bis, is, process);
        }
        return maximo;
    }

    private int obtenerBrilloActual() {
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
            cerrarProcesos(bf, is, process);
        }
        return actual * 100 / BRILLO;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JSBrillo = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        JSBrillo.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                JSBrilloMouseWheelMoved(evt);
            }
        });
        JSBrillo.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                JSBrilloStateChanged(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brillo/icono.png"))); // NOI18N
        jLabel1.setToolTipText("Brillo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JSBrillo, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(126, 126, 126))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JSBrillo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JSBrilloStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_JSBrilloStateChanged
        if (JSBrillo.getValue() > 0) {
            String[] comando = {"sh", "-c", "echo " + ((BRILLO * JSBrillo.getValue()) / 100) + " > /sys/class/backlight/intel_backlight/brightness"};
            try {
                Runtime.getRuntime().exec(comando);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_JSBrilloStateChanged

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        JSBrillo.setMinimum(0);
        JSBrillo.setMaximum(100);
        JSBrillo.setMajorTickSpacing(10);
        JSBrillo.setMinorTickSpacing(10);
        JSBrillo.setPaintLabels(true);
    }//GEN-LAST:event_formWindowOpened

    private void JSBrilloMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_JSBrilloMouseWheelMoved
        int giro = evt.getWheelRotation();
        JSBrillo.setValue(giro < 0 ? JSBrillo.getValue() + 1 : JSBrillo.getValue() - 1);
    }//GEN-LAST:event_JSBrilloMouseWheelMoved

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(slider.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(slider.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(slider.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(slider.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new slider().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSlider JSBrillo;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}

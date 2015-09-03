package brillo;

import javax.swing.JSlider;

/**
 *
 * @author estebanfcv
 */
public class Hilo implements Runnable {

    private final int BRILLO_MAXIMO;
    private boolean corriendo;
    private JSlider js;

    public Hilo(int BRILLO_MAXIMO) {
        this.BRILLO_MAXIMO = BRILLO_MAXIMO;
    }

    @Override
    public void run() {
        while (corriendo) {
            try {
                int brilloAnterior = js.getValue();
                int brilloActual = Util.obtenerBrilloActual(BRILLO_MAXIMO);
                if (brilloAnterior != brilloActual) {
                    js.setValue(brilloActual);
                }
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isCorriendo() {
        return corriendo;
    }

    public void setCorriendo(boolean corriendo) {
        this.corriendo = corriendo;
    }

    public void setJs(JSlider js) {
        this.js = js;
    }
}

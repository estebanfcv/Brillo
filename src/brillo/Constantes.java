package brillo;

import java.net.URL;
import static brillo.Util.ObtenerBrilloMaximo;

/**
 *
 * @author estebanfcv
 */
public class Constantes {

    public final URL icono = getClass().getResource("icono.png");
    public static final int brilloMaximo = inicializarBrilloAlMaximo();

    public static int inicializarBrilloAlMaximo() {
        return ObtenerBrilloMaximo();
    }
}
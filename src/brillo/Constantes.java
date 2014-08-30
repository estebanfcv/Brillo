package brillo;

import java.net.URL;
import static brillo.Util.ObtenerBrilloMaximo;

/**
 *
 * @author estebanfcv
 */
public class Constantes {

    public final URL icono = getClass().getResource("icono.png");
    public static final int brilloMaximo = inicializarBrilloMaximo();

    public static int inicializarBrilloMaximo() {
        return ObtenerBrilloMaximo();
    }
}
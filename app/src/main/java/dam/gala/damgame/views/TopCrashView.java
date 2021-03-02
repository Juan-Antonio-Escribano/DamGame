package dam.gala.damgame.views;

import androidx.annotation.NonNull;

import java.util.Random;
/**
 * Bloque de choque de la parte superior de la escena
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public class TopCrashView extends CrashView{
    /**
     * Construye el bloque de choque de la parte superior
     * @param gameView Jugada en la que se construye el bloque de choque
     */
    public TopCrashView(@NonNull GameView gameView) {
        super(gameView);
        Random random = new Random();
        int max;
        //la altura del bloque se generará aleatoriamente. Los valores oscilarán entre
        //la altura de la pantalla menos la altura del OGP, menos la altura mínima del bloque
        //, menos el hueco
        max = this.getScene().getScreenHeight() - (this.getScene().
                getBouncyViewHeight() + this.getMinHeight()
                + this.getCrashBlockGap());

        //se calculan las coordenadas x e y
        this.setHeight(random.nextInt(max - this.getMinHeight()) + this.getMinHeight());
        this.setyCoor(this.getHeight()-this.getScene().getScreenHeight());
    }
}

package dam.gala.damgame.views;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import dam.gala.damgame.scenes.Scene;

/**
 * Explosión que se produce cuando flappy cae al suelo
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public class ExplosionView {
    private int spriteWidth;
    private int spriteHeight;
    public int xCoord, yCoord;
    private int spriteIndex;
    private GameView gameView;
    private Bitmap explosionBitmap;
    private boolean finished;
    private int bouncyViewHeigth;

    /**
     * Construye la animación para una explosión
     * @param gameView Gestor del juego
     */
    public ExplosionView(GameView gameView){
        this.gameView = gameView;
        Scene scene = this.gameView.getScene();
        this.spriteWidth = scene.getExplosionViewWidth()/scene.getExplosionViewImgNumber();
        this.spriteHeight = scene.getExplosionViewHeight();
        this.explosionBitmap = scene.getExplosionViewBitmap();
        this.bouncyViewHeigth = scene.getBouncyViewHeight();
        spriteIndex = -1; //recien creado
    }

    /**
     * Actualiza el estado la animación de explosión
     */
    public void updateState(){
        if(isFinished()) return;
        //incrementamos el estado al siguiente momento de la explosión
        this.spriteIndex++;
    }

    /**
     * Dibuja la explosión
     * @param canvas Lienzo del dibujo
     * @param paint Estilo y color para pintar en el lienzo
     */
    public void draw(Canvas canvas, Paint paint){
        if(!isFinished()) {
            this.yCoord = this.gameView.getBouncyView().getyCurrentCoord();
            this.xCoord = this.gameView.getBouncyView().getxCoord();
            //Calculamos el cuadrado del sprite que vamos a dibujar
            Rect startRect = new Rect(this.spriteIndex * this.spriteWidth,
                    0, (this.spriteIndex * this.spriteWidth) + this.spriteWidth,
                    this.spriteHeight);

            //calculamos donde vamos a dibujar la porcion del sprite
            Rect endRect = new Rect((int) this.xCoord,(int) this.yCoord,
                    (int) this.xCoord + this.bouncyViewHeigth,
                    (int) this.yCoord + this.bouncyViewHeigth);

            canvas.drawBitmap(this.explosionBitmap,startRect,endRect,paint);
        }else{
            this.gameView.setStopGame(true);
        }

    }
    /**
     * Obtiene el estado de finalización de la animación de la explosión
     * @return Devuelve true si la animación ha finalizado, es decir se han mostrado todas las
     * imágenes del sprite de explosión
     */
    public boolean isFinished(){
        return this.spriteIndex>=this.gameView.getScene().getExplosionViewImgNumber();
    }

    public Bitmap getExplosionBitmap(){
        return this.explosionBitmap;
    }
}

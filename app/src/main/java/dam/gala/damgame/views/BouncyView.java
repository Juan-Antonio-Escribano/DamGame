package dam.gala.damgame.views;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import dam.gala.damgame.model.GameConfig;
import dam.gala.damgame.model.Play;
import dam.gala.damgame.scenes.Scene;

import java.util.Iterator;

/**
 * Personaje principal, debe sortear obstáculos y responder el máximo número de preguntas
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public class BouncyView {
    private int spriteWidth;
    private int spriteHeight;
    public int xCoord, yCoord, yCurrentCoord;
    private int spriteIndex;
    private Bitmap bouncyBitmap;
    private int gravity;
    private boolean finished;
    private boolean landed;
    private boolean collision;
    private boolean questionCatched;
    private GameView gameView;
    private GameConfig gameConfig;
    private QuestionView questionViewCatched;
    private Play play;

    public BouncyView(GameView gameView) {
        this.gameView = gameView;
        this.play = gameView.getPlay();
        Scene scene = this.gameView.getScene();
        gameConfig = gameView.getGameActivity().getGameConfig();
        this.yCoord = scene.getScreenHeight() / 2 - scene.getBouncyViewHeight() / 2;
        this.xCoord = scene.getBouncyViewWidth() / 5;
        this.yCurrentCoord = yCoord;
        this.spriteWidth = scene.getBouncyViewWidth() / scene.getBackgroundViewImgNumber();
        this.spriteHeight = scene.getBouncyViewHeight()/3;
        this.bouncyBitmap = scene.getBouncyViewBitmap();
        this.gravity = scene.getScreenWidth() * this.gameConfig.getGravity()/1920;
        spriteIndex = -1; //recien creado
    }

    public void updateState() {
        if(this.finished) {
            this.landed = false;
            return;
        }
        if (gravity > (this.yCoord)) {
            this.gravity = 0;
            this.spriteIndex = -1;
            this.finished = true;
            this.landed = true;
            this.play.setLifes(this.play.getLifes()-1);
        } else {
            if (this.spriteIndex == 8)
                this.spriteIndex = -1;
        }

        for(CrashView crashView:this.gameView.getPlay().getCrashViews()){
            if(this.xCoord+this.spriteWidth>=crashView.getxCoor()) {
                this.collision = true;
                this.play.setLifes(this.play.getLifes()-1);
                break;
            }
        }
        //Genera las preguntas
        Iterator iterator = this.gameView.getPlay().getQuestionViews().iterator();
        QuestionView questionView;
        while(iterator.hasNext() && !this.isFinished()){
            questionView = (QuestionView) iterator.next();
            if(this.xCoord+this.spriteWidth>=questionView.getxCoor()
                    && this.yCoord+this.spriteHeight>=questionView.getyCoor()) {
                this.questionCatched = true;
                questionView.setQuestionCatched(true);
                this.questionViewCatched = questionView;
                iterator.remove();
            }
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        //terminará la animación de movimiento cuando se capture una pregunta
        //, se choque con un obstáculo o se caiga al suelo
        if (isFinished())
            return;

        this.spriteIndex++;
        //se calcula el area del bitmap que se va a dibujar
        /*
        Rect startRect = new Rect(this.spriteIndex * this.spriteWidth, 0,
                (this.spriteIndex + 1) * this.spriteWidth
                , this.spriteHeight);
        Rect endRect = new Rect(this.xCoord, this.yCoord + this.gravity, this.xCoord +
                this.spriteWidth
                , this.yCoord + this.spriteHeight + this.gravity);
        */
        Bitmap bouncy;
        if (spriteIndex<3)
            bouncy = Bitmap.createBitmap(this.getBouncyBitmap(),
                    this.spriteWidth*this.spriteIndex, 0, this.spriteHeight, this.spriteHeight);
        else if (spriteIndex<6)bouncy = Bitmap.createBitmap(this.getBouncyBitmap(),
                this.spriteWidth*(this.spriteIndex-3), this.spriteHeight, this.spriteWidth, this.spriteHeight);
        else bouncy = Bitmap.createBitmap(this.getBouncyBitmap(),
                    this.spriteWidth*(this.spriteIndex-6), this.spriteHeight*2, this.spriteWidth, this.spriteHeight);

        canvas.drawBitmap(bouncy, this.getxCoord(), this.getyCurrentCoord(), paint);

        //comentar las líneas de abajo para quitar la gravedad
        this.gravity+=this.gameConfig.getGravity();
        this.yCurrentCoord = this.yCoord + this.gravity;
    }

    /**
     * Tererminará la animación de movimiento cuando se capture una pregunta
     * o se choque con un obstáculo
     *
     * @return boolean Valor true significa que se debe detener la animación
     */
    public boolean isFinished() {
        return this.landed || this.collision || this.questionCatched;
    }

    public int getxCoord() {
        return this.xCoord;
    }

    public int getyCoord() {
        return this.yCoord;
    }

    public int getyCurrentCoord(){
        return this.yCurrentCoord;
    }

    public int getSpriteIndex() {
        return spriteIndex;
    }

    public Bitmap getBouncyBitmap() {
        return this.bouncyBitmap;
    }

    public boolean isLanded() {
        return this.landed;
    }
    public boolean isColluded(){
        return this.collision;
    }
    public boolean isQuestionCatched(){
        return this.questionCatched;
    }
    public QuestionView getQuestionCatched(){
        return this.questionViewCatched;
    }
    public void setQuestionViewCatched(QuestionView questionViewCatched){
        this.questionViewCatched = questionViewCatched;
    }
    public void reStart(){
        this.collision=false;
        this.landed = false;
        this.questionCatched = false;
    }
}

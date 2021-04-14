package dam.gala.damgame.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

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
    private boolean floor=false, jump=false, ceiling=false;
    private int framesDrawBouncy, framesJump, framesJumper=10;
    public BouncyView(GameView gameView) {
        this.gameView = gameView;
        this.play = gameView.getPlay();
        Scene scene = this.gameView.getScene();
        gameConfig = gameView.getGameActivity().getGameConfig();
        this.yCoord = scene.getScreenHeight() / 2 - scene.getBouncyViewHeight() / 2;
        this.xCoord = scene.getBouncyViewWidth() / 5;
        this.yCurrentCoord = yCoord;
        this.spriteWidth = scene.getBouncyViewWidth() / scene.getBouncyViewImgNumber();
        this.spriteHeight = scene.getBouncyViewHeight()/3;
        this.bouncyBitmap = scene.getBouncyViewBitmap();
        this.gravity = scene.getScreenHeight() * this.gameConfig.getGravity()/1920;
        spriteIndex = 0; //recien creado
    }

    public void updateState() {
        if(this.finished) {
            this.landed = false;
            return;
        }
         if (this.spriteIndex == 7)
            this.spriteIndex = 0;
        /*
            Comprobacion de choque del pajaro contra los obstaculos
         */
        for(CrashView crashView:this.gameView.getPlay().getCrashViews()){
            /*
                Comprobacion del choque del pajaro contra el obstaculo que esta situado en el techo
                * Comprueba si el pajaro(Objeto Grafico Principal) esta situado entre la posicion X y la posicion X mas el ancho del obtaculo
                * y si la posicion Y es menor que la altura del obstaculo.
                * Si ocurre estas condiciones el pajaro habra chocado con el obstaculo del techo
             */
            if(this.getxCoord()+this.spriteWidth>=crashView.getxCoor()&&this.getxCoord()+this.spriteWidth<=crashView.getxCoor()+crashView.getWidth()
                    &&this.getyCurrentCoord()<=crashView.getHeight()) {
                this.collision = true;
                this.play.setLifes(this.play.getLifes()-1);
                break;
            }
            /*
                Comprobacion del choque del pajaro contra el obstaculo que esta situado en el suelo
                * Comprueba si el pajaro(Objeto Grafico Principal) esta situado entre la posicion X y la posicion X mas el ancho del obtaculo y
                * si la posicion Y del pajaro esta entre la altura de la pantalla y la altura de la pantalla mas el alto del obtaculo del suelo
                * Si ocurre estas condiciones el pajaro habra chocado con el obstaculo del suelo
             */
            if(this.getxCoord()+this.spriteWidth>=crashView.getxCoor()&&this.getxCoord()+this.spriteWidth<=crashView.getxCoor()+crashView.getWidth()
                    &&this.getyCurrentCoord()+this.spriteHeight>=this.play.getScene().getScreenHeight()-crashView.getHeightDown()) {
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
        if (this.framesJump==this.framesJumper||isCeiling()){
            this.framesJump=0;
            this.framesJumper=5;
            this.setJump(false);
        }
        //Gravedad
        if (isJump()){
            this.framesJump++;
            this.yCurrentCoord = this.yCurrentCoord-10;
        }else if(!isFloor()){
            this.yCurrentCoord = this.getyCurrentCoord()+this.gameConfig.getGravity();
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        //terminará la animación de movimiento cuando se capture una pregunta
        //, se choque con un obstáculo o se caiga al suelo
        if (isFinished())
            return;
        this.framesDrawBouncy++;
        if (this.framesDrawBouncy==3){
            this.spriteIndex++;
            this.framesDrawBouncy=0;
        }
        //Animacion del pajaro
        Bitmap bouncy;
        if (spriteIndex<3)
            bouncy = Bitmap.createBitmap(this.getBouncyBitmap(),
                    this.spriteWidth*this.spriteIndex, 0, this.spriteHeight, this.spriteHeight);
        else if (spriteIndex<6)bouncy = Bitmap.createBitmap(this.getBouncyBitmap(),
                this.spriteWidth*(this.spriteIndex-3), this.spriteHeight, this.spriteWidth, this.spriteHeight);
        else bouncy = Bitmap.createBitmap(this.getBouncyBitmap(),
                    this.spriteWidth*(this.spriteIndex-5), this.spriteHeight*2, this.spriteWidth, this.spriteHeight);

        bouncy=Bitmap.createScaledBitmap(bouncy, this.gameView.getScene().getScreenWidth()*10/100, this.gameView.getScene().getScreenWidth()*10/100,true);
        canvas.drawBitmap(bouncy, this.getxCoord(), this.getyCurrentCoord(), paint);

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

    public int getSpriteHeight() {
        return spriteHeight;
    }

    public void setSpriteHeight(int spriteHeight) {
        this.spriteHeight = spriteHeight;
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

    public boolean isFloor() {
        return floor;
    }

    public void setFloor(boolean floor) {
        this.floor = floor;
    }

    public boolean isJump() {
        return jump;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public boolean isCeiling() {
        return ceiling;
    }

    public void setCeiling(boolean ceiling) {
        this.ceiling = ceiling;
    }

    public int getFramesJumper() {
        return framesJumper;
    }

    public void setFramesJumper(int framesJumper) {
        this.framesJumper = framesJumper;
    }

    public void reStart(){
        this.collision=false;
        this.landed = false;
        this.questionCatched = false;
    }
}

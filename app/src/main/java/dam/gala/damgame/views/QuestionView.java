package dam.gala.damgame.views;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;

import java.util.Random;

import dam.gala.damgame.model.Play;
import dam.gala.damgame.model.Question;
import dam.gala.damgame.scenes.Scene;
import dam.gala.damgame.utils.GameUtil;

/**
 * Vista gráfica del objeto de una pregunta
 *
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public class QuestionView {
    private int spriteWidth, spriteHeight;
    private int questionWidth, questionHeight;
    private int xCoor, yCoor;
    private int spriteIndex;
    private Play play;
    private Scene scene;
    private Bitmap questionBitmap;
    private Question question;
    private int verticalDirection;
    private int horizontalDirection;
    private int speed;
    private boolean questionCatched;
    private int frameDrawQuestion=0, row=0;

    /**
     * Construye la vista de una pregunta
     * @param play Jugada en la que se construye la vista gráfica de una pregunta
     * @param question Pregunta relacionada con la vista gráfica
     */
    public QuestionView(@NonNull Play play, @NonNull Question question) {
        Random random;
        float randomCoor;
        this.play = play;
        this.scene = play.getScene();
        this.question = question;
        spriteWidth = this.scene.getQuestionViewWidth()/4;
        spriteHeight =this.scene.getQuestionViewHeight()/4;
        spriteIndex = 0;
        questionHeight=200;
        questionWidth=200;
        this.questionBitmap = play.getScene().getQuestionViewBitmap(question.getComplejidad());
        random = new Random();
        switch (question.getComplejidad()){
            case GameUtil.PREGUNTA_COMPLEJIDAD_ALTA:
                this.speed =  GameUtil.HIGH_COMPLEX_SPEED * this.scene.getScreenWidth() /1920;
                this.questionBitmap = this.scene.getQuestionViewBitmap(GameUtil.PREGUNTA_COMPLEJIDAD_ALTA);
                break;
            case GameUtil.PREGUNTA_COMPLEJIDAD_BAJA:
                this.speed = GameUtil.LOW_COMPLEX_SPEED * this.scene.getScreenWidth() /1920;
                this.questionBitmap = this.scene.getQuestionViewBitmap(GameUtil.PREGUNTA_COMPLEJIDAD_BAJA);
                break;
        }
        //cálculo de dirección aleatoria de cada pregunta generada
        if(random.nextFloat()>0.5)
            this.horizontalDirection = 1;
        else
            this.horizontalDirection = -1;

        if(random.nextFloat()>0.5)
            this.verticalDirection = 1;
        else
            this.verticalDirection = -1;

        /* Posicionamiento de la pregunta */
        //entre 0,66y 0.999 sale por arriba (y=0, x=aleatorio(1/5) alto pantalla)
        //entre 0.33 y 0.66 sale por abajo (y=AltoPantalla-altobitmap, x=aleatorio(1/5))
        //< 0,33 sale por el centro (x=0, y=aleatorio entre 0
        // y AltoPantalla-AltoBitmap)
        randomCoor = random.nextFloat();
        if (randomCoor>=0.33) {
            //probabilidad de que la pregunta salga por los arriba o abajo
            if (randomCoor > 0.66) //sale por la arriba
                this.yCoor =0;
            else
                this.yCoor = this.scene.getScreenHeight() -
                        this.spriteHeight;
        } else {
            this.yCoor = (int) (random.nextFloat() * (this.scene.getScreenHeight()
                                - this.spriteHeight));
        }
        this.xCoor = (int) (this.scene.getScreenWidth()+ (random.nextFloat() * 10));
        //prueba para hacer que la pregunta choque con el bouncy
        /*this.yCoor = scene.getScreenHeight() / 2 - scene.getBouncyViewHeight() / 2;
        this.xCoor = scene.getScreenWidth() / 4;
        this.horizontalDirection=-1;*/
    }

    /**
     * Actualiza el estado gráfico y de animación del objeto gráfico de una pregunta
     */
    public void updatePosition(){
        //se calcula la nueva posición de la pregunta
        this.xCoor += this.horizontalDirection * this.speed;
        this.yCoor += this.verticalDirection * this.speed;
        //Cambios de direcciones al llegar a los bordes de la pantalla
        if(this.yCoor <=0 && this.verticalDirection ==-1)
            this.verticalDirection =1;
        if(this.yCoor >this.scene.getScreenHeight()-
                this.spriteHeight &&
                this.verticalDirection ==1)
            this.verticalDirection =-1;

        if(this.xCoor >=this.scene.getScreenWidth() && this.horizontalDirection ==1)
            this.horizontalDirection =-1;
        if(this.xCoor <=0 && this.horizontalDirection ==-1)
            this.horizontalDirection =1;
    }

    /**
     * Dibuja el objeto gráfico de la pregunta
     * @param canvas Lienzo donde se dibuja el objeto gráfico de la pregunta
     * @param paint Estilo y color con el que se dibuja
     */
    public void draw(Canvas canvas, Paint paint){
        if(!isFinished()) {
            /*
                Animación de la pregunta
             */
            this.frameDrawQuestion++;
            if(this.frameDrawQuestion==2){
                this.row=this.spriteIndex==3?this.row+1:this.row;
                this.row=this.row==3?0:this.row;
                this.spriteIndex=this.spriteIndex==3?0:this.spriteIndex+1;
                this.frameDrawQuestion=0;
            }

            Bitmap nowQuestionFrame=Bitmap.createBitmap(this.questionBitmap, this.spriteWidth*this.spriteIndex, this.spriteHeight*this.row,
                    this.spriteWidth ,this.spriteHeight);
            nowQuestionFrame=Bitmap.createScaledBitmap(nowQuestionFrame,questionWidth, questionHeight,true);
            canvas.drawBitmap(nowQuestionFrame, this.getxCoor(), this.getyCoor(), paint);
        }
    }

    /**
     * Comprueba si hay que seguir mostrando el objeto gráfico de la pregunta
     * @return
     */
    public boolean isFinished(){
        return spriteIndex >=this.scene.getBackgroundViewImgNumber() || this.questionCatched;
    }
    public Question getQuestion() {
        return question;
    }
    public int getxCoor(){
        return this.xCoor;
    }
    public int getyCoor(){
        return this.yCoor;
    }

    public boolean isQuestionCatched() {
        return questionCatched;
    }

    public void setQuestionCatched(boolean questionCatched) {
        this.questionCatched = questionCatched;
    }
    public QuestionView getQuestionCatched(){
        return this;
    }

    public int getQuestionWidth() {
        return questionWidth;
    }

    public int getQuestionHeight() {
        return questionHeight;
    }
}

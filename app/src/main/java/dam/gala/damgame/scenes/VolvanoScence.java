package dam.gala.damgame.scenes;

import android.graphics.Bitmap;
import android.view.View;

import dam.gala.damgame.activities.GameActivity;

public class VolvanoScence extends Scene{
    /**
     * Construye la escena del juego
     *
     * @param gameActivity Actividad principal del juego
     */
    public VolvanoScence(GameActivity gameActivity) {
        super(gameActivity);
    }

    @Override
    public int getQuestionViewWidth() {
        return 0;
    }

    @Override
    public int getQuestionViewHeight() {
        return 0;
    }

    @Override
    public Bitmap getQuestionViewBitmap(int complexity) {
        return null;
    }

    @Override
    public int getBackgroundViewImgNumber() {
        return 0;
    }

    @Override
    public int getBouncyViewWidth() {
        return 0;
    }

    @Override
    public int getBouncyViewHeight() {
        return 0;
    }

    @Override
    public Bitmap getBouncyViewBitmap() {
        return null;
    }

    @Override
    public int getBouncyViewImgNumber() {
        return 0;
    }

    @Override
    public int getBackgroundBitmapId() {
        return 0;
    }

    @Override
    public int[] getBackgroundScenes() {
        return new int[0];
    }

    @Override
    public int getxCurrentImg() {
        return 0;
    }

    @Override
    public int getxNextImg() {
        return 0;
    }

    @Override
    public void setxCurrentImg(int xCurrentImg) {

    }

    @Override
    public void setxNextImg(int xNextImg) {

    }

    @Override
    public int getCurrentImgIndex() {
        return 0;
    }

    @Override
    public void setCurrentImgIndex(int currentImgIndex) {

    }

    @Override
    public int getNextImgIndex() {
        return 0;
    }

    @Override
    public void setNextImgIndex(int nextImgIndex) {

    }

    @Override
    public int getExplosionViewWidth() {
        return 0;
    }

    @Override
    public int getExplosionViewHeight() {
        return 0;
    }

    @Override
    public int getExplosionBitmapId() {
        return 0;
    }

    @Override
    public int getExplosionViewImgNumber() {
        return 0;
    }

    @Override
    public Bitmap getExplosionViewBitmap() {
        return null;
    }

    @Override
    public View getCharacterView() {
        return null;
    }

    @Override
    public int getAudioPlay() {
        return 0;
    }

    @Override
    public int getAudioExplosion() {
        return 0;
    }

    @Override
    public int getAudioCrash() {
        return 0;
    }

    @Override
    public int getCrashViewWidth() {
        return 0;
    }

    @Override
    public int getCrashViewHeight() {
        return 0;
    }

    @Override
    public Bitmap getCrashViewBitmapTop() {
        return null;
    }

    @Override
    public Bitmap getCrashViewBitmapDown() {
        return null;
    }

    @Override
    public int getQuesExplosionViewWidth() {
        return 0;
    }

    @Override
    public int getQuesExplosionViewHeight() {
        return 0;
    }

    @Override
    public int getQuesExplosionBitmapId() {
        return 0;
    }

    @Override
    public int getQuesExplosionViewImgNumber() {
        return 0;
    }

    @Override
    public Bitmap getQuesExplosionViewBitmap() {
        return null;
    }

    @Override
    public int getAudioQuestionExplosion() {
        return 0;
    }
}

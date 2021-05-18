package dam.gala.damgame.scenes;

import android.graphics.Bitmap;
import android.view.View;

import dam.gala.damgame.activities.GameActivity;
/**
 * Escena del mundo submarino
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public class UnderWaterScene extends Scene {
    public UnderWaterScene(GameActivity gameActivity){
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
        return backgroundScenes;
    }

    @Override
    public int getxCurrentImg() {
        return xCurrentImg;
    }

    @Override
    public int getxNextImg() {
        return xNextImg;
    }

    @Override
    public void setxCurrentImg(int xCurrentImg) {
        this.xCurrentImg = xCurrentImg;
    }

    @Override
    public void setxNextImg(int xNextImg) {
        this.xNextImg = xNextImg;
    }

    @Override
    public int getCurrentImgIndex() {
        return this.currentImgIndex;
    }

    @Override
    public void setCurrentImgIndex(int currentImgIndex) {
        this.currentImgIndex = currentImgIndex;
    }

    @Override
    public int getNextImgIndex() {
        return this.nextImgIndex;
    }

    @Override
    public void setNextImgIndex(int nextImgIndex) {
        this.nextImgIndex = nextImgIndex;
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

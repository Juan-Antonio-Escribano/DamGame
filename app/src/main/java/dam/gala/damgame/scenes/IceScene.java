package dam.gala.damgame.scenes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.example.damgame.R;

import dam.gala.damgame.activities.GameActivity;
import dam.gala.damgame.utils.GameUtil;

/**
 * Escena del hielo
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public class IceScene extends Scene {
    private GameActivity gameActivity;
    public IceScene(GameActivity gameActivity){
        super(gameActivity);
        this.gameActivity = gameActivity;
        this.backgroundScenes = new int[]{this.getBackgroundBitmapId(),this.getInverterBackgroundBitmapId(),
                this.getBackgroundBitmapId(),this.getInverterBackgroundBitmapId(),
                this.getBackgroundBitmapId(),this.getInverterBackgroundBitmapId()};
        this.xCurrentImg=0;
        this.xNextImg= this.getScreenWidth();

    }
    @Override
    public int getQuestionViewWidth() {
        return getQuestionViewBitmap(GameUtil.PREGUNTA_COMPLEJIDAD_BAJA).getWidth();
    }

    @Override
    public int getQuestionViewHeight() {
        return getQuestionViewBitmap(GameUtil.PREGUNTA_COMPLEJIDAD_BAJA).getHeight();
    }

    @Override
    public Bitmap getQuestionViewBitmap(int complexity) {
        if(complexity== GameUtil.PREGUNTA_COMPLEJIDAD_ALTA)
            return BitmapFactory.decodeResource(gameActivity.getResources(), R.drawable.ice_quest_d);
        else
            return BitmapFactory.decodeResource(gameActivity.getResources(), R.drawable.ice_quest_e);
    }

    @Override
    public int getBackgroundViewImgNumber() {
        return 6;
    }

    @Override
    public int getBouncyViewWidth() {
        return getBouncyViewBitmap().getWidth();
    }

    @Override
    public int getBouncyViewHeight() {
        return getBouncyViewBitmap().getHeight();
    }

    @Override
    public Bitmap getBouncyViewBitmap() {
        return BitmapFactory.decodeResource(gameActivity.getResources(), R.drawable.pajaro_azul);
    }

    @Override
    public int getBouncyViewImgNumber() {
        return 3;
    }

    @Override
    public int getBackgroundBitmapId() {
        return R.drawable.cueva_helada;
    }
    public int getInverterBackgroundBitmapId() {
        return R.drawable.cueva_helada_invertida;
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
        return getExplosionViewBitmap().getWidth();
    }

    @Override
    public int getExplosionViewHeight() {
        return getExplosionViewBitmap().getHeight();
    }

    @Override
    public int getExplosionBitmapId() {
        return R.drawable.water_exp;
    }

    @Override
    public int getExplosionViewImgNumber() {
        return 5;
    }

    @Override
    public Bitmap getExplosionViewBitmap() {
        return BitmapFactory.decodeResource(gameActivity.getResources(), R.drawable.water_exp);
    }

    @Override
    public View getCharacterView() {
        return null;
    }

    @Override
    public int getAudioPlay() {
        return R.raw.ice_cave;
    }

    @Override
    public int getAudioExplosion() {
        return R.raw.explosion_ice;
    }

    @Override
    public int getAudioCrash() {
        return R.raw.crash;
    }

    @Override
    public int getCrashViewWidth() {
        return getCrashViewBitmapTop().getWidth();
    }

    @Override
    public int getCrashViewHeight() {
        return getCrashViewBitmapTop().getHeight();
    }

    @Override
    public Bitmap getCrashViewBitmapTop() {
        return BitmapFactory.decodeResource(gameActivity.getResources(), R.drawable.estalactita);
    }

    @Override
    public Bitmap getCrashViewBitmapDown() {
        return BitmapFactory.decodeResource(gameActivity.getResources(), R.drawable.estalagmita);
    }

    @Override
    public int getQuesExplosionViewWidth() {
        return getQuesExplosionViewBitmap().getWidth();
    }

    @Override
    public int getQuesExplosionViewHeight() {
        return getQuesExplosionViewBitmap().getHeight();
    }

    @Override
    public int getQuesExplosionBitmapId() {
        return R.drawable.water_exp;
    }

    @Override
    public int getQuesExplosionViewImgNumber() {
        return 5;
    }

    @Override
    public Bitmap getQuesExplosionViewBitmap() {
        return BitmapFactory.decodeResource(gameActivity.getResources(), R.drawable.water_exp);
    }

    @Override
    public int getAudioQuestionExplosion() {
        return R.raw.explosion_ice;
    }
}

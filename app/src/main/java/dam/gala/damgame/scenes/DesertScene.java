package dam.gala.damgame.scenes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

import com.example.damgame.R;
import dam.gala.damgame.activities.GameActivity;
import dam.gala.damgame.utils.GameUtil;

/**
 * Escena del desierto
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public class DesertScene extends Scene {
    private GameActivity gameActivity;

    public DesertScene(GameActivity gameActivity){
        super(gameActivity);
        this.gameActivity = gameActivity;
        this.backgroundScenes = new int[]{this.getBackgroundBitmapId(),this.getBackgroundBitmapId()
                ,this.getBackgroundBitmapId(),
                this.getBackgroundBitmapId(),
                this.getBackgroundBitmapId(),
                this.getBackgroundBitmapId()};
        this.xCurrentImg=0;
        this.xNextImg= this.getScreenWidth()-1;
    }
    @Override
    public int getQuestionViewWidth() {
        return ResourcesCompat.getDrawable(this.gameActivity.getResources(),
                R.drawable.desert_quest_d, this.gameActivity.getTheme()).getMinimumWidth();
    }
    @Override
    public int getQuestionViewHeight() {
        return ResourcesCompat.getDrawable(this.gameActivity.getResources(),
                R.drawable.desert_quest_d, this.gameActivity.getTheme()).getMinimumHeight();
    }

    @Override
    public Bitmap getQuestionViewBitmap(int complexity) {
        if(complexity== GameUtil.PREGUNTA_COMPLEJIDAD_ALTA)
            return BitmapFactory.decodeResource(gameActivity.getResources(), R.drawable.desert_quest_d);
        else
            return BitmapFactory.decodeResource(gameActivity.getResources(), R.drawable.desert_quest_e);
    }

    @Override
    public int getQuestionViewImgNumber() {
        return 4;
    }

    @Override
    public int getBouncyViewWidth() {
        return ResourcesCompat.getDrawable(this.gameActivity.getResources(),
                R.drawable.desert_hatbird, this.gameActivity.getTheme()).getMinimumWidth();
    }

    @Override
    public int getBouncyViewHeight() {
        return ResourcesCompat.getDrawable(this.gameActivity.getResources(),
                R.drawable.desert_hatbird, this.gameActivity.getTheme()).getMinimumHeight();
    }

    @Override
    public Bitmap getBouncyViewBitmap() {
        return BitmapFactory.decodeResource(gameActivity.getResources(), R.drawable.desert_hatbird);
    }

    @Override
    public int getBackgroundViewImgNumber() {
        return 4;
    }

    @Override
    public int getBackgroundBitmapId() {
        return R.drawable.desert_bg;
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
        return ResourcesCompat.getDrawable(this.gameActivity.getResources(),
                R.drawable.explosion_out, this.gameActivity.getTheme()).getMinimumWidth();
    }

    @Override
    public int getExplosionViewHeight() {
        return ResourcesCompat.getDrawable(this.gameActivity.getResources(),
                R.drawable.explosion_out, this.gameActivity.getTheme()).getMinimumHeight();
    }

    @Override
    public int getExplosionBitmapId() {
        return R.drawable.explosion_out;
    }

    @Override
    public int getExplosionViewImgNumber() {
        return 4;
    }

    @Override
    public Bitmap getExplosionViewBitmap() {
        return BitmapFactory.decodeResource(gameActivity.getResources(), R.drawable.explosion_out);
    }

    @Override
    public View getCharacterView() {
        return null;
    }

    @Override
    public int getAudioPlay() {
        return R.raw.my_street;
    }

    @Override
    public int getAudioExplosion() {
        return R.raw.explosion;
    }

    @Override
    public int getAudioCrash() {
        return R.raw.crash;
    }

    @Override
    public int getCrashViewWidth() {
        return this.getCrashViewBitmapTop().getWidth();
    }

    @Override
    public int getCrashViewHeight() {
        return this.getCrashViewBitmapTop().getHeight();
    }

    @Override
    public Bitmap getCrashViewBitmapTop() {
        return BitmapFactory.decodeResource(gameActivity.getResources(), R.drawable.desert_column_top);
    }

    @Override
    public Bitmap getCrashViewBitmapDown() {
        return BitmapFactory.decodeResource(gameActivity.getResources(), R.drawable.desert_column_down);
    }

    @Override
    public int getQuesExplosionViewWidth() {
        return this.getQuesExplosionViewBitmap().getWidth();
    }

    @Override
    public int getQuesExplosionViewHeight() {
        return this.getQuesExplosionViewBitmap().getHeight();
    }

    @Override
    public int getQuesExplosionBitmapId() {
        return R.drawable.explosion_out;
    }

    @Override
    public int getQuesExplosionViewImgNumber() {
        return 4;
    }

    @Override
    public Bitmap getQuesExplosionViewBitmap() {
        return BitmapFactory.decodeResource(gameActivity.getResources(), R.drawable.explosion_out);
    }

    @Override
    public int getAudioQuestionExplosion() {
        return R.raw.shimmer;
    }
}

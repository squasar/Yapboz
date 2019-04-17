package qbotx.mobile.yapboz.game;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import java.util.Random;
import qbotx.mobile.yapboz.R;

import static android.view.View.VISIBLE;

public class Logic {
    GameOb obj;
    Context context;
    Resources resources;
    int howMany;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Logic(Context context, int howMany, Resources resources, @Nullable AttributeSet attributeSet){

        this.context=context;
        this.resources=resources;
        this.howMany=howMany;

        this.obj=new GameOb(R.drawable.tiger,howMany,context);

        //obj.getLevel().set_cozum_resimler(obj.get_split().get_sub_matrices());

        //rasgele resim bos resim yapiliyor...
        Random r = new Random();
        int sayac_one = r.nextInt((int) Math.sqrt(howMany)-1) + 1;
        int sayac_two = r.nextInt((int) Math.sqrt(howMany)-1) + 1;

        obj.getLevel().get_cozum_resimler()[sayac_one-1][sayac_two-1].set_id(0);
        obj.getLevel().get_cozum_resimler()[sayac_one-1][sayac_two-1].getImageView().setImageBitmap(null);
        obj.getLevel().get_cozum_resimler()[sayac_one-1][sayac_two-1].setLastImage();

        //obj.getLevel().karistirilmis_resimler=obj.level.get_karistirilmis_resimler();
        drawImages(obj.getLevel().get_karistirilmis_resimler());
    }

    private void drawImages(MyImage[][] imgeViews){

        int rows,cols,chunkHeight,chunkWidth;
        rows = cols = (int) Math.sqrt(howMany);
        chunkHeight = imgeViews[0][0].getBitmap().getHeight() / rows; //constant
        chunkWidth = imgeViews[0][0].getBitmap().getWidth() / cols; // constant
        final RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(20,20);

        int yCoord = 0;
        for (int x = 0,i=0; x < rows; x++) {
            int xCoord = 0;
            for (int y = 0; y < cols; y++, i++) {
                imgeViews[x][y].getImageView().setLayoutParams(lp);
                imgeViews[x][y].getImageView().bringToFront();
                imgeViews[x][y].getImageView().setClickable(true);
                imgeViews[x][y].getImageView().setX(xCoord);
                imgeViews[x][y].getImageView().setY(yCoord);
                imgeViews[x][y].getImageView().setVisibility(VISIBLE);
                xCoord += chunkWidth;
            }
            yCoord += chunkHeight;
        }
    }


    public GameOb getObj(){
        return this.obj;
    }
}

package qbotx.mobile.yapboz.customview;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

import qbotx.mobile.yapboz.R;
import qbotx.mobile.yapboz.SplitImage;
import qbotx.mobile.yapboz.oyun.FKaristir;
import qbotx.mobile.yapboz.oyun.Level;
import qbotx.mobile.yapboz.swap.Game;
import qbotx.mobile.yapboz.swap.MyImage;

import static android.view.View.VISIBLE;

/**
 * Created by Suleyman on 7/7/2018.
 */

//Oyunun basarili bir sekilde oynanabilmesi hedeflendi...

public class CustomImages {

    private ArrayList<MyImage> imageViews;
    Context context;
    Resources resources;

    private int howMany,rows,cols,chunkHeight,chunkWidth;
    private SplitImage splitImage;
    private Game game;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomImages(Context context, int howMany, Resources resources, @Nullable AttributeSet attributeSet){
        imageViews=new ArrayList<MyImage>();
        this.context=context;
        this.resources=resources;
        this.howMany=howMany;

        //splitImage=new SplitImage(R.drawable.tiger,howMany,context);
        splitImage=new SplitImage(R.drawable.tiger,howMany,context);

        //parcalanmis resimler aliniyor...
        imageViews=splitImage.getSubImages();

        //rasgele resim bos resim yapiliyor...
        Random r = new Random();
        int sayac = r.nextInt(howMany-1) + 1;
        imageViews.get(sayac-1).set_resimler(0);
        imageViews.get(sayac-1).getImageView().setImageBitmap(null);
        imageViews.get(sayac-1).setLastImage();//bos resim belirtilmesi

        //dogru sonuc oyuna kaydediliyor...
        game=new Game(imageViews,howMany);

        //resimler karistiriliyor...
        //Collections.shuffle(imageViews);
        //karistirma algoritmasi ve ysa baglantilari -- level


        //resimler int degerlerini al
        int say = (int) Math.sqrt(howMany);
        int temp[][] = new int[say][say];

        for(int i=0, k=0; i<say; i++){
            for(int j=0; j<say; j++, k++){
                temp[i][j]=imageViews.get(k).get_resimler();
            }
        }
        //resimler int degerlerini yolla
        Date date = new Date();
        Level lvl = new Level(2, date, temp);
        //resimlerin yerlerini, int resimler degerine gore dizide degistir.
        int [][] karistirilmis = lvl.get_karistirilmis_resimler();
        //diziyi bastan sona dolasan ve her sayida o resimle yer degistirecek sayiyi arayan
        int ilk_indis;
        int son_indis=-2;

        for(int i=0, k=0; i<imageViews.size(); i++){
            ilk_indis=i;
            boolean bulundu = false;
            for(int j=0; j<say && !bulundu; j++, k++) {
                for (int m = 0; m < say && !bulundu; m++) {
                    if (imageViews.get(i).get_resimler() == karistirilmis[j][m]) {
                        son_indis = j * say + m;
                        bulundu = true;
                    }
                }
            }
            dizide_yer_degistir(ilk_indis,son_indis);
        }
        //ilk tahmin degeri eklenip cizimler saglaniyor...(?)
        drawImages(imageViews,game);
    }

    private void dizide_yer_degistir(int indis1, int indis2){
        MyImage gecici=imageViews.get(indis2);
        imageViews.set(indis2, imageViews.get(indis1));
        imageViews.set(indis1, gecici);
    }

    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void drawImages(ArrayList<MyImage> imgeViews, Game game){

        game.setGame_guess(imgeViews);

        rows = cols = (int) Math.sqrt(howMany);
        chunkHeight = imgeViews.get(0).getBitmap().getHeight() / rows; //constant
        chunkWidth = imgeViews.get(0).getBitmap().getWidth() / cols; // constant
        final RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(20,20);

        int yCoord = 0;
            for (int x = 0,i=0; x < rows; x++) {
                int xCoord = 0;
                for (int y = 0; y < cols; y++, i++) {
                        imgeViews.get(i).getImageView().setLayoutParams(lp);
                        imgeViews.get(i).getImageView().bringToFront();
                        imgeViews.get(i).getImageView().setClickable(true);
                        imgeViews.get(i).getImageView().setX(xCoord);
                        imgeViews.get(i).getImageView().setY(yCoord);
                        imgeViews.get(i).getImageView().setVisibility(VISIBLE);
                        xCoord += chunkWidth;
                    }
                    yCoord += chunkHeight;
                }

}
    public ArrayList<MyImage> getImageViews(){
        return imageViews;
    }
    //public void setGuess(ArrayList<MyImage>images){
    //    this.imageViews=images;
    //}
    public Game getGame(){
        return game;
    }
}
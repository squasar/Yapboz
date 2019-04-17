package qbotx.mobile.yapboz.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;

//import qbotx.mobile.yapboz.swap.MyImage;

/**
 * Created by Suleyman on 6/25/2018.
 */

public class SplitImage {

    private ArrayList<MyImage> imgs;
    private int image_id;

    private BitmapDrawable drawable;
    private Bitmap bitmap;
    private Bitmap scaledBitmap;

    private ArrayList<Bitmap> chunkedImages;
    private int chunkNumbers;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SplitImage(int R_image_id, int chunk_nums, Context context){
        imgs = new ArrayList<MyImage>();
        image_id=R_image_id;
        this.chunkNumbers=chunk_nums;
        splitImage(chunk_nums,context);
    }



    public ArrayList<MyImage> getSubImages(){
        return imgs;
    }

    public MyImage[][] get_sub_matrices() {
        int num = (int) Math.sqrt(chunkNumbers);
        MyImage[][] temp = new MyImage[num][num];
        for(int i=0, count=0; i<num; i++){
            for(int j=0; j<num; j++, count++){
                temp[i][j]=imgs.get(count);
                Log.d("AAAAAAAAAAAAAAAAA","" +
                            "       temp["+i+"]"+"["+j+"] = "+ temp[i][j].get_id()+
                "  --------  length = "+temp.length);
            }
        }
        return temp;
    }

    private void splitImage(int chunkNumbers, Context context) {

        //For the number of rows and columns of the grid to be displayed
        int rows, cols;
        //For height and width of the small image chunks
        int chunkHeight, chunkWidth;

        //To store all the small image chunks in bitmap format in this list
        chunkedImages = new ArrayList<Bitmap>(chunkNumbers);
        //Getting the scaled bitmap of the source image
        drawable = (BitmapDrawable) ContextCompat.getDrawable(context,image_id);//R.drawable.icesid);
        bitmap = drawable.getBitmap();
        scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        //rows = cols = (int) Math.sqrt(chunkNumbers);
        rows = cols = (int) Math.sqrt(chunkNumbers);
        chunkHeight = bitmap.getHeight() / rows;
        chunkWidth = bitmap.getWidth() / cols;

        int resimler=1;

        //xCoord and yCoord are the pixel positions of the image chunks
        int yCoord = 0;
        for (int x = 0; x < rows; x++) {
            int xCoord = 0;
            for (int y = 0; y < cols; y++) {
                chunkedImages.add(Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight));
                MyImage mi;
                    mi = new MyImage(context, scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight, resimler);
                    //mi.getPoint_f().x=xCoord;
                    //mi.getPoint_f().y=yCoord;
                    imgs.add(mi);
                    xCoord += chunkWidth;
                    resimler=resimler+1;
            }
            yCoord += chunkHeight;
        }
    }

    public ArrayList<Bitmap> getBitmaps(){
        return chunkedImages;
    }

    public MyImage getEmptyImage(){
        //MyImage a=null;
        /*for(int i=0; i<imgs.size(); i++){
            if(imgs.get(i).isEmpty){
                a= imgs.get(i);
            }
        }
        return a;*/
        return null;
    }
}
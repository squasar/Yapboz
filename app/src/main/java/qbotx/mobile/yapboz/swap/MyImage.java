package qbotx.mobile.yapboz.swap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Suleyman on 7/7/2018.
 */

public class MyImage {

    private boolean isLastImage=false;
    private ImageView iv;
    private PointF point_f;
    public Bitmap bitmap;


    private int resimler;

    private int chunkHeight,chunkWidth;

    public MyImage(Context context, Bitmap scaledBitmap, int xCoord, int yCoord, int chunkWidth, int chunkHeight
    ,int resimler){
        this.resimler=resimler;

        iv = new ImageView(context);
        iv.setImageBitmap(Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight));

        this.chunkHeight=chunkHeight;
        this.chunkWidth=chunkWidth;

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        iv.setLayoutParams(lp);
        point_f=new PointF();
        bitmap=Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight);
    }

    public PointF getPoint_f(){
        return point_f;
    }
    public void setIv(ImageView iv){
        this.iv=iv;
    }
    public ImageView getImageView(){
        return iv;
    }
    public Bitmap getBitmap(){
        return bitmap;
    }
    public void setLastImage() {
        isLastImage = true;
    }
    public boolean isLastImage() {
        return isLastImage;
    }

    public int get_resimler(){
        return this.resimler;
    }

    public void set_resimler(int resimler){
        this.resimler=resimler;
    }


}

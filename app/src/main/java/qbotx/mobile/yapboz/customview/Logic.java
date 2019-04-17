package qbotx.mobile.yapboz.customview;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Suleyman on 7/7/2018.
 */

public class Logic {

    private CustomImages customImages;
    private View view;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Logic(Context context, int howMany, Resources resources, @Nullable AttributeSet attributeSet){
        customImages=new CustomImages(context,howMany,resources,attributeSet);
    }

    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setView(View view, CustomImages cusges){
        this.view=view;
        change(cusges);
        this.customImages.drawImages(this.customImages.getImageViews(), this.customImages.getGame());
    }

    private void change(CustomImages cusges){
      customImages.getGame().update_guess(cusges.getImageViews(),view);
    }

    public CustomImages getCustomImages(){
        return customImages;
    }

}

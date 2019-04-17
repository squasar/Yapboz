package qbotx.mobile.yapboz;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import qbotx.mobile.yapboz.game.Logic;

//import qbotx.mobile.yapboz.customview.Logic;

public class MainActivity extends AppCompatActivity {

    Logic logic;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        logic = new Logic(getApplicationContext(), 64, getResources(), null);

        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rlayout);
        //RelativeLayout relativeLayout = new RelativeLayout(this);
        //relativeLayout.setId(R.id.rlayout);

        for (int i = 0, k = 0; i < logic.getObj().get_indis_length(); i++) {
            for (int j = 0; j < logic.getObj().get_indis_length(); j++, k++) {

                logic.getObj().getLevel().get_karistirilmis_resimler()[i][j].getImageView().setId(
                        logic.getObj().getLevel().get_karistirilmis_resimler()[i][j].get_id()
                );

                Log.d("BBBBBBBBBBBBBBBBBB", "" +
                        "       id[" + i + "]" + "[" + j + "] = " + logic.getObj().getLevel().get_karistirilmis_resimler()
                        [i][j].get_id() +
                        "  --------  length = " +
                        logic.getObj().get_indis_length());

                //relativeLayout.addView(logic.getObj().getLevel().get_karistirilmis_resimler()[i][0].getImageView());
                if (logic.getObj().getLevel().get_karistirilmis_resimler()[i][j].getImageView().getParent() != null) {
                    relativeLayout.removeView(logic.getObj().getLevel().get_karistirilmis_resimler()[i][j].getImageView()); // <- fix
                }
                relativeLayout.addView(logic.getObj().getLevel().get_karistirilmis_resimler()[i][j].getImageView()); //  <==========  ERROR IN THIS LINE DURING 2ND RUN
// EDITTEXT

                Log.d("NNNNNNNNNNNNNNNNNNN","  COUNT  =  "+ relativeLayout.getChildCount());
            }}

            relativeLayout.setPivotX(-5);
            relativeLayout.setPivotY(-5);
            relativeLayout.setScaleX(3f);
            relativeLayout.setScaleY(3f);

            for (int i = 0; i < logic.getObj().get_indis_length() * logic.getObj().get_indis_length()-1; i++) {
                //for (int j = 0; j < logic.getObj().get_indis_length(); j++) {
               Log.d("CCCCCCCCCCCCCCCCCCCCCC","  COUNT  =  "+ relativeLayout.getChildCount());
                    relativeLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //logic.setView(view, logic.getCustomImages());
                            logic.getObj().update_guess(logic.getObj().getLevel().get_karistirilmis_resimler()
                                    , view);

                            Toast.makeText(getApplicationContext(), "SUCCESS:  " +
                                    logic.getObj().get_is_success()+
                                    " id : "+"["+view.getId()+"]", Toast.LENGTH_SHORT).show();
                            //logic.getCustomImages().getGame().setGame_guess(logic.getCustomImages().getImageViews());
                            // Toast.makeText(getApplicationContext(), "SUCCESS:  " +
                            //       logic.getCustomImages().getGame().getSuccess(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }






/*
        logic = new Logic(getApplicationContext(), 64, getResources(), null);

        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rlayout);

        for (int i = 0; i < logic.getCustomImages().getImageViews().size(); i++) {
            relativeLayout.addView(logic.getCustomImages().getImageViews().get(i).getImageView());
        }

        relativeLayout.setPivotX(-5);
        relativeLayout.setPivotY(-5);
        relativeLayout.setScaleX(3f);
        relativeLayout.setScaleY(3f);

        for (int i = 0; i < logic.getCustomImages().getImageViews().size(); i++) {
            relativeLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logic.setView(view, logic.getCustomImages());
                    //logic.getCustomImages().getGame().setGame_guess(logic.getCustomImages().getImageViews());
                    Toast.makeText(getApplicationContext(), "SUCCESS:  " +
                            logic.getCustomImages().getGame().getSuccess(), Toast.LENGTH_SHORT).show();

                }
            });
        }

    }
}*/

        }//}
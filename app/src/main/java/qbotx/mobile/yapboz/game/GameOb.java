package qbotx.mobile.yapboz.game;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import java.util.Date;

//import qbotx.mobile.yapboz.swap.MyImage;

public class GameOb {
    SplitImage splitImage;
    Level level;
    private int indis;
    private MyImage [][] views;
    boolean is_success;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GameOb(int R_image_id, int chunk_nums, Context context){
        indis=(int) Math.sqrt(chunk_nums);



        splitImage=new SplitImage(R_image_id,chunk_nums,context);
        Date date = new Date();
        this.level=new Level(2,date, splitImage.get_sub_matrices());

        //this.level.set_cozum_resimler(this.splitImage.get_sub_matrices());

    }

    public MyImage[][] update_guess(MyImage [][] views, View view){
        //tiklanan resmin cevresinde bos resim olup olmadigini kontrol eden ve gerektiginde
        //level nesnesini duzenleyen, cozumu kontrol eden fonksiyon.

        MyImage cursed_one = null;
        MyImage clicked_one = null;
        int cursed_column = -3;
        int cursed_row = -3;
        int clicked_column = -3;
        int clicked_row = -3;

        //daha iyi bir arama algoritmasi kullan ileride, bu en kotusu :)
        for(int i=0,k; i<indis; i++){
            for(int j=0; j<indis; j++){
                //k=x*indis+y;
                if(level.get_karistirilmis_resimler()[i][j].isLastImage()){
                    cursed_one=level.get_karistirilmis_resimler()[i][j];
                    cursed_row=i;
                    cursed_column=j;
                }else if( (view.getX()== views[i][j].getImageView().getX()) &&
                        (view.getY() == views[i][j].getImageView().getY())){
                    clicked_one=views[i][j];
                    for(int a=0; a<indis; a++){
                        for(int b=0; b<indis; b++){
                            if(level.get_karistirilmis_resimler()[a][b]==clicked_one){
                                clicked_row=a;
                                clicked_column=b;
                            }
                        }
                    }
                }
            }
        }
        views=swap_if_necessary(views,cursed_one,clicked_one,cursed_row,cursed_column,clicked_row,clicked_column);
        //dogru cevap kontrolu yapilacak
        //setGame_guess(views);
        level.set_karistirilmis_resimler(views);
        this.is_success=level.check_puzzle_cevap();
        //isSuccess=checkSolution();
        return views;
    }






    private MyImage[][] swap_if_necessary(MyImage[][] views,
                                          MyImage cursed_one, MyImage clicked_one,
                                          int cursed_row, int cursed_column,
                                          int clicked_row, int clicked_column){
        MyImage temp_clicked = null;
        boolean isProvided = false;
        try
        {
            //same row
            if(((clicked_column+1)==(cursed_column) || (clicked_column-1)==(cursed_column))){
                if((clicked_row==cursed_row)) {
                    isProvided = true;
                }
            }
            //same column different rows
            else if((clicked_column==cursed_column)){
                if(((clicked_row+1)==(cursed_row) ||
                        (clicked_row-1)==(cursed_row))){
                    isProvided=true;
                }
            }
            if(isProvided){
                //SWAP...
                temp_clicked=clicked_one;

                int clicked_index_x=-1;
                int clicked_index_y=-1;
                int cursed_index_x=-1;
                int cursed_index_y=-1;

                for(int i=0; i<indis; i++){
                    for(int j=0; j<indis; j++){
                        if(views[i][j]==clicked_one){
                            clicked_index_x=i;
                            clicked_index_y=j;
                        }else if(views[i][j]==cursed_one){
                            cursed_index_x=i;
                            cursed_index_y=j;
                        }

                    }
                }

                if((!(clicked_index_x<0)) && (!(clicked_index_y<0))
                        && (!(cursed_index_x<0))&& (!(cursed_index_y<0))){
                    views[clicked_index_x][clicked_index_y]=views[cursed_index_x][cursed_index_y];
                    views[cursed_index_x][cursed_index_y]=temp_clicked;
                }

            }
        }
        catch (IndexOutOfBoundsException ie){
            //error handling code
            return views;
        }
        return views;
    }

    public boolean get_is_success(){
        return this.is_success;
    }
    public Level getLevel(){
        return this.level;
    }
    public int get_indis_length(){
        return this.indis;
    }

    public SplitImage get_split(){
        return this.splitImage;
    }


}

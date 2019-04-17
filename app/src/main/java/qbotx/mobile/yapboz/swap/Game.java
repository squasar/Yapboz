package qbotx.mobile.yapboz.swap;

import android.graphics.PointF;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Suleyman on 6/26/2018.
 */

public class Game {


    private ArrayList<MyImage> solution;
    private boolean isSuccess=false;
    public ArrayList<MyImage> game_guess;
    MyImage empty_img_key_object;
    MyImage[][] matris;

    int indis;

    public Game(ArrayList<MyImage> images, int pieces){
        solution=new ArrayList<MyImage>();
        game_guess=new ArrayList<MyImage>();
        int indx_num= (int) Math.sqrt(pieces);
        indis=indx_num;
        matris=new MyImage[indx_num][indx_num];
        for(int i=0; i<images.size(); i++){
            MyImage im = images.get(i);
            solution.add(im);
        }
    }

    public void setGame_guess(ArrayList<MyImage> images){
        //bir deger varsa, game_guess hashmap in bosaltilmasi lazim
        if(!game_guess.isEmpty()){
            game_guess.clear();
        }
        //eklemeler
        for(int i=0; i<images.size(); i++){
            MyImage im = images.get(i);
            game_guess.add(im);
            if(i==images.size()-1){
                empty_img_key_object=im;
            }
        }
        for(int i=0,k=0; i<indis; i++){
            for(int j=0;  j<indis; j++,k++){
                matris[i][j]=images.get(k);
            }
        }


    }

    public ArrayList<MyImage> update_guess(ArrayList<MyImage> views, View view){
        //bu image e tiklarsan bos olanla kordinat point degistirecek gerekli kontrollerle
        //Bos resim cevredeyse degistir, degilse islem yapma

        MyImage cursed_one = null;
        MyImage clicked_one = null;
        int cursed_column = -1;
        int cursed_row = -1;
        int clicked_column = -1;
        int clicked_row = -1;

        //daha iyi bir arama algoritmasi kullan ileride, bu en kotusu :)
        for(int i=0,k=0; i<indis; i++){
            for(int j=0; j<indis; j++,k++){
                if(matris[i][j].isLastImage()){
                    cursed_one=matris[i][j];
                    cursed_row=i;
                    cursed_column=j;
                }else if( (view.getX()== views.get(k).getImageView().getX()) &&
                        (view.getY() == views.get(k).getImageView().getY())){
                            clicked_one=views.get(k);
                              for(int a=0; a<indis; a++){
                                for(int b=0; b<indis; b++){
                                    if(matris[a][b]==clicked_one){
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
        setGame_guess(views);
        isSuccess=checkSolution();
        return views;
    }

    private ArrayList<MyImage> swap_if_necessary(ArrayList<MyImage> views,
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

                int cursed = views.indexOf(cursed_one);
                int clicked = views.indexOf(clicked_one);

                views.remove(clicked);
                views.add(clicked,cursed_one);
                views.remove(cursed);
                views.add(cursed,temp_clicked);

            }
        }
        catch (IndexOutOfBoundsException ie){
            //error handling code
            return views;
        }
        return views;
    }

    public boolean getSuccess(){
        return isSuccess;
    }

    private boolean checkSolution(){
        boolean success=false;

        ArrayList<MyImage> temp = game_guess;
        temp.remove(empty_img_key_object);

        for(int i=0; i<temp.size();i++){
            if(temp==solution){
                success=true;
            }else{
                success=false;
            }
        }
        return success;
    }
}
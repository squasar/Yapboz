/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qbotx.mobile.yapboz.oyun;

import java.io.Serializable;
import java.util.Random;

/**
 *
 * @author Suleyman
 */
public class FKaristir implements Serializable {
    
    public enum X_Yonler{
         SAG(0), SOL(1);
         public final int id;
            public boolean isTanimli=false;
            X_Yonler(int id){ 
                this.id = id;
            }
             public int get_id(){
                return this.id;
            }
            public void set_isTanimli(boolean tanimli){
                this.isTanimli=tanimli;
            }
            public boolean get_is_tanimli(){
                return this.isTanimli;
            }
    }
    
    public enum Y_Yonler {
            YUKARI(2), ASAGI(3);
            public final int id;
            public boolean isTanimli=false;
            Y_Yonler(int id){ 
                this.id = id;
            }
             public int get_id(){
                return this.id;
            }
            public void set_isTanimli(boolean tanimli){
                this.isTanimli=tanimli;
            }
            public boolean get_is_tanimli(){
                return this.isTanimli;
            }
    }
    
    int[][] resimler;
    int bos_resim_index_satir;
    int bos_resim_index_sutun;
    
    int [][] sayaclar;//her kareye kac kez gidildiginin kaydi
    
    public FKaristir(){
        
    }
    
    public FKaristir(int[][] resimler, int level){
        this.resimler=resimler;
        //int level=4;
        //sayaclari ayarla
        sayaclar= new int [resimler.length][resimler.length];
        for(int i=0; i<resimler.length; i++){
            for(int j=0; j<resimler.length; j++){
                if(resimler[i][j] == 0){//bos resimdeyse
                    sayaclar[i][j]=1;
                }else{
                    sayaclar[i][j]=0;
                }
            }
        }
        //System.out.println("Level Iterasyon : "+level);
        karistir(level);
    }
    
    private void bos_resmi_bul(){
        for(int j=0; j<resimler.length; j++){
            for(int k=0; k<resimler.length; k++){
                if(resimler[j][k] == 0){
                    this.bos_resim_index_satir = j;
                    this.bos_resim_index_sutun = k;
                    //System.out.println("Resimler length : "+resimler.length);
                    //System.out.println("Satir , Sutun = "+bos_resim_index_satir+" , "+bos_resim_index_sutun);
                    return;
                }
            }
        }
    }
    
    private void yon_belirle(){
        
            Random r = new Random();
            int rasgele;
            int rasgele_yon;
            int temp,in;
            
            while(true){
                rasgele = r.nextInt();//rasgele sayiyi guncelle
                rasgele_yon = Math.abs(rasgele) % 2;
                temp=r.nextInt();
                in=Math.abs(temp)%2;
                //System.out.println("in : "+in);
                //System.out.println("temp : "+temp);
                //System.out.println("rasgele yon : "+rasgele_yon);
                switch(rasgele_yon){
                case 0: if(in==0 && Y_Yonler.YUKARI.get_is_tanimli()==false && 
                        Y_Yonler.ASAGI.get_is_tanimli()==false && bos_resim_index_satir!=0 &&
                        sayaclar[bos_resim_index_satir][bos_resim_index_sutun]<4 &&
                        (!check_is_kafes_yapisi(Y_Yonler.YUKARI.id))){
                          //System.out.println("YUKARI SECILDI");
                        sayaclar[bos_resim_index_satir][bos_resim_index_sutun]=
                                sayaclar[bos_resim_index_satir][bos_resim_index_sutun]+1;
                            Y_Yonler.YUKARI.set_isTanimli(true);
                            Y_Yonler.ASAGI.set_isTanimli(false);
                            X_Yonler.SAG.set_isTanimli(false);
                            X_Yonler.SOL.set_isTanimli(false);
                            return;
                        }
                        if(in==1 && Y_Yonler.YUKARI.get_is_tanimli()==false && 
                        Y_Yonler.ASAGI.get_is_tanimli()==false && 
                        bos_resim_index_satir!=resimler.length -1 &&
                        sayaclar[bos_resim_index_satir][bos_resim_index_sutun]<4 &&
                        (!check_is_kafes_yapisi(Y_Yonler.ASAGI.id))){
                            //System.out.println("ASAGI SECILDI");
                            //sayaclar hesabini yap
                            sayaclar[bos_resim_index_satir][bos_resim_index_sutun]=
                                sayaclar[bos_resim_index_satir][bos_resim_index_sutun]+1;
                            Y_Yonler.YUKARI.set_isTanimli(false);
                            Y_Yonler.ASAGI.set_isTanimli(true);
                            X_Yonler.SAG.set_isTanimli(false);
                            X_Yonler.SOL.set_isTanimli(false);
                            return;
                        }
                        break;
                case 1: if(in==0 && X_Yonler.SAG.get_is_tanimli()==false && 
                        X_Yonler.SOL.get_is_tanimli()==false &&
                        bos_resim_index_sutun!=resimler.length -1 &&
                        sayaclar[bos_resim_index_satir][bos_resim_index_sutun]<4 &&
                        (!check_is_kafes_yapisi(X_Yonler.SAG.id))){
                            //System.out.println("SAG SECILDI");
                            //sayaclar hesabini yap
                            sayaclar[bos_resim_index_satir][bos_resim_index_sutun]=
                                sayaclar[bos_resim_index_satir][bos_resim_index_sutun]+1;
                            Y_Yonler.YUKARI.set_isTanimli(false);
                            Y_Yonler.ASAGI.set_isTanimli(false);
                            X_Yonler.SAG.set_isTanimli(true);
                            X_Yonler.SOL.set_isTanimli(false);
                            return;
                        }
                        if(in==1 && X_Yonler.SOL.get_is_tanimli()==false && 
                        X_Yonler.SAG.get_is_tanimli()==false &&
                        bos_resim_index_sutun!=0 &&
                        sayaclar[bos_resim_index_satir][bos_resim_index_sutun]<4 &&
                        (!check_is_kafes_yapisi(X_Yonler.SOL.id))){
                            //System.out.println("SOL SECILDI");
                            //sayaclar hesabini yap
                            sayaclar[bos_resim_index_satir][bos_resim_index_sutun]=
                                sayaclar[bos_resim_index_satir][bos_resim_index_sutun]+1;
                            Y_Yonler.YUKARI.set_isTanimli(false);
                            Y_Yonler.ASAGI.set_isTanimli(false);
                            X_Yonler.SAG.set_isTanimli(false);
                            X_Yonler.SOL.set_isTanimli(true);
                            return;
                        }
            }
            }
    }
    
    private boolean check_is_kafes_yapisi(int id){
        //iki hucre kontrolu yaparak kafes olusumunu belirle
        //veya bir yere girildiginde 4 carprazin bloklandigi olmamali -- girilecek yerde kafes var mi
        boolean result;
        boolean ret = false;
        boolean ret_two = false;
        int temp_satir = -2;
        int temp_sutun = -2;
        
        //girilecek yerde kafes var mi kontrolu icin girisler
        switch(id){
            case 0://sag
                    temp_satir=bos_resim_index_satir;
                    temp_sutun=bos_resim_index_sutun+1;
                break;
            case 1://sol
                    temp_satir=bos_resim_index_satir;
                    temp_sutun=bos_resim_index_sutun-1;
                break;
            case 2://yukari
                    temp_satir=bos_resim_index_satir-1;
                    temp_sutun=bos_resim_index_sutun;
                break;
            case 3://asagi
                    temp_satir=bos_resim_index_satir+1;
                    temp_sutun=bos_resim_index_sutun;
                break;
        }
        if(!((temp_satir ==0 || temp_satir==resimler.length -1)
                || (temp_sutun ==0 || temp_sutun==resimler.length -1))){
        
           //kafes olusumu var mi
           if(sayaclar[temp_satir+1][temp_sutun+1]==4 &&
           sayaclar[temp_satir+1][temp_sutun-1]==4 &&
           sayaclar[temp_satir-1][temp_sutun+1]==4 &&
           sayaclar[temp_satir-1][temp_sutun-1]==4){
           ret_two=true;
        }
           //tekli kontrollerde engel var mi
        switch(id){
            case 0://sag
                if((sayaclar[bos_resim_index_satir+1][bos_resim_index_sutun+1]==4 && 
                        sayaclar[bos_resim_index_satir-1][bos_resim_index_sutun+1]==4)){
                    ret=true;
                }
                break;
            case 1://sol
                if((sayaclar[bos_resim_index_satir+1][bos_resim_index_sutun-1]==4 && 
                        sayaclar[bos_resim_index_satir-1][bos_resim_index_sutun-1]==4)){
                    ret=true;
                }
                break;
            case 2://yukari
                if((sayaclar[bos_resim_index_satir-1][bos_resim_index_sutun-1]==4 && 
                        sayaclar[bos_resim_index_satir-1][bos_resim_index_sutun+1]==4)){
                    ret=true;
                }
                break;
            case 3://asagi
                if((sayaclar[bos_resim_index_satir+1][bos_resim_index_sutun+1]==4 && 
                        sayaclar[bos_resim_index_satir+1][bos_resim_index_sutun-1]==4)){
                    ret=true;
                }
                break;
       }
       }
        result = ret || ret_two;
        return result;
    }
    
    private void karistir(int level){
        int iterasyon_sayisi = level;//fibonacci_hesapla(level);
        //bos resmi arayip belirleme algoritmasi n^2
        bos_resmi_bul();
        //karistirma islemi
        for(int i=1; i<=iterasyon_sayisi; i++){//karistirmada kullanilacak adim sayisi == i
            yon_belirle();
            //tanimli yolu kullanarak karistirma islemini gerceklestir
            if(Y_Yonler.ASAGI.get_is_tanimli()){
                //Asagi ile karistir ve kaydet
                int temp = resimler[bos_resim_index_satir+1][bos_resim_index_sutun];//asagidaki sayiyi al
                resimler[bos_resim_index_satir][bos_resim_index_sutun]=temp;//bos resmin yerine yaz
                resimler[bos_resim_index_satir+1][bos_resim_index_sutun]=0;//asagidaki resmi bos yap
                
                System.out.println("ASAGI YONUNDE KARISTIRMA YAPILDI");
            }else if(Y_Yonler.YUKARI.get_is_tanimli()){
                //Yukari ile karistir ve kaydet
                int temp = resimler[bos_resim_index_satir-1][bos_resim_index_sutun];//yukaridaki sayiyi al
                resimler[bos_resim_index_satir][bos_resim_index_sutun]=temp;//bos resmin yerine yaz
                resimler[bos_resim_index_satir-1][bos_resim_index_sutun]=0;//yukaridaki resmi bos yap
                
                System.out.println("YUKARI YONUNDE KARISTIRMA YAPILDI");
            }else if(X_Yonler.SAG.get_is_tanimli()){
                //Sag ile karistir ve kaydet
                int temp = resimler[bos_resim_index_satir][bos_resim_index_sutun+1];//sagdaki sayiyi al
                resimler[bos_resim_index_satir][bos_resim_index_sutun]=temp;//bos resmin yerine yaz
                resimler[bos_resim_index_satir][bos_resim_index_sutun+1]=0;//sagdaki resmi bos yap
                
                System.out.println("SAG YONUNDE KARISTIRMA YAPILDI");
            }else if(X_Yonler.SOL.get_is_tanimli()){
                //Sol ile karistir ve kaydet
                int temp = resimler[bos_resim_index_satir][bos_resim_index_sutun-1];//soldaki sayiyi al
                resimler[bos_resim_index_satir][bos_resim_index_sutun]=temp;//bos resmin yerine yaz
                resimler[bos_resim_index_satir][bos_resim_index_sutun-1]=0;//soldaki resmi bos yap
                
                System.out.println("SOL YONUNDE KARISTIRMA YAPILDI");
            }else{
                System.out.println("GECERSIZ ISLEM ALGILANDI...");
            }
            bos_resmi_bul();
        }
    }
    //1,2,3,5,8,13
    public int fibonacci_hesapla(int istenen){
        int onceki = 0;
        int son = 1;
        int toplam=0;
            for(int i=0; i<istenen; i++) {
                toplam = onceki + son;
                onceki = son;
                son = toplam;
            }
        //System.out.println("fibo hesapla (ite) : "+toplam);
        return toplam;
    }

    public int[][] get_resimler(){
        return this.resimler;
    }    
}
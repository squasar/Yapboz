/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qbotx.mobile.yapboz.oyun;

import java.io.Serializable;
import java.util.Date;

import qbotx.mobile.yapboz.ysa.JAnn;

/**
 *
 * @author Suleyman
 */
public class Level implements Serializable {
    
    private int puan;//yapay zeka tarafindan belirlenecek puan degeri
    private int _level;//ilk olusan level
    private int sonraki_level;//puana gore belirlenecek olan level
    public int resimler [][]; //= new int[8][8];//orjinal resimler -- cozum degerleri
    private FKaristir karistirma_islemleri;
    public int [][] karistirilmis_resimler;//puzzle i olusturan resimler
    private boolean is_cozuldu;
    
    private Date baslangic_tarihi;
    private long oyunda_gecirilen_milisecond;
    private Date bitis_tarihi;
    
    private int satir,sutun;
    
    //getters setters
    
    public void baslangic_tarihi_guncelle(Date baslangic_tarihi){
        this.baslangic_tarihi=baslangic_tarihi;
    }
    
    private void set_oyunda_gecirilen_milisecond(){
        this.oyunda_gecirilen_milisecond = bitis_tarihi.getTime() - baslangic_tarihi.getTime()
                +this.oyunda_gecirilen_milisecond;
    }
    
    public int get_puan(){
        return this.puan;
    }
    
    public int[][] get_karistirilmis_resimler(){
        return this.karistirilmis_resimler;
    }
    
    public int[][] get_cozum_resimler(){
        return this.resimler;
    }
    
    public Level(){
        
    }
    
    public Level(int puan, Date baslangic_tarihi, int[][] resimler){
        this.puan=puan;
        this.baslangic_tarihi=baslangic_tarihi;
        
        this.resimler=resimler;
        this.karistirilmis_resimler=new int [resimler.length][resimler.length];
        
        this.satir=this.sutun=resimler.length;
        
        //levellerin in ilk durumlarinin initial edilmesi
        _level=get_sira_fibo(puan);
        this.sonraki_level=_level;
        this.is_cozuldu=false;
        //resimlerin initial edilmesi
        for(int i=0; i<satir; i++){
            for(int j=0; j<sutun; j++){
                karistirilmis_resimler[i][j]=resimler[i][j];
            }
        }
        //resimlerin level degerine gore karsilastirilmasi
        //System.out.println("SASASA");
        karistirma_islemleri=new FKaristir(karistirilmis_resimler, sonraki_level);
        //System.out.println("SASASA");
        karistirilmis_resimler=karistirma_islemleri.get_resimler();
    }
    
    //bu metodu disaridan cagirarak puan ve level hesabi yaptir
    /*public void set_puan_level(Date bitis_tarihi, boolean is_success){
        set_level_status(bitis_tarihi,is_success);
    }*/
    public void set_level_status(Date bitis_tarihi, boolean is_success){
        //oyunda gecirilen sureyi hesapla
        this.bitis_tarihi=bitis_tarihi;
        set_oyunda_gecirilen_milisecond();
        //puani hesaplayarak guncelle
        update_puan(is_success);
        //sonraki puan degerinin olusturdugu level e bak
        sonraki_level=get_sira_fibo(puan);
        //levele bagli karistirma islemi uygula
        FKaristir karistir = new FKaristir(resimler, sonraki_level);
        resimler=karistir.get_resimler();
        //bitis tarihini baslangic tarihi olarak al
        this.baslangic_tarihi=bitis_tarihi;
        //sureyi sifirla
        this.oyunda_gecirilen_milisecond=0;
    }
    
    //oyundan tanimli olmayan sekilde (vazgecme veya basarma disinda) cikilirsa sure kaydinin guncellenmesi
    public void exit_game(Date bitis_tarihi){
        this.bitis_tarihi=bitis_tarihi;
        set_oyunda_gecirilen_milisecond();
    }
    
    //bu metodu her adimda disaridan cagirarak, cozumun kontrol edilmesi gerekir
    //--simdilik gosterim amacli yazilmistir
    public boolean check_puzzle_cevap(){
        int sayac = 0;
        for(int i=0; i<satir; i++){
            for(int j=0; j<sutun; j++){
                if(karistirilmis_resimler[i][j]==resimler[i][j]){
                    sayac=sayac+1;
                }
            }
        }
        is_cozuldu = (sayac == satir*sutun);
        return is_cozuldu;
    }
    
    
    private int get_sira_fibo(int deger){
        //verilen bir sayinin fibonacci sayi dizisindeki sirasi-grubu
        int sayac=1;
        int toplam=0;
        int k;
        FKaristir x = new FKaristir();
        //ornegin 5 puan demek; level 3 demektir = 1+2+3 icerisindedir
        for(int i=1; deger>=toplam; i++){
            k=x.fibonacci_hesapla(i);
            toplam=toplam+k;//1,2,3,5,8,13
            System.out.println("Degerler "+sayac+" : "+k);
            if(deger != 1){
            sayac=sayac+1;
            }
            //System.out.println("Sayac : "+sayac);
        }
        System.out.println("Karistirma Sayisi : "+sayac);
        return sayac;
    }
    
    
    private void update_puan(boolean is_success){
        _level=get_sira_fibo(puan);//her puan guncellemesinden once, icinde bulunulan level bilgisini kaydet
        
        //date ve success parametrelerinin alt ve ust sinirlarini ayarla
        double date;
        double success;
        int result;
        
        //dakika farki = bitis_tarihi.getTime() - baslangic_tarihi.getTime() / (60*100);
        long dakika_farki = oyunda_gecirilen_milisecond / (60 * 1000);
        //level e bagli sure tanimlama.. istenen sey bir adimin dogru tahmin edilmesi icin kisiye 2 dakika verilmesidir
        long oyun_icin_verilen_dakika = _level * 2;
        
        if(dakika_farki <= oyun_icin_verilen_dakika){ //720 dakika = 12 saattir
            date=0;//oyun hizli bitirildi anlamina gelir
        }else{
            date=1;//oyun gec bitirildi anlamina gelir
        }
        if(is_success){
            success=1;//oyun basariyla sonlandi
        }else{
            success=0;//oyun basarisizlikla sonlandi
        }
        
        JAnn ysa = new JAnn(date,success);
        result=(int) ysa.get_sonuc();
        
        if(date>0 && success<1){
            result = result * -1;
        }
        
        puan=puan+result;
    }
}
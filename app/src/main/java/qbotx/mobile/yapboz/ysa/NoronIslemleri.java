/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qbotx.mobile.yapboz.ysa;

/**
 *
 * @author Suleyman
 */
public class NoronIslemleri {
    
    private int katman_sayisi;
    private int[] katman_olculeri;
    private double[][] noronlarin_ciktilari;
    private double[][] degisimler;
    private double [][][] agirliklar;
    private double [][][] onceki_agirlik_degerleri;
    
    NoronIslemleri(int [] katman_olculeri, int katman_sayisi){
        this.katman_olculeri=katman_olculeri;
        this.katman_sayisi = katman_sayisi;
    }
    
    public int get_katman_sayisi(){
        return this.katman_sayisi;
    }
    
    public int [] get_katman_olculeri(){
        return this.katman_olculeri;
    }
    
    
    
    public double[][] get_her_noronun_ciktilari(){
        return this.noronlarin_ciktilari;
    }
    public void set_noronlarin_ciktilari(){
        double temp [][] = new double[katman_sayisi][];
        for(int i=0; i<katman_sayisi; i++){
            temp[i]=new double[katman_olculeri[i]];
        }
        this.noronlarin_ciktilari=temp;
    }
    
    public double [][] get_degisimler(){
        return this.degisimler;
    }
    public void set_degisimler(){
        double temp [][] = new double[katman_sayisi][];
        for(int i=0; i<katman_sayisi; i++){
            temp[i]=new double[katman_olculeri[i]];
        }
        this.degisimler=temp;
    }
    
    public double [][][] get_agirliklar(){
        return this.agirliklar;
    }
    public void set_agirliklar(){
        double temp [][][] = new double[katman_sayisi][][];
        for(int i=0; i<katman_sayisi; i++){
            temp[i]=new double[katman_olculeri[i]][];
        }
            for (int i = 0; i<katman_sayisi; i++){
		for (int j = 0; j<katman_olculeri[i]; j++){
			temp[i][j] = new double[katman_olculeri[i] + 1];
		}
	}
        this.agirliklar=temp;
    }
       
    public double[][][] get_onceki_agirlik_degerleri(){
        return this.onceki_agirlik_degerleri;
    }
    public void set_onceki_agirlik_degerleri(){
        double temp [][][] = new double[katman_sayisi][][];
        for(int i=0; i<katman_sayisi; i++){
            temp[i]=new double[katman_olculeri[i]][];
        }
            for (int i = 0; i<katman_sayisi; i++){
		for (int j = 0; j<katman_olculeri[i]; j++){
			temp[i][j] = new double[katman_olculeri[i] + 1];
		}
	}
        this.onceki_agirlik_degerleri=temp;
    }
    
    //public mset_
    public void mset_her_noronun_ciktilari(double [][] noronlarin_ciktilari){
        this.noronlarin_ciktilari=noronlarin_ciktilari;
    }
    
    public void mset_degisimler( double[][] degisimler){
        this.degisimler=degisimler;
    }
    
    public void mset_agirliklar(double [][][] agirliklar){
        this.agirliklar=agirliklar;
    }
    
    public void mset_onceki_agirlik_degerleri(double [][][] onceki_agirlik_degerleri){
        this.onceki_agirlik_degerleri=onceki_agirlik_degerleri;
    }
}
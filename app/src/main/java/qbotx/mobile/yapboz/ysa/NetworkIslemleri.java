/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qbotx.mobile.yapboz.ysa;

import java.util.Random;

/**
 *
 * @author Suleyman
 */
public class NetworkIslemleri {
    
    
    int katman_sayisi;
    int[] katman_olculeri;
    
    NoronIslemleri noron_islemleri;
    double alpha;
    double beta;
    
    public NetworkIslemleri(NoronIslemleri noron_islemleri, double alpha, double beta){
        this.noron_islemleri=noron_islemleri;
        this.katman_sayisi=this.noron_islemleri.get_katman_sayisi();
        this.katman_olculeri=this.noron_islemleri.get_katman_olculeri();
        this.alpha = alpha;
        this.beta = beta;
        
        set_onceki_agirliklar_sifir(katman_sayisi,katman_olculeri, this.noron_islemleri.get_onceki_agirlik_degerleri());
        set_rasgele_agirliklar(katman_sayisi,katman_olculeri, this.noron_islemleri.get_agirliklar());
    }
    
    private double get_rasgele_double_sayi(double alt_sinir, double ust_sinir){
       return  (double) alt_sinir + new Random().nextDouble() * (ust_sinir - alt_sinir);
       //return new Random().nextDouble();
    }
    
    public void set_rasgele_agirliklar(int katman_sayisi, int[] katman_olculeri, double [][][] agirliklar){
        for (int i = 1; i<katman_sayisi; i++){
		for (int j = 0; j<katman_olculeri[i]; j++){
			for (int k = 0; k<katman_olculeri[i - 1] + 1; k++){
                            agirliklar[i-1][j][k] = get_rasgele_double_sayi(0,1);
                        }
                }
        }
    }
    
    public void set_onceki_agirliklar_sifir(int katman_sayisi, int[] katman_olculeri, double [][][] onceki_agirlik_degerleri){
        for (int i = 1; i<katman_sayisi; i++){
		for (int j = 0; j<katman_olculeri[i]; j++){
			for (int k = 0; k<katman_olculeri[i - 1] + 1; k++){
				onceki_agirlik_degerleri[i-1][j][k] = (double)0.0;
                        }
                }
        }
    }
    
    
    public double sigmoid_hesabi(double x) {
        return (1/( 1 + Math.pow(Math.E,(-1*x))));
  }
    
    public double risk_hata_hesabi(double[] hedef){
	double mse = 0;
	for (int i = 0; i<katman_olculeri[katman_sayisi - 1]; i++){
		mse += (hedef[i] - this.noron_islemleri.get_her_noronun_ciktilari()[katman_sayisi - 1][i])*
                        (hedef[i] - this.noron_islemleri.get_her_noronun_ciktilari()[katman_sayisi - 1][i]);
	}
	return mse / 2;
}
    
    //get
    public int get_katman_sayisi(){
        return this.katman_sayisi;
    }
    
    public int [] get_katman_olculeri(){
        return this.katman_olculeri;
    }
    
    public double [][] get_noronlarin_ciktilari(){
        return this.noron_islemleri.get_her_noronun_ciktilari();
    }
    
    public double [][][] get_onceki_agirliklar(){
        return this.noron_islemleri.get_onceki_agirlik_degerleri();
    }
    
    public double [][][] get_agirliklar(){
        return this.noron_islemleri.get_agirliklar();
    }
    
    public double [][] get_degisimler(){
        return this.noron_islemleri.get_degisimler();
    }
    
    public double get_alpha(){
        return this.alpha;
    }
    public double get_beta(){
        return this.beta;
    }
    
    //public mset_
    public void mset_her_noronun_ciktilari(double [][] noronlarin_ciktilari){
        this.noron_islemleri.mset_her_noronun_ciktilari(noronlarin_ciktilari);
    }
    
    public void mset_degisimler( double[][] degisimler){
        this.noron_islemleri.mset_degisimler(degisimler);
    }
    
    public void mset_agirliklar(double [][][] agirliklar){
        this.noron_islemleri.mset_agirliklar(agirliklar);
    }
    
    public void mset_onceki_agirlik_degerleri(double [][][] onceki_agirlik_degerleri){
        this.noron_islemleri.mset_onceki_agirlik_degerleri(onceki_agirlik_degerleri);
    }
}
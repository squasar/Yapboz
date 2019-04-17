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
public class Net {
    NoronIslemleri noron_islemler;
    NetworkIslemleri network_islemleri;
    
    public Net(int katman_sayisi, int[] katman_buyuklugu, double alpha, double beta){
        
        //ciktilar, degisimler, agirliklar icin gerekli alanlari tanimla
        noron_islemler = new NoronIslemleri(katman_buyuklugu, katman_sayisi);
        noron_islemler.set_noronlarin_ciktilari();
        noron_islemler.set_degisimler();
        noron_islemler.set_agirliklar();
        noron_islemler.set_onceki_agirlik_degerleri();
        
        //ilk rasgele network degerlerini belirle, sigmoid ve hata fonksiyonlarini tanimla 
        network_islemleri = new NetworkIslemleri(noron_islemler, alpha, beta);
        
    }
    
    
    // girilen inputlarla agi ileri besleme
    public void agi_ileri_besle(double [] in){
        double sum;

	//	input icerikleri gir
	for (int i = 0; i<network_islemleri.get_katman_olculeri()[0]; i++){
		network_islemleri.get_noronlarin_ciktilari()[0][i] = in[i];
        }
        
        //sigmoid fonksiyonunu kullanarak her norona aktivasyon degerini (ciktisini) gir
	for (int i = 1; i<network_islemleri.get_katman_sayisi(); i++){				// Her katman icin
		for (int j = 0; j<network_islemleri.get_katman_olculeri()[i]; j++){		// Her katmandaki noronlar icin
			sum = 0.0;
			for (int k = 0; k<network_islemleri.get_katman_olculeri()[i - 1]; k++){		// her noron icin...
				
                            sum += network_islemleri.get_noronlarin_ciktilari()[i - 1][k] * 
                                   network_islemleri.get_agirliklar()[i][j][k];
        		}
			sum += network_islemleri.get_agirliklar()[i][j][network_islemleri.get_katman_olculeri()[i]];		// sapma degerini uygula
			network_islemleri.get_noronlarin_ciktilari()[i][j] = network_islemleri.sigmoid_hesabi(sum);// Sigmoid fonksiyonunu uygula
		}
	}
        this.network_islemleri.mset_her_noronun_ciktilari(network_islemleri.get_noronlarin_ciktilari());
    }
   
    public void agi_geri_besle(double[] in, double[] hedef){
    	double sum;

        //	her noron icin cikis degerini guncelle
	agi_ileri_besle(in);

	//	cikis katmanindaki degisimi hesapla
        
	for (int i = 0; i<network_islemleri.get_katman_olculeri()[network_islemleri.get_katman_sayisi() - 1]; i++){
		network_islemleri.get_degisimler()[network_islemleri.get_katman_sayisi() - 1][i] = 
                        network_islemleri.get_noronlarin_ciktilari()[network_islemleri.get_katman_sayisi() - 1][i] *
			(1 - network_islemleri.get_noronlarin_ciktilari()[network_islemleri.get_katman_sayisi() - 1][i])*
                        (hedef[i] - network_islemleri.get_noronlarin_ciktilari()[network_islemleri.get_katman_sayisi() - 1][i]);
	}

        //	diger katmanlardaki degisimi hesapla	
	for (int i = network_islemleri.get_katman_sayisi() - 2; i>0; i--){
		for (int j = 0; j<network_islemleri.get_katman_olculeri()[i]; j++){
			sum = 0.0;
			for (int k = 0; k<network_islemleri.get_katman_olculeri()[i + 1]; k++){
				sum += network_islemleri.get_degisimler()[i + 1][k] * network_islemleri.get_agirliklar()[i + 1][k][j];
			}
			network_islemleri.get_degisimler()[i][j] = network_islemleri.get_noronlarin_ciktilari()[i][j] * 
                                (1 - network_islemleri.get_noronlarin_ciktilari()[i][j])*sum;
		}
	}
        this.network_islemleri.mset_degisimler(network_islemleri.get_degisimler());
        
        //	momentum uygula (alpha degeri 0 ise bu kisim bir islem yapmaz)
	for (int i = 1; i<network_islemleri.get_katman_sayisi(); i++){
		for (int j = 0; j<network_islemleri.get_katman_olculeri()[i]; j++){
			for (int k = 0; k<network_islemleri.get_katman_olculeri()[i - 1]; k++){
				network_islemleri.get_agirliklar()[i][j][k] += network_islemleri.get_alpha() * 
                                        network_islemleri.get_onceki_agirliklar()[i][j][k];
			}
			network_islemleri.get_agirliklar()[i][j][network_islemleri.get_katman_olculeri()[i]] += 
                                network_islemleri.get_alpha() 
                                * network_islemleri.get_onceki_agirliklar()[i][j][network_islemleri.get_katman_olculeri()[i]];
		}
	}

	//	agirliklari guncelle	
	for (int i = 1; i<network_islemleri.get_katman_sayisi(); i++){
            	for (int j = 0; j<network_islemleri.get_katman_olculeri()[i]; j++){
			for (int k = 0; k<network_islemleri.get_katman_olculeri()[i - 1]; k++){
				network_islemleri.get_onceki_agirliklar()[i][j][k] = network_islemleri.get_beta() * 
                                        network_islemleri.get_degisimler()[i][j] * 
                                        network_islemleri.get_noronlarin_ciktilari()[i - 1][k];
				network_islemleri.get_agirliklar()[i][j][k] += network_islemleri.get_onceki_agirliklar()[i][j][k];
			}
			network_islemleri.get_onceki_agirliklar()[i][j][network_islemleri.get_katman_olculeri()[i]] = 
                                network_islemleri.get_beta() * 
                                network_islemleri.get_degisimler()[i][j];
			network_islemleri.get_agirliklar()[i][j][network_islemleri.get_katman_olculeri()[i]] += 
                                network_islemleri.get_onceki_agirliklar()[i][j][network_islemleri.get_katman_olculeri()[i]];
		}
	}
        this.network_islemleri.mset_onceki_agirlik_degerleri(network_islemleri.get_onceki_agirliklar());
        this.network_islemleri.mset_agirliklar(network_islemleri.get_agirliklar());
        
}
    //	sinir aginin i. ciktisini al
    public double get_i_inci_noron_ciktisi(int i){
        return network_islemleri.get_noronlarin_ciktilari()[network_islemleri.get_katman_sayisi()-1][i];
    }
    //  hata oranini al
    public double get_hata_orani(double [] hedef){
        return network_islemleri.risk_hata_hesabi(hedef);
    }
}
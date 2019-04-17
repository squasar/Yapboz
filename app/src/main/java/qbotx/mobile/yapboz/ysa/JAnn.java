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
public class JAnn {
    
    //BU SINIFI TEST ET
    
    double sonuc;
    
    public double get_sonuc(){
        int a = (int) Math.round(sonuc);//en yakin tamsayiya yuvarlama islemi
        sonuc = a;
        return sonuc;
    }
    
    double[][] data ={
              { 0, 0, 0 },
              { 0, 1, 1 },
              { 1, 0, 1 },
              { 1, 1, 0 }
        };
    
    public JAnn(double date, double success){
        
        double[][] testData ={
              { date, success }
        };
        int katman_sayisi = 3;
        int[] katman_buyuklugu = { 2, 2, 1 };//her katmandaki noron sayilari - ilk katman inputlar icindir - katman sayisiyla uyumlu olmali

        double ogrenme_orani = 2;//beta
        double momentum = 0.1;//alpha
        double hedef_hata_orani = 0.000001;


	// egitim surecindeki maksimum iterasyon sayisi
	int num_iter = 200000;

        //Agi olustur
        
        Net nt = new Net(katman_sayisi, katman_buyuklugu, momentum, ogrenme_orani);
        System.out.println("Agin egitimi basliyor....");
        System.out.println("Sonuc degerleri egitim icin aliniyor...");
        
        double [][] hedef = new double[data.length][1];
        for(int i=0; i<data.length; i++){
            //sonuc degeri daima 3.sutundadir ; data[0].length-1
            hedef[i][0]=data[i][data[i].length-1];
        }
        
        System.out.println(data[0].length+ " degeri anlasildi");
        System.out.println("Egitim basliyor");
        
        for (int i = 0; i<num_iter; i++){

            nt.agi_geri_besle(data[i%4], hedef[i%4]);
            
            if(nt.get_hata_orani(hedef[i%4])<hedef_hata_orani){
                    System.out.println("Network Egitildi. Ust sinira "+i+" iterasyonda ulasildi.");
                    System.out.println("HATA+RISK:  "+nt.get_hata_orani(data[i%4]));
                    break;
                }
		if (i % (num_iter / 10) == 0)
                    System.out.println("HATA+RISK:  "+nt.get_hata_orani(hedef[i%4])+"  ...");
		}
        
        System.out.println(""+num_iter+" iterasyon tamamlandi...");
        System.out.println("HATA+RISK:  "+nt.get_hata_orani(hedef[num_iter%4]));
        System.out.println("Egitilen agin test verisiyle test edilme sureci basliyor....");

	for (int i = 0; i < 1; i++)
	{
            nt.agi_ileri_besle(testData[i]);
            sonuc=nt.get_i_inci_noron_ciktisi(0);
            //System.out.println("  "+testData[i][0]+"  "+testData[i][1]+"  "+
            //        testData[i][2]+"  "+nt.get_i_inci_noron_ciktisi(0));
	}
    }
    }
    
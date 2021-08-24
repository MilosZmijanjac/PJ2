package Teritorija;

import Konstanta.Konstanta;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;


public class Mapa {

    public static final int VELICINA_MAPE=30;
    public static Polje[][] mapa=new Polje[VELICINA_MAPE][VELICINA_MAPE];
    public static Dionica AB;
    public static Dionica BC;
    public static Dionica CD;
    public static Dionica CE;

    public static void postaviMapu(Group gr){
        kreirajTeritoriju();
        postaviPutLijevi(gr);
        postaviPutSrednji(gr);
        postaviPutDesni(gr);
        postaviStanicuA(gr);
        postaviStanicuB(gr);
        postaviStanicuC(gr);
        postaviStanicuD(gr);
        postaviStanicuE(gr);
        postaviDionicuAB(gr);
        postaviDionicuBC(gr);
        postaviDionicuCD(gr);
        postaviDionicuCE(gr);
    }
    private static void kreirajTeritoriju(){
        for(int i=0;i<VELICINA_MAPE;i++)
            for(int j=0;j<VELICINA_MAPE;j++)
                mapa[i][j]=null;
    }
    private static void postaviStanicuA(Group gr){
        Stanica stanica = new Stanica("A",1);
        mapa[27][2]=new Polje(new Lokacija(2,27),Pravac.VERTIKALAN,stanica);
        mapa[27][2].setStanica(true);
        mapa[28][2]=new Polje(new Lokacija(2,28),Pravac.VERTIKALAN,stanica);
        mapa[28][2].setStanica(true);
        kreirajStanicuNaMapi(gr, Konstanta.slikeFolder+File.separator+"StanicaA.png",1,27);
        new Thread(stanica).start();
    }
    private static void postaviStanicuB(Group gr ){
        Stanica stanica =new Stanica("B",2);
        mapa[6][6]=new Polje(new Lokacija(6,6),Pravac.VERTIKALAN,stanica);
        mapa[6][6].setStanica(true);
        mapa[6][7]=new Polje(new Lokacija(7,6),Pravac.VERTIKALAN,stanica);
        mapa[6][7].setStanica(true);
        kreirajStanicuNaMapi(gr, Konstanta.slikeFolder+File.separator+"StanicaB.png",6,5);
        new Thread(stanica).start();
    }
    private static void postaviDionicuAB(Group gr){

        //dionica do skretanja desno
        for(int i = 26; i > 15; i--) {
            mapa[i][2]=new Polje(new Lokacija(2,i),Pravac.VERTIKALAN,null);
            kreirajPoljeNaMapi(gr,Color.GREY,2,i);
        }
        //dionica do skretanja gore
        for(int j=3; j < 6; j++) {
            mapa[16][j]=new Polje(new Lokacija(j,16),Pravac.HORIZONTALAN,null);
            kreirajPoljeNaMapi(gr,Color.GREY,j,16);
        }

        //dionica do stanice B
        for(int i = 15; i > 5; i--) {
            mapa[i][5]=new Polje(new Lokacija(5,i),Pravac.VERTIKALAN,null);
            kreirajPoljeNaMapi(gr,Color.GREY,5,i);
        }
        kreirajPoljeNaMapi(gr,Color.GREY,2,29);
        mapa[16][2].setSkretnica(true);
        mapa[16][5].setSkretnica(true);
        mapa[6][5].setSkretnica(true);
        mapa[21][2].setPrelaz(true);
        mapa[20][2].setPrelaz(true);
        AB=new Dionica();

    }
    private static void postaviStanicuC(Group gr){
        Stanica stanica=new Stanica("C",3);
        mapa[12][19]=new Polje(new Lokacija(19,12),Pravac.VERTIKALAN,stanica);
        mapa[12][19].setStanica(true);
        mapa[12][20]=new Polje(new Lokacija(20,12),Pravac.VERTIKALAN,stanica);
        mapa[12][20].setStanica(true);
        mapa[13][19]=new Polje(new Lokacija(19,13),Pravac.VERTIKALAN,stanica);
        mapa[13][19].setStanica(true);
        mapa[13][20]=new Polje(new Lokacija(20,13),Pravac.VERTIKALAN,stanica);
        mapa[13][20].setStanica(true);
        kreirajStanicuNaMapi(gr, Konstanta.slikeFolder+File.separator+ "StanicaC.png",19,12);
        new Thread(stanica).start();
    }
    private static void postaviDionicuBC(Group gr){
        // od stanice B do skretnice
        for(int j=8; j < 19; j++) {
            mapa[6][j]=new Polje(new Lokacija(j,6),Pravac.HORIZONTALAN,null);
            kreirajPoljeNaMapi(gr,Color.GREY,j,6);
        }

        //dionica kada skrene prema dole pa do stanice C
        for(int i = 6; i < 12; i++) {
            mapa[i][19]=new Polje(new Lokacija(19,i),Pravac.VERTIKALAN,null);
            kreirajPoljeNaMapi(gr,Color.GREY,19,i);
        }
        mapa[6][19].setSkretnica(true);
        mapa[6][14].setPrelaz(true);
        mapa[6][13].setPrelaz(true);
        BC=new Dionica();
    }
    private static void postaviStanicuD(Group gr){
        Stanica stanica = new Stanica("D",1);
        mapa[1][26]=new Polje(new Lokacija(26,1),Pravac.VERTIKALAN,stanica);
        mapa[1][26].setStanica(true);
        mapa[1][27]=new Polje(new Lokacija(27,1),Pravac.VERTIKALAN,stanica);
        mapa[1][27].setStanica(true);
        kreirajStanicuNaMapi(gr, Konstanta.slikeFolder+File.separator+ "StanicaD.png",26,1);
        new Thread(stanica).start();
    }
    private static void postaviDionicuCD(Group gr){

        //dionica od C stanice desno do skretanja prema gore
        for(int j=21; j < 26; j++) {
            mapa[12][j]=new Polje(new Lokacija(j,12),Pravac.HORIZONTALAN,null);
            kreirajPoljeNaMapi(gr,Color.GREY,j,12);
        }
        //dionica puta od skretanja prema gore
        for(int i=12; i > 8; i--) {
            mapa[i][26]=new Polje(new Lokacija(26,i),Pravac.VERTIKALAN,null);
            kreirajPoljeNaMapi(gr,Color.GREY,26,i);
        }
        //dionica puta skretanje desno
        for(int j=27; j < 28; j++) {
            mapa[9][j]=new Polje(new Lokacija(j,9),Pravac.HORIZONTALAN,null);
            kreirajPoljeNaMapi(gr,Color.GREY,j,9);
        }
        //dionica od skretanja desno prema gore
        for(int i=9; i > 4; i--) {
            mapa[i][28]=new Polje(new Lokacija(28,i),Pravac.VERTIKALAN,null);
            kreirajPoljeNaMapi(gr,Color.GREY,28,i);
        }

        //dionica kada skrene lijevo
        for(int j=27; j > 22; j--) {
            mapa[5][j]=new Polje(new Lokacija(j,5),Pravac.HORIZONTALAN,null);
            kreirajPoljeNaMapi(gr,Color.GREY,j,5);
        }
        //dionica kad se skrene gore
        for(int i=4; i > 2; i--) {
            mapa[i][23]=new Polje(new Lokacija(23,i),Pravac.VERTIKALAN,null);
            kreirajPoljeNaMapi(gr,Color.GREY,23,i);
        }

        //dionica kada skrene lijevo i krene gore
        for(int i=3;i>0;i--){
            mapa[i][22]=new Polje(new Lokacija(22,i),Pravac.VERTIKALAN,null);
            kreirajPoljeNaMapi(gr,Color.GREY,22,i);
        }
        //dionica kada skrene desno prema D stanici
        for(int j=23; j < 26; j++) {
            mapa[1][j]=new Polje(new Lokacija(j,1),Pravac.HORIZONTALAN,null);
            kreirajPoljeNaMapi(gr,Color.GREY,j,1);
        }

        mapa[12][26].setSkretnica(true);
        mapa[9][26].setSkretnica(true);
        mapa[9][28].setSkretnica(true);
        mapa[5][28].setSkretnica(true);
        mapa[5][23].setSkretnica(true);
        mapa[3][23].setSkretnica(true);
        mapa[3][22].setSkretnica(true);
        mapa[1][22].setSkretnica(true);

        CD=new Dionica();
    }
    private static void postaviStanicuE(Group gr){
        Stanica stanica = new Stanica("E",1);
        mapa[25][26]=new Polje(new Lokacija(26,25),Pravac.VERTIKALAN,stanica);
        mapa[25][26].setStanica(true);
        kreirajStanicuNaMapi(gr, Konstanta.slikeFolder+File.separator+ "StanicaE.png",25,25);
        new Thread(stanica).start();
    }
    private static void postaviDionicuCE(Group gr){
        //dionica kada ide od C prema dole
        for(int i=14; i < 18; i++) {
            mapa[i][20]=new Polje(new Lokacija(20,i),Pravac.VERTIKALAN,null);
            kreirajPoljeNaMapi(gr,Color.GREY,20,i);
        }
        //dionica puta kada skrene desno
        for(int j = 20; j < 27 ; j++) {
            mapa[18][j]=new Polje(new Lokacija(j,18),Pravac.HORIZONTALAN,null);
            kreirajPoljeNaMapi(gr,Color.GREY,j,18);
        }
        //dionica puta prema dole do stanice E
        for(int i=19; i <25; i++) {
            mapa[i][26]=new Polje(new Lokacija(26,i),Pravac.VERTIKALAN,null);
            kreirajPoljeNaMapi(gr,Color.GREY,26,i);
        }
        kreirajPoljeNaMapi(gr,Color.GREY,27,25);
        kreirajPoljeNaMapi(gr,Color.GREY,28,25);
        kreirajPoljeNaMapi(gr,Color.GREY,29,25);
        mapa[18][20].setSkretnica(true);
        mapa[18][26].setSkretnica(true);
        mapa[21][26].setPrelaz(true);
        mapa[20][26].setPrelaz(true);
        CE=new Dionica();
    }
    private static void postaviPutLijevi(Group gr){
        //lijeva traka
        for(int i=21; i <30; i++) {
            mapa[i][7]=new Polje(new Lokacija(7,i),Pravac.VERTIKALAN,null);
            kreirajPoljeNaMapi(gr,Color.STEELBLUE,7,i);
        }
        for(int j = 0; j < 7 ; j++) {
            mapa[21][j]=new Polje(new Lokacija(j,21),Pravac.HORIZONTALAN,null);
            kreirajPoljeNaMapi(gr,Color.STEELBLUE,j,21);
        }
        mapa[21][7].setSkretnica(true);
        mapa[29][7].setKraj(true);

        //desna traka
        for(int i=20; i <30; i++) {
            mapa[i][8]=new Polje(new Lokacija(8,i),Pravac.VERTIKALAN,null);
            kreirajPoljeNaMapi(gr,Color.STEELBLUE,8,i);
        }
        for(int j = 0; j < 8 ; j++) {
            mapa[20][j]=new Polje(new Lokacija(j,20),Pravac.HORIZONTALAN,null);
            kreirajPoljeNaMapi(gr,Color.STEELBLUE,j,20);
        }
        mapa[20][8].setSkretnica(true);
        mapa[20][0].setKraj(true);
    }
    private static void postaviPutSrednji(Group gr){
        //lijeva traka
        for(int i=0; i <30; i++) {
            mapa[i][13]=new Polje(new Lokacija(13,i),Pravac.VERTIKALAN,null);
            kreirajPoljeNaMapi(gr,Color.STEELBLUE,13,i);
        }
        mapa[29][13].setKraj(true);

        //desna traka
        for(int i=0; i <30; i++) {
            mapa[i][14]=new Polje(new Lokacija(14,i),Pravac.VERTIKALAN,null);
            kreirajPoljeNaMapi(gr,Color.STEELBLUE,14,i);
        }
        mapa[0][14].setKraj(true);

    }
    private static void postaviPutDesni(Group gr){
        //lijeva traka
        for(int i=20; i <30; i++) {
            mapa[i][21]=new Polje(new Lokacija(21,i),Pravac.VERTIKALAN,null);
            kreirajPoljeNaMapi(gr,Color.STEELBLUE,21,i);
        }
        for(int j = 22; j < 30 ; j++) {
            mapa[20][j]=new Polje(new Lokacija(j,20),Pravac.HORIZONTALAN,null);
            kreirajPoljeNaMapi(gr,Color.STEELBLUE,j,20);
        }
        mapa[20][21].setSkretnica(true);
        mapa[29][21].setKraj(true);

        //desna traka
        for(int i=21; i <30; i++) {
            mapa[i][22]=new Polje(new Lokacija(22,i),Pravac.VERTIKALAN,null);
            kreirajPoljeNaMapi(gr,Color.STEELBLUE,22,i);
        }
        for(int j = 23; j < 30 ; j++) {
            mapa[21][j]=new Polje(new Lokacija(j,21),Pravac.HORIZONTALAN,null);
            kreirajPoljeNaMapi(gr,Color.STEELBLUE,j,21);
        }
        mapa[21][22].setSkretnica(true);
        mapa[21][29].setKraj(true);
    }
    public static Stanica getStanica(String oznaka){
        Stanica stanica = switch (oznaka) {
            case "A" -> (Stanica) mapa[27][2].getElement();
            case "B" -> (Stanica) mapa[6][6].getElement();
            case "C" -> (Stanica) mapa[12][19].getElement();
            case "D" -> (Stanica) mapa[1][26].getElement();
            case "E" -> (Stanica) mapa[25][26].getElement();
            default -> throw new IllegalStateException("Unexpected value: " + oznaka);
        };
        return stanica;
    }
    private static void kreirajPoljeNaMapi(Group group, Color color,double x,double y){
        Platform.runLater(()->   {
            Rectangle rec = new Rectangle(20, 20);
            rec.setFill(color.deriveColor(1, 1, 1, 0.7));
            rec.setX(x * 20);
            rec.setY(y * 20);
            group.getChildren().add(rec);
        });
    }
    private static void kreirajStanicuNaMapi(Group group,String imagePath,double x,double y){
        Platform.runLater(()-> {
            ImageView img = new ImageView();
            img.setImage(new Image("file:"+imagePath, 40, 40, false, false));
            img.setX(x * 20);
            img.setY(y * 20);
            group.getChildren().add(img);
        });
    }
    public static synchronized boolean isSlobodnaDionica(Lokacija lokacija) {
        if(lokacija.odgovaraKoordinatama(2,26)||lokacija.odgovaraKoordinatama(2,21)||
                lokacija.odgovaraKoordinatama(2,20)||lokacija.odgovaraKoordinatama(5,6))
            return AB.isSlobodna();
        else if(lokacija.odgovaraKoordinatama(8,6)|| lokacija.odgovaraKoordinatama(13,6)||
                lokacija.odgovaraKoordinatama(14,6)|| lokacija.odgovaraKoordinatama(19,11))
            return BC.isSlobodna();
        else if(lokacija.odgovaraKoordinatama(21,12)|| lokacija.odgovaraKoordinatama(25,1))
            return CD.isSlobodna();
        else if(lokacija.odgovaraKoordinatama(20,14)|| lokacija.odgovaraKoordinatama(26,20)||
                lokacija.odgovaraKoordinatama(26,21)|| lokacija.odgovaraKoordinatama(26,24))
            return CE.isSlobodna();
        else
            return true;
    }
    public static Smjer smjerDionice(Lokacija lokacija){
        if(lokacija.odgovaraKoordinatama(2,26)||lokacija.odgovaraKoordinatama(2,21)||
                lokacija.odgovaraKoordinatama(2,20)||lokacija.odgovaraKoordinatama(5,6))
            return AB.getSmjerDionice();
        else if(lokacija.odgovaraKoordinatama(8,6)|| lokacija.odgovaraKoordinatama(13,6)||
                lokacija.odgovaraKoordinatama(14,6)|| lokacija.odgovaraKoordinatama(19,11))
            return BC.getSmjerDionice();
        else if(lokacija.odgovaraKoordinatama(21,12)|| lokacija.odgovaraKoordinatama(25,1))
            return CD.getSmjerDionice();
        else if(lokacija.odgovaraKoordinatama(20,14)|| lokacija.odgovaraKoordinatama(26,20)||
                lokacija.odgovaraKoordinatama(26,21)|| lokacija.odgovaraKoordinatama(26,24))
            return CE.getSmjerDionice();
        else return Smjer.NAPRIJED;
    }
    public static void  postaviElement(Lokacija lokacija , Element element){
        mapa[lokacija.getPozicijaY()][lokacija.getPozicijaX()].setElement(element);
    }
    public static synchronized Element getElement(Lokacija lokacija){
        return mapa[lokacija.getPozicijaY()][lokacija.getPozicijaX()].getElement();
    }
}

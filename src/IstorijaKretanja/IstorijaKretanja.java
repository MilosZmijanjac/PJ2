package IstorijaKretanja;

import Teritorija.Lokacija;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IstorijaKretanja  implements Serializable {
    private ArrayList<String> posjeceneStanice;
    private ArrayList<Double> vremenaPosjete;
    private ArrayList<Lokacija> lokacije;
    private static int count=0;
    private String fileName;
    static{
        try {
            Logger.getLogger(IstorijaKretanja.class.getName()).addHandler(new FileHandler("logs/IstorijaKretanja.log"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public IstorijaKretanja(){
        posjeceneStanice=new ArrayList<>();
        vremenaPosjete=new ArrayList<>();
        lokacije=new ArrayList<>();
        fileName="kompozicija"+count;
    }
    public String getFileName(){
        return fileName;
    }
    public void dodajStanicu(String stanica,double vrijeme){
        posjeceneStanice.add(stanica);
        vremenaPosjete.add(vrijeme/1000.0);
    }
    public void dodajLokaciju(Lokacija lokacija){
        lokacije.add(lokacija);
    }
    public ArrayList<String> getPosjeceneStanice(){
        return posjeceneStanice;
    }
    public ArrayList<Lokacija> getLokacije(){
        return lokacije;
    }
    public ArrayList<Double> getVremenaPosjete(){
        return vremenaPosjete;
    }

    public static void serializuj(IstorijaKretanja istorijaKretanja)  {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream( "kretanja/kompozicija" + (count++) + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(istorijaKretanja);
            out.close();
            fileOut.close();
        }catch (Exception ex){
            Logger.getLogger(IstorijaKretanja.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
        }
    }
    public static IstorijaKretanja deserializuj(String filePath) {
        IstorijaKretanja istorijaKretanja = null;
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            istorijaKretanja = (IstorijaKretanja) in.readObject();
            in.close();
            fileIn.close();
        }catch (Exception ex){
            Logger.getLogger(IstorijaKretanja.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
        }
        return istorijaKretanja;
    }



}

package IstorijaKretanja;

import Konstanta.Konstanta;
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
    private  String fileName;
    private double startTime;
    public static FileHandler handler;
    private static final long serialVersionUID = 6529685098267757690L;
    static{
        try {
            handler=new FileHandler(Konstanta.logFolder+ File.separator+"IstorijaKretanja.log");
            Logger.getLogger(IstorijaKretanja.class.getName()).addHandler(handler);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public IstorijaKretanja(){}
    public IstorijaKretanja(String nazivKompozicije){
        posjeceneStanice=new ArrayList<>();
        vremenaPosjete=new ArrayList<>();
        lokacije=new ArrayList<>();
        fileName=nazivKompozicije;
        int brojFajlova;
        File file=new File(Konstanta.kretanjaFolder+File.separator+fileName+".ser");
        if(file.exists()){
            String finalFileName = fileName;
            brojFajlova=file.getParentFile().list((dir, name) -> name.startsWith(finalFileName)).length;
            fileName=new StringBuilder().append(fileName).append(" (").append(brojFajlova++).append(")").toString();
        }

        startTime=System.currentTimeMillis()/1000.0;
    }
    public  String getFileName(){
        return fileName;
    }
    public synchronized void dodajStanicu(String stanica){
        posjeceneStanice.add(stanica);
        vremenaPosjete.add(System.currentTimeMillis()/1000.0-startTime);
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

    public void serializuj(IstorijaKretanja istorijaKretanja)  {
        try {
            String filePath=Konstanta.kretanjaFolder+File.separator;
            String fileName=istorijaKretanja.getFileName();
            String fileExtension=".ser";
            int brojFajlova;
            File file=new File(filePath+fileName+fileExtension);
            if(file.exists()){
                String finalFileName = fileName;
                brojFajlova=file.getParentFile().list((dir, name) -> name.startsWith(finalFileName)).length;
                fileName=new StringBuilder().append(fileName).append(" (").append(brojFajlova++).append(")").toString();
            }
            FileOutputStream fileOut =
                    new FileOutputStream( filePath+fileName+fileExtension);
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

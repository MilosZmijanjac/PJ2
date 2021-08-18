package Kompozicija;

import Teritorija.Element;
import Teritorija.Mapa;
import Kompozicija.Vagon.*;
import javafx.scene.Group;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KompozicijaService implements Runnable {

    private Path directoryPath;
    private WatchService watcher;
    private Group group;
    public static boolean stopThread;
    private Map<String,String[]> kombinacije= Stream.of(new Object[][]{
            {"LPE", new String[]{"LPE", "LUE", "VPR", "VPL", "VPSS", "VPZS"}},
            {"LPD", new String[]{"LPD", "LUD", "VPR", "VPL", "VPSS", "VPZS"}},
            {"LPP", new String[]{"LPP", "LUP", "VPR", "VPL", "VPSS", "VPZS"}},
            {"LTD", new String[]{"LTD", "LUD", "VT"}},
            {"LTE", new String[]{"LTE", "LUE", "VT"}},
            {"LTP", new String[]{"LTP", "LUP", "VT"}},
            {"LUE", new String[]{"LPE","LTE", "LUE", "VT", "VPR", "VPL", "VPSS", "VPZS", "VPN"}},
            {"LUD", new String[]{"LPD","LTD", "LUD", "VT", "VPR", "VPL", "VPSS", "VPZS", "VPN"}},
            {"LUP", new String[]{"LPP","LTP", "LUP", "VT", "VPR", "VPL", "VPSS", "VPZS", "VPN"}},
            {"LME", new String[]{"VPN"}},
            {"LMD", new String[]{"VPN"}},
            {"LMP", new String[]{"VPN"}}}).collect(Collectors.toMap(data -> (String) data[0], data -> (String[]) data[1]));
    static{
        try {
            Logger.getLogger(KompozicijaService.class.getName()).addHandler(new FileHandler("logs/KompozicijaService.log"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public KompozicijaService(Group group, String directoryPath)  {
        try{
        this.directoryPath=Path.of(directoryPath);
        watcher= FileSystems.getDefault().newWatchService();
        this.directoryPath.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY,StandardWatchEventKinds.ENTRY_MODIFY);
        stopThread=true;
        this.group=group;
        }
        catch (Exception ex){
            Logger.getLogger(KompozicijaService.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
        }
    }

    @Override
    public void run() {
        while(stopThread){
            WatchKey watchKey = null;
            try {
                watchKey = watcher.take();
                for (final WatchEvent<?> event : watchKey.pollEvents()) {
                    takeActionOnChangeEvent(event);
                }

                if (!watchKey.reset()) {
                    watchKey.cancel();
                    watcher.close();
                    break;
                }
            } catch (InterruptedException | IOException ex) {
                Logger.getLogger(KompozicijaService.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
            }
        }
    }

    private  void takeActionOnChangeEvent(WatchEvent<?> event) throws IOException, InterruptedException {
        WatchEvent<Path> ev = (WatchEvent<Path>) event;
        Path fileName = ev.context();
        if (fileName.toString().trim().endsWith(".txt")) {
            List<String> content = Files.readAllLines(directoryPath.resolve(fileName));
            String podaciOKompoziciji=content.get(2);
            String[] podaci=podaciOKompoziciji.trim().split(",");

            int brLokomotiva = Integer.parseInt(podaci[0].trim());
            int brVagona = Integer.parseInt(podaci[1].trim());
            String raspored = podaci[2].trim();
            int brzina = Integer.parseInt(podaci[3].trim());
            String pocetnaStanica = podaci[4];
            String krajnjaStanica = podaci[5];

            String[] nizDijelova=raspored.split(";");
            if((brLokomotiva+brVagona)!=nizDijelova.length)
                return;
            ArrayList<Element>elementi=kreirajKompoziciju(nizDijelova);
            Mapa.getStanica(pocetnaStanica).staviKompozicijuURedCekanja(new Kompozicija(group,elementi,pocetnaStanica,krajnjaStanica,brzina));
        }
    }

    private ArrayList<Element> kreirajKompoziciju(String[] nizDijelova){
        ArrayList<Element> elementiKompozicije=new ArrayList<>();
        if (!nizDijelova[0].startsWith("L"))
            return null;
        if(nizDijelova[0].endsWith("E"))
            elementiKompozicije.add(new StrujnoPolje());
        for(String string : nizDijelova){
            if(Arrays.stream((kombinacije.get(nizDijelova[0]))).noneMatch(string::equals))
                return null;
            switch (string) {
                case "LPE" -> elementiKompozicije.add(new Lokomotiva(Pogon.ELEKTRICNI, TipLokomotive.PUTNICKA));
                case "LPD" -> elementiKompozicije.add(new Lokomotiva(Pogon.DIZELSKI, TipLokomotive.PUTNICKA));
                case "LPP" -> elementiKompozicije.add(new Lokomotiva(Pogon.PARNI, TipLokomotive.PUTNICKA));
                case "LTE" -> elementiKompozicije.add(new Lokomotiva(Pogon.ELEKTRICNI, TipLokomotive.TERETNA));
                case "LTD" -> elementiKompozicije.add(new Lokomotiva(Pogon.DIZELSKI, TipLokomotive.TERETNA));
                case "LTP" -> elementiKompozicije.add(new Lokomotiva(Pogon.PARNI, TipLokomotive.TERETNA));
                case "LUE" -> elementiKompozicije.add(new Lokomotiva(Pogon.ELEKTRICNI, TipLokomotive.UNIVERZALNA));
                case "LUD" -> elementiKompozicije.add(new Lokomotiva(Pogon.DIZELSKI, TipLokomotive.UNIVERZALNA));
                case "LUP" -> elementiKompozicije.add(new Lokomotiva(Pogon.PARNI, TipLokomotive.UNIVERZALNA));
                case "LME" -> elementiKompozicije.add(new Lokomotiva(Pogon.ELEKTRICNI, TipLokomotive.MANEVARSKA));
                case "LMD" -> elementiKompozicije.add(new Lokomotiva(Pogon.DIZELSKI, TipLokomotive.MANEVARSKA));
                case "LMP" -> elementiKompozicije.add(new Lokomotiva(Pogon.PARNI, TipLokomotive.MANEVARSKA));
                case "VPR" -> elementiKompozicije.add(new VagonPutnickiRestoran());
                case "VPL" -> elementiKompozicije.add(new VagonPutnickiSaLezajima());
                case "VPSS" -> elementiKompozicije.add(new VagonPutnickiSaSjedistima());
                case "VPZS" -> elementiKompozicije.add(new VagonPutnickiZaSpavanje());
                case "VT" -> elementiKompozicije.add(new VagonTeretni());
                case "VPN" -> elementiKompozicije.add(new VagonZaPosebnuNamjenu());
            }
        }
        if(nizDijelova[0].endsWith("E"))
            elementiKompozicije.add(new StrujnoPolje());

      return elementiKompozicije;
    }
}

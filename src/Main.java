import IstorijaKretanja.IstorijaKretanja;
import IstorijaKretanja.IstorijaKretanjaController;
import Kompozicija.Kompozicija;
import Kompozicija.KompozicijaService;
import Konstanta.Konstanta;
import Teritorija.Mapa;
import Teritorija.Stanica;
import Vozilo.Vozilo;
import Vozilo.VoziloService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Timer;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    static FileHandler handler;
    static{
        try {
            handler=new FileHandler(Konstanta.logFolder+ File.separator+"Main.log");
            Logger.getLogger(Main.class.getName()).addHandler(handler);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ucitajParametreSimulacije();
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        Timer timer=new Timer();
        HBox kontroleHBox=new HBox();
        Button start = new Button();
        Button istorijaKretanja=new Button();
        Group root = new Group();

        kontroleHBox.setMinWidth(600);
        kontroleHBox.setMinHeight(100);
        kontroleHBox.setTranslateY(30*20);
        kontroleHBox.setBackground(new Background(new BackgroundFill(Color.WHITE,CornerRadii.EMPTY, Insets.EMPTY)));

        start.setText("START");
        istorijaKretanja.setText("Istorija kretanja");


        new Thread(()-> Mapa.postaviMapu(root)).start();
        root.getChildren().add(kontroleHBox);
        Scene scene = new Scene( root, 600, 700);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.setFill(iscrtajMrezu());
        //klikom na dugme Istorija Kretanja
        istorijaKretanja.setOnAction(actionEvent -> {
            try {
                Parent root1 = FXMLLoader.load(Main.class.getResource("IstorijaKretanja/IstorijaKretanjaView.fxml"));
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setScene(new Scene(root1));
                stage.show();
                stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, event -> IstorijaKretanjaController.update=false);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
            }
        });
        //klik na dugme START
        start.setOnAction(actionEvent -> new Thread(() -> {
            try {
               Thread t= new Thread(new KompozicijaService(root, Konstanta.kompozicijeFolder));
               t.setDaemon(true);
               t.start();
                timer.schedule(new VoziloService(root,Konstanta.voziloConfig), 0, 2000);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
            }
        }).start());
        kontroleHBox.getChildren().add(start);
        kontroleHBox.getChildren().add(istorijaKretanja);
        ucitajSlikuVagona(kontroleHBox);
        //prilikom izlaza iz programa
        primaryStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, event->{
            timer.cancel();
            Vozilo.stopThread=false;
            Kompozicija.stopThread=false;
            Stanica.stopThread=false;
            KompozicijaService.stopThread=false;
            IstorijaKretanjaController.update=false;
            zatvoriHandlere();
        });
    }
    public ImagePattern iscrtajMrezu() {

        double sirina = 20;
        double visina = 20;
        Canvas canvas=new Canvas(sirina,visina);

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.setFill(Color.WHITE.deriveColor(1, 1, 1, 0.2));
        graphicsContext.fillRect(1, 1, sirina, visina);
        graphicsContext.strokeRect(0.5, 0.5, sirina, visina);
        Image slika = canvas.snapshot(new SnapshotParameters(), null);

        return new ImagePattern(slika, 0, 0, sirina, visina, false);

    }
    private static void ucitajParametreSimulacije(){
        try (InputStream inputStream = new FileInputStream("ZeljeznickiSaobracaj.config")) {
            Properties parametri = new Properties();
            parametri.load(inputStream);

            Konstanta.kretanjaFolder=parametri.getProperty("kretanjaFolder");
            Konstanta.voziloConfig=parametri.getProperty("voziloConfig");
            Konstanta.kompozicijeFolder=parametri.getProperty("kompozicijeFolder");
            Konstanta.slikeFolder=parametri.getProperty("slikeFolder");
            Konstanta.logFolder=parametri.getProperty("logFolder");

        }catch (Exception ex){
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
        }
    }
    private void zatvoriHandlere(){
        if(handler!=null)
            handler.close();
        if(VoziloService.handler!=null)
            VoziloService.handler.close();
        if(Vozilo.handler!=null)
            Vozilo.handler.close();
        if(Stanica.handler!=null)
            Stanica.handler.close();
        if(KompozicijaService.handler!=null)
            KompozicijaService.handler.close();
        if (Kompozicija.handler!=null)
            Kompozicija.handler.close();
        if(IstorijaKretanjaController.handler!=null)
            IstorijaKretanjaController.handler.close();
        if(IstorijaKretanja.handler!=null)
            IstorijaKretanja.handler.close();
    }
    private void ucitajSlikuVagona(HBox hBox){
        ImageView img=new ImageView();
        img.setImage(new Image("file:"+Konstanta.slikeFolder+File.separator+"vagoni.png",450,100,false,false));
        hBox.getChildren().add(img);

    }
}

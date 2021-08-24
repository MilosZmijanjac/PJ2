package IstorijaKretanja;

import Konstanta.Konstanta;
import Teritorija.Lokacija;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class IstorijaKretanjaController implements Initializable {


    public JFXListView<IstorijaKretanja> kretanjaList;
    public static boolean update;
    public ObservableList<IstorijaKretanja> listItems = FXCollections.observableArrayList();
    public ArrayList<IstorijaKretanja> istorijaKretanjaArray= new ArrayList<>();
    public Pane infoPane;
    public TextArea staniceTextArea;
    public TextArea tackeTextArea;
    public Label ukupnoVrijemeLabel;
    public static FileHandler handler;

    static{
        try {
            handler=new FileHandler(Konstanta.logFolder+ File.separator+"IstorijaKretanjaController.log");
            Logger.getLogger(IstorijaKretanjaController.class.getName()).addHandler(handler);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        update=true;
        setCellFactory();
        kretanjaList.setItems(listItems);
        setIstorijaKretanjaListener();
        tackeTextArea.setWrapText(true);

    }
    public void userChoose(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount()==2) {
            Platform.runLater(()->{
                infoPane.setVisible(true);
                IstorijaKretanja istorijaKretanja= kretanjaList.getSelectionModel().getSelectedItem();
                staniceTextArea.setText("");
                tackeTextArea.setText("");
                if(istorijaKretanja==null)
                    return;
                for (int i = 0; i <istorijaKretanja.getPosjeceneStanice().size() ; i++) {
                    staniceTextArea.appendText(istorijaKretanja.getPosjeceneStanice().get(i)+" ("+String.format("%.2f",istorijaKretanja.getVremenaPosjete().get(i))+") ");
                }
                for (Lokacija lokacija:istorijaKretanja.getLokacije()) {
                    tackeTextArea.appendText(lokacija.toString()+", ");
                }
                ukupnoVrijemeLabel.setText(String.format("%.4f",istorijaKretanja.getVremenaPosjete().get(istorijaKretanja.getVremenaPosjete().size()-1))+" s");
            });

        }
    }
    private void setCellFactory(){
        kretanjaList.setCellFactory(cell -> new ListCell<>() {
            protected void updateItem(IstorijaKretanja istorijaKretanja, boolean empty) {
                super.updateItem(istorijaKretanja, empty);
                if (istorijaKretanja == null || empty)
                    setText(null);
                else
                    setText(istorijaKretanja.getFileName());
            }
        });
    }
    public synchronized void initListView(){
        Platform.runLater(()-> {
            listItems.clear();
            try {
                istorijaKretanjaArray = getIstorijaKretanja();
            } catch (Exception  ex) {
                Logger.getLogger(IstorijaKretanjaController.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
            }
            listItems.addAll(istorijaKretanjaArray);
            kretanjaList.refresh();
        });
    }
    private void setIstorijaKretanjaListener(){
        new Thread(()-> {
            try {
                while(update) {
                    initListView();
                    Thread.sleep(3000);
                }
            } catch ( InterruptedException ex) {
                Logger.getLogger(IstorijaKretanjaController.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
            }
        }).start();
    }
    private synchronized ArrayList<IstorijaKretanja> getIstorijaKretanja()  {
        istorijaKretanjaArray.clear();
        IstorijaKretanja istorijaKretanja;
        ArrayList<IstorijaKretanja> istorijeKretanja = new ArrayList<>();
        File[] tmp = new File(Konstanta.kretanjaFolder).listFiles();

        assert tmp != null;
        for(File x : tmp){
            //System.out.println(x.getName());
            istorijaKretanja=(IstorijaKretanja.deserializuj(x.toString()));
            istorijeKretanja.add(istorijaKretanja);
        }
        return istorijeKretanja;
    }
}

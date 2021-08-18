package Teritorija;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;



public class Element extends StackPane {

    int sirinaPolja;
    int visinaPolja;
    public Element(String imagePath) {
        sirinaPolja=20;
        visinaPolja=20;
        ImageView img = new ImageView();
        img.setImage(new Image(imagePath,sirinaPolja,visinaPolja,false,false));
        setVisible(false);
        getChildren().addAll( img);
    }

    public void translirajXoY(double X,double Y){
        Platform.runLater(()->{
            setVisible(true);
            setTranslateX(X*sirinaPolja);
            setTranslateY(Y*visinaPolja);
        });

    }
}

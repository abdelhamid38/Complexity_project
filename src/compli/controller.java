package compli;

import com.jfoenix.controls.JFXButton;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javax.swing.JOptionPane;

public class controller implements Initializable {

     @FXML
    private AnchorPane parent;

    @FXML
    private FlowPane flowpane;

    @FXML
    private JFXButton search;

    @FXML
    private TextField Search;

    @FXML
    private JFXButton remove;

    private double xOffset = 0;
    private double yOffset = 0;

    Calendar m_stop = new GregorianCalendar();
    Calendar m_start = new GregorianCalendar();

    private ImageView iv;
    private FileReader fr;
    private BufferedReader br;

    public ObservableList<String> linkfile = FXCollections.observableArrayList();
    public ObservableList<String> image = FXCollections.observableArrayList();
    public ObservableList<String> imagedwn = FXCollections.observableArrayList();

    File f = new File("C:\\Users\\hamid\\Documents\\NetBeansProjects\\Compli\\src\\complexity");
    File[] list = f.listFiles();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

// TODO exite of program
        makeStageDrageable();

        remove.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler -> {
            Platform.exit();
        });

//TODO list all file and picture in observable list 
        for (File str : list) {
            if (!(str.getName().contains(".txt"))) {
               
                image.add("/complexity/" + str.getName());
            } else {
                linkfile.add("C:\\Users\\hamid\\Documents\\NetBeansProjects\\Compli\\src\\complexity\\" + str.getName());

            }
        }
        

    }

    public void start() {
        m_start.setTime(new Date());
    }

    public void stop() {
        m_stop.setTime(new Date());
    }

    public void search() {

            flowpane.getChildren().clear();
            try {

//TODO look at the pictures that we searche befor and show and download the pictures  
                int g = 0;
                int b = 1;
                String content = Search.getText().toLowerCase();

                for (String n : linkfile) {

                    fr = new FileReader(n);
                    br = new BufferedReader(fr);
                    String m;
                    while ((m = br.readLine()) != null) {
                        m = m.toLowerCase();
                        if (m.equals(content)) {
                            afficher(g, b);
                            b++;
                            break;
                        }
                    }
                    g++;
                }

                g = 0;
                for (String n : linkfile) {

                    fr = new FileReader(n);
                    br = new BufferedReader(fr);
                    String m;
                    while ((m = br.readLine()) != null) {
                        m = m.toLowerCase();
                        if ((m.endsWith("s") && (m.subSequence(0, m.length() - 1).equals(content))) || (content.endsWith("s") && (content.subSequence(0, content.length() - 1).equals(m)))) {
                            afficher(g, b);
                            b++;
                            break;
                        }
                    }
                    g++;
                }
                g = 0;
                for (String n : linkfile) {

                    fr = new FileReader(n);
                    br = new BufferedReader(fr);
                    String m;
                    while ((m = br.readLine()) != null) {
                        m = m.toLowerCase();
                        if (((m.endsWith(content) && m.length() > content.length() + 1) && content.length() >= 3 || (content.length() > 2 && content.endsWith(m) && content.length() > m.length() + 1)) && (cherch(imagedwn, image.get(g)))) {
                            afficher(g, b);
                            b++;
                            break;
                        }
                    }
                    g++;
                    
                }
                content = null;
                stop();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            
           
        
    }

    public boolean cherch(ObservableList<String> g, String s) {
        Boolean v = true;
        int i = 0;
        for (String string : g) {
            if (s.equals(g.get(i))) {
                v = false;
            }
            i++;
        }
        return v;
    }

    public void afficher(int x, int y) {
        flowpane.setHgap(10);
        flowpane.setVgap(10);
        Label l;
        if(y<10) l = new Label("0" + y + "");
        else l = new Label( y + "");
        l.setFont(new Font(30));
        l.setBackground(new Background(new BackgroundFill(Color.valueOf("#0746A6"), CornerRadii.EMPTY, Insets.EMPTY)));
        l.setPrefWidth(189);
        l.setTextFill(Color.WHITE);
        l.setAlignment(Pos.CENTER);
        iv = new ImageView(image.get(x));
        iv.setFitWidth(189);
        iv.setFitHeight(180);
        VBox vb = new VBox(l, iv);
        vb.setAlignment(Pos.CENTER);
        flowpane.getChildren().add(vb);
        imagedwn.add(image.get(x));
    }

//TODO move the curent stage    
    private void makeStageDrageable() {
        parent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        parent.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                compli.Compli.stage.setX(event.getScreenX() - xOffset);
                compli.Compli.stage.setY(event.getScreenY() - yOffset);
                compli.Compli.stage.setOpacity(0.7f);

            }
        });

        parent.setOnDragDone((e) -> {
            compli.Compli.stage.setOpacity(1.0f);
        });
        parent.setOnMouseReleased((e) -> {
            compli.Compli.stage.setOpacity(1.0f);
        });

    }
}

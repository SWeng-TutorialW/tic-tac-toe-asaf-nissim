package il.cshaifasweng.OCSFMediatorExample.client;

/**
 * Sample Skeleton for 'game.fxml' Controller Class
 */



import il.cshaifasweng.OCSFMediatorExample.client.ocsf.turnEvent;
import il.cshaifasweng.OCSFMediatorExample.client.ocsf.winnerEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

public class Game {
    private Button[] buttons;

    @FXML
    private Button seven_7; // Value injected by FXMLLoader

    @FXML // fx:id="first_line"
    private HBox first_line; // Value injected by FXMLLoader

    @FXML
    private Button four_4; // Value injected by FXMLLoader

    @FXML
    private Button three_3; // Value injected by FXMLLoader

    @FXML
    private Button eight_8; // Value injected by FXMLLoader

    @FXML
    private Button zero_0; // Value injected by FXMLLoader

    @FXML // fx:id="paste_symbol"
    private Label paste_symbol; // Value injected by FXMLLoader

    @FXML // fx:id="paste_turn"
    private Label paste_turn; // Value injected by FXMLLoader

    @FXML // fx:id="second_line"
    private HBox second_line; // Value injected by FXMLLoader

    @FXML
    private Button six_6; // Value injected by FXMLLoader

    @FXML
    private Button five_5; // Value injected by FXMLLoader

    @FXML // fx:id="third_line"
    private HBox third_line; // Value injected by FXMLLoader

    @FXML
    private Button two_2; // Value injected by FXMLLoader

    @FXML
    private Button one_1; // Value injected by FXMLLoader
    @FXML
    private Label your;

    @FXML
    private Label current;
    @FXML
    private Label winner_label;

    @Subscribe
    public void finish(winnerEvent event) {
        if (!event.getWinner().equals("draw"))
            Platform.runLater(() -> {
                third_line.setVisible(false);
                second_line.setVisible(false);
                first_line.setVisible(false);
                paste_symbol.setVisible(false);
                paste_turn.setVisible(false);
                your.setVisible(false);
                current.setVisible(false);
                winner_label.setText("The Winner is: " + event.getWinner());
                winner_label.setVisible(true);
            });
        else {
            Platform.runLater(() -> {
                third_line.setVisible(false);
                second_line.setVisible(false);
                first_line.setVisible(false);
                paste_symbol.setVisible(false);
                paste_turn.setVisible(false);
                your.setVisible(false);
                current.setVisible(false);
                winner_label.setText("It's a tie! game over.");
                winner_label.setVisible(true);
            });
        }
    }

    @Subscribe
    public void printturn(turnEvent event) {
        Platform.runLater(() -> {
            String symbol = event.getturn();
            String position = event.getposition();
            int num = Integer.parseInt(position);
            System.out.println(num);
            System.out.println("asaf_");
            buttons[num].setText(symbol);
            if(symbol.equals("O")) {
                paste_turn.setText("X");
            } else {
                paste_turn.setText("O");
            }
            try {
                SimpleClient.getClient().sendToServer("check-win_"+symbol+"_"+position);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }




    public void setSymbolAndFirst(String symbol, int first) {
        paste_symbol.setText(symbol);
        if(first == 1) {
            paste_turn.setText(symbol);
        }
        else
        {
            if(symbol.equals("X"))
                paste_turn.setText("O");
            else
                paste_turn.setText("X");
        }
    }
    @FXML
    void Press(ActionEvent event)
    {
        String player =paste_symbol.getText();
        String currentturn = paste_turn.getText();
        Button btn = (Button) event.getSource();
        String id = btn.getId();
        String[] parts = id.split("_");
        String message= player+"_"+currentturn+"_"+parts[1]+"_turn";
        try {
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    @FXML
    void sendWarning(ActionEvent event) {
        try {
            SimpleClient.getClient().sendToServer("#warning");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @FXML
    void initialize()
    {
        System.out.println("INITIALIZED GAME CONTROLLER");
        EventBus.getDefault().register(this);
        winner_label.setVisible(false);
        Platform.runLater(() -> {
            buttons = new Button[]{
                    zero_0,
                    one_1,
                    two_2,
                    three_3,
                    four_4,
                    five_5,
                    six_6,
                    seven_7,
                    eight_8
            };
        });
    }


}


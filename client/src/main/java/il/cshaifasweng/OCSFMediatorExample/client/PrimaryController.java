package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.PlayerCountEvent;
import il.cshaifasweng.OCSFMediatorExample.client.ocsf.PlayerSymbolEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Sample Skeleton for 'primary.fxml' Controller Class
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static il.cshaifasweng.OCSFMediatorExample.client.App.*;


public class PrimaryController  {


	@FXML // fx:id="IP"
	private TextField IP; // Value injected by FXMLLoader

	@FXML // fx:id="Port"
	private AnchorPane Port; // Value injected by FXMLLoader

	@FXML // fx:id="connect"
	private Button connect; // Value injected by FXMLLoader

	@FXML // fx:id="ip_wrong"
	private Label ip_wrong; // Value injected by FXMLLoader

	@FXML // fx:id="port_box"
	private TextField port_box; // Value injected by FXMLLoader

	@FXML // fx:id="port_wrong"
	private Label port_wrong; // Value injected by FXMLLoader

	@FXML // fx:id="ss_1"
	private Label ss_1; // Value injected by FXMLLoader

	@FXML // fx:id="ss_2"
	private Label ss_2; // Value injected by FXMLLoader
	@FXML
	private Label wait_label;


	@FXML
	void Connect(ActionEvent event)
	{
		String ip = IP.getText().trim();
		String portText = port_box.getText().trim();
		int port = Integer.parseInt(portText);
		SimpleClient.setClient(ip, port);

		try {
			SimpleClient.getClient().openConnection();
			SimpleClient.getClient().sendToServer("add client");
			SimpleClient.getClient().sendToServer("how many players");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Subscribe
	public void onPlayerCountReceived(PlayerCountEvent event) {
		int count = event.getCount();
			if (count == 1)
			{
				try {
					setRoot("wait_scene");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if (count == 2)
			{
				try {
					SimpleClient.getClient().sendToServer("ask for X/0");

				} catch (IOException e) {
					e.printStackTrace();
				}


			}

	}
	@Subscribe
	public void onPlayerSymbolReceived(PlayerSymbolEvent event) {
		String symbol = event.getSymbol();
		int first = event.getFirst();
		System.out.println("Received PlayerSymbolEvent! Symbol: " + symbol + ", First: " + first);

		Platform.runLater(() -> {
			try {
				FXMLLoader loader = new FXMLLoader(App.class.getResource("game.fxml"));
				Parent root = loader.load();

				Game controller = loader.getController();
				controller.setSymbolAndFirst(symbol, first);


				App.getScene().setRoot(root);

			} catch (IOException e) {
				e.printStackTrace();
			}
		});
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
		EventBus.getDefault().register(this);
		try {
			SimpleClient.getClient().sendToServer("add client");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}







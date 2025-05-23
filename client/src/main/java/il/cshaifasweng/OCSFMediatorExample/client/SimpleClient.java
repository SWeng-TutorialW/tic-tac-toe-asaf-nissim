package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.*;
import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.entities.Warning;

public class SimpleClient extends AbstractClient {
	
	public static SimpleClient client = null;
	public static int port = 3000;
	public static String ip = "localhost";


	private SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		if (msg.getClass().equals(Warning.class)) {
			EventBus.getDefault().post(new WarningEvent((Warning) msg));
		}
		else{
			String message = msg.toString();
			System.out.println(message);
			if (message.startsWith("player count"))
			{
				try {
					String[] parts = message.split(" ");
					int count = Integer.parseInt(parts[2]);
					EventBus.getDefault().post(new PlayerCountEvent(count));
				} catch (Exception e) {
					System.out.println("Failed to parse player count");
					e.printStackTrace();
				}
			}
			if(message.startsWith("1legal"))
			{
			EventBus.getDefault().post(new turnEvent(message));
			}
			if(message.startsWith("there-is-winner"))
			{
				EventBus.getDefault().post(new winnerEvent(message));
			}

			if (message.contains("X") || message.contains("O")) {
				EventBus.getDefault().post(new PlayerSymbolEvent(message));
			}
		}
	}
	public static void setClient(String host, int port) {
		client = new SimpleClient(host, port);
	}

	public static SimpleClient getClient() {
		if (client == null) {
			System.out.println(ip);
			System.out.println(port);
			client = new SimpleClient(ip, port);
		}
		return client;
	}

}

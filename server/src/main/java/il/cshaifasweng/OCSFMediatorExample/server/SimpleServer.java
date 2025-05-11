package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.util.ArrayList;

import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;

public class SimpleServer extends AbstractServer {
	private static ArrayList<SubscribedClient> SubscribersList = new ArrayList<>();
	private static int[] boardOccupied = {0, 0, 0, 0, 0, 0, 0, 0, 0};




	public SimpleServer(int port) {
		super(port);


		
	}


	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client)
	{
		String msgString = msg.toString();
		if (msgString.startsWith("#warning")) {
			Warning warning = new Warning("Warning from server!");
			try {
				client.sendToClient(warning);
				System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (msgString.contains("turn"))
		{
			String[] fields = msgString.split("_");
			String player = fields[0];
			String currentTurn = fields[1];
			String position = fields[2];
			int turn = Integer.parseInt(position);
			System.out.println(position);
			if(!player.equals(currentTurn))
			{
				try {
					Warning warning = new Warning("Not your turn!,please wait.");
					client.sendToClient(warning);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
			if(boardOccupied[turn] != 0)
			{
				{
					try {

						Warning warning = new Warning("You clicked an already occupied cell!");
						client.sendToClient(warning);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return;
			}

			    System.out.println("cell"+turn+"taken");
				if(currentTurn.equals("O"))
				{
					boardOccupied[turn]=2;
					System.out.println("cell"+turn+"taken");

				}
				else
				{
					boardOccupied[turn]=1;
					System.out.println("cell"+turn+"taken");
				}
				sendToAllClients("1legal_" + currentTurn + "_" + position);

		}
		else if(msgString.startsWith("check-win"))
		{
			String[] fields = msgString.split("_");
			String player = fields[1];
			String position = fields[2];
			int turn = Integer.parseInt(position);
			int checkWin=0;
			if(player.equals("O"))
			{
				checkWin=2;
			}
			else
			{
				checkWin=1;
			}
			if(turn<=2)
			{
				if(boardOccupied[0]==checkWin && boardOccupied[1]==checkWin && boardOccupied[2]==checkWin)
				{
					sendToAllClients("there-is-winner_"+player);
				}
				if(turn==0)
				{
					if(boardOccupied[0]==checkWin && boardOccupied[3]==checkWin && boardOccupied[6]==checkWin)
					 sendToAllClients("there-is-winner_"+player);
					if(boardOccupied[0]==checkWin && boardOccupied[4]==checkWin && boardOccupied[8]==checkWin)
						sendToAllClients("there-is-winner_"+player);
				}
				if(turn==1)
				{
					if(boardOccupied[1]==checkWin && boardOccupied[4]==checkWin && boardOccupied[7]==checkWin)
						sendToAllClients("there-is-winner_"+player);
				}
				if(turn==2)
				{
					if(boardOccupied[2]==checkWin && boardOccupied[5]==checkWin && boardOccupied[8]==checkWin)
						sendToAllClients("there-is-winner_"+player);
					if(boardOccupied[2]==checkWin && boardOccupied[4]==checkWin && boardOccupied[6]==checkWin)
						sendToAllClients("there-is-winner_"+player);
				}

			}
			if(2<turn && turn<6)
			{
				if(boardOccupied[3]==checkWin && boardOccupied[4]==checkWin && boardOccupied[5]==checkWin)
					sendToAllClients("there-is-winner_"+player);
				if(turn==3)
				{
					if(boardOccupied[0]==checkWin && boardOccupied[3]==checkWin && boardOccupied[6]==checkWin)
						sendToAllClients("there-is-winner_"+player);
				}
				if(turn==4)
				{
					if(boardOccupied[1]==checkWin && boardOccupied[4]==checkWin && boardOccupied[7]==checkWin)
						sendToAllClients("there-is-winner_"+player);
					if(boardOccupied[0]==checkWin && boardOccupied[4]==checkWin && boardOccupied[8]==checkWin)
						sendToAllClients("there-is-winner_"+player);
					if(boardOccupied[2]==checkWin && boardOccupied[4]==checkWin && boardOccupied[6]==checkWin)
						sendToAllClients("there-is-winner_"+player);
				}
				if(turn==5)
				{
					if(boardOccupied[2]==checkWin && boardOccupied[5]==checkWin && boardOccupied[8]==checkWin)
						sendToAllClients("there-is-winner_"+player);

				}

			}
			if(turn>=6)
			{
				if(boardOccupied[6]==checkWin && boardOccupied[7]==checkWin && boardOccupied[8]==checkWin)
					sendToAllClients("there-is-winner_"+player);
				if(turn==6)
				{
					if(boardOccupied[0]==checkWin && boardOccupied[3]==checkWin && boardOccupied[6]==checkWin)
						sendToAllClients("there-is-winner_"+player);
					if(boardOccupied[6]==checkWin && boardOccupied[4]==checkWin && boardOccupied[2]==checkWin)
						sendToAllClients("there-is-winner_"+player);
				}
				if(turn==7)
				{
					if(boardOccupied[1]==checkWin && boardOccupied[4]==checkWin && boardOccupied[7]==checkWin)
						sendToAllClients("there-is-winner_"+player);

				}
				if(turn==8)
				{
					if(boardOccupied[2]==checkWin && boardOccupied[5]==checkWin && boardOccupied[8]==checkWin)
						sendToAllClients("there-is-winner_"+player);
					if(boardOccupied[0]==checkWin && boardOccupied[4]==checkWin && boardOccupied[8]==checkWin)
						sendToAllClients("there-is-winner_"+player);

				}
			}
			boolean hasZero = false;
			for (int i = 0; i < boardOccupied.length; i++)
			{
				if (boardOccupied[i] == 0)
				{
					hasZero = true;
					break;
				}

			}
			if(!hasZero)
			{
				sendToAllClients("there-is-winner_draw");
			}



		}
		else if(msgString.startsWith("add client")){
			SubscribedClient connection = new SubscribedClient(client);
			SubscribersList.add(connection);
			try {
				client.sendToClient("client added successfully");
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		if (msg.equals("how many players"))
		{
			try {
				int count = SubscribersList.size();
				client.sendToClient("player count " + count);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		if (msg.equals("ask for X/0")) {
			try {
					ConnectionToClient client1 = SubscribersList.get(0).getClient();
					ConnectionToClient client2 = SubscribersList.get(1).getClient();
					boolean client1GetsX = Math.random() < 0.5;
					boolean client1first = Math.random() < 0.5;
					if(client1first)
					{
						if (client1GetsX) {
							client1.sendToClient("X 1");
							client2.sendToClient("O 0");
						} else {
							client1.sendToClient("O 1");
							client2.sendToClient("X 0");
						}
					}
					else
					{
						if (client1GetsX) {
							client1.sendToClient("X 0");
							client2.sendToClient("O 1");
						} else {
							client1.sendToClient("O 0");
							client2.sendToClient("X 1");
						}
					}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}


		else if(msgString.startsWith("remove client")){
			if(!SubscribersList.isEmpty()){
				for(SubscribedClient subscribedClient: SubscribersList){
					if(subscribedClient.getClient().equals(client)){
						SubscribersList.remove(subscribedClient);
						break;
					}
				}
			}
		}
	}
	public void sendToAllClients(String message) {
		try {
			for (SubscribedClient subscribedClient : SubscribersList) {
				subscribedClient.getClient().sendToClient(message);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}

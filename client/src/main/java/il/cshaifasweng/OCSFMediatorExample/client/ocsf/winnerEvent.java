package il.cshaifasweng.OCSFMediatorExample.client.ocsf;


public class winnerEvent {

    String winner;



    public winnerEvent(String message) {
        String[] parts = message.split("_");
        winner = parts[1];


    }
    public String getWinner() {
        return winner;
    }





}
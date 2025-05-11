package il.cshaifasweng.OCSFMediatorExample.client.ocsf;

public class turnEvent {
    String current_turn;
    String position;



    public turnEvent(String message) {
        String[] parts = message.split("_", 3);
        current_turn = parts[1];
        position = parts[2];

    }

    public String getturn() {
        return current_turn;

    }
    public String getposition() {
        return position;


    }
}
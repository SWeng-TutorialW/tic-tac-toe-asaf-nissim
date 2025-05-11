package il.cshaifasweng.OCSFMediatorExample.client.ocsf;

public class PlayerSymbolEvent {
    private String symbol;
    int first ; //zero for second one for first


    public PlayerSymbolEvent(String message) {
        String[] parts = message.split(" ", 2);
        symbol = parts[0];
        int num = Integer.parseInt(parts[1]);
        first = num;
    }

    public String getSymbol() {
        return symbol;
    }
    public int getFirst() {
        return first;
    }
}
package il.cshaifasweng.OCSFMediatorExample.client.ocsf;

public class PlayerCountEvent
{
    private int count;

    public PlayerCountEvent(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}

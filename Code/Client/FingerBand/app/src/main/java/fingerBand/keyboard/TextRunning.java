package fingerBand.keyboard;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 2015/6/23.
 */
public class TextRunning implements Runnable {
    private ArrayList<TextView> textViews = new ArrayList<TextView>();

    public void run() {
        while(true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            android.os.Message msg = new android.os.Message();
            Bundle bundle = new Bundle();
            bundle.putString("cmd", "moving");
            msg.setData(bundle);
            KeyboardUIHandler.getInstance().sendMessage(msg);
        }
    }

}

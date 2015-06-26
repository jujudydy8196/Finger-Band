package fingerBand.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;

import fingerBand.R;

/**
 * Created by user on 2015/6/23.
 */
public class UIHandler extends Handler {
    @Override
    public void handleMessage(Message msg) {
        // TODO Auto-generated method stub
        super.handleMessage(msg);
        Bundle bundle = msg.getData();
        String cmd = bundle.getString("cmd");
        if(cmd.equals( "sessionEnd")){
            MainActivity.getInstance().goInvitation();
            ((Button)MainActivity.getInstance().findViewById(R.id.createSessionBtn)).setText("C");
        }
        else if(cmd.equals("playRecord")){
            MainActivity.getInstance().goPlayRecord();
        }
    }
}

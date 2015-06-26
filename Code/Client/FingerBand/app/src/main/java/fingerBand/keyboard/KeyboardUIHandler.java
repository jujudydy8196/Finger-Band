package fingerBand.keyboard;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 2015/6/23.
 */
public class KeyboardUIHandler extends Handler{
    private static KeyboardUIHandler keyboardUIHandler = new KeyboardUIHandler();
    public static KeyboardUIHandler getInstance(){
        return keyboardUIHandler;
    }
    private ArrayList<TextView> textViews = new ArrayList<TextView>();
    @Override
    public void handleMessage(Message msg) {
        // TODO Auto-generated method stub
        super.handleMessage(msg);
        Bundle bundle = msg.getData();
        String cmd = bundle.getString("cmd");
        if(cmd== "moving"){
            for(TextView textView : textViews){
                textView.setY(textView.getY()+10);
            }
        }
    }
    public void addTextView(TextView textView){
        if(!textViews.contains(textView))
            textViews.add(textView);
    }

    public void removeTextView(TextView textView){
        if(textViews.contains(textView))
            textViews.remove(textView);
    }
}

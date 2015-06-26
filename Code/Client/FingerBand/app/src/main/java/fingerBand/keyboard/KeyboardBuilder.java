package fingerBand.keyboard;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fingerBand.InstrumentPlayer.PitchPlayer;
import fingerBand.R;
import fingerBand.activities.MainActivity;
import fingerBand.session.Session;

/**
 * Created by user on 2015/6/22.
 */
public class KeyboardBuilder {
    private static KeyboardBuilder keyboardBuilder = new KeyboardBuilder(MainActivity.getInstance());
    private Activity activity;
    private FrameLayout keyBoardLayout;
    private KeyboardUIHandler keyboardUIHandler;
    private static Thread thread ;
    ExecutorService executor = Executors.newFixedThreadPool(10);

    public static KeyboardBuilder getInstance(){
        return  keyboardBuilder;
    }
    private KeyboardBuilder(Activity activity){
        this.activity = activity;
        thread =new Thread(new TextRunning());
        thread.start();
    }

    public View getKeyBoard(){


        keyboardUIHandler = KeyboardUIHandler.getInstance();
        LayoutInflater inflater = LayoutInflater.from(activity);
        keyBoardLayout = (FrameLayout)inflater.inflate(R.layout.keyboard_layout, null);

        LinearLayout keyBoardList1Layout = (LinearLayout) keyBoardLayout.findViewById(R.id.keyboardList1_layout);
        loadKeyboard(keyBoardList1Layout);
        LinearLayout keyBoardList2Layout = (LinearLayout) keyBoardLayout.findViewById(R.id.keyboardList2_layout);
        loadKeyboard(keyBoardList2Layout);


//        textView.setRotationX(30);


        return keyBoardLayout;
    }

    private void loadKeyboard(LinearLayout keyBoardListLayout){
        int Xmargin = 200;
        int Ymargin = 100;
        for (int i = 0 ; i < 56 ; i++) {
            FrameLayout frameLayout = new FrameLayout(activity);
            FrameLayout.LayoutParams frameLayoutParams = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    150
            );
            frameLayout.setLayoutParams(frameLayoutParams);
            frameLayout.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.boarder));
            int mod = i % 7 ;
            if(mod  == 0  || mod == 3){

                FrameLayout frameLayout_black = new FrameLayout(activity);
                frameLayout.addView(frameLayout_black);
                frameLayout_black.setX(Xmargin);
                frameLayout_black.setY(Ymargin);
                frameLayout_black.setBackgroundColor(activity.getResources().getColor(R.color.black));
                if(mod == 0 ){
                    frameLayout.setOnTouchListener(new KeyOnTouchListener(i/7*12+4,"Do"));
                    frameLayout_black.setOnTouchListener(new KeyOnTouchListener(i/7*12+5,"Do#"));
                }
                else if(mod == 3){
                    frameLayout.setOnTouchListener(new KeyOnTouchListener(i/7*12+9,"Fa"));
                    frameLayout_black.setOnTouchListener(new KeyOnTouchListener(i/7*12+10,"Fa#"));
                }
            }
            else if(mod  == 1  || mod == 4 || mod == 5){

                FrameLayout frameLayout_BlackDown = new FrameLayout(activity);
                frameLayout.addView(frameLayout_BlackDown);
                frameLayout_BlackDown.setX(Xmargin);
                frameLayout_BlackDown.setY(Ymargin);
                frameLayout_BlackDown.setBackgroundColor(activity.getResources().getColor(R.color.black));

                FrameLayout frameLayout_BlackUp = new FrameLayout(activity);
                frameLayout.addView(frameLayout_BlackUp);
                frameLayout_BlackUp.setX(Xmargin);
                frameLayout_BlackUp.setY(-Ymargin);
                frameLayout_BlackUp.setBackgroundColor(activity.getResources().getColor(R.color.black));

                if(mod == 1) {
                    frameLayout_BlackUp.setOnTouchListener(new KeyOnTouchListener(i/7*12+ 5,"Do#"));
                    frameLayout.setOnTouchListener(new KeyOnTouchListener(i/7*12+ 6,"Re"));
                    frameLayout_BlackDown.setOnTouchListener(new KeyOnTouchListener(i/7*12+ 7,"Re#"));
                }
                else if(mod == 4 ){
                    frameLayout_BlackUp.setOnTouchListener(new KeyOnTouchListener(i/7*12+ 10,"Fa#"));
                    frameLayout.setOnTouchListener(new KeyOnTouchListener(i/7*12+ 11,"Sol"));
                    frameLayout_BlackDown.setOnTouchListener(new KeyOnTouchListener(i/7*12+ 12,"Sol#"));
                }
                else if(mod == 5){
                    frameLayout_BlackUp.setOnTouchListener(new KeyOnTouchListener(i/7*12+ 12,"Sol#"));
                    frameLayout.setOnTouchListener(new KeyOnTouchListener(i/7*12+ 13,"La"));
                    frameLayout_BlackDown.setOnTouchListener(new KeyOnTouchListener(i/7*12+ 14,"La#"));
                }
            }
            else if(mod == 2 || mod == 6){
                FrameLayout frameLayout_BlackUp = new FrameLayout(activity);
                frameLayout.addView(frameLayout_BlackUp);
                frameLayout_BlackUp.setX(Xmargin);
                frameLayout_BlackUp.setY(-Ymargin);
                frameLayout_BlackUp.setBackgroundColor(activity.getResources().getColor(R.color.black));
                if (mod == 2){
                    frameLayout_BlackUp.setOnTouchListener(new KeyOnTouchListener(i/7*12+ 7,"Re#"));
                    frameLayout.setOnTouchListener(new KeyOnTouchListener(i/7*12+ 8,"Mi"));
                }
                else if(mod == 6){

                    frameLayout_BlackUp.setOnTouchListener(new KeyOnTouchListener(i/7*12+ 14,"La#"));
                    frameLayout.setOnTouchListener(new KeyOnTouchListener(i/7*12+ 15,"Si"));
                }
            }

            keyBoardListLayout.addView(frameLayout);
        }
    }
    private class KeyOnTouchListener implements View.OnTouchListener {
        private int freq;
        private String note;
        private PitchPlayer pitchPlayer;
        private Thread thread;
        private ArrayList<TextView> textViews= new ArrayList<TextView>();
        public KeyOnTouchListener(int freq,String note){
            this.note = note;
            this.freq = freq;
        }
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                pitchPlayer = PitchPlayer.obtain();
                pitchPlayer.setEvent("Start");
                pitchPlayer.convertKeyToFrequency_piano(freq);
                executor.execute(pitchPlayer);

//                thread = new Thread(pitchPlayer);
//                thread.run();
                if(Session.getInstance().getSession_id()!= -1)
                    Session.getInstance().sendSound(freq,"Start");

                int randomNum = (int )(Math.random()*3);
                for(int i = 0 ; i <= randomNum ; i ++) {
                    TextView textView;
                    textView = new TextView(activity);
                    keyBoardLayout.addView(textView);
                    textView.setText(note);
                    int randomColor = (int) (Math.random() * 3);
                    if (randomColor == 0)
                        textView.setTextColor(activity.getResources().getColor(R.color.red));
                    else if (randomColor == 1)
                        textView.setTextColor(activity.getResources().getColor(R.color.blue3));
                    else if (randomColor == 2)
                        textView.setTextColor(activity.getResources().getColor(R.color.green));
                    textView.setTextSize((float) Math.random() * 15 + 20);
                    textView.setPivotX(0);
                    textView.setPivotY(0);
                    textView.setX((float) Math.random() * 500 + 100);
                    textView.setY((float) Math.random() * 900);
                    textView.setRotation(90);
                    keyboardUIHandler.addTextView(textView);
                    textViews.add(textView);
                    Log.v("Key","X:"+textView.getX()+" Y:"+textView.getY()+"  note:"+note +" TextSize "+textView.getTextSize()  );
                }


            } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                for(TextView textView : textViews) {
                    keyBoardLayout.removeView(textView);
                    keyboardUIHandler.removeTextView(textView);
                }
                Log.v("Key", "freq done");
                pitchPlayer.setEvent("Stop");
                executor.execute(pitchPlayer);
                pitchPlayer.recycle();
//                thread.run();
                pitchPlayer = null;
                thread = null;

                if(Session.getInstance().getSession_id()!= -1)
                    Session.getInstance().sendSound(freq,"Stop");
            }
            else{
                Log.v("Key", "action:" +action);
            }
            return true;
        }
    }
}

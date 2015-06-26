package fingerBand.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import fingerBand.R;
import fingerBand.session.Session;

public class OptionManager {
	private MainActivity mainActivity;
	
	public OptionManager(MainActivity mainActivity){
		this.mainActivity = mainActivity;
	}
	
	public View getOption(){
		LayoutInflater inflater = LayoutInflater.from(mainActivity);
		LinearLayout optionLayout = (LinearLayout)inflater.inflate(R.layout.fragment_option, null);
		
		((Button)optionLayout.findViewById(R.id.createSessionBtn)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
                Button btn= (Button) v;
                if(btn.getText().equals("C")){
                    mainActivity.createSession();
                }
                else if(btn.getText().equals("L")){
                    mainActivity.leaveSession();
                }

			}
		});
		
		((Button)optionLayout.findViewById(R.id.invitationBtn)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				mainActivity.goInvitation();
			}
		});
		
		((Button)optionLayout.findViewById(R.id.inviteFriendBtn)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
                if(Session.getInstance().getSession_id()!= -1)
				    mainActivity.goInviteList();
                else {
                    TextView textView = new TextView(mainActivity);
                    textView.setText("You can't invite friend\nSince you aren't in Session");
                    textView.setTextSize(20);
                    textView.setGravity(Gravity.CENTER);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mainActivity);
                    alertDialogBuilder.setView(textView);
                    // set dialog message
                    alertDialogBuilder.setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            MainActivity.getInstance().createSession();
                                        }
                                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
			}
		});
		
		((Button)optionLayout.findViewById(R.id.viewRecordBtn)).setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                mainActivity.goRecordList();
            }
        });

        ((Button)optionLayout.findViewById(R.id.keyboardBtn)).setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                mainActivity.goKeyBoard();
            }
        });
		return optionLayout;
	}
}

package fingerBand.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import fingerBand.AndroidApplication;
import fingerBand.R;
import fingerBand.invite.InviteManager;
import fingerBand.keyboard.KeyboardBuilder;
import fingerBand.record.RecordManager;
import fingerBand.record.RecordPlayer;
import fingerBand.session.Session;

public class MainActivity extends ActionBarActivity {
	
	private static MainActivity mainActivity;
	private String state = new String();
	private LinearLayout bodyLayout ;
	private Session session;
    public UIHandler UiHandler;

	public static MainActivity getInstance(){
		return mainActivity;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		OptionManager optionManager = new OptionManager(this);
		((LinearLayout)findViewById(R.id.main_option_layout)).addView(optionManager.getOption());
		
		this.mainActivity = this;
		this.session = Session.getInstance();
		this.bodyLayout = (LinearLayout) findViewById(R.id.main_body_layout);
		this.UiHandler = new UIHandler();
		goInvitation();
		
	}

    @Override
    public void onBackPressed(){
        Intent i= new Intent(Intent.ACTION_MAIN);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void goInvitation(){
		if(!state.equals("Invitation")){
			bodyLayout.removeAllViews();
			bodyLayout.addView( InviteManager.getInstance().getInvitationList());
			state = "Invitation";
		}
	}
	
	protected void goInviteList(){
		if(!state.equals("Invite")){
			bodyLayout.removeAllViews();
			bodyLayout.addView( InviteManager.getInstance().getFriendList());
			state = "Invite";
		}
	}

	protected void goRecordList(){
		if(!state.equals("ViewRecord")){
			bodyLayout.removeAllViews();
			bodyLayout.addView( RecordManager.getInstance().getRecordList());
			state = "ViewRecord";
		}
	}


    public void goKeyBoard(){
        if(!state.equals("Keyboard")){
            bodyLayout.removeAllViews();
            bodyLayout.addView(  KeyboardBuilder.getInstance().getKeyBoard() );
            state = "Keyboard";
        }
    }
	protected void createSession(){
		LayoutInflater inflater = LayoutInflater.from(this);
		View createSessionLayout = inflater.inflate(R.layout.createsession_layout, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		alertDialogBuilder.setView(createSessionLayout);

		final EditText createSessionNameEditText= (EditText) createSessionLayout.findViewById(R.id.createSessionNameEditText);

		// set dialog message
		alertDialogBuilder.setCancelable(false)
			.setPositiveButton("OK",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
                    ((Button)mainActivity.findViewById(R.id.createSessionBtn)).setText("L");
			    	session.createSession(AndroidApplication.getInstance().getUser().getName(), AndroidApplication.getInstance().getUser().getId(), createSessionNameEditText.getText().toString());
			    	goKeyBoard();
			    }
			  })
			.setNegativeButton("Cancel",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
			    	dialog.cancel();
			    }
			  });

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

    protected void leaveSession() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        TextView textView = new TextView(mainActivity);
        textView.setText("Sure for Exit Session?");
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        alertDialogBuilder.setView(textView);
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Sure",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                session.leaveSession();
                                MainActivity.getInstance().goInvitation();
                                ((Button)mainActivity.findViewById(R.id.createSessionBtn)).setText("C");
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void goPlayRecord(){
        bodyLayout.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout playRecordLayout = (LinearLayout)inflater.inflate(R.layout.playrecord_layout,null);
        bodyLayout.addView(playRecordLayout);
        RecordPlayer.getInstance().play();
    }

}


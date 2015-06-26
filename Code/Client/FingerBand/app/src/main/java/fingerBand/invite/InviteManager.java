package fingerBand.invite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import fingerBand.AndroidApplication;
import fingerBand.R;
import fingerBand.User;
import fingerBand.activities.MainActivity;
import fingerBand.asynctask.LoadImage;
import fingerBand.connection.Packet;
import fingerBand.connection.ServiceHandler;
import fingerBand.session.Session;

public class InviteManager {
	private final static String tag = "InviteManager";
	private static InviteManager inviteManager = new InviteManager();
	private MainActivity mainActivity;
	private ArrayList<String> inviteFriendIDList;
	private ArrayList<User> friendList;
	private ArrayList<Invitation> invitationList;
	private ServiceHandler invitationHandler;
	
	private boolean waitRespond ;
	
	public static InviteManager getInstance(){
		return inviteManager ; 
	}
	
	private InviteManager(){
		this.mainActivity = MainActivity.getInstance();
		this.waitRespond = false;
        this.invitationHandler = AndroidApplication.getInstance().getInvitationHandler();

	}
	public View getInvitationList(){

		requestInvitationFromServer();
		waitRequest();

		LayoutInflater inflater = LayoutInflater.from(mainActivity);
        RelativeLayout invitedsessionLayout = (RelativeLayout)inflater.inflate(R.layout.invitedsession_layout, null);
		LinearLayout invitationListLayout = (LinearLayout)invitedsessionLayout.findViewById(R.id.invitationListLayout);
        loadInvitation(invitationListLayout);
        Button refreshBtn = (Button) invitedsessionLayout.findViewById(R.id.refreshBtn);
        refreshBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout invitationListLayout= (LinearLayout)((RelativeLayout)v.getParent()).findViewById(R.id.invitationListLayout);
                requestInvitationFromServer();
                waitRequest();
                invitationListLayout.removeAllViews();
                loadInvitation(invitationListLayout);
                Log.v("Refresh","click");
            }
        });


		return invitedsessionLayout;
	}

    private void loadInvitation(LinearLayout invitationListLayout){
        if(invitationList != null ) {
            Log.v("InvitationList", "" + invitationList.size());
            LayoutInflater inflater = LayoutInflater.from(mainActivity);
            for (int i = 0; i < invitationList.size(); i++) {
                Invitation invitation = invitationList.get(i);

                LinearLayout invitationLayout = (LinearLayout) inflater.inflate(R.layout.session_layout, null);
                ((Button) invitationLayout.findViewById(R.id.acceptBtn)).setOnClickListener(new InvitationOnClickListener(invitation));
                if (invitation.getSessionName() != null)
                    ((TextView) invitationLayout.findViewById(R.id.sessionName)).setText(invitation.getSessionName());
                if (invitation.getCreatorName() != null)
                    ((TextView) invitationLayout.findViewById(R.id.creatorName)).setText(invitation.getCreatorName());
                new LoadImage((ImageView) invitationLayout.findViewById(R.id.inviterImageView)).execute("https://graph.facebook.com/" + invitation.getCreatorID() + "/picture?type=normal", "100", "100");
                invitationListLayout.addView(invitationLayout);
                ((LinearLayout.LayoutParams) invitationLayout.getLayoutParams()).setMargins(10, 20, 10, 20);

            }
        }
    }
	
	private void requestInvitationFromServer(){
        Packet requestInvitationListPacket = new Packet("Request_Invitation");
        requestInvitationListPacket.addItems("FB_id", AndroidApplication.getInstance().getUser().getId());
        invitationHandler.write(requestInvitationListPacket);
        waitRespond = true;
        Log.v("requestInvitation","Send Packet");
	}
	public View getFriendList(){
		LayoutInflater inflater = LayoutInflater.from(mainActivity);
		RelativeLayout friendLayout = (RelativeLayout)inflater.inflate(R.layout.invitefriend_layout, null);
		LinearLayout friendListLayout = (LinearLayout)friendLayout.findViewById(R.id.friendListLayout);
		

		inviteFriendIDList = new ArrayList<String>();
        friendList = AndroidApplication.getInstance().getFriends();

		for(int i = 0 ; i < friendList.size() ; i++){
			User friend = friendList.get(i);

			LinearLayout friendCheckLayout = (LinearLayout) inflater.inflate(R.layout.friend_layout, null);
			((CheckBox)friendCheckLayout.findViewById(R.id.friendCheckBox)).setOnClickListener( new FriendOnClickListener(inviteFriendIDList,friend));
			if(friend.getName()!=null)
				((TextView)friendCheckLayout.findViewById(R.id.friendName)).setText(friend.getName());
			new LoadImage((ImageView)friendCheckLayout.findViewById(R.id.friendImageView)).execute("https://graph.facebook.com/"+friend.getId()+"/picture?type=normal","50","50");
			friendListLayout.addView(friendCheckLayout);
			(( LinearLayout.LayoutParams) friendCheckLayout.getLayoutParams()).setMargins(10,20,10,20);

		}
		((Button)friendLayout.findViewById(R.id.inviteBtn)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				InviteManager.getInstance().inviteFriend();
			}
		});
		return friendLayout;
	}
	
	public void inviteFriend(){

        if(inviteFriendIDList.size() >0) {
            Packet requestInvitationListPacket = new Packet("Add_Invitation");
            requestInvitationListPacket.addItems("FB_id", AndroidApplication.getInstance().getUser().getId());
            requestInvitationListPacket.addItems("Session_id", Session.getInstance().getSession_id());

            requestInvitationListPacket.addItems("Friends_id", inviteFriendIDList);
            requestInvitationListPacket.addItems("Session_Name", Session.getInstance().getSessionName());
            invitationHandler.write(requestInvitationListPacket);

            TextView textView = new TextView(mainActivity);
            textView.setText("Invite "+inviteFriendIDList.size()+" friends");
            textView.setTextSize(20);
            textView.setGravity(Gravity.CENTER);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mainActivity);
            alertDialogBuilder.setView(textView);
            // set dialog message
            alertDialogBuilder.setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    MainActivity.getInstance().goKeyBoard();
                                }
                            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            Log.v("requestInvitation", "Send Packet");
        }
	}
	
	public void respond(){
		waitRespond = false;
	}
	
	public void setInvitationList(ArrayList<Invitation> invitationList){
		this.invitationList = invitationList ;
	}
	
	private void waitRequest(){
        while(waitRespond){
        	try {
        		Log.v("Invite","Waiting");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
}

package fingerBand.invite;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;

import java.util.ArrayList;

import fingerBand.User;

public class FriendOnClickListener implements OnClickListener {
	private final static String tag = "InviteFriend/Check";
	private User friend;
	private ArrayList<String> addList;
	public FriendOnClickListener(ArrayList<String> inviteFriendList , User friend){
		this.friend = friend ;
		if(inviteFriendList != null) this.addList = inviteFriendList;
		else Log.v(tag, "addList null");
	}
	@Override
	public void onClick(View v) {
		CheckBox checkBox = (CheckBox) v;
		if (checkBox.isChecked() )  {
			if(!addList.contains(friend.getId())){
				addList.add(friend.getId());
				Log.v(tag, "added");
			}
			else Log.v(tag,"checked but already contain this friend");
		}
		else {
			if(addList.contains(friend.getId())){
				addList.remove(friend.getId());
				Log.v(tag, "remove");
			}
			else Log.v(tag,"unchecked but not contain this friend");
		}
	}

}

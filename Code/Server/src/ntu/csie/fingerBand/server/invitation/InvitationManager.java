package ntu.csie.fingerBand.server.invitation;



import java.util.ArrayList;
import java.util.HashMap;

public class InvitationManager {
	public HashMap<String, HashMap<Integer,Invitation> > ownInvitationLists;
	public HashMap<Integer, Invitation> invitations ;

	public InvitationManager() {
		ownInvitationLists = new HashMap<String, HashMap<Integer,Invitation> >();
		invitations = new HashMap<Integer, Invitation>();
		ArrayList<String> friend = new ArrayList<String>();
		Invitation invitation1 = new Invitation("100000131582241","session1",1,"amy");
		Invitation invitation2 = new Invitation("100000131582243","session2",2,"Judy");
		Invitation invitation3 = new Invitation("100000131582242","session3",3,"wow");

		invitations.put(1, invitation1);
		invitations.put(2, invitation2);
		invitations.put(3, invitation3);
		
		HashMap <Integer,Invitation> h = new HashMap<Integer,Invitation>();
		h.put(1, invitation1);
		h.put(2, invitation2);
		h.put(3, invitation3);
		ownInvitationLists.put("100000131582244", h);

	}
	public ArrayList<Invitation> getOwnInvitations(String fbid) {
		System.out.println("getOwnInvitations");

		if (!ownInvitationLists.containsKey(fbid)) {
			HashMap<Integer, Invitation> temp = new HashMap<Integer, Invitation>();
			ownInvitationLists.put(fbid,temp);
			System.out.println("fbid has no attribute in ownInvitationList");
			return null;
		}
		if (ownInvitationLists.get(fbid).isEmpty() )
			return null;
		
		ArrayList<Invitation> invitations= new ArrayList<Invitation> (ownInvitationLists.get(fbid).values());
		return invitations;
	}
	
	public void addInvitation(String fbid, ArrayList<String> invitedFriends, String sessionName, int sessionId, String creatorName) {
		Invitation invitation;
		System.out.println("addInvitation");

		if ( invitations.containsKey(sessionId) )
			invitation = invitations.get(sessionId);
		else {
			invitation = new Invitation(fbid,sessionName,sessionId,creatorName);
			invitations.put(sessionId, invitation);
		}
		for (String friend: invitedFriends) {
			if (!ownInvitationLists.containsKey(friend)) {
				System.out.println("friend has no attribute in ownInvitationList");
				HashMap<Integer, Invitation> temp = new HashMap<Integer, Invitation>();
				temp.put(sessionId, invitation);
				ownInvitationLists.put(friend, temp);
			}
			else if (!ownInvitationLists.get(friend).containsKey(sessionId))
				ownInvitationLists.get(friend).put(sessionId, invitation);
		}
	}
	
	public void respondInvitation(String fbid, int sessionID) {
		System.out.println("respondInvitation");
		if  (!ownInvitationLists.containsKey(fbid))
			System.out.println("ownInviationList without fbid");
		else if (!ownInvitationLists.get(fbid).containsKey(sessionID))
			System.out.println("list without sessionid");
		else
			ownInvitationLists.get(fbid).remove(sessionID);
	}
	public void deleteInvitation(int sessionid) {
		for (Object key: ownInvitationLists.keySet() ){
			if (ownInvitationLists.get(key).containsKey(sessionid)) {
				ownInvitationLists.get(key).remove(sessionid);
			}
		}
		invitations.remove(sessionid);
	}
}


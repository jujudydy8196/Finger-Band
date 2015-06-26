package fingerBand;

import android.app.Application;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import fingerBand.connection.Reactor;
import fingerBand.connection.ReceiveAddInvitationStatusTask;
import fingerBand.connection.ReceiveInvitationStatusTask;
import fingerBand.connection.ReceiveInvitationTask;
import fingerBand.connection.ReceiveRecordListTask;
import fingerBand.connection.ReceiveRecordTask;
import fingerBand.connection.ServiceHandler;
import fingerBand.session.Session;

/**
 * Created by User on 2015/06/18.
 */
public class AndroidApplication extends Application{
    final static public  int LOGIN_PORT = 1120;
    final static public int INVITATION_PORT = 1121;
    final static public int SESSION_PORT = 1122;
    final static public int RECORD_PORT = 1123;
    public static final String serverIP = "140.112.90.150" ;
    private static AndroidApplication sInstance;
    private ServiceHandler loginHandler;
    private ServiceHandler invitationHandler;
    private ServiceHandler sessionHandler;
    private ServiceHandler recordHandler;
    private Reactor reactor;
    private User user;
    private Session session;
    private Thread reactorThread;
    private String state;

    private CallbackManager callbackManager;

    private static String friendName = null;
    private static String friendID = null;
    private ArrayList<User> friends;
    private ArrayList<User> updataFriends;


    public static AndroidApplication getInstance(){
        return sInstance;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        sInstance = this;
        sInstance.initializeInstance();
//        startReactor();
    }

    protected void initializeInstance(){

        try {
            reactor = new Reactor();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loginHandler = new ServiceHandler(reactor,"LoginHandler");
        invitationHandler = new ServiceHandler(reactor,"InvitationHandler");
        invitationHandler.addTaskListner(ReceiveInvitationTask.command, new ReceiveInvitationTask());
        invitationHandler.addTaskListner(ReceiveInvitationStatusTask.command, new ReceiveInvitationStatusTask());
        invitationHandler.addTaskListner(ReceiveAddInvitationStatusTask.command, new ReceiveAddInvitationStatusTask());
        sessionHandler = new ServiceHandler(reactor,"SessionHandler");
        recordHandler = new ServiceHandler(reactor,"RecordHandler");
        recordHandler.addTaskListner(ReceiveRecordListTask.command,new ReceiveRecordListTask());
        recordHandler.addTaskListner(ReceiveRecordTask.command,new ReceiveRecordTask());

    }


    public void startReactor(){
        reactorThread = new Thread(reactor);
        reactorThread.start();
    }

    public void stopReactor(){
        reactorThread .interrupt();
        reactor.setTerminate(true);

    }
    public ServiceHandler getLoginHandler(){return loginHandler;}
    public ServiceHandler getInvitationHandler(){return invitationHandler;}
    public ServiceHandler getSessionHandler(){return sessionHandler;}
    public ServiceHandler getRecordHandler(){return recordHandler;}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setState(String state){
        this.state = state;
    }

    public String getState(){
        return state;
    }

    public void updateFriendLists(){
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = new GraphRequest(
                                loginResult.getAccessToken(),
                                "/me/friends",
                                null,
                                HttpMethod.GET,
                                new GraphRequest.Callback() {
                                    @Override
                                    public void onCompleted(GraphResponse response) {
                                        JSONObject jsonObject = response.getJSONObject();
                                        try {
                                            JSONArray dataArray = jsonObject.getJSONArray("data");
                                            for (int dataSize = 0; dataSize < dataArray.length(); dataSize++) {
                                                friendID = (String) ((JSONObject)dataArray.get(dataSize)).get("id");
                                                friendName = (String) ((JSONObject)dataArray.get(dataSize)).get("name");
                                                User friend = new User(friendID,friendName);
                                                updataFriends.add(friend);
                                            }
                                        }catch (JSONException e){
                                            e.printStackTrace();
                                        }
                                    }
                                });

                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException exception) {
                    }
                });
        setFriends(updataFriends);
    }

}

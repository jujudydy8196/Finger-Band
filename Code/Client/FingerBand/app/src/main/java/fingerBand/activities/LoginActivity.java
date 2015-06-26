package fingerBand.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import fingerBand.AndroidApplication;
import fingerBand.R;
import fingerBand.User;
import fingerBand.connection.Connector;
import fingerBand.connection.Packet;
import fingerBand.connection.ServiceHandler;
import fingerBand.connection.Task;


public class LoginActivity extends Activity {
    final static String TAG = "LoginActivity";

    final ServiceHandler loginHandler = AndroidApplication.getInstance().getLoginHandler();

    private ArrayList<String> accessToken = new ArrayList<>();
    private CallbackManager callbackManager;
    private LoginButton loginButton;

    private static LoginActivity loginActivity;

    private static String userName = null;
    private static String userID = null;
    private static String friendName = null;
    private static String friendID = null;

    private static ArrayList<User> friends = new ArrayList<>();

    public static LoginActivity getInstance(){
        return loginActivity;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.loginActivity = this;
        accessToken.add("user_friends");
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(accessToken);

        addTask();
        //connect to FB server, get user information
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                Toast.makeText(getApplication(), "success", Toast.LENGTH_SHORT).show();
                                handleUserInformation(loginResult);

                                handleFriendList(loginResult);
                                AndroidApplication.getInstance().setFriends(friends);
                                Log.e(TAG, "Success");
                            }

                            @Override
                            public void onCancel() {
                                Toast.makeText(getApplication(), "fail", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(FacebookException e) {
                                Toast.makeText(getApplication(), "error", Toast.LENGTH_SHORT).show();

                        }
                        });
            }
        });
        AccountManager accountManager = AccountManager.get(this);
        Account[] accounts =accountManager.getAccountsByType("com.facebook.auth.login");
        for(Account account: accounts){
            Log.i("Get facebook account", account.name);
            Log.i("Get auth type", account.type);
            if(!account.name.isEmpty() && !account.name.isEmpty()){
                Log.i("Account access ok", "OK");
                authFacebook();

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void authFacebook(){
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Toast.makeText(getApplication(), "success", Toast.LENGTH_SHORT).show();
                        handleUserInformation(loginResult);

                        handleFriendList(loginResult);
                        AndroidApplication.getInstance().setFriends(friends);
                        Log.e(TAG, "Success");
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplication(), "fail", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(getApplication(), "error", Toast.LENGTH_SHORT).show();
                        Log.e("login",exception.toString());
                    }
                });
        LoginManager.getInstance().logInWithReadPermissions(this, accessToken);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_login, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    private void handleUserInformation(LoginResult loginResult){

        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback(){
                    @Override
                    public void onCompleted(JSONObject object,GraphResponse response){

                        JSONObject jsonObject = response.getJSONObject();
                        try {
                            userID = jsonObject.getString("id");
                            userName = jsonObject.getString("name");

                            User user = new User(userID,userName);
                            AndroidApplication.getInstance().setUser(user);

                            sendTokenToServer();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                }
        );
        request.executeAsync();
    }

    private void handleFriendList(LoginResult loginResult){

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
                                friends.add(friend);

                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                });

        request.executeAsync();
    }

    private void addTask(){
        loginHandler.addTaskListner("Login_Response", new Task() {
            @Override
            public void handle(Packet p) {
                //create User
                //go to LobbyActivity
                Intent i = new Intent( LoginActivity.getInstance() , MainActivity.class);
                startActivity(i);
                LoginActivity.getInstance().finish();

            }
        });
    }

    private void sendTokenToServer(){
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                // connect to server
                try {
                    Log.e("Login" ,"start connect");
                    ServiceHandler loginHandler = AndroidApplication.getInstance().getLoginHandler();
                    Connector.connect(loginHandler, AndroidApplication.LOGIN_PORT);
                    Connector.connect(AndroidApplication.getInstance().getInvitationHandler(), AndroidApplication.INVITATION_PORT);
                    Connector.connect(AndroidApplication.getInstance().getSessionHandler(), AndroidApplication.SESSION_PORT);
                    Connector.connect(AndroidApplication.getInstance().getRecordHandler(), AndroidApplication.RECORD_PORT);

                    AndroidApplication.getInstance().startReactor();
                    Log.e("Login" ,"end connect");

                    //send user to server
                    Packet userPacket = new Packet("Login");
                    userPacket.addItems("User", AndroidApplication.getInstance().getUser());
                    loginHandler.write(userPacket);

                    //send friend to server
//                    Packet friendPacket = new Packet("Login");
//                    friendPacket.addItems("Friend", AndroidApplication.getInstance().getFriend());
//                    loginHandler.write(friendPacket);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        task.execute();
    }
}

package fingerBand.session;

import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;

import fingerBand.AndroidApplication;
import fingerBand.Databean.Message;
import fingerBand.Databean.Music;
import fingerBand.InstrumentPlayer.PitchPlayer;
import fingerBand.activities.MainActivity;
import fingerBand.connection.Packet;
import fingerBand.connection.ServiceHandler;
import fingerBand.connection.SessionIDTask;
import fingerBand.connection.SessionOverTask;
import fingerBand.connection.TransmitMessageTask;
import fingerBand.connection.TransmitMusicTask;


enum State{SELF, CONNECTED}
public class Session {
    private static final int MUSIC_START = 1;
    private static final int MUSIC_END = 0;
    private static final String ACTIVITY_TAG="Session";
    private static final int DEFAULT_TEMPO = 60;
    private static Session session ;
    private HashMap<String, Thread> playerThreads = new HashMap<>();
    private HashMap<String, PitchPlayer> playerChannels = new HashMap<>();
    private int session_id;
    private String FB_id;
    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    private String sessionName;
    private State state ;
    private int tempo;
    private boolean isCreater;
    private ServiceHandler serviceHandler;
    public static Session getInstance()
    {
        if(session == null) {
            session = new Session();

        }
        return session;
    }
    private Session(){
        this.state =  State.SELF;
        this.tempo = DEFAULT_TEMPO;
        this.isCreater = false;
        this.session_id = -1;
    }
    public State getState(){
        return state;
    }
    public int getTempo(){
        return tempo;
    }
    public void setTempo(int tempo) {
        this.tempo = tempo;
    }
    public void createSession(String FB_name, String FB_id, String Session_name){
        state=State.CONNECTED;
        try {
            register();
            serviceHandler = AndroidApplication.getInstance().getSessionHandler();
            Packet sessionPacket = new Packet("Join_Session");
            sessionPacket.addItems("FB_id", FB_id);
            sessionPacket.addItems("Session_Name", Session_name);
            sessionPacket.addItems("FB_name", FB_name);
            sessionPacket.addItems("Session_id",-1);
            serviceHandler.write(sessionPacket);


        } catch(Exception e){
            Log.e(Session.ACTIVITY_TAG,"CANNOT CONNECT TO SESSION WHILE CREATING");
            e.printStackTrace();
        }
        this.isCreater = true;

    }
    public void sendSound(int soundNum, String start_or_stop){

        if(state == State.CONNECTED) {
            try {
                Music music = new Music();
                music.setSoundNum(soundNum);
                music.setFB_name(AndroidApplication.getInstance().getUser().getName());
                music.setFB_id(AndroidApplication.getInstance().getUser().getId());
                music.setEvent(start_or_stop);
                Packet voicePacket = new Packet("Transmit_music");
                voicePacket.addItems("Music",music);
                ServiceHandler serviceHandler = AndroidApplication.getInstance().getSessionHandler();
                serviceHandler.write(voicePacket);
            } catch (Exception e) {
                Log.e(Session.ACTIVITY_TAG,"CANNOT SEND A SOUND");
            }
        }
    }
    public void sendText(Message msg){
        if(state == State.CONNECTED) {
            try {
                Packet msgPacket = new Packet("Transmit_message");
                msgPacket.addItems("Message",msg);
                ServiceHandler serviceHandler = AndroidApplication.getInstance().getSessionHandler();
                serviceHandler.write(msgPacket);
            } catch (Exception e) {
                Log.e(Session.ACTIVITY_TAG,"CANNOT SEND A MESSAGE");
            }
        }
    }
//    public void end(){
//        if(isCreater && state == State.CONNECTED){
//            try {
//                Packet endPacket = new Packet("Session_Over");
//                endPacket.addItems("Session_id",session_id);
//                ServiceHandler serviceHandler = AndroidApplication.getInstance().getSessionHandler();
//                serviceHandler.write(endPacket);
//
//            } catch (Exception e) {
//                Log.e(Session.ACTIVITY_TAG,"CANNOT END A SESSION");
//            }
//
//
//        }
//    }

    public void sessionEnd(){
        state = State.SELF;
        try {
            unregister();
            this.session_id = -1;
            android.os.Message msg = new android.os.Message();
            Bundle bundle = new Bundle();
            bundle.putString("cmd", "sessionEnd");
            msg.setData(bundle);
            MainActivity.getInstance().UiHandler.sendMessage(msg);
            Log.v(Session.ACTIVITY_TAG,"SESSION ENDED");
        } catch(Exception e){
            Log.e(Session.ACTIVITY_TAG,"CANNOT UNREGISTER");
        }
    }
    public void joinSession(String FB_id, int Session_id){
        state=State.CONNECTED;
        try {
            register();
            serviceHandler = AndroidApplication.getInstance().getSessionHandler();
            Packet sessionPacket = new Packet("Join_Session");
            sessionPacket.addItems("FB_id", FB_id);
            sessionPacket.addItems("Session_Name","");
            sessionPacket.addItems("FB_name", "");
            sessionPacket.addItems("Session_id",Session_id);
            serviceHandler.write(sessionPacket);

        } catch(Exception e){
            Log.e(Session.ACTIVITY_TAG,"CANNOT CONNECT TO SESSION WHILE JOINING");
            e.printStackTrace();
        }

    }
    public void receiveSound(Music music){
        //coreeponding thread play music

        Log.e("receiveSound", music.getEvent());
        if(playerThreads.get(music.getFB_id())==null){
            PitchPlayer pitchPlayer = new PitchPlayer();
            playerChannels.put(music.getFB_id(),pitchPlayer );
            playerThreads.put(music.getFB_id(),new Thread(pitchPlayer));
            pitchPlayer.setEvent(music.getEvent());
            pitchPlayer.convertKeyToFrequency_piano(music.getSoundNum());
            playerThreads.get(music.getFB_id()).run();
        }else{

            PitchPlayer pitchPlayer=playerChannels.get(music.getFB_id());
            pitchPlayer.setEvent(music.getEvent());
            pitchPlayer.convertKeyToFrequency_piano(music.getSoundNum());
            playerThreads.get(music.getFB_id()).run();
        }


    }
    public void receiveMsg(Message msg){
        //play that msg
    }
    public void register(){
        ServiceHandler serviceHandler = AndroidApplication.getInstance().getSessionHandler();
        try {
            SessionIDTask sessionIDTask = new SessionIDTask();
            serviceHandler.addTaskListner("Session_id_Response",sessionIDTask);
            TransmitMessageTask tmsgt = new TransmitMessageTask();
            serviceHandler.addTaskListner("Transmit_message_Receive", tmsgt);
            TransmitMusicTask tmust = new TransmitMusicTask();
            serviceHandler.addTaskListner("Transmit_music_Receive", tmust);
            SessionOverTask sot = new SessionOverTask();
            serviceHandler.addTaskListner("Session_Over_Response", sot);
        }catch(Exception e){
            Log.e(Session.ACTIVITY_TAG, "CANNOT REG HANDLER");
        }
    }
    public void unregister(){
        ServiceHandler serviceHandler = AndroidApplication.getInstance().getSessionHandler();
        try {
            serviceHandler.removeTaskListener("Transmit_message_Receive");
            serviceHandler.removeTaskListener("Transmit_music_Receive");
            serviceHandler.removeTaskListener("Session_Over_Response");
        }catch(Exception e){
            Log.e(Session.ACTIVITY_TAG, "CANNOT REG HANDLER");
        }
    }
    public void receiveSession_id(int session_id){
        this.session_id = session_id;
        ServiceHandler serviceHandler = AndroidApplication.getInstance().getSessionHandler();
        serviceHandler.removeTaskListener("Session_id_Response");
        Log.v(Session.ACTIVITY_TAG,"IN SESSION, ID = "+session_id);

    }
    public void leaveSession(){
        if(isCreater && state == State.CONNECTED){
            try {
                Packet endPacket = new Packet("Session_Over");
                endPacket.addItems("Session_id",session_id);
                ServiceHandler serviceHandler = AndroidApplication.getInstance().getSessionHandler();
                serviceHandler.write(endPacket);

            } catch (Exception e) {
                Log.e(Session.ACTIVITY_TAG,"CANNOT END A SESSION");
            }
        }else if (state == State.CONNECTED) {
            ServiceHandler serviceHandler = AndroidApplication.getInstance().getSessionHandler();
            Packet packet = new Packet("Session_Quit");
            packet.addItems("Session_id", session_id);
            FB_id = AndroidApplication.getInstance().getUser().getId();
            packet.addItems("FB_id", FB_id);
            serviceHandler.write(packet);
            session_id=-1;
            state = State.SELF;
            unregister();
        }

    }
    public int getSession_id() {
        return session_id;
    }
}

package fingerBand.record;

import java.util.ArrayList;
import java.util.HashMap;

import fingerBand.Databean.Music;

/**
 * Created by user on 2015/6/23.
 */
public class RecordPlayer {
    private  static RecordPlayer recordPlayer = new RecordPlayer();

    public static RecordPlayer getInstance(){
        return recordPlayer;
    }

    private RecordPlayer(){

    }

    public HashMap<String, ArrayList<Music>> getRecord() {
        return record;
    }

    public void setRecord(HashMap<String, ArrayList<Music>> record) {
        this.record = record;
    }

    private HashMap<String,ArrayList<Music>> record;


    public void play(){

    }

}

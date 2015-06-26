package ntu.csie.fingerBand.server.databean;


public class Music {
    private String FB_id;
    private String FB_name;
    private int soundNum;
    private String event;
    private double timeStamp;
    public String getFB_id() {
        return FB_id;
    }

    public void setFB_id(String FB_id) {
        this.FB_id = FB_id;
    }

    public String getFB_name() {
        return FB_name;
    }

    public void setFB_name(String FB_name) {
        this.FB_name = FB_name;
    }

    public int getSoundNum() {
        return soundNum;
    }

    public void setSoundNum(int soundNum) {
        this.soundNum = soundNum;
    }


    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

	public double getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(double timeStamp) {
		this.timeStamp = timeStamp;
	}
}


package fingerBand.connection;

import android.util.Log;

import java.io.IOException;


public abstract class EventHandler {
	public String handlerName;
	protected Handle handle=null;
	
	
	public abstract void handleEvent() throws IOException;
	
	public Handle getHandle(){
		return handle;
	}
	
	public void setHandle(Handle handle){
		Log.d("EventHandler", "I-setHandle");
		this.handle=handle;
	}
}

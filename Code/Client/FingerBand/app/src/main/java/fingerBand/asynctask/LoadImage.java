package fingerBand.asynctask;

import java.io.IOException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class LoadImage extends AsyncTask<String, Void, Void>{

    private ImageView imageView;
    private  Bitmap bitmap;
    public LoadImage(ImageView imageView){
    	this.imageView = imageView;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected Void doInBackground(String... params) {
    	try {
			URL url = new URL(params[0]);
			int width = 200;
			int height = 200;
			if(params.length >= 3 ){
				
				try {
					width = Integer.parseInt(params[1]);
				}
				catch (NumberFormatException e) {
					;
				}
				
				try {
					height =  Integer.parseInt(params[2]);
				}
				catch (NumberFormatException e) {
					;
				}
				
			}
        	bitmap = BitmapFactory.decodeStream(url.openConnection() .getInputStream());
        	bitmap = Bitmap.createScaledBitmap(bitmap, width, height,false);
        

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }
    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if (bitmap != null){
        	imageView.setImageBitmap(bitmap);
        }
        else {
        }
    }

}
package fingerband.pitchplayer;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

/**
 * Created by User on 2015/6/21.
 */
public class PitchPlayer implements Runnable
{


   /*
    * PRIVATE DATA
    */

    private AudioTrack mAudio;
    private int mSampleCount;

    // some constants
    private final int sampleRate = 44100;
    private final int minFrequency = 200;
    private final int bufferSize = sampleRate / minFrequency;
    private String event;


   /*
    * PUBLIC METHODS
    */


    // Constructor
    public PitchPlayer()
    {
        mAudio = new AudioTrack(
                AudioManager.STREAM_MUSIC,
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_8BIT,
                bufferSize,
                AudioTrack.MODE_STATIC );
    }


    // Set the frequency
    public void setFrequency( double frequency )
    {
        int x = (int)( (double)bufferSize * frequency / sampleRate );
        mSampleCount = (int)( (double)x * sampleRate / frequency );

        byte[] samples = new byte[ mSampleCount ];

        for( int i = 0; i != mSampleCount; ++i ) {
            double t = (double)i * (1.0/sampleRate);
            double f = Math.sin( t * 2*Math.PI * frequency );
            samples[i] = (byte)(f * 127);
        }

        mAudio.write( samples, 0, mSampleCount );
    }


    public void start()
    {
        mAudio.reloadStaticData();
        mAudio.setLoopPoints( 0, mSampleCount, -1 );
        mAudio.play();
    }


    public void stop()
    {
        mAudio.stop();
    }

    @Override
     public void run() {
    if(event.equals("Start")){
        start();
    }else{
        stop();
    }
}

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}

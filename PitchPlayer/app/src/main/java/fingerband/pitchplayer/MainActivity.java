package fingerband.pitchplayer;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ToggleButton;
import android.app.Activity;


public class MainActivity extends Activity
        implements OnCheckedChangeListener
{
   /*
    * PRIVATE DATA
    */

    private PitchPlayer mPlayer = new PitchPlayer();
    private PitchPlayer mPlayer2 = new PitchPlayer();
    private EditText mFrequency;
    private ToggleButton mStartStop;
    Thread t;
   /*
    * PUBLIC METHODS
    */


    // On activity creation
    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        this.setContentView( R.layout.activity_main );
        mFrequency = (EditText) this.findViewById( R.id.inputFrequency );
        mStartStop = (ToggleButton) this.findViewById( R.id.buttonStartStop );
        mStartStop.setOnCheckedChangeListener( this );

        t = new Thread(mPlayer2);
    }


    // On button toggled state changed
    @Override
    public void onCheckedChanged( CompoundButton buttonView, boolean isChecked )
    {
        if( isChecked ) {
            mFrequency.setEnabled( false );
            mPlayer.setFrequency( Double.parseDouble(mFrequency.getText().toString()) );
            mPlayer.start();
            mPlayer2.setEvent("Start");
            mPlayer2.setFrequency( Double.parseDouble(mFrequency.getText().toString())-200);
            t.run();
        }
        else {
            mPlayer.stop();
            mPlayer2.setEvent("Stop");
            t.run();

            mFrequency.setEnabled( true );
        }
    }

}
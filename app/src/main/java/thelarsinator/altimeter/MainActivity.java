package thelarsinator.altimeter;

import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.vision.text.Text;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    GoogleApiClient mGoogleApiClient;

    TextView textView;

    Location l;

    boolean hasFoundAPosition = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set the textView
        textView = (TextView) findViewById(R.id.heigth);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    //Get connection when the app starts
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    //terminate connection when the app stops
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override //This should update the position whenever it can
    public void onConnected(Bundle connectionHint)
    {
        //Check for permission first...
        int permissionCheck = ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION");
        if(permissionCheck == PackageManager.PERMISSION_GRANTED)
        {
            l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            hasFoundAPosition = true;
        }

    }

    //Update the textView to show the next altitude
    public void updateTextView(Location l)
    {
        textView.setText(Double.toString(l.getAltitude()));
    }

    //This will try to update the position, and then write this to the screen.
    public void updateAltitude(View view)
    {
        //If we have a position, update it.
        if(hasFoundAPosition)
        {
            updateTextView(l);
            hasFoundAPosition = false;
        }
        else { //If we dont have a position, lets try to get one anyway :P
            int permissionCheck = ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION");
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                updateTextView(l);
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onConnectionFailed(ConnectionResult r)
    {
        //TODO Auto-generated method stub
    }
}

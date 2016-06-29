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

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle connectionHint)
    {
        int permissionCheck = ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION");
        if(permissionCheck == PackageManager.PERMISSION_GRANTED)
        {
            l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }

    }

    public void updateTextView(Location l)
    {
        textView.setText(Double.toString(l.getAltitude()));
    }

    public void updateAltitude(View view)
    {
        int permissionCheck = ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION");
        if(permissionCheck == PackageManager.PERMISSION_GRANTED)
        {
            l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            updateTextView(l);
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

package mx.itesm.acm.monitorearbeacons;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import java.util.List;
import java.util.UUID;

public class MonitorBeaconsActiv extends AppCompatActivity {

    private BeaconManager admBeacons;
    private BeaconRegion region;
    private ConstraintLayout layout;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_beacons);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        configurarBeacons();
        layout = findViewById(R.id.layoutCuerpo);
        img = findViewById(R.id.img);
    }

    private void configurarBeacons() {
        admBeacons = new BeaconManager(this);
        region = new BeaconRegion("beaconsAM",
                UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                1405, 57096);
//        // Agregar el listener para detectarlos RANGING
//        admBeacons.setRangingListener(new BeaconManager.BeaconRangingListener() {
//            @Override
//            public void onBeaconsDiscovered(BeaconRegion beaconRegion, List<Beacon> beacons) {
//                int n = 1;
//                for (Beacon beacon: beacons){
//                    Log.i("BEACONS", n++ + "," + beacon.toString());
//                }
//            }
//        });

        admBeacons.setMonitoringListener(new BeaconManager.BeaconMonitoringListener() {
            @Override
            public void onEnteredRegion(BeaconRegion beaconRegion, List<Beacon> beacons) {
                Log.i("BEACON","ENTRÓ ------> ");
                //layout.setBackgroundColor(getResources().getColor(R.color.green));
                /*ESTO ES PARA CUANDO ENTREN, SI Y NO DEPENDIENDO DE A CUAL SE ACERQUE
                img.setImageDrawable(getDrawable(R.drawable.si));
                img.setImageDrawable(getDrawable(R.drawable.no));

                img.setVisibility(View.VISIBLE);
                */
            }

            @Override
            public void onExitedRegion(BeaconRegion beaconRegion) {
                Log.i("BEACON","SALIÓ ------> ");
                //layout.setBackgroundColor(getResources().getColor(R.color.red));
                /*ES ESTE EL DE SALIDA PARA QUE DESAPAREZCA*/img.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SystemRequirementsChecker.checkWithDefaultDialogs(this);

        admBeacons.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                admBeacons.startMonitoring(region);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_monitor_beacons, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_exit){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

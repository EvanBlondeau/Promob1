package app_promod.application_promod;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.widget.Toast;

public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private Multiplayeur multiplayeur;

    public WiFiDirectBroadcastReceiver(WifiP2pManager mManager,WifiP2pManager.Channel mChannel,Multiplayeur multiplayeur)
    {
        this.mManager = mManager;
        this.mChannel = mChannel;
        this.multiplayeur = multiplayeur;

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction() ;
        if(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)){
           int state=intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE,-1);

           if(state==WifiP2pManager.WIFI_P2P_STATE_ENABLED)
            {
                Toast.makeText(context,"Wifi is On",Toast.LENGTH_SHORT).show();

            }
            else {
                Toast.makeText(context,"Wifi is off",Toast.LENGTH_SHORT).show();
            }
        }else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)){

            //do something
        }else if(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)){

            //do something
        }

    }
}

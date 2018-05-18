package app.example.baking.bakingapp.widget;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BakingService extends Service {
    public BakingService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

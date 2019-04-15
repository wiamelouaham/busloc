package howlingcodes.busloc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class ChargementActivity extends AppCompatActivity {
    ImageView imageview;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            imageview.setVisibility(View.VISIBLE);
            Intent intent = new Intent(ChargementActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageview = (ImageView) findViewById(R.id.imageview);
        setContentView(R.layout.activity_chargement);
        handler.postDelayed(runnable, 4000);
    }
}


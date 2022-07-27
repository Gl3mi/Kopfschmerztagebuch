package at.htlkaindorf.kopfschmerztagebuch.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Objects;

import at.htlkaindorf.kopfschmerztagebuch.R;

public class StartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        Intent intent = new Intent(this, MainActivity.class);

        TextView textView = findViewById(R.id.title);
        LottieAnimationView lottieAnimationView = findViewById(R.id.animationView);

        lottieAnimationView.animate().translationY(2000).setDuration(1000).setStartDelay(5000);
        textView.animate().translationY(2000).setDuration(1000).setStartDelay(5000);

        Handler mHandler = new Handler();
        mHandler.postDelayed(() -> startActivity(intent), 3250L);
    }
}

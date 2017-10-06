package rocks.teagantotally.sample.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import rocks.teagantotally.sample.R;
import rocks.teagantotally.sample.databinding.ActivityMainBinding;
import rocks.teagantotally.sample.ui.viewmodels.FeedViewModel;

public class MainActivity
          extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this,
                                                                     R.layout.activity_main);
        binding.setFeed(new FeedViewModel(this));
    }
}

package com.prakritibansal.quick_draw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    private static int PIXEL_WIDTH = 28;
    private SketchDetector mnistClassifier;


    @BindView(R.id.button_detect)
    View detectButton;

    @BindView(R.id.button_clear)
    View clearButton;

    @BindView(R.id.text_result)
    TextView mResultText;

    @BindView(R.id.paintView)
    PaintView paintView;

    @BindView(R.id.preview_image)
    ImageView previewImage;

    @BindView(R.id.inference_preview)
    LinearLayout inferencePreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);
        ButterKnife.bind(this);

        mnistClassifier = new SketchDetector(this);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);

        detectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDetectClicked();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClearClicked();
            }
        });
    }

    private void onDetectClicked() {
        inferencePreview.setVisibility(View.VISIBLE);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(paintView.getBitmap(), PIXEL_WIDTH, PIXEL_WIDTH, false);
        previewImage.setImageBitmap(scaledBitmap);
        String digit = mnistClassifier.classify(scaledBitmap);

        Log.d(TAG, "Found Image = " + digit);
        mResultText.setText(getString(R.string.found_digits, String.valueOf(digit)));

    }

    private void onClearClicked() {
        mResultText.setText("");
        paintView.clear();
    }
}



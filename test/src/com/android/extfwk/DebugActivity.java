package com.android.extfwk;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


public class DebugActivity extends Activity {
    private static final String TAG = DebugActivity.class.getSimpleName();
    private LinearLayout mLayout;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        mLayout = new LinearLayout(this);
        mLayout.setOrientation(LinearLayout.VERTICAL);
        initView();
    }

       @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        TextView textView = new TextView(this);
        mLayout.addView(textView);

        Button button = new Button(this);
        button.setText("60 FPS");
        button.setOnClickListener(v -> {
            Log.d(TAG, "60 button click");
            setFps(60.01f);
        });
        mLayout.addView(button);

        button = new Button(this);
        button.setText("120 FPS");
        button.setOnClickListener(v -> {
            Log.d(TAG, "120 button click");
            setFps(120.49f);
        });
        mLayout.addView(button);

        button = new Button(this);
        button.setText("Test");
        button.setOnClickListener(v -> {
            Log.d(TAG, "test button click");
        });
        mLayout.addView(button);

        ScrollView sv = new ScrollView(this);
        sv.addView(mLayout);
        setContentView(sv);
    }

    private void setFps(float fps) {
        Display.Mode[] modes = getDisplayModes();
        for (Display.Mode mode : modes) {
            if (Math.round(mode.getRefreshRate()) == Math.round(fps)) {
                setMode(this, mode);
                break;
            }
        }
    }

    private Display.Mode[] getDisplayModes() {
        Display primaryDisplay = getDisplay();
        Display.Mode[] modes = primaryDisplay.getSupportedModes();
        return modes;
    }

    private void setMode(Activity activity, Display.Mode mode) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.preferredDisplayModeId = mode.getModeId();
        window.setAttributes(params);
    }
}

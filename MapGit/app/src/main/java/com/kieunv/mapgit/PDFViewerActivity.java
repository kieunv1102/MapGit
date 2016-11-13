package com.kieunv.mapgit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class PDFViewerActivity extends AppCompatActivity {
    LinearLayout aa;
    RelativeLayout bb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);
        int width = getWindowManager().getDefaultDisplay().getWidth();
        int height = getWindowManager().getDefaultDisplay().getHeight();
        bb = (RelativeLayout) findViewById(R.id.activity_pdfviewer);
        aa = (LinearLayout) findViewById(R.id.aaaa);
        int wi = aa.getWidth();
        int he = aa.getHeight();

        bb.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        // TODO Auto-generated method stub
                        int w = bb.getWidth();
                        int h = bb.getHeight();
                        Log.v("W-H", w + "-" + h);
                        bb.getViewTreeObserver()
                                .removeOnGlobalLayoutListener(this);
                    }
                });
    }


}

package com.transdignity.driver.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;

import com.transdignity.driver.R;
import com.transdignity.driver.databinding.ActivityHtmlDocsViewerBinding;

public class HtmlDocsViewerActivity extends AppCompatActivity {
    ActivityHtmlDocsViewerBinding binding;
    StringBuilder stringBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      binding = DataBindingUtil. setContentView(this,R.layout.activity_html_docs_viewer);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String htmlData = bundle.getString("data");
            stringBuilder = new StringBuilder(htmlData);
            Log.e("HtmlView", "onCreate: "+stringBuilder.toString() );

            binding.webview.loadData(stringBuilder.toString(), "text/html", "UTF-8");
        }
    }
}

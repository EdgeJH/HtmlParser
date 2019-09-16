package com.edge.htmlparser;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements OnHtmlParseListener {

    private TextView resultTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getHtmlData();
    }

    private void initView() {
        resultTv = findViewById(R.id.result_tv);
        resultTv.setMovementMethod(new ScrollingMovementMethod());
    }

    private void getHtmlData() {
        HtmlParser htmlParser = new HtmlParser();
        htmlParser.setOnHtmlParseListener(this);
        htmlParser.execute();
    }

    @Override
    public void onHtmlParse(String result) {
        if (!TextUtils.isEmpty(result)){
            resultTv.setText(result);
        } else {
            resultTv.setText("데이터를 불러오는데 실패했습니다.");
        }
    }
}

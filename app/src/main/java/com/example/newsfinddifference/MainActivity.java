package com.example.newsfinddifference;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finddifference.Lib;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TextView time;
    private TextView intro;
    private Button start;
    private Button cancel;
    private CountDownTimer countDownTimer;
    private ImageView face;
    private ImageView faceCat;
    private ImageView bottom;
    private TextView title;
    private TextView left;
    private TextView right;
    private RequestQueue queue;
    private String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=eeb219e71a9248f2ba5ea29acf83cca7";



    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.start:
                    start();
                    break;
                case R.id.cancel:
                    cancel();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(btnClickListener);
        intro = (TextView) findViewById(R.id.Introduction);
        intro.setVisibility(View.VISIBLE);
        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(btnClickListener);
        cancel.setVisibility(View.GONE);
        time = (TextView) findViewById(R.id.time);
        time.setVisibility(View.GONE);
        face = (ImageView) findViewById(R.id.faceu);
        face.setVisibility(View.INVISIBLE);
        faceCat = (ImageView) findViewById(R.id.faceacat);
        faceCat.setVisibility(View.INVISIBLE);
        bottom = (ImageView) findViewById(R.id.bottom);
        bottom.setVisibility(View.VISIBLE);
        title = (TextView) findViewById(R.id.title);
        title.setVisibility(View.GONE);
        left = (TextView) findViewById(R.id.left);
        left.setVisibility(View.VISIBLE);
        right = (TextView) findViewById(R.id.right);
        right.setVisibility(View.VISIBLE);

        queue = Volley.newRequestQueue(this);


    }
    void makeCall() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        System.out.print(response.toString());
                        String content = Lib.getContent(response.toString());
                        left.setText(content);
                        right.setText(content);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                if (!("eeb219e71a9248f2ba5ea29acf83cca7".equals(""))) {
                    headers.put("X-API-KEY", "eeb219e71a9248f2ba5ea29acf83cca7");
                }
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }





    private void start(){
        intro.setVisibility(View.INVISIBLE);
        start.setVisibility(View.INVISIBLE);
        title.setVisibility(View.VISIBLE);
        left = (TextView) findViewById(R.id.left);
        left.setVisibility(View.VISIBLE);
        right = (TextView) findViewById(R.id.right);
        right.setVisibility(View.VISIBLE);
        time.setText("20");
        makeCall();
        countDownTimer = new CountDownTimer(20 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time.setText("" + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                title.setVisibility(View.INVISIBLE);
                if (Math.random() < 0.5) {
                    face.setVisibility(View.VISIBLE);
                } else {
                    faceCat.setVisibility(View.VISIBLE);
                }
                start.setVisibility(View.INVISIBLE);
                countDownTimer = new CountDownTimer(3 * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        face.setVisibility(View.INVISIBLE);
                        faceCat.setVisibility(View.INVISIBLE);
                        start.setVisibility(View.VISIBLE);
                        intro.setVisibility(View.VISIBLE);
                        right.setVisibility(View.INVISIBLE);
                        left.setVisibility(View.INVISIBLE);
                    }
                };

                countDownTimer.start();

            }
        };

        countDownTimer.start();
    }


    private void cancel() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }
}
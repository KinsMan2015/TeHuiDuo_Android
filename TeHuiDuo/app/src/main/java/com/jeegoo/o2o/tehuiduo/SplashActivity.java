package com.jeegoo.o2o.tehuiduo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.jeegoo.o2o.tehuiduo.bean.SvgCompletedCallBack;
import com.jeegoo.o2o.tehuiduo.bean.SvgView;

public class SplashActivity extends ActionBarActivity {
    private void addSvgView(LayoutInflater inflater, LinearLayout container)
    {
        final View view = inflater.inflate(R.layout.item_svg, container, false);
        final SvgView svgView = (SvgView) view.findViewById(R.id.svg);

        svgView.setSvgResource(R.raw.cloud);
        view.setBackgroundResource(R.color.accent);
        svgView.setmCallback(new SvgCompletedCallBack() {

            @Override
            public void onSvgCompleted() {
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        container.addView(view);
        Handler handlerDelay = new Handler();
        handlerDelay.postDelayed(new Runnable(){
            public void run() {
                svgView.startAnimation();
            }}, 1000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        LinearLayout container = (LinearLayout) findViewById(R.id.container);
        LayoutInflater inflater = getLayoutInflater();
        addSvgView(inflater, container);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

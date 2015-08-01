package com.jeegoo.o2o.tehuiduo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactUsActivity extends ActionBarActivity {
    LinearLayout mPhoneLinsearLayout,mMailLinearLayout;
    public int sendMailByIntent() {
        String[] reciver = new String[] { "business@jeegoo.com " };
        String[] mySbuject = new String[] { "标题" };
        String myCc = "cc";
        String mybody = "请填写内容";
        Intent myIntent = new Intent(android.content.Intent.ACTION_SEND);
        myIntent.setType("plain/text");
        myIntent.putExtra(android.content.Intent.EXTRA_EMAIL, reciver);
        myIntent.putExtra(android.content.Intent.EXTRA_CC, myCc);
        myIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mySbuject);
        myIntent.putExtra(android.content.Intent.EXTRA_TEXT, mybody);
        startActivity(Intent.createChooser(myIntent, "mail test"));

        return 1;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        TextView tite = (TextView) findViewById(R.id.title);
        tite.setText(R.string.title_activity_contact_us);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);
        mPhoneLinsearLayout = (LinearLayout) findViewById(R.id.phone);
        mPhoneLinsearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + "0451-82329838");
                intent.setData(data);
                startActivity(intent);
            }
        });
        mMailLinearLayout = (LinearLayout) findViewById(R.id.mail);
        mMailLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMailByIntent();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_us, menu);
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
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

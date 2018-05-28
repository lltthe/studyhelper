package com.example.lamlethanhthe.studyhelper;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lamlethanhthe.studyhelper.DataModules.Host;

import java.net.URLEncoder;

public class HostDetails extends AppCompatActivity {

    Host host;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_details);

        Intent intent = getIntent();
        host = (Host)intent.getSerializableExtra("host");

        ((TextView)findViewById(R.id.txtName)).setText(host.getName());
        ((TextView)findViewById(R.id.txtWeb)).setText(host.getWebsite());
        ((TextView)findViewById(R.id.txtPhone)).setText(host.getPhone());
        ((TextView)findViewById(R.id.txtAddress)).setText(host.getAddress());

        Toast.makeText(HostDetails.this, "Nhấn vào các biểu tượng để tương tác liên hệ!", Toast.LENGTH_SHORT).show();
    }

    public void startWeb(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(host.getWebsite()));
        startActivity(intent);
    }

    public void startCall(View view) {
        Uri phoneNo = Uri.parse("tel:" + host.getPhone());
        Intent intent = new Intent(Intent.ACTION_DIAL, phoneNo);
        startActivity(intent);
    }


    public void startSearchLocation(View view) {
        try {
            Uri url = Uri.parse("https://www.google.com/maps/search/?api=1&query="
                    + URLEncoder.encode(host.getAddress(), "utf-8"));
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(url);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.example.lamlethanhthe.studyhelper;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.lamlethanhthe.studyhelper.Global.Flags;

import java.util.ArrayList;
import java.util.Calendar;

public class TextInputActivity extends AppCompatActivity {
    EditText edt;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_text_input, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuVoiceInput:
                Intent voiceIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, getResources().getString(R.string.lang_country));
                try {
                    startActivityForResult(voiceIntent, Flags.requestVoiceSearch);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.menuConfirm:
                Intent resIntent = new Intent();
                resIntent.putExtra(Flags.transText, edt.getText().toString());
                setResult(RESULT_OK, resIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_input);

        Bundle extras = getIntent().getExtras();
        String str = extras.getString(Flags.transText);

        edt = (EditText)findViewById(R.id.edtText);
        edt.setText(str);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && resultCode == RESULT_OK) {
            if (requestCode == Flags.requestVoiceSearch) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                edt.setText(result.get(0));
            }
        }
    }
}

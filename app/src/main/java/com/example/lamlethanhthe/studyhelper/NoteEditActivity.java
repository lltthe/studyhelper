package com.example.lamlethanhthe.studyhelper;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lamlethanhthe.studyhelper.AdapterModules.NoteItemAdapter;
import com.example.lamlethanhthe.studyhelper.AdapterModules.RemAdapter;
import com.example.lamlethanhthe.studyhelper.AlgoModules.BasicAlgos;
import com.example.lamlethanhthe.studyhelper.DataModules.Note;
import com.example.lamlethanhthe.studyhelper.DataModules.NoteItem;
import com.example.lamlethanhthe.studyhelper.DataModules.Reminder;
import com.example.lamlethanhthe.studyhelper.DataModules.SampleData;
import com.example.lamlethanhthe.studyhelper.Global.Flags;
import com.example.lamlethanhthe.studyhelper.Global.NoteItemType;
import com.example.lamlethanhthe.studyhelper.Global.TimeInterval;
import com.woxthebox.draglistview.DragListView;
import com.woxthebox.draglistview.swipe.ListSwipeHelper;
import com.woxthebox.draglistview.swipe.ListSwipeItem;

import java.util.ArrayList;
import java.util.Calendar;

public class NoteEditActivity extends AppCompatActivity {

    EditText edtTitle;
    DragListView dLv;
    RecyclerView rvRem, rvImg;
    TextView txtDue;

    Note curNote;
    NoteItem curEditItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);

        attachViews();
        initComponents();
    }

    private void attachViews() {
        edtTitle = (EditText)findViewById(R.id.edtNoteTitle);
        dLv = (DragListView)findViewById(R.id.dLvNoteItem);
        rvRem = (RecyclerView)findViewById(R.id.rvRem);
        rvImg = (RecyclerView)findViewById(R.id.rvImg);
        txtDue = (TextView)findViewById(R.id.txtDue);
    }


    private void initComponents() {
        Bundle extras = getIntent().getExtras();
        curNote = SampleData.getNoteList().get(extras.getInt(Flags.transNoteIdx));

        edtTitle.setText(curNote.title);
        edtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
            }
        });
        edtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                curNote.title = s.toString();
            }
        });

        String addStr = "";
        if (curNote.due != null)
            addStr = BasicAlgos.CalendartoString(getResources(), curNote.due);
        txtDue.setText(getResources().getString(R.string.info_due) + " " + addStr);
        txtDue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flags.setDue = true;
                Calendar c = Calendar.getInstance();
                Intent invokeRemSet = new Intent(NoteEditActivity.this, ReminderSetActivity.class);
                invokeRemSet.putExtra(Flags.transCalendar, c);
                startActivityForResult(invokeRemSet, Flags.requestRem);
            }
        });

        initDragLv();
        initRemRv();
    }

    private void initRemRv() {
        rvRem.setLayoutManager(new LinearLayoutManager(this));
        rvRem.setAdapter(new RemAdapter(this, curNote.rems));
    }

    private void initDragLv() {
        dLv.setSwipeListener(new ListSwipeHelper.OnSwipeListener() {
            @Override
            public void onItemSwipeStarted(ListSwipeItem item) {

            }

            @Override
            public void onItemSwipeEnded(ListSwipeItem item, ListSwipeItem.SwipeDirection swipedDirection) {
                Pair<Long, NoteItem> curDat = (Pair<Long, NoteItem>)item.getTag();
                curEditItem = curDat.second;

                if (swipedDirection == ListSwipeItem.SwipeDirection.LEFT) {
                    Intent invokeTextInput = new Intent(NoteEditActivity.this, TextInputActivity.class);
                    invokeTextInput.putExtra(Flags.transText, curDat.second.data);
                    startActivityForResult(invokeTextInput, Flags.requestTextInput);
                }
                else if (swipedDirection == ListSwipeItem.SwipeDirection.RIGHT) {
                    int pos = dLv.getAdapter().getPositionForItem(curDat);
                    dLv.getAdapter().removeItem(pos);
                    pos = (int)(long)curDat.first;
                    if (pos >= curNote.items.size())
                        pos = curNote.items.size() - 1;
                    curNote.items.remove(pos);
                }

                dLv.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onItemSwiping(ListSwipeItem item, float swipedDistanceX) {

            }
        });

        ArrayList<Pair<Long, NoteItem>> data = new ArrayList<>();
        int n = curNote.items.size();
        for (int i = 0; i < n; ++i)
            data.add(new Pair<>((long)i, curNote.items.get(i)));

        dLv.setLayoutManager(new LinearLayoutManager(this));
        NoteItemAdapter adapter = new NoteItemAdapter(R.layout.item_noteitem_edit, R.id.llNoteItem, true, data);
        dLv.setAdapter(adapter, false);
        dLv.setCanDragHorizontally(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && resultCode == RESULT_OK) {
            if (requestCode == Flags.requestTextInput) {
                String resStr = data.getStringExtra(Flags.transText);
                curEditItem.data = resStr;
                dLv.getAdapter().notifyDataSetChanged();
            } else if (requestCode == Flags.requestRem) {
                Calendar c = (Calendar)data.getSerializableExtra(Flags.transCalendar);
                TimeInterval t = (TimeInterval)data.getSerializableExtra(Flags.transInterval);

                if (Flags.setDue) {
                    txtDue.setText(txtDue.getText().toString() + " " + BasicAlgos.CalendartoString(getResources(), c));
                    curNote.due = c;
                    Flags.setDue = false;
                }

                curNote.rems.add(new Reminder(c, t));
                setAlarm(c, t);
                rvRem.getAdapter().notifyDataSetChanged();
            }
        }
    }

    private void setAlarm(Calendar calendar, TimeInterval timeInterval) {
        Intent alarmIntent = new Intent(this, alarmReceiver.class);
        alarmIntent.putExtra(Flags.alarmNotif, getNotif());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        if (timeInterval == TimeInterval.none)
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        else {
            long ti = 1000 * 3600 * 24;
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), timeInterval == TimeInterval.day ? ti : ti * 7, pendingIntent);
        }
    }

    private Notification getNotif() {
        Intent intent = new Intent(this, NoteActivity.class);
        PendingIntent pending = PendingIntent.getActivity(this, 0, intent, 0);

        int count = 0;
        String str = "";
        for (NoteItem item : curNote.items) {
            if (item.type == NoteItemType.ongoing) {
                str += item.data + "\n";
                ++count;
            }
        }
        str += getResources().getString(R.string.info_due) + " " + BasicAlgos.CalendartoString(getResources(), curNote.due);

        Notification.BigTextStyle style = new Notification.BigTextStyle()
                .bigText(str)
                .setBigContentTitle(curNote.title);

        Notification.Builder builder = new Notification.Builder(this)
                .setContentText(count + " " + getResources().getString(R.string.info_todo))
                .setContentIntent(pending)
                .setContentTitle(curNote.title)
                .setSmallIcon(R.mipmap.ic_speaker_notes_white_24dp)
                .setStyle(style);

        return builder.build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_note_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAddPlain:
                NoteItem newPlain = new NoteItem(getResources().getString(R.string.hint_new_plain), NoteItemType.plain);
                curNote.items.add(newPlain);
                int n = dLv.getAdapter().getItemCount();
                dLv.getAdapter().addItem(n, new Pair<Long, NoteItem>((long)n, newPlain));
                dLv.getAdapter().notifyDataSetChanged();
                return true;

            case R.id.menuAddTodo:
                NoteItem newTodo = new NoteItem(getResources().getString(R.string.hint_new_todo), NoteItemType.ongoing);
                curNote.items.add(newTodo);
                int m = dLv.getAdapter().getItemCount();
                dLv.getAdapter().addItem(m, new Pair<Long, NoteItem>((long)m, newTodo));
                dLv.getAdapter().notifyDataSetChanged();
                return true;

            case R.id.menuAddRem:
                Calendar c = Calendar.getInstance();
                Intent invokeRemSet = new Intent(this, ReminderSetActivity.class);
                invokeRemSet.putExtra(Flags.transCalendar, c);
                startActivityForResult(invokeRemSet, Flags.requestRem);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

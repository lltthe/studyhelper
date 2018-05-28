package com.example.lamlethanhthe.studyhelper;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lamlethanhthe.studyhelper.AdapterModules.NoteAdapter;
import com.example.lamlethanhthe.studyhelper.AlgoModules.NoteListAlgos;
import com.example.lamlethanhthe.studyhelper.DataModules.Note;
import com.example.lamlethanhthe.studyhelper.DataModules.NoteItem;
import com.example.lamlethanhthe.studyhelper.DataModules.SampleData;
import com.example.lamlethanhthe.studyhelper.Global.Flags;

import java.util.ArrayList;
import java.util.Calendar;

public class NoteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText edtSearch;
    ImageView imgAdd, imgDel, imgLess, imgMore, imgVoiceSearch;
    Spinner spnSort, spnFilter;
    ExpandableListView expLv;

    NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        attachViews();
        initComponents();
    }

    private void attachViews() {
        edtSearch = (EditText)findViewById(R.id.edtNoteSearch);
        imgAdd = (ImageView)findViewById(R.id.imgNoteAdd);
        imgDel = (ImageView)findViewById(R.id.imgNoteDel);
        imgLess = (ImageView)findViewById(R.id.imgNoteLess);
        imgMore = (ImageView)findViewById(R.id.imgNoteMore);
        imgVoiceSearch = (ImageView)findViewById(R.id.imgNoteVoiceSearch);
        spnSort = (Spinner)findViewById(R.id.spnNoteSort);
        spnFilter = (Spinner)findViewById(R.id.spnNoteFilter);
        expLv = (ExpandableListView)findViewById(R.id.expLvNote);
    }

    private void initComponents() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                noteAdapter.setList(SampleData.getNoteList());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                NoteListAlgos.search(noteAdapter.notes, s.toString());
                noteAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imgVoiceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent voiceIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, getResources().getString(R.string.lang_country));
                try {
                    startActivityForResult(voiceIntent, Flags.requestVoiceSearch);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = new Note(getResources().getString(R.string.hint_new_note), new ArrayList<NoteItem>(), null, null, null, Calendar.getInstance());
                SampleData.getNoteList().add(note);
                noteAdapter.setList(SampleData.getNoteList());
                noteAdapter.notifyDataSetChanged();
            }
        });

        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NoteActivity.this, getResources().getString(R.string.hint_del_note), Toast.LENGTH_LONG).show();
            }
        });

        imgLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noteAdapter.getGroupCount() == 0)
                    return;
                int n = noteAdapter.getGroupCount();
                for (int i = 0; i < n; ++i)
                    expLv.collapseGroup(i);
            }
        });

        imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noteAdapter.getGroupCount() == 0)
                    return;
                int n = noteAdapter.getGroupCount();
                for (int i = 0; i < n; ++i)
                    expLv.expandGroup(i);
            }
        });

        ArrayList<String> sortCrits = new ArrayList<>();
        sortCrits.add(getResources().getString(R.string.crit_new));
        sortCrits.add(getResources().getString(R.string.crit_old));
        sortCrits.add(getResources().getString(R.string.crit_due));
        initSpinner(spnSort, sortCrits);

        ArrayList<String> filterCrits = new ArrayList<>();
        filterCrits.add(getResources().getString(R.string.crit_todo));
        filterCrits.add(getResources().getString(R.string.crit_today));
        filterCrits.add(getResources().getString(R.string.crit_plainOnly));
        filterCrits.add(getResources().getString(R.string.crit_all));
        initSpinner(spnFilter, filterCrits);

        initExpLv();

        initFlags();
    }

    private void initFlags() {
        Flags.selectMode = false;
    }

    private void initExpLv() {
        noteAdapter = new NoteAdapter(this, SampleData.getNoteList());
        expLv.setAdapter(noteAdapter);

        expLv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (Flags.selectMode) {
                    groupPosition = expLv.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(groupPosition));
                    expLv.setItemChecked(groupPosition, !expLv.isItemChecked(groupPosition));
                    return true; // No group expanding
                }
                return false;
            }
        });

        expLv.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        expLv.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                int checkedCount = expLv.getCheckedItemCount();
                mode.setTitle(getResources().getString(R.string.info_selected) + " " + String.valueOf(checkedCount));
                position = ExpandableListView.getPackedPositionGroup(expLv.getExpandableListPosition(position));
                noteAdapter.toggleSelection(position);
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.menu_delete, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                Flags.selectMode = true;
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuDelete:
                        int n = noteAdapter.selected.size();
                        for (int i = n - 1; i >= 0; --i) {
                            Note pivot = (Note)noteAdapter.getGroup(noteAdapter.selected.keyAt(i));
                            noteAdapter.remove(pivot);
                            SampleData.removeNote(pivot);
                        }
                        mode.finish();
                        return true;
                    case R.id.menuAll:
                        noteAdapter.resetSelection();
                        int m = noteAdapter.getGroupCount();
                        for (int i = 0; i < m; ++i) {
                            int j = expLv.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(i));
                            expLv.setItemChecked(j, true);
                        }
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                noteAdapter.resetSelection();
                Flags.selectMode = false;
            }
        });
    }

    private void initSpinner(Spinner spn, ArrayList<String> strs) {
        spn.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, strs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String crit = (String)parent.getItemAtPosition(position);
        if (crit.equals(getResources().getString(R.string.crit_new)))
            NoteListAlgos.sort(noteAdapter.notes, "new");
        else if (crit.equals(getResources().getString(R.string.crit_old)))
            NoteListAlgos.sort(noteAdapter.notes, "old");
        else if (crit.equals(getResources().getString(R.string.crit_due)))
            NoteListAlgos.sort(noteAdapter.notes, "due");
        else {
            noteAdapter.setList(SampleData.getNoteList());
            if (crit.equals(getResources().getString(R.string.crit_plainOnly)))
                NoteListAlgos.filter(noteAdapter.notes, "plain");
            else if (crit.equals(getResources().getString(R.string.crit_todo)))
                NoteListAlgos.filter(noteAdapter.notes, "todo");
            else if (crit.equals(getResources().getString(R.string.crit_today)))
                NoteListAlgos.filter(noteAdapter.notes, "dueToday");
        }
        noteAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && resultCode == RESULT_OK) {
            if (requestCode == Flags.requestVoiceSearch) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                edtSearch.setText(result.get(0));
            }
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        noteAdapter.notifyDataSetChanged();
    }
}

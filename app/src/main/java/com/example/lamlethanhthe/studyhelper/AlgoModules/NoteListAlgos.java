package com.example.lamlethanhthe.studyhelper.AlgoModules;

import com.example.lamlethanhthe.studyhelper.DataModules.Note;
import com.example.lamlethanhthe.studyhelper.DataModules.NoteItem;
import com.example.lamlethanhthe.studyhelper.Global.NoteItemType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

/**
 * Created by Lam Le Thanh The on 10/9/2017.
 */

public class NoteListAlgos {
    public static void sort(ArrayList<Note> notes, String crit) {
        if (notes == null)
            return;

        int i, j, n = notes.size();
        Calendar c1, c2;
        for (i = 0; i < n - 1; ++i)
            for (j = i + 1; j < n; ++j) {
                c1 = notes.get(i).created;
                c2 = notes.get(j).created;
                if (crit.equals("due")) {
                    c1 = notes.get(i).due;
                    c2 = notes.get(j).due;
                }
                if (BasicAlgos.willSwapCalendar(c1, c2, crit.equals("new") ? "desc" : "asc"))
                    Collections.swap(notes, i, j);
            }
    }

    public static void filter(ArrayList<Note> notes, String crit) {
        if (notes == null)
            return;

        ArrayList<Note> newNotes = new ArrayList<>();
        int i, n = notes.size();

        if (crit.equals("dueToday")) {
            Calendar today = Calendar.getInstance();
            today.setTimeInMillis(System.currentTimeMillis());

            for (i = 0; i < n; ++i) {
                Calendar pvCal = notes.get(i).due;
                if (pvCal != null && pvCal.get(Calendar.YEAR) == today.get(Calendar.YEAR) && pvCal.get(Calendar.MONTH) == today.get(Calendar.MONTH) && pvCal.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH))
                    newNotes.add(notes.get(i));
            }
        } else if (crit.equals("plain")) {
            for (i = 0; i < n; ++i) {
                ArrayList<NoteItem> items = notes.get(i).items;
                if (items != null) {
                    int m = items.size(), j;
                    for (j = 0; j < m; ++j)
                        if (items.get(j).type != NoteItemType.plain)
                            break;
                    if (j == m)
                        newNotes.add(notes.get(i));
                }
            }
        } else if (crit.equals("todo")) {
            for (i = 0; i < n; ++i) {
                ArrayList<NoteItem> items = notes.get(i).items;
                if (items != null) {
                    int m = items.size(), j;
                    for (j = 0; j < m; ++j)
                        if (items.get(j).type == NoteItemType.ongoing) {
                            newNotes.add(notes.get(i));
                            break;
                        }
                }
            }
        }

        notes.clear();
        notes.addAll(newNotes);
    }

    public static void search(ArrayList<Note> notes, String key) {
        if (key != null && notes != null) {
            ArrayList<Note> newNotes = new ArrayList<>();

            if (key.length() > 0) {
                key = key.toLowerCase();
                for (Note tmp : notes) {
                    if (tmp.title.toLowerCase().contains(key))
                        newNotes.add(tmp);
                }

                notes.clear();
                notes.addAll(newNotes);
            }
        }
    }
}

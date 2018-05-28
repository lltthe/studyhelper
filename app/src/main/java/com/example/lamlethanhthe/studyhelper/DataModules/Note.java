package com.example.lamlethanhthe.studyhelper.DataModules;

import android.content.res.Resources;
import android.graphics.Bitmap;

import com.example.lamlethanhthe.studyhelper.Global.NoteItemType;
import com.example.lamlethanhthe.studyhelper.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Lam Le Thanh The on 9/9/2017.
 */

public class Note {
    public String title;
    public ArrayList<NoteItem> items;
    public ArrayList<Reminder> rems;
    public ArrayList<Bitmap> imgs;
    public Calendar due, created;

    public Note(String title, ArrayList<NoteItem> items, ArrayList<Reminder> rems, ArrayList<Bitmap> imgs, Calendar due, Calendar created) {
        this.title = title;
        this.items = items;
        this.rems = rems;
        this.imgs = imgs;
        this.due = due;
        this.created = created;
    }

    public ArrayList<Integer> stats() {
        ArrayList<Integer> res = new ArrayList<>();

        Integer plain, todo, ongoing;
        plain = todo = ongoing = 0;

        if (items != null)
            for (NoteItem curItem : items) {
                if (curItem.type == NoteItemType.plain)
                    ++plain;
                else if (curItem.type == NoteItemType.ongoing) {
                    ++todo;
                    ++ongoing;
                } else
                    ++todo;
            }
        res.add(plain);
        res.add(todo);
        res.add(ongoing);

        res.add(rems == null ? 0 : rems.size());
        res.add(imgs == null ? 0 : imgs.size());

        return res;
    }
}

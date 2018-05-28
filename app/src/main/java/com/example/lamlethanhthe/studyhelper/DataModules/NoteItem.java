package com.example.lamlethanhthe.studyhelper.DataModules;

import com.example.lamlethanhthe.studyhelper.Global.NoteItemType;

/**
 * Created by Lam Le Thanh The on 9/9/2017.
 */

public class NoteItem {
    public String data;
    public NoteItemType type;

    public NoteItem(String data, NoteItemType type) {
        this.data = data;
        this.type = type;
    }
}

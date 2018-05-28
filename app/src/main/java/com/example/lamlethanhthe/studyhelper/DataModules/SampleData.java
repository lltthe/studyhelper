package com.example.lamlethanhthe.studyhelper.DataModules;

import com.example.lamlethanhthe.studyhelper.Global.NoteItemType;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Lam Le Thanh The on 9/9/2017.
 */

public class SampleData {
    private static ArrayList<Note> mainNoteList = null;

    public static ArrayList<Note> getNoteList() {
        if (mainNoteList == null) {
            mainNoteList = new ArrayList<>();

            ArrayList<NoteItem> items = new ArrayList<>();
            ArrayList<NoteItem> items2 = new ArrayList<>();
            ArrayList<Reminder> rems = new ArrayList<>();
            Calendar c1 = Calendar.getInstance(), c2 = Calendar.getInstance(), c3 = Calendar.getInstance(), c4 = Calendar.getInstance(), c5 = Calendar.getInstance(), c6 = Calendar.getInstance();
            c1.setTimeInMillis(System.currentTimeMillis());
            c2.setTimeInMillis(System.currentTimeMillis());
            c3.setTimeInMillis(System.currentTimeMillis());
            c4.setTimeInMillis(System.currentTimeMillis());
            c5.setTimeInMillis(System.currentTimeMillis());
            c6.setTimeInMillis(System.currentTimeMillis());

            items.add(new NoteItem("Ăn hết bánh", NoteItemType.ongoing));
            items.add(new NoteItem("Uống hết nước", NoteItemType.done));
            items.add(new NoteItem("Nhớ là có tới 2 hộp bánh!!!", NoteItemType.plain));
            rems.add(new Reminder());
            rems.add(new Reminder());

            c1.set(2017, 9, 10);
            c2.set(2017, 9, 20);
            mainNoteList.add(new Note("Cho no bụng", items, rems, null, c2, c1));

            c3.set(2017, 7, 8);
            c4.set(2017, 8, 10);
            items2.add(new NoteItem("Ai ăn hết rồi ?!!!! T.T", NoteItemType.ongoing));
            mainNoteList.add(new Note("La ó", items2, null, null, c4, c3));

            c5.set(2017, 7, 10);
            mainNoteList.add(new Note("Không sao", null, null, null, null, c5));
            c6.set(2017, 9, 3);
            mainNoteList.add(new Note("Ngày hôm nay không vui", null, null, null, null, c6));
            mainNoteList.add(new Note("Thật là mệt mỏi", null, null, null, null, c6));
            mainNoteList.add(new Note("Thèm đồ chua", null, null, null, null, c6));
        }

        return mainNoteList;
    }

    public static void removeNote(Note note) {
        mainNoteList.remove(note);
    }
}

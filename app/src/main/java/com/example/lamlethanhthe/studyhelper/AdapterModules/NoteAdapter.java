package com.example.lamlethanhthe.studyhelper.AdapterModules;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lamlethanhthe.studyhelper.AlgoModules.BasicAlgos;
import com.example.lamlethanhthe.studyhelper.DataModules.Note;
import com.example.lamlethanhthe.studyhelper.DataModules.NoteItem;
import com.example.lamlethanhthe.studyhelper.Global.Flags;
import com.example.lamlethanhthe.studyhelper.Global.NoteItemType;
import com.example.lamlethanhthe.studyhelper.NoteEditActivity;
import com.example.lamlethanhthe.studyhelper.R;

import java.util.ArrayList;

/**
 * Created by Lam Le Thanh The on 9/9/2017.
 */

public class NoteAdapter extends BaseExpandableListAdapter {
    Context context;
    public ArrayList<Note> notes;
    public SparseBooleanArray selected;

    public void setList(ArrayList<Note> newList) {
        if (newList != null) {
            if (notes == null)
                notes = new ArrayList<>();
            else
                notes.clear();
            notes.addAll(newList);
        }
    }

    public NoteAdapter(Context context, ArrayList<Note> notes) {
        this.context = context;
        this.notes = null;
        setList(notes);
        selected = new SparseBooleanArray();
    }

    @Override
    public int getGroupCount() {
        return notes == null ? 0 : notes.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (getGroupCount() == 0)
            return 0;

        Note note = notes.get(groupPosition);
        if (note == null)
            return 0;

        return note.items == null ? 0 : note.items.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return notes == null ? null : notes.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (getGroup(groupPosition) == null)
            return null;

        Note note = notes.get(groupPosition);
        if (note == null)
            return null;

        return note.items == null ? null : note.items.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            if (getGroupCount() == 0)
                return LayoutInflater.from(context).inflate(R.layout.item_empty, parent, false);
            convertView = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
        }

        try {
            Note cur = (Note)getGroup(groupPosition);
            Resources res = context.getResources();

            TextView txtTitle = (TextView)convertView.findViewById(R.id.txtNoteTitle);
            txtTitle.setText(cur.title);

            ArrayList<Integer> counts = cur.stats();

            TextView txtTodo = (TextView)convertView.findViewById(R.id.txtNoteTodoCount);
            TextView txtPlain = (TextView)convertView.findViewById(R.id.txtNotePlainCount);
            TextView txtRem = (TextView)convertView.findViewById(R.id.txtNoteRemCount);
            TextView txtImg = (TextView)convertView.findViewById(R.id.txtNoteImgCount);
            TextView txtDue = (TextView)convertView.findViewById(R.id.txtNoteDue);

            if (counts.get(0) + counts.get(1) + counts.get(3) + counts.get(4) > 0) {
                if (counts.get(2) > 0)
                    txtTodo.setText(counts.get(2).toString() + "/" + counts.get(1).toString() + " " + res.getString(R.string.info_todo));
                else
                    txtTodo.setText(res.getString(R.string.info_0todo));

                txtPlain.setText(counts.get(0).toString() + " " + res.getString(R.string.info_plainNote));
                txtRem.setText(counts.get(3).toString() + " " + res.getString(R.string.info_rem));
                txtImg.setText(counts.get(4).toString() + " " + res.getString(R.string.info_img));

                if (cur.due != null)
                    txtDue.setText(res.getString(R.string.info_due) + " " + BasicAlgos.CalendartoString(res, cur.due));
                else
                    txtDue.setText("");
            } else {
                txtTodo.setText(res.getString(R.string.hint_empty));
                txtPlain.setText("");
                txtRem.setText("");
                txtImg.setText("");
                txtDue.setText("");
            }

            ImageView imgEdit = (ImageView)convertView.findViewById(R.id.imgNoteEdit);
            final int groupPos = groupPosition;
            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent invokeNoteEdit = new Intent(context, NoteEditActivity.class);
                    invokeNoteEdit.putExtra(Flags.transNoteIdx, groupPos);
                    context.startActivity(invokeNoteEdit);
                }
            });

            if (selected.get(groupPosition, false)) {
                convertView.setBackgroundColor(0xff0099cc);
            } else
                convertView.setBackgroundColor(0xffffffff);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            if (getChildrenCount(groupPosition) == 0)
                return LayoutInflater.from(context).inflate(R.layout.item_empty, parent, false);
            convertView = LayoutInflater.from(context).inflate(R.layout.item_noteitem, parent, false);
        }

        try {
            NoteItem curNoteItem = (NoteItem)getChild(groupPosition, childPosition);

            TextView txtNoteItem = (TextView)convertView.findViewById(R.id.txtNoteItem);
            txtNoteItem.setText(curNoteItem.data);

            CheckBox chk = (CheckBox)convertView.findViewById(R.id.chkNoteItem);
            if (curNoteItem.type == NoteItemType.plain) {
                chk.setVisibility(View.GONE);
                txtNoteItem.setPaintFlags(txtNoteItem.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }
            else {
                chk.setVisibility(View.VISIBLE);
                if (curNoteItem.type == NoteItemType.done) {
                    chk.setChecked(true);
                    txtNoteItem.setPaintFlags(txtNoteItem.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
                else {
                    chk.setChecked(false);
                    txtNoteItem.setPaintFlags(txtNoteItem.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void remove(Note note) {
        notes.remove(note);
        notifyDataSetChanged();
    }

    public void toggleSelection(int position) {
        selectView(position, !selected.get(position));
    }

    public void selectView(int position, boolean value) {
        if (value)
            selected.put(position, value);
        else
            selected.delete(position);
        notifyDataSetChanged();
    }

    public void resetSelection() {
        selected = new SparseBooleanArray();
        notifyDataSetChanged();
    }
}

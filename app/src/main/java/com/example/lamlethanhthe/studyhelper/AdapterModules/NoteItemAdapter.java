package com.example.lamlethanhthe.studyhelper.AdapterModules;

import android.graphics.Paint;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.lamlethanhthe.studyhelper.DataModules.NoteItem;
import com.example.lamlethanhthe.studyhelper.Global.NoteItemType;
import com.example.lamlethanhthe.studyhelper.R;
import com.woxthebox.draglistview.DragItemAdapter;

import java.util.ArrayList;

/**
 * Created by Lam Le Thanh The on 10/9/2017.
 */

public class NoteItemAdapter extends DragItemAdapter<Pair<Long, NoteItem>, NoteItemAdapter.ViewHolder> {
    int layoutId, grabId;
    boolean dragOnLongPress;

    public NoteItemAdapter(int layoutId, int grabId, boolean dragOnLongPress, ArrayList<Pair<Long, NoteItem>> items) {
        this.layoutId = layoutId;
        this.grabId = grabId;
        this.dragOnLongPress = dragOnLongPress;
        setHasStableIds(true);
        setItemList(items);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noteitem_edit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        try {
            final ViewHolder fHolder = holder;
            final NoteItem item = mItemList.get(position).second;
            fHolder.txt.setText(item.data);

            fHolder.chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        fHolder.txt.setPaintFlags(fHolder.txt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        item.type = NoteItemType.done;
                    } else {
                        fHolder.txt.setPaintFlags(fHolder.txt.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        item.type = NoteItemType.ongoing;
                    }
                }
            });

            if (item.type == NoteItemType.plain) {
                fHolder.txt.setPaintFlags(fHolder.txt.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                fHolder.chk.setVisibility(View.GONE);
            } else {
                fHolder.chk.setVisibility(View.VISIBLE);
                if (item.type == NoteItemType.done)
                    fHolder.chk.setChecked(true);
                else
                    fHolder.chk.setChecked(false);
            }

            holder.itemView.setTag(mItemList.get(position));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public long getItemId(int position) {
        return mItemList.get(position).first;
    }

    public class ViewHolder extends DragItemAdapter.ViewHolder {
        CheckBox chk;
        TextView txt;

        public ViewHolder(View itemView) {
            super(itemView, grabId, dragOnLongPress);

            txt = (TextView)itemView.findViewById(R.id.txtNoteItemEdit);
            chk = (CheckBox)itemView.findViewById(R.id.chkNoteItemEdit);
        }
    }
}

package com.elfstudio.login.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.elfstudio.login.R;
import com.elfstudio.login.model.TeacherData;

import java.util.List;

public class TeacherListAdapter extends ArrayAdapter<TeacherData> {

    private Activity context;
    private List<TeacherData> teacherDataList;

    public TeacherListAdapter (Activity context, List<TeacherData> teacherDataList) {
        super(context, R.layout.layout_teacher_list, teacherDataList);
        this.context = context;
        this.teacherDataList = teacherDataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();

        @SuppressLint("ViewHolder") View listViewItem = layoutInflater.inflate(R.layout.layout_teacher_list,null,true);

        //TextView textViewTeacherCount = listViewItem.findViewById(R.id.tv_teacherCount);
        TextView textViewTeacherName = listViewItem.findViewById(R.id.tv_teacherName);

        TeacherData teacherData = teacherDataList.get(position);

        textViewTeacherName.setText(teacherData.getTeacherName());

        return listViewItem;
    }
}

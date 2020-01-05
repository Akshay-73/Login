package com.elfstudio.login.ui.firebase_data;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.elfstudio.login.R;
import com.elfstudio.login.adapter.TeacherListAdapter;
import com.elfstudio.login.base.BaseActivity;
import com.elfstudio.login.databinding.ActivityTeacherBinding;
import com.elfstudio.login.model.TeacherData;
import com.elfstudio.login.ui.main.MainHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TeacherActivity extends BaseActivity implements MainHandler {

    ActivityTeacherBinding binding;
    DatabaseReference teacherDatabaseReference;
    List<TeacherData> teacherDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_teacher );
        teacherDatabaseReference = FirebaseDatabase.getInstance().getReference( "teacher" );
        teacherDataList = new ArrayList<>();

        binding.saveBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTeacher();
                binding.teacherName.getText().clear();
            }
        } );

        binding.lvTeacher.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                TeacherData teacherData = teacherDataList.get( i );
                showUpdateDialog( teacherData.getTeacherId(), teacherData.getTeacherName() );

                return true;
            }
        } );
    }

    @Override
    protected void onStart() {
        super.onStart();

        teacherDatabaseReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                teacherDataList.clear();

                for (DataSnapshot teacherDataSnapShot : dataSnapshot.getChildren()) {

                    TeacherData teacherData = teacherDataSnapShot.getValue( TeacherData.class );
                    teacherDataList.add( teacherData );
                }

                TeacherListAdapter adapter = new TeacherListAdapter( TeacherActivity.this, teacherDataList );
                binding.lvTeacher.setAdapter( adapter );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }

    private void showUpdateDialog(final String teacherId, final String teacherName) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder( this );

        LayoutInflater layoutInflater = getLayoutInflater();
        final View view = layoutInflater.inflate( R.layout.layout_update_dialogue, null );
        alertDialog.setView( view );

        final EditText editText = view.findViewById( R.id.editTextTeacherName );
        final Button updateBtn = view.findViewById( R.id.updateBtn );

        alertDialog.setTitle( "Updating teacher(" + teacherName + ")" );

        final AlertDialog updateDialog = alertDialog.create();
        updateDialog.show();

        updateBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editText.getText().toString();


                if (!TextUtils.isEmpty( name )) {
                    updateTeacher( teacherId,name );
                    updateDialog.dismiss();
                } else {
                    editText.setError( "Please enter name" );
                    updateDialog.dismiss();
                }
            }
        } );

    }

    private boolean updateTeacher(String id, String teacherName) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference( "teacher" ).child( id );

        TeacherData teacherData = new TeacherData( id,teacherName );
        databaseReference.setValue( teacherData );

        showMsg( "Teacher Updated" );
        return true;
    }

    private void addTeacher() {
        String name = binding.teacherName.getText().toString();
        String teacherClass = binding.spinner.getSelectedItem().toString();

        if (!TextUtils.isEmpty( name )) {

            String id = teacherDatabaseReference.push().getKey();

            TeacherData teacherData = new TeacherData( id, name );
            teacherDatabaseReference.child( id ).setValue( teacherData );
            showMsg( "Teacher Added" );

        } else {
            showMsg( "Please enter name" );
        }
    }
}

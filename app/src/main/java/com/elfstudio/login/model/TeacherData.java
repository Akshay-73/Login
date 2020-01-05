package com.elfstudio.login.model;

public class TeacherData {

    private String teacherId;
    private String teacherName;

    public TeacherData(){


    }


    public TeacherData(String teacherId, String teacherName ) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

}

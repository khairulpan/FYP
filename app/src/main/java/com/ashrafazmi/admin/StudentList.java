package com.ashrafazmi.admin;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by AshrafAzmi on 10/18/2017.
 */

public class StudentList extends ArrayAdapter<Student> {

    private Activity context;
    private List<Student> studentList;

    public StudentList(Activity context, List<Student> studentList) {
        super(context, R.layout.layout_student_list, studentList);
        this.context = context;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_student_list, null, true);

        TextView textViewfullname = (TextView) listViewItem.findViewById(R.id.fullname);
        TextView textViewcourse = (TextView) listViewItem.findViewById(R.id.courseid);
        TextView textViewstud = (TextView) listViewItem.findViewById(R.id.studid);
        TextView textViewlastcls = (TextView) listViewItem.findViewById(R.id.lastcls);
        TextView textViewtimecls = (TextView) listViewItem.findViewById(R.id.timecls);



/**        final String course =  textViewcourse.getText().toString();
        final String name = textViewfullname.getText().toString();
        final String studentid = textViewstud.getText().toString();
        final String lastcls =  textViewlastcls.getText().toString();
        final String clstime =  textViewtimecls.getText().toString();
        final String ghoc = textViewghoc.getText().toString();
        final String ghoctime =  textViewghoctime.getText().toString(); **/



        Student student = studentList.get(position);
        textViewfullname.setText("Username: "+student.getNamesavee());
        textViewcourse.setText("Email: "+student.getCoursesavee());
        textViewlastcls.setText(student.getKelaslecturesaavee());
        textViewtimecls.setText("Feedback/News: "+student.getKelastimesave());

        textViewstud.setText(student.getIdsaavee());

        return listViewItem;


    }


}
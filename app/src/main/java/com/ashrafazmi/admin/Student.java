package com.ashrafazmi.admin;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Student {
    private String namesavee;
    private String idsavee;
    private String coursesavee;
    private String kelaslecturesaavee;
    private String kelastimesave;



    public Student(){
        //this constructor is required
    }

    public Student(String namesavee, String idsavee, String coursesavee, String kelaslecturesaavee, String kelastimesave) {

        this.namesavee = namesavee;
        this.idsavee = idsavee;
        this.coursesavee = coursesavee;
        this. kelaslecturesaavee =  kelaslecturesaavee;
        this.kelastimesave = kelastimesave;


    }

    public String getNamesavee() {
        return namesavee;
    }

    public String getIdsaavee() {
        return idsavee;
    }

    public String getCoursesavee() {
        return coursesavee;
    }

    public String getKelaslecturesaavee() {
        return kelaslecturesaavee;
    }

    public String getKelastimesave() {
        return kelastimesave;
    }


}

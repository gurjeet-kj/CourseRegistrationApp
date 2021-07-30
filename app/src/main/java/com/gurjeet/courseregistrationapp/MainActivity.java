package com.gurjeet.courseregistrationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView courseFees, courseHours, totalFees, totalHours, studentName;
    Button add;
    RadioButton graduate, underGraduate;
    CheckBox accommodations, medInsurance;
    Spinner spinnerCourses;

    String courseNames[]={"Java","Swift","iOS","Android","Database"};
    ArrayList<Course> courseList = new ArrayList<>();
    ArrayList<Course> addedCourses = new ArrayList<>();
    Course selectedCourse;
    static double finalTotalFees = 0;
    static int finalTotalHours = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the variable
        studentName = findViewById(R.id.txvstudentName);
        courseFees = findViewById(R.id.txvCourseFees);
        courseHours = findViewById(R.id.txvCourseHours);
        totalFees = findViewById(R.id.txvTotalFees);
        totalHours = findViewById(R.id.txvTotalHours);
        add = findViewById(R.id.btnAdd);
        graduate = findViewById(R.id.rdbGraduate);
        underGraduate = findViewById(R.id.rdbUngraduated);
        accommodations = findViewById(R.id.chkAccommodations);
        medInsurance = findViewById(R.id.chkMedInsurance);
        spinnerCourses = findViewById(R.id.spCourse);

        //Filling data
        fillData();
        ArrayAdapter<String> aa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, courseNames);
        spinnerCourses.setAdapter(aa);

        //Listeners
        spinnerCourses.setOnItemSelectedListener(new SpinnersEvents());
        accommodations.setOnCheckedChangeListener(new CheckboxListeners());
        medInsurance.setOnCheckedChangeListener(new CheckboxListeners());

        graduate.setOnClickListener(new ButtonEvents());
        underGraduate.setOnClickListener(new ButtonEvents());


        //Add Course button
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkHoursLimit()) {
                    addedCourses.add(selectedCourse);
                    finalTotalFees += selectedCourse.getFees();
                    finalTotalHours += selectedCourse.getHours();
                    totalFees.setText("$ " + finalTotalFees);
                    totalHours.setText(finalTotalHours + "  hours/week");
                }
                else {
                    Toast.makeText(MainActivity.this, "You can't add this course. Maximum hours limit exceed!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Check buttons method for Accomodation & MedicalInsurance
    public class CheckboxListeners implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.chkAccommodations:
                    if (buttonView.isChecked())
                        finalTotalFees += 1000;
                    else
                        finalTotalFees -= 1000;
                    break;
                case R.id.chkMedInsurance:
                    if (buttonView.isChecked())
                        finalTotalFees += 700;
                    else
                        finalTotalFees -= 700;
                    break;
                default:
                    break;
            }
            totalFees.setText("$ " + finalTotalFees);
        }
    }

    // function to change course from spinner
    public class SpinnersEvents implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            courseFees.setText("$ " +courseList.get(position).getFees());
            courseHours.setText(courseList.get(position).getHours() + "  hours/week");
            selectedCourse = courseList.get(position);
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    // function to fill the data
    public void fillData() {
        courseList.add(new Course(courseNames[0], 1300, 6));
        courseList.add(new Course(courseNames[1], 1500, 5));
        courseList.add(new Course(courseNames[2], 1350, 5));
        courseList.add(new Course(courseNames[3], 1400, 7));
        courseList.add(new Course(courseNames[4], 1000, 4));
    }

    //function to check total hours limit for graduate & ungraduate
    private boolean checkHoursLimit() {
        if (graduate.isChecked()) {
            if (finalTotalHours + selectedCourse.getHours() <= 21)
                return true;
            else
                return false;
        }
        else {
            if (finalTotalHours + selectedCourse.getHours() <= 19)
                return true;
            else
                return false;
        }
    }

    //function to reset all courses when change graduate to undergraduate or vice versa
    public class ButtonEvents implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.rdbUngraduated:
                    underGraduate.setChecked(true);
                    accommodations.setChecked(false);
                    medInsurance.setChecked(false);
                    spinnerCourses.setSelection(0, true);
                    finalTotalFees = 0;
                    finalTotalHours = 0;
                    addedCourses = new ArrayList<>();
                    courseFees.setText("$ 0");
                    courseHours.setText("0  hours/week");
                    totalFees.setText("$ " + String.format("%.0f", finalTotalFees));
                    totalHours.setText(finalTotalHours + "  hours/week");
                    break;
                case R.id.rdbGraduate:
                    graduate.setChecked(true);
                    accommodations.setChecked(false);
                    medInsurance.setChecked(false);
                    spinnerCourses.setSelection(0, true);
                    finalTotalFees = 0;
                    finalTotalHours = 0;
                    addedCourses = new ArrayList<>();
                    courseFees.setText("$ 0");
                    courseHours.setText("0  hours/week");
                    totalFees.setText("$ " + String.format("%.0f", finalTotalFees));
                    totalHours.setText(finalTotalHours + "  hours/week");
                    break;

            }
        }
    }

}
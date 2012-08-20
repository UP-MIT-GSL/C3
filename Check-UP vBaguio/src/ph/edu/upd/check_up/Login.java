package ph.edu.upd.check_up;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	private DbAdapter mDbHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		mDbHelper = new DbAdapter(this);
		mDbHelper.open();
		mDbHelper.resetDatabase();

		if (mDbHelper.getAllColleges().getCount() < 26) {
			mDbHelper.addCollege("Archaeological Studies Program");
			mDbHelper.addCollege("College of Architecture");
			mDbHelper.addCollege("College of Arts and Letters");
			mDbHelper.addCollege("Asian Center");
			mDbHelper.addCollege("Asian Institute of Tourism");
			mDbHelper.addCollege("College of Business Administration");
			mDbHelper.addCollege("School of Economics");
			mDbHelper.addCollege("College of Education");
			mDbHelper.addCollege("College of Engineering");
			mDbHelper.addCollege("College of Fine Arts");
			mDbHelper.addCollege("College of Home Economics");
			mDbHelper.addCollege("College of Human Kinetics");
			mDbHelper.addCollege("Institute of Islamic Studies");
			mDbHelper.addCollege("School of Labor and Industrial Relations");
			mDbHelper.addCollege("College of Law");
			mDbHelper.addCollege("Institute of Library and Information Science");
			mDbHelper.addCollege("College of Mass Comunication");
			mDbHelper.addCollege("College of Music");
			mDbHelper.addCollege("National College of Public Administration and Governance");
			mDbHelper.addCollege("College of Science");
			mDbHelper.addCollege("College of Social Sciences and Philosophy");
			mDbHelper.addCollege("College of Social Work and Community Development");
			mDbHelper.addCollege("Institue of Small Scale Industries");
			mDbHelper.addCollege("School of Statistics");
			mDbHelper.addCollege("Technology Management Center");
			mDbHelper.addCollege("School of Urban and Regional Planning");
		}
		
		if (mDbHelper.getAllCourses().getCount() < 9 &&
				mDbHelper.ifCollegeExists("College of Engineering")) {
			mDbHelper.addCourse("BS Electrical Engineering", "College of Engineering");
			mDbHelper.addCourse("BS Computer Science", "College of Engineering");
			mDbHelper.addCourse("BS Electronics and Communications Engineering", "College of Engineering");
			mDbHelper.addCourse("BS Materials Engineering", "College of Engineering");
			mDbHelper.addCourse("BS Chemical Engineering", "College of Engineering");
			mDbHelper.addCourse("BS Industrial Engineering", "College of Engineering");
			mDbHelper.addCourse("BS Geodetic Engineering", "College of Engineering");
			mDbHelper.addCourse("BS Metallurgical Engineering", "College of Engineering");
			mDbHelper.addCourse("BS Civil Engineering", "College of Engineering");
		}
		
		if (mDbHelper.getAllStatuses().getCount() != 6) {
			mDbHelper.addStatus("Regular");
			mDbHelper.addStatus("Graduating");
			mDbHelper.addStatus("Leave of Absence");
			mDbHelper.addStatus("Non-Degree");
			mDbHelper.addStatus("Non-Major");
			mDbHelper.addStatus("Residency");
		}
		
		if (mDbHelper.getAllSubjectTypes().getCount() < 7) {
			mDbHelper.addSubjectType("GE(MST)");
			mDbHelper.addSubjectType("GE(AH)");
			mDbHelper.addSubjectType("GE(SSP)");
			mDbHelper.addSubjectType("Major");
			mDbHelper.addSubjectType("Elective");
			mDbHelper.addSubjectType("CWTS");
			mDbHelper.addSubjectType("PE");
			
			if (mDbHelper.ifCourseExists("BS Computer Science")) {
				mDbHelper.addCurriculumSubject("BS Computer Science", "Comm 3", 1, 1);
				mDbHelper.addCurriculumSubject("BS Computer Science", "Kas 1", 1, 1);
				mDbHelper.addCurriculumSubject("BS Computer Science", "Physics 10", 1, 1);
				mDbHelper.addCurriculumSubject("BS Computer Science", "Math 17", 1, 1);
				mDbHelper.addCurriculumSubject("BS Computer Science", "CS 11", 1, 1);
				mDbHelper.addCurriculumSubject("BS Computer Science", "PE 2 MJG", 1, 1);
				mDbHelper.addCurriculumSubject("BS Computer Science", "Eng 1", 1, 2);
				mDbHelper.addCurriculumSubject("BS Computer Science", "Kas 2", 1, 2);
				mDbHelper.addCurriculumSubject("BS Computer Science", "GE 1", 1, 2);
				mDbHelper.addCurriculumSubject("BS Computer Science", "Math 53", 1, 2);
				mDbHelper.addCurriculumSubject("BS Computer Science", "CS 12", 1, 2);
				mDbHelper.addCurriculumSubject("BS Computer Science", "PE 2 BRD", 1, 2);
			}
		}

		Button buttonSignIn = (Button) findViewById(R.id.btn_signin);
		buttonSignIn.setOnClickListener(new OnClickListener() {
			public void onClick(View viewParam) {
				String username = ((EditText) findViewById(R.id.edit_sign_in_username)).getText().toString();
				String password = ((EditText) findViewById(R.id.edit_sign_in_password)).getText().toString();

				if (username.length() == 0 || password.length() == 0) {
					Toast.makeText(Login.this, "All fields are required.", Toast.LENGTH_SHORT).show();
				} else if (!mDbHelper.ifUserExists(username)){
					Toast.makeText(Login.this, "User does not exist.", Toast.LENGTH_SHORT).show();
				} else if (!mDbHelper.ifValidUser(username, password)) {
					Toast.makeText(Login.this, "Incorrect password.", Toast.LENGTH_SHORT).show();
				} else {
					Intent intent = new Intent(Login.this, TabLayout.class);
					intent.putExtra("username", username);
					intent.putExtra("password", password);
					startActivity(intent);
				}
			}
		});
		
		Button buttonNoAccount = (Button) findViewById(R.id.button_no_account);
		buttonNoAccount.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Login.this, Signup.class);
				startActivity(intent);
			}
		});
	}
}
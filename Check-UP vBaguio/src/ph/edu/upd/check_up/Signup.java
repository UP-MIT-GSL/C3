package ph.edu.upd.check_up;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends Activity {
	private DbAdapter mDbHelper;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);

		mDbHelper = new DbAdapter(this);
		mDbHelper.open();
		
		// setup Course autocomplete field
		ArrayList<String> courses = new ArrayList<String>();
		if (mDbHelper.getAllCourses().getCount() > 0) { // get from database
			Cursor c = mDbHelper.getAllCourses();
			c.moveToFirst();
			do {
				courses.add(c.getString(c.getColumnIndex(DbAdapter.KEY_COURSES_NAME)));
			} while(c.moveToNext());
		} else { // get from resources
			String[] strCourses = getResources().getStringArray(R.array.user_statuses);
			Collections.addAll(courses, strCourses);
			
			Toast.makeText(this, "Download failed. Courses list from application installation", Toast.LENGTH_LONG).show();
		}
		final AutoCompleteTextView autocompleteCourse = (AutoCompleteTextView) findViewById(R.id.autocomplete_course);
		ArrayAdapter<String> adapterCourses = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, courses);
		autocompleteCourse.setAdapter(adapterCourses);
		
		// setup Status autocomplete field
		ArrayList<String> statuses = new ArrayList<String>();
		if (mDbHelper.getAllStatuses().getCount() > 0) { // get from database
			Cursor c = mDbHelper.getAllStatuses();
			c.moveToFirst();
			do {
				statuses.add(c.getString(0));
			} while(c.moveToNext());
		} else { // get from resources
			String[] strStatuses = getResources().getStringArray(R.array.user_statuses);
			Collections.addAll(statuses, strStatuses);
			
			Toast.makeText(this, "Download failed. Status list from application installation", Toast.LENGTH_LONG).show();
		}
		final AutoCompleteTextView autocompleteStatus = (AutoCompleteTextView) findViewById(R.id.autocomplete_status);
		ArrayAdapter<String> adapterStatuses = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, statuses);
		autocompleteStatus.setAdapter(adapterStatuses);
		
		// setup Sign-UP Button
		Button buttonSignUp = (Button) findViewById(R.id.button_sign_up);
		buttonSignUp.setOnClickListener(new OnClickListener() {
			public void onClick(View viewParam) {
				String username = ((EditText) findViewById(R.id.edit_username)).getText().toString();
				String upWebmail = ((EditText) findViewById(R.id.edit_up_webmail)).getText().toString();
				String password = ((EditText) findViewById(R.id.edit_password)).getText().toString();
				String confirmation = ((EditText) findViewById(R.id.edit_password_confirmation)).getText().toString();
				String strStudentNumber = ((EditText) findViewById(R.id.edit_student_number)).getText().toString();
				
				String course = autocompleteCourse.getText().toString();
				String status = autocompleteStatus.getText().toString();
				
				int studentNumber = 0;
				if (strStudentNumber.length() != 0)
					studentNumber = Integer.parseInt(strStudentNumber);
				
				if (username.length() == 0 || upWebmail.length() == 0 || course.length() == 0 || status.length() == 0) {
					Toast.makeText(Signup.this, "All fields are required.", Toast.LENGTH_SHORT).show();
				} else if (password.length() == 0 || !password.equals(confirmation)) {
					Toast.makeText(Signup.this, "Incorrect password.", Toast.LENGTH_SHORT).show();
				} else {
					mDbHelper.addUser(username, password, studentNumber, upWebmail, course, status);
					Intent intent = new Intent(Signup.this, TabLayout.class);
					startActivity(intent);
				}
			}
		});
	}
}
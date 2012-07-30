package ph.edu.upd.check_up;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class History extends Activity {
	// String username;
	// String password;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);

		Button buttonGrades = (Button) findViewById(R.id.button_grades);
		Button buttonSubjects = (Button) findViewById(R.id.button_subjects);
		
		buttonGrades.setOnClickListener(new OnClickListener() {
			public void onClick(View v){
				Intent intent = new Intent(History.this, Grades.class);
				startActivity(intent);
			}
		});
	}
}
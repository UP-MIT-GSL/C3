package upd.edu.ph.check_up;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import upd.edu.ph.check_up.R;
import upd.edu.ph.check_up.DbAdapter;

import android.os.Bundle;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import android.database.Cursor;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
//import android.webkit.WebView;

public class Enroll extends Activity {

	private DbAdapter mDbA;
	private Cursor mCursor;
	private TextView tsemester;
	private TextView tsubj1;
	private TextView tsubj2;
	private TextView tsubj3;
	private TextView tsubj4;
	private TextView tsubj5;
	private TextView tsubj6;
	private TextView tsubj7;
	private TextView tsubj8;
	private TextView tsubj9;
	private TextView tunit1;
	private TextView tunit2;
	private TextView tunit3;
	private TextView tunit4;
	private TextView tunit5;
	private TextView tunit6;
	private TextView tunit7;
	private TextView tunit8;
	private TextView tunit9;
	private Button bAdd;
	private Button bSet;
	private int units = 0;
	String username;
	String password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);
        
        Bundle extras = getIntent().getExtras();
     	this.username = extras.getString("username");
     	this.password = extras.getString("password");
     	
        tsemester = (TextView) findViewById(R.id.semester);
        tsubj1 = (TextView) findViewById(R.id.subject1);
        tsubj2 = (TextView) findViewById(R.id.subject2);
        tsubj3 = (TextView) findViewById(R.id.subject3);
        tsubj4 = (TextView) findViewById(R.id.subject4);
        tsubj5 = (TextView) findViewById(R.id.subject5);
        tsubj6 = (TextView) findViewById(R.id.subject6);
        tsubj7 = (TextView) findViewById(R.id.subject7);
        tsubj7 = (TextView) findViewById(R.id.subject8);
        tsubj7 = (TextView) findViewById(R.id.subject9);
        tunit1 = (TextView) findViewById(R.id.unit1);
        tunit2 = (TextView) findViewById(R.id.unit2);
        tunit3 = (TextView) findViewById(R.id.unit3);
        tunit4 = (TextView) findViewById(R.id.unit4);
        tunit5 = (TextView) findViewById(R.id.unit5);
        tunit6 = (TextView) findViewById(R.id.unit6);
        tunit7 = (TextView) findViewById(R.id.unit7);
        tunit7 = (TextView) findViewById(R.id.unit8);
        tunit7 = (TextView) findViewById(R.id.unit9);
        bAdd = (Button) findViewById(R.id.add);
        bSet = (Button) findViewById(R.id.set);
        mDbA = new DbAdapter(this);
        mDbA.open();
        
        //tsemester.setText(mDbA.getCurrentSemester());
        
        tsubj1.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		if (tsubj1.getText() == "GE(SSP)") {
            		Recommend("SSP", tsubj1);        		
        		} else if (tsubj1.getText() == "GE(AH)") {
            		Recommend("AH", tsubj1);        			
        		} else if (tsubj1.getText() == "GE(MST)") {
            		Recommend("MST", tsubj1);        			
        		} else if (tsubj1.getText() == "Free Elective") {
            		Recommend("FE", tsubj1);        			
        		} else if (tsubj1.getText() == "MSE Elective") {
            		Recommend("MSE", tsubj1);        			
        		} else if (tsubj1.getText() == "CS Elective") {
            		Recommend("CSE", tsubj1);        			
        		} else if (tsubj1.getText() != "") {
            		DelPopup(tsubj1, tunit1);        			
        		}
        	}
        	
        });
        
        tsubj2.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		if (tsubj2.getText() == "GE(SSP)") {
            		Recommend("SSP", tsubj2);            		
        		} else if (tsubj2.getText() == "GE(AH)") {
            		Recommend("AH", tsubj2);        			
        		} else if (tsubj2.getText() == "GE(MST)") {
            		Recommend("MST", tsubj2);        			
        		} else if (tsubj2.getText() == "Free Elective") {
            		Recommend("FE", tsubj2);        			
        		} else if (tsubj2.getText() == "MSE Elective") {
            		Recommend("MSE", tsubj2);        			
        		} else if (tsubj2.getText() == "CS Elective") {
            		Recommend("CSE", tsubj2);        			
        		} else if(tsubj2.getText() != "") {
            		DelPopup(tsubj2, tunit2);        			
        		}
        	}
        	
        });

        tsubj3.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		if (tsubj3.getText() == "GE(SSP)") {
            		Recommend("SSP", tsubj3);            		
        		} else if (tsubj3.getText() == "GE(AH)") {
            		Recommend("AH", tsubj3);        			
        		} else if (tsubj3.getText() == "GE(MST)") {
            		Recommend("MST", tsubj3);        		
        		} else if (tsubj3.getText() == "Free Elective") {
            		Recommend("FE", tsubj3);        			
        		} else if (tsubj3.getText() == "MSE Elective") {
            		Recommend("MSE", tsubj3);        			
        		} else if (tsubj3.getText() == "CS Elective") {
            		Recommend("CSE", tsubj3);        			
        		} else if(tsubj3.getText() != "") {
            		DelPopup(tsubj3, tunit3);        			
        		}
        	}
        	
        });

        tsubj4.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		if (tsubj4.getText() == "GE(SSP)") {
            		Recommend("SSP", tsubj4);            		
        		} else if (tsubj4.getText() == "GE(AH)") {
            		Recommend("AH", tsubj4);        			
        		} else if (tsubj4.getText() == "GE(MST)") {
            		Recommend("MST", tsubj4);        			
        		} else if (tsubj4.getText() == "Free Elective") {
            		Recommend("FE", tsubj4);        			
        		} else if (tsubj4.getText() == "MSE Elective") {
            		Recommend("MSE", tsubj4);        			
        		} else if (tsubj4.getText() == "CS Elective") {
            		Recommend("CSE", tsubj4);        			
        		} else if(tsubj4.getText() != "") {
            		DelPopup(tsubj4, tunit4);        			
        		}
        	}
        	
        });

        tsubj5.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		if (tsubj5.getText() == "GE(SSP)") {
            		Recommend("SSP", tsubj5);            		
        		} else if (tsubj5.getText() == "GE(AH)") {
            		Recommend("AH", tsubj5);        			
        		} else if (tsubj5.getText() == "GE(MST)") {
            		Recommend("MST", tsubj5);        			
        		} else if (tsubj5.getText() == "Free Elective") {
            		Recommend("FE", tsubj5);        			
        		} else if (tsubj5.getText() == "MSE Elective") {
            		Recommend("MSE", tsubj5);        			
        		} else if (tsubj5.getText() == "CS Elective") {
            		Recommend("CSE", tsubj5);        			
        		} else if(tsubj5.getText() != "") {
            		DelPopup(tsubj5, tunit5);        			
        		}
        	}
        	
        });

        tsubj6.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		if (tsubj6.getText() == "GE(SSP)") {
            		Recommend("SSP", tsubj6);            		
        		} else if (tsubj6.getText() == "GE(AH)") {
            		Recommend("AH", tsubj6);        			
        		} else if (tsubj6.getText() == "GE(MST)") {
            		Recommend("MST", tsubj6);        			
        		} else if (tsubj6.getText() == "Free Elective") {
            		Recommend("FE", tsubj6);        			
        		} else if (tsubj6.getText() == "MSE Elective") {
            		Recommend("MSE", tsubj6);        			
        		} else if (tsubj6.getText() == "CS Elective") {
            		Recommend("CSE", tsubj6);        			
        		} else if(tsubj6.getText() != "") {
            		DelPopup(tsubj6, tunit6);        			
        		}
        	}
        	
        });

        tsubj7.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		if (tsubj7.getText() == "GE(SSP)") {
            		Recommend("SSP", tsubj7);            		
        		} else if (tsubj7.getText() == "GE(AH)") {
            		Recommend("AH", tsubj7);        			
        		} else if (tsubj7.getText() == "GE(MST)") {
            		Recommend("MST", tsubj7);        			
        		} else if (tsubj7.getText() == "Free Elective") {
            		Recommend("FE", tsubj7);        			
        		} else if (tsubj7.getText() == "MSE Elective") {
            		Recommend("MSE", tsubj7);        			
        		} else if (tsubj7.getText() == "CS Elective") {
            		Recommend("CSE", tsubj7);        			
        		} else if(tsubj7.getText() != "") {
            		DelPopup(tsubj7, tunit7);        			
        		}
        	}
        	
        });

        tsubj8.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		if (tsubj8.getText() == "GE(SSP)") {
            		Recommend("SSP", tsubj8);            		
        		} else if (tsubj8.getText() == "GE(AH)") {
            		Recommend("AH", tsubj8);        			
        		} else if (tsubj8.getText() == "GE(MST)") {
            		Recommend("MST", tsubj8);        			
        		} else if (tsubj8.getText() == "Free Elective") {
            		Recommend("FE", tsubj8);        			
        		} else if (tsubj8.getText() == "MSE Elective") {
            		Recommend("MSE", tsubj8);        			
        		} else if (tsubj8.getText() == "CS Elective") {
            		Recommend("CSE", tsubj8);        			
        		} else if(tsubj8.getText() != "") {
            		DelPopup(tsubj8, tunit8);        			
        		}
        	}
        	
        });

        tsubj9.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		if (tsubj9.getText() == "GE(SSP)") {
            		Recommend("SSP", tsubj9);
        		} else if (tsubj9.getText() == "GE(AH)") {
        			Recommend("AH", tsubj9);
        		} else if (tsubj9.getText() == "GE(MST)") {
        			Recommend("MST", tsubj9);
        		} else if (tsubj9.getText() == "Free Elective") {
        			Recommend("FE", tsubj9);
        		} else if (tsubj9.getText() == "MSE Elective") {
        			Recommend("MSE", tsubj9);
        		} else if (tsubj9.getText() == "CS Elective") {
        			Recommend("CSE", tsubj9);
        		} else if(tsubj9.getText() != "") {
            		DelPopup(tsubj9, tunit9);        			
        		}
        	}
        	
        });
        
        bAdd.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		if (units < 21) {
            		AddPopup();        			
        		} else if (units > 21) {
        			Restrict("over");
        		}
        	}
        	
        });

        bSet.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        	
        	}
        	
        });
        
    //mCursor = mDbA.getAllUntakenSubjects(username);
   	mCursor.moveToFirst();
   	int i = 1;
   	while ( (units < 18) && (i < 9) ) {
   		//mDbA.addEnrollSubject(mCursor.getString(1));
   		getSubjTextView(i).setText(mCursor.getString(1));
		getUnitTextView(i).setText(Integer.toString(mCursor.getInt(2)));
   		units = units + mCursor.getInt(2);
   	}
   		getSubjTextView(i).setText("Total Units:");
   		getUnitTextView(i).setText(Integer.toString(units));
		
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_enroll, menu);
        return true;
    }
    

private void updateView() {
	
	resetView();
	//mCursor = mDbA.getEnrollSubjects(username);
	mCursor.moveToFirst();
	int i = 1;
	while (!mCursor.isAfterLast()) {
		getSubjTextView(i).setText(mCursor.getString(1));
		getUnitTextView(i).setText(Integer.toString(mCursor.getInt(2)));
	}
	getSubjTextView(i).setText("Total Units:");
	getUnitTextView(i).setText(Integer.toString(units));

}

private TextView getSubjTextView(int i) {

	TextView tv = tsubj1;

	if (i == 2) {
		tv = tsubj2;
	} else if (i == 3) {
		tv = tsubj3;
	} else if (i == 4) {
		tv = tsubj4;
	} else if (i == 5) {
		tv = tsubj5;
	} else if (i == 6) {
		tv = tsubj6;
	} else if (i == 7) {
		tv = tsubj7;
	} else if (i == 8) {
		tv = tsubj8;
	} else if (i == 9) {
		tv = tsubj9;
	}
	
	return tv;
	
}

private TextView getUnitTextView(int i) {

	TextView tv = tunit1;

	if (i == 2) {
		tv = tunit2;
	} else if (i == 3) {
		tv = tunit3;
	} else if (i == 4) {
		tv = tunit4;
	} else if (i == 5) {
		tv = tunit5;
	} else if (i == 6) {
		tv = tunit6;
	} else if (i == 7) {
		tv = tunit7;
	} else if (i == 8) {
		tv = tunit8;
	} else if (i == 9) {
		tv = tunit9;
	}
	
	return tv;
	
}

private void resetView() {
	int i = 1;
	while (i < 8) {
		getSubjTextView(i).setText("");
		getUnitTextView(i).setText("");
	}
}

private void Restrict(String restriction) {
AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
helpBuilder.setTitle("Error");
	
	if (restriction == "over") {
		helpBuilder.setMessage("Units cannot exceed 21.");
	} else if (restriction == "under") {
		helpBuilder.setMessage("Units should be at least 15.");
	} else if (restriction == "prereq") {
		helpBuilder.setMessage("Prerequisites not satisfied. Cannot take subject yet.");
	}
	
helpBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
	public void onClick(DialogInterface dialog, int which) {
		
	}
});

	
AlertDialog helpDialog = helpBuilder.create();
helpDialog.show();

}

/*private void updateWebView() {
        
     StringBuilder builder = new StringBuilder();
      
     builder.append("<html><body>");
     builder.append("<h1>");
     //builder.append(mDbA.getCurrentSemester());
     builder.append("</h1>");
     
     mCursor = mDbA.getEnrollSubjects(username);
     mCursor.moveToFirst();
     while(!mCursor.isAfterLast()) {
    	builder.append("<p>");
     	builder.append(mCursor.getString(1) + "\t" + Integer.toString(mCursor.getInt(2));
     	builder.append("</p>");

     }
     
     

     builder.append("</body></html>");
     
}*/

private void Recommend(String type, final TextView t) {
AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
helpBuilder.setTitle("Add subject");

LayoutInflater inflater = getLayoutInflater();
View addPopupLayout = inflater.inflate(R.layout.enroll_addpopup, null);
helpBuilder.setView(addPopupLayout);
ArrayList<String> subjects = new ArrayList<String>();

	if (type == "MST") {
		
		helpBuilder.setMessage("Input desired GE(MST):");
		if (mDbA.getAllCourses().getCount() > 0) { // get from database
			Cursor c = mDbA.getAllUntakenSubjects(username, "MST");
			c.moveToFirst();
			do {
				subjects.add(c.getString(c.getColumnIndex(DbAdapter.KEY_COURSES_NAME)));
			} while(c.moveToNext());
		} else { // get from resources
			String[] strSubjects = getResources().getStringArray(R.array.user_statuses);
			Collections.addAll(subjects, strSubjects);
			
			Toast.makeText(this, "Download failed. Recommended MST list from application installation", Toast.LENGTH_LONG).show();
		}
		
	} else if (type == "SSP") {
		
	} else if (type == "AH") {
		
	} else if (type == "MSE") {
		
	} else if (type == "FE") {
		
	} else if (type == "CSE") {
		
	}
	
	final AutoCompleteTextView autocompleteSubjects = (AutoCompleteTextView) findViewById(R.id.autocomplete_addsubject);
	ArrayAdapter<String> adapterSubjects = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , subjects);
	autocompleteSubjects.setAdapter(adapterSubjects);
	
	helpBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			//mDbA.deleteEnrollSubject(t.getText().toString());
			//mDbA.addEnrollSubject(autocompleteSubjects.getText().toString());
			//t.setText(autocompleteAdd.getText().toString());
		}
	});

	helpBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			
		}
	});

	AlertDialog helpDialog = helpBuilder.create();
	helpDialog.show();

}

private void AddPopup() {
AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
helpBuilder.setTitle("Add subject");
helpBuilder.setMessage("Input desired subject:");

LayoutInflater inflater = getLayoutInflater();
View addPopupLayout = inflater.inflate(R.layout.enroll_addpopup, null);
helpBuilder.setView(addPopupLayout);

ArrayList<String> subjects = new ArrayList<String>();
if (mDbA.getAllUntakenSubjects(username).getCount() > 0) { // get from database
	Cursor c = mDbA.getAllUntakenSubjects(username);
	c.moveToFirst();
	do {
		subjects.add(c.getString(c.getColumnIndex(DbAdapter.KEY_COURSES_NAME)));
	} while(c.moveToNext());
} else { // get from resources
	String[] strSubjects = getResources().getStringArray(R.array.user_statuses);
	Collections.addAll(subjects, strSubjects);
	
	Toast.makeText(this, "Download failed. Subjects list from application installation", Toast.LENGTH_LONG).show();
}
final AutoCompleteTextView autocompleteSubjects = (AutoCompleteTextView) findViewById(R.id.autocomplete_addsubject);
ArrayAdapter<String> adapterSubjects = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , subjects);
autocompleteSubjects.setAdapter(adapterSubjects);

helpBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
	public void onClick(DialogInterface dialog, int which) {
		//mDbA.addEnrollSubject(autocompleteSubjects.getText().toString());
		//units = units + mDbA.getunit(autocompleteSubjects.getText().toString());
	}
});

helpBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	public void onClick(DialogInterface dialog, int which) {
		
	}
});

AlertDialog helpDialog = helpBuilder.create();
helpDialog.show();

updateView();

}

private void DelPopup(final TextView t, final TextView u) {

AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);

helpBuilder.setTitle("Delete " + t.getText());		
helpBuilder.setMessage("Are you sure you want to delete " + t.getText() + " ?");

helpBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	public void onClick(DialogInterface dialog, int which) {
		//mDbA.deleteEnrollSubject(t.getText());
		units = units - Integer.parseInt(u.getText().toString());
	}
});
helpBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	public void onClick(DialogInterface dialog, int which) {
		
	}
});

AlertDialog helpDialog = helpBuilder.create();
helpDialog.show();

updateView();

}

}
package upd.edu.ph.check_up;

import java.util.List;

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


public class Enroll extends Activity {

	private TextView tsemester;
	private TextView tsubj1;
	private TextView tsubj2;
	private TextView tsubj3;
	private TextView tsubj4;
	private TextView tsubj5;
	private TextView tsubj6;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);

        tsemester = (TextView) findViewById(R.id.semester);
        tsubj1 = (TextView) findViewById(R.id.subject1);
        tsubj2 = (TextView) findViewById(R.id.subject2);
        tsubj3 = (TextView) findViewById(R.id.subject3);
        tsubj4 = (TextView) findViewById(R.id.subject4);
        tsubj5 = (TextView) findViewById(R.id.subject5);
        tsubj6 = (TextView) findViewById(R.id.subject6);
        tsubj1.setText("GE(SSP)");
        tsubj6.setText("click to add subject");
        tsubj1.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		if((tsubj1.getText() == "click to add subject") || (tsubj1.getText() == "GE(SSP)")) {
            		AddPopup(tsubj1);        			
        		} else {
        			DelPopup(tsubj1);
        		}
        	}
        	
        });
        
        tsubj2.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		if(tsubj2.getText() == "click to add subject") {
            		AddPopup(tsubj2);        			
        		} else {
        			DelPopup(tsubj2);
        		}
        	}
        	
        });

        tsubj3.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		if(tsubj3.getText() == "click to add subject") {
            		AddPopup(tsubj3);        			
        		} else {
        			DelPopup(tsubj3);
        		}
        	}
        	
        });

        tsubj4.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		if(tsubj4.getText() == "click to add subject") {
            		AddPopup(tsubj4);        			
        		} else {
        			DelPopup(tsubj4);
        		}
        	}
        	
        });

        tsubj5.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		if(tsubj5.getText() == "click to add subject") {
            		AddPopup(tsubj5);        			
        		} else {
        			DelPopup(tsubj5);
        		}
        	}
        	
        });

        tsubj6.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		if(tsubj6.getText() == "click to add subject") {
            		AddPopup(tsubj6);        			
        		} else {
        			DelPopup(tsubj6);
        		}
        	}
        	
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_enroll, menu);
        return true;
    }

    
private void AddPopup(final TextView t) {
	AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
	helpBuilder.setTitle("Add subject");
	helpBuilder.setMessage("Input desired subject:");
	final EditText input = new EditText(this);
	input.setSingleLine();
	input.setText("");
	helpBuilder.setView(input);

	helpBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
				t.setText(input.getText());
		}
	});

	helpBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			
		}
	});
	
	AlertDialog helpDialog = helpBuilder.create();
	helpDialog.show();
	
}

private void DelPopup(final TextView t) {
	
	AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
	
	helpBuilder.setTitle("Delete " + t.getText());		
	helpBuilder.setMessage("Are you sure you want to delete " + t.getText() + " ?");

	helpBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			t.setText("click to add subject");
		}
	});
	helpBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			
		}
	});

	AlertDialog helpDialog = helpBuilder.create();
	helpDialog.show();

}
}
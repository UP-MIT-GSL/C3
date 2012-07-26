package com.example.note;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.database.Cursor;
import android.webkit.WebView;
import android.widget.SimpleCursorAdapter;
import android.app.ListActivity;

public class NotepadActivity extends Activity{
	
	private DbAdapter mDbHelper;
	private WebView mWebView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbtest);
        
        mWebView = (WebView) findViewById(R.id.webView1);
        mWebView.getSettings().setJavaScriptEnabled(true);
        
        mDbHelper = new DbAdapter(this);
        mDbHelper.open();
        //use mDbHelper to access database data
        mDbHelper.addCollege("College of Arts and Letters");
        mDbHelper.addCollege("College of Law");
        fillWithCollegeData();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    /**
     * fills test activity with college data form the database
     */
	@SuppressLint("ParserError")
	private void fillWithCollegeData() {
    	Cursor c = mDbHelper.getAllColleges();
    	StringBuilder builder = new StringBuilder();
        builder.append("<html><body>");
        builder.append(mDbHelper.ifCollegeExists("College of Law") + "<br>" + c.getCount());
        //c.moveToFirst();
        //builder.append(c.getString(0)+ "<br>");
        //c.moveToNext();
        //builder.append(c.getString(0)+ "<br>");
        builder.append("</body></html>");

        mWebView.loadData(builder.toString(), "text/html", "UTF-8");
    }
    
}

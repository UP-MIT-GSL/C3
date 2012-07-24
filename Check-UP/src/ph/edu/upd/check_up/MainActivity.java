package ph.edu.upd.check_up;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.content.Intent;
import android.database.Cursor;
import android.webkit.WebView;

public class MainActivity extends Activity{
	
	private DbAdapter mDbHelper;
	private WebView mWebView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mWebView = (WebView) findViewById(R.id.webView1);
        mWebView.getSettings().setJavaScriptEnabled(true);
        
        mDbHelper = new DbAdapter(this);
        mDbHelper.open();
        mDbHelper.resetDatabase();
        //use mDbHelper to access database data
        //mDbHelper.addCollege("College of Arts and Letters");
        //mDbHelper.addCollege("College of Law");
        
        Bundle extras = getIntent().getExtras();
        
        mDbHelper.addCollege(extras.getString("username"));
        mDbHelper.addCollege(extras.getString("password"));
        fillWithCollegeData();
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_sign_out, menu);
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
        c.moveToFirst();
        builder.append("user " + c.getString(0)+ " logged-in!<br>");
        c.moveToNext();
        builder.append("password: " + c.getString(0)+ "<br>");
        builder.append("</body></html>");

        mWebView.loadData(builder.toString(), "text/html", "UTF-8");
    }
    
	public void signOut(View v) {
		Intent intent = new Intent(MainActivity.this, SignOut.class);
        startActivity(intent);
	}
}
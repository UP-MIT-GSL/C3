package ph.edu.upd.check_up;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TabLayout extends TabActivity {
//	String username;
//	String password;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tablayout);

//		Bundle extras = getIntent().getExtras();
//		this.username = extras.getString("username");
//		this.password = extras.getString("password");

		Resources res = getResources();
		TabHost tabHost = getTabHost();

		// status tab
		Intent intentStatus = new Intent().setClass(this, Status.class);
		TabSpec tabSpecStatus = tabHost
				.newTabSpec("Status")
				.setIndicator("Status", res.getDrawable(R.drawable.icon_status))
				.setContent(intentStatus);

		// checklist tab
		Intent intentChecklist = new Intent().setClass(this, Checklist.class);
		TabSpec tabSpecChecklist = tabHost
				.newTabSpec("Checklist")
				.setIndicator("Checklist",
						res.getDrawable(R.drawable.icon_checklist))
				.setContent(intentChecklist);

		// history tab
		Intent intentHistory = new Intent().setClass(this, History.class);
		TabSpec tabSpecHistory = tabHost
				.newTabSpec("History")
				.setIndicator("History",
						res.getDrawable(R.drawable.icon_history))
				.setContent(intentHistory);

		// enroll tab
		Intent intentEnroll = new Intent().setClass(this, Enroll.class);
		TabSpec tabSpecEnroll = tabHost
				.newTabSpec("Enroll")
				.setIndicator("Enroll", res.getDrawable(R.drawable.icon_enroll))
				.setContent(intentEnroll);

		// logout tab
		Intent intentLogout = new Intent().setClass(this, Logout.class);
		TabSpec tabSpecLogout = tabHost
				.newTabSpec("Logout")
				.setIndicator("Logout", res.getDrawable(R.drawable.icon_logout))
				.setContent(intentLogout);

//		intentStatus.putExtra("username", this.username);
//		intentStatus.putExtra("password", this.password);
//		intentChecklist.putExtra("username", this.username);
//		intentChecklist.putExtra("password", this.password);
//		intentHistory.putExtra("username", this.username);
//		intentHistory.putExtra("password", this.password);
//		intentEnroll.putExtra("username", this.username);
//		intentEnroll.putExtra("password", this.password);
//		intentLogout.putExtra("username", this.username);
//		intentLogout.putExtra("password", this.password);

		tabHost.addTab(tabSpecStatus);
		tabHost.addTab(tabSpecChecklist);
		tabHost.addTab(tabSpecHistory);
		tabHost.addTab(tabSpecEnroll);
		tabHost.addTab(tabSpecLogout);
	}
}
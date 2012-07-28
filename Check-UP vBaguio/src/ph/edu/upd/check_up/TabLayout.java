package ph.edu.upd.check_up;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TabLayout extends TabActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tablayout);
		
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
				.setIndicator("Checklist", res.getDrawable(R.drawable.icon_checklist))
				.setContent(intentChecklist);
		
		// history tab
		Intent intentHistory = new Intent().setClass(this, History.class);
		TabSpec tabSpecHistory = tabHost
				.newTabSpec("History")
				.setIndicator("History", res.getDrawable(R.drawable.icon_history))
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
		
		tabHost.addTab(tabSpecChecklist);
		tabHost.addTab(tabSpecEnroll);
		tabHost.addTab(tabSpecStatus);
		tabHost.addTab(tabSpecHistory);
		tabHost.addTab(tabSpecLogout);
	}
}
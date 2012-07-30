package ph.edu.upd.check_up;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;


public class Grades extends Activity {
	WebView mWebView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grades);
		mWebView = (WebView) findViewById(R.id.webview_grades);
		mWebView.getSettings().setJavaScriptEnabled(true);
		updateWebView();
	}

	private void updateWebView() {
		StringBuilder builder = new StringBuilder();
		builder.append("<html><body><table>");
		builder.append("<tr><td>Hello world</td></tr>");
		builder.append("</table></body></html>");
		mWebView.loadData(builder.toString(), "text/html", "UTF-8");
	}
}
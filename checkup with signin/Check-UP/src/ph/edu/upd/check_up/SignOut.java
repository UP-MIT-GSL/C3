package ph.edu.upd.check_up;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.*;

public class SignOut extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_out);
        
        Button button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(new OnClickListener() {
            
        	//@Override
            public void onClick(View v) {
            Intent intent=new Intent(SignOut.this,SignIn.class);
            startActivity(intent);
            }
        });
    }

    

    
}

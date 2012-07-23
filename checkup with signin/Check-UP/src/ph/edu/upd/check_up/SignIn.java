package ph.edu.upd.check_up;

import android.os.Bundle;
import android.app.Activity;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SignIn extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        
        Button launch = (Button)findViewById(R.id.btn_signin);
        
        // this is the action listener
        launch.setOnClickListener( new OnClickListener()
        {
               
        	public void onClick(View viewParam)
            {
                // this gets the resources in the xml file and assigns it to a local variable of type EditText
                EditText usernameEditText = (EditText) findViewById(R.id.editText1);
                EditText passwordEditText = (EditText) findViewById(R.id.editText2);
               
                // the getText() gets the current value of the text box
                // the toString() converts the value to String data type
                // then assigns it to a variable of type String
                String UserName = usernameEditText.getText().toString();
                String Password = passwordEditText.getText().toString();
                       
                // catches the error if the program can't locate the GUI stuff
                if(usernameEditText == null || passwordEditText == null){
              
                	//showAlert("Crap!", "Couldn't find the 'txt_username' or 'txt_password' "
                      //        + "EditView in main.xml", "Oh shit!", false);
                } 
                
                else{
                        // display the username and the password in string format
                        //showAlert("Logging in", "Username: " + UserName + "nPassword: " + Password , "Ok", true);
                        }
                }
        });
        
    }    
}


package ph.edu.upd.check_up;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SignIn extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        
        Button button = (Button)findViewById(R.id.btn_signin);
        
        // this is the action listener
        button.setOnClickListener( new OnClickListener()
        {
               
        	public void onClick(View viewParam)
            {
                // this gets the resources in the xml file and assigns it to a local variable of type EditText
                EditText usernameEditText = (EditText) findViewById(R.id.editText1);
                EditText passwordEditText = (EditText) findViewById(R.id.editText2);
               
                // the getText() gets the current value of the text box
                // the toString() converts the value to String data type
                // then assigns it to a variable of type String
                //String UserName = usernameEditText.getText().toString();
                //String Password = passwordEditText.getText().toString();
                       
                // catches the error if the program can't locate the GUI stuff
                if(usernameEditText == null || passwordEditText == null){
                	//do this
                } 
                
                else{                        
                        Intent intent=new Intent(SignIn.this,MainActivity.class);
                        intent.putExtra("username", usernameEditText.getText().toString());
                        intent.putExtra("password", passwordEditText.getText().toString());
                        startActivity(intent);
                                  
                }
                }
        });
        
    }    
}


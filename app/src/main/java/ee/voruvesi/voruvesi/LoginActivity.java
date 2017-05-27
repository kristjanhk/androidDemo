package ee.voruvesi.voruvesi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    public static final String USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString(USERNAME, null);
        if (username != null) {
            changeActivity();
            return;
        }
        final EditText userNameField = (EditText) findViewById(R.id.insert_username);
        final Button continueButton = (Button) findViewById(R.id.login_continue);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userNameField.getText().toString();
                if (username == null || username.length() == 0) {
                    Toast.makeText(LoginActivity.this, "Username was invalid", Toast.LENGTH_SHORT).show();
                    return;
                }
                prefs.edit().putString("username", username).apply();
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                database.child("Töötajad").child(username).setValue(true);
                changeActivity();
            }
        });
    }

    private void changeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

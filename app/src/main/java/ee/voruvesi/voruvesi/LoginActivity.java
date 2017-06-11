package ee.voruvesi.voruvesi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//Sisselogimise/ kasutajanime sisestamise ekraan
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //final FirebaseAuth auth = FirebaseAuth.getInstance();
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this); //salvestatud seaded
        String username = prefs.getString("username", null); //võtame seadetest kasutajanime
        if (username != null) { //kui see olemas -> oleme varem seda äppi jooksutanud
           /* auth.signInWithEmailAndPassword(username, username)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        changeActivity();
                    } else {
                        Toast.makeText(LoginActivity.this, "LOGIN FAIL", Toast.LENGTH_SHORT).show();
                    }
                }
            });*/
            changeActivity(); //läheme MainActivityle
            return;
        }
        final EditText userNameField = (EditText) findViewById(R.id.insert_username); //nime sisestamise väli
        final Button continueButton = (Button) findViewById(R.id.login_continue); //edasimineku nupp
        continueButton.setOnClickListener(new View.OnClickListener() { //kui nuppu vajutame
            @Override
            public void onClick(View v) {
                String username = userNameField.getText().toString(); //sisestatud kasutajanimi
                if (username == null || username.length() == 0) { //kui seda polnud
                    Toast.makeText(LoginActivity.this,
                        getString(R.string.invalid_username), Toast.LENGTH_SHORT).show(); //teade
                    return;
                }
                prefs.edit().putString("username", username).apply(); //
                //auth.createUserWithEmailAndPassword(username, username);
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                database.child("Töötajad").child(username).setValue(true);
                changeActivity();
            }
        });
    }

    private void changeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent); //vahetame MainActivityle
        finish(); //siia activityle tagasi enam ei tule (backiga)
    }
}

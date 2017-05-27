package ee.voruvesi.voruvesi;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.database.*;
import ee.voruvesi.voruvesi.adapter.ObjectsCardViewAdapter;
import ee.voruvesi.voruvesi.listener.WorkObjectsListener;

import java.util.ArrayList;
import java.util.List;

// Objektide ekraan
public class MainActivity extends AppCompatActivity {
    private final List<String> workObjects = new ArrayList<>(); //list objektidest

    // TODO: 26.05.2017 lifecycle stuff
    // TODO: 27/05/2017 tõlked -> est, eng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setTitle(R.string.choose_object); //määrame teksti menüüribale kui see eksisteerib (vanematel androididel ei pruugi vist)
        }
        DatabaseReference database = FirebaseDatabase.getInstance().getReference(); //firebase db
        RecyclerView view = (RecyclerView) findViewById(R.id.recycler_view_main); //vaade mis hoiab endas kaarte
        view.setHasFixedSize(true); //laius ei muutu kunagi
        view.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.Adapter adapter = new ObjectsCardViewAdapter(workObjects, this); //adapter objektidelisti ja kaartide vaate vahel
        view.setAdapter(adapter); //määrame vaatele adapteri
        //lisame andmebaasi "Objektid"-le kuulaja
        database.child("Objektid").addChildEventListener(
                new WorkObjectsListener(workObjects, adapter, (ProgressBar) findViewById(R.id.progressBar)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //loome menüüriba
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu); //seame menüüribale layouti
        return true;
    }
}

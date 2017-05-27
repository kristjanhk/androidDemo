package ee.voruvesi.voruvesi;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import com.google.firebase.database.*;
import ee.voruvesi.voruvesi.adapter.MaintenanceCardViewAdapter;
import ee.voruvesi.voruvesi.entity.Work;
import ee.voruvesi.voruvesi.listener.DoneWorksListener;
import ee.voruvesi.voruvesi.listener.MaintenanceWorksListener;

import java.util.Map;
import java.util.TreeMap;

// Hooldustööde ekraan ühele objektile
public class MaintenanceActivity extends AppCompatActivity {
    //hoiame töid sorteeritud mapis, et sellest alati samasugust listi kätte saada ja ainult viimast tööd alles hoida
    private final Map<String, Work> works = new TreeMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setTitle(R.string.choose_maintenance_work); //määrame teksti menüüribale kui see eksisteerib (vanematel androididel ei pruugi vist)
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this); //kohalikud seaded
        final String user = prefs.getString("username", "Teadmata"); //võtame seadetest kasutajanime, "Teadmata" kui seda ei ole (ei tohiks juhtuda)
        final String workObject = getIntent().getStringExtra("workObject"); //eelmisest activityst saadud objekti nimi
        DatabaseReference database = FirebaseDatabase.getInstance().getReference(); //firebase db
        RecyclerView view = (RecyclerView) findViewById(R.id.recyclerView); //kaartide vaade
        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.Adapter adapter = new MaintenanceCardViewAdapter(works, this, user, workObject); //vaate ja mapi vaheline adapter
        view.setAdapter(adapter);
        database.child("Hooldustööd").addChildEventListener(new MaintenanceWorksListener(works, adapter, workObject)); //kuulame "Hooldustööd"-e väärtuste muutusi
        database.child("Tehtud tööd").addChildEventListener(new DoneWorksListener(works, adapter, workObject)); //kuulame "Tehtud tööd"-e väärtuste muutusi
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //loome menüüriba
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu); //seame sellele layouti
        return true;
    }
}

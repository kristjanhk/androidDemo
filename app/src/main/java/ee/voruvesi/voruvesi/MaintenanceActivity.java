package ee.voruvesi.voruvesi;

import android.os.Bundle;
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

public class MaintenanceActivity extends AppCompatActivity {
    private final Map<String, Work> works = new TreeMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setTitle(R.string.choose_maintenance_work);
        }
        final String user = "Aimar";
        final String workObject = getIntent().getStringExtra("workObject");
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        RecyclerView view = (RecyclerView) findViewById(R.id.recyclerView);
        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.Adapter adapter = new MaintenanceCardViewAdapter(works, this, user, workObject);
        view.setAdapter(adapter);
        database.child("Hooldustööd").addChildEventListener(new MaintenanceWorksListener(works, adapter, workObject));
        database.child("Tehtud tööd").addChildEventListener(new DoneWorksListener(works, adapter, workObject));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}

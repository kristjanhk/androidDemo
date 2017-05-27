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

public class MainActivity extends AppCompatActivity {
    private final List<String> workObjects = new ArrayList<>();

    // TODO: 26.05.2017 lifecycle stuff
    // TODO: 27/05/2017 tõlked -> est, eng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setTitle(R.string.choose_object);
        }
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        RecyclerView view = (RecyclerView) findViewById(R.id.recycler_view_main);
        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.Adapter adapter = new ObjectsCardViewAdapter(workObjects, this);
        view.setAdapter(adapter);
        database.child("Objektid").addChildEventListener(
                new WorkObjectsListener(workObjects, adapter, (ProgressBar) findViewById(R.id.progressBar)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}

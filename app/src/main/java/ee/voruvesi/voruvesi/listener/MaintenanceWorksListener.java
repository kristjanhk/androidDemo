package ee.voruvesi.voruvesi.listener;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import ee.voruvesi.voruvesi.entity.Work;

import java.util.Map;

public class MaintenanceWorksListener implements ChildEventListener {
    private final Map<String, Work> works;
    private final RecyclerView.Adapter adapter;
    private final String workObject;

    public MaintenanceWorksListener(Map<String, Work> works, RecyclerView.Adapter adapter, String workObject) {
        this.works = works;
        this.adapter = adapter;
        this.workObject = workObject;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        String workName = dataSnapshot.getValue(String.class);
        if (!works.containsKey(workName)) {
            Work work = new Work();
            work.setWorkName(workName);
            work.setWorkObject(workObject);
            works.put(workName, work);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        String workName = dataSnapshot.getValue(String.class);
        if (works.containsKey(workName)) {
            works.remove(workName);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.d("Firebase error", databaseError.getMessage(), databaseError.toException());
    }
}

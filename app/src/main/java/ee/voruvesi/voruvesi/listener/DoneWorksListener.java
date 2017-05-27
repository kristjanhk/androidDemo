package ee.voruvesi.voruvesi.listener;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import ee.voruvesi.voruvesi.entity.Work;

import java.util.Map;

public class DoneWorksListener implements ChildEventListener {
    private final Map<String, Work> works;
    private final RecyclerView.Adapter adapter;
    private final String workObject;

    public DoneWorksListener(Map<String, Work> works, RecyclerView.Adapter adapter, String workObject) {
        this.works = works;
        this.adapter = adapter;
        this.workObject = workObject;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        String workName = dataSnapshot.child("Hooldustöö").getValue(String.class);
        String object = dataSnapshot.child("Objekt").getValue(String.class);
        String username = dataSnapshot.child("Töötaja").getValue(String.class);
        String date = dataSnapshot.child("Aeg").getValue(String.class);
        if (workObject.equals(object) && (!works.containsKey(workName) || works.get(workName).isBefore(date))) {
            works.put(workName, new Work(workName, object, username, date));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        // TODO: 26.05.2017 ??
        System.out.println("Done works removed one");
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.d("Firebase error", databaseError.getMessage(), databaseError.toException());
    }
}

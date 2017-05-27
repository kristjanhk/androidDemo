package ee.voruvesi.voruvesi.listener;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.List;

public class WorkObjectsListener implements ChildEventListener {
    private final List<String> workObjects;
    private final RecyclerView.Adapter adapter;
    private final ProgressBar bar;

    public WorkObjectsListener(List<String> workObjects, RecyclerView.Adapter adapter, ProgressBar bar) {
        this.workObjects = workObjects;
        this.adapter = adapter;
        this.bar = bar;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        workObjects.add(dataSnapshot.getValue(String.class));
        adapter.notifyDataSetChanged();
        if (bar.getVisibility() == View.VISIBLE) {
            bar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        String workName = (String) dataSnapshot.getValue();
        for (String object : workObjects) {
            if (object.equals(workName)) {
                workObjects.remove(object);
                break;
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.d("Firebase error", databaseError.getMessage(), databaseError.toException());
    }
}

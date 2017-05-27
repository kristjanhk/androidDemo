package ee.voruvesi.voruvesi.listener;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import ee.voruvesi.voruvesi.entity.Work;

import java.util.Map;

//kuulab "Hooldustööd" andmeid
public class MaintenanceWorksListener implements ChildEventListener {
    private final Map<String, Work> works;
    private final RecyclerView.Adapter adapter;
    private final String workObject;

    //saadud info MaintenanceActivityst
    public MaintenanceWorksListener(Map<String, Work> works, RecyclerView.Adapter adapter, String workObject) {
        this.works = works;
        this.adapter = adapter;
        this.workObject = workObject;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) { //kui väärtus muutub
        String workName = dataSnapshot.getValue(String.class); //hooldustöö nimi
        if (!works.containsKey(workName)) { //kui tööde mapis pole sellise võtmega tööd veel
            Work work = new Work();
            work.setWorkName(workName);
            work.setWorkObject(workObject);
            works.put(workName, work); //lisame selle
            adapter.notifyDataSetChanged(); //teavitame adapterit
        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        //eeldame et andmed ei muudeta
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) { //kui väärtus kustutatakse
        String workName = dataSnapshot.getValue(String.class); //hooldustöö nimi
        if (works.containsKey(workName)) { //kui selline olemas (alati peaks olema tglt)
            works.remove(workName);
            adapter.notifyDataSetChanged(); //teavitame adapterit
        }
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        //eeldame et andmed ei muudeta
    }

    @Override
    public void onCancelled(DatabaseError databaseError) { //vea korral logime
        Log.d("Firebase error", databaseError.getMessage(), databaseError.toException());
    }
}

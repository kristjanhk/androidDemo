package ee.voruvesi.voruvesi.listener;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import ee.voruvesi.voruvesi.entity.Work;

import java.util.Map;

// Kuulab "Tehtud tööd"-d
public class DoneWorksListener implements ChildEventListener {
    private final Map<String, Work> works;
    private final RecyclerView.Adapter adapter;
    private final String workObject;

    //saadud kraam MaintenanceActivityst
    public DoneWorksListener(Map<String, Work> works, RecyclerView.Adapter adapter, String workObject) {
        this.works = works;
        this.adapter = adapter;
        this.workObject = workObject;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) { //kui väärtus lisati
        //töö info
        String workName = dataSnapshot.child("Hooldustöö").getValue(String.class);
        String object = dataSnapshot.child("Objekt").getValue(String.class);
        String username = dataSnapshot.child("Töötaja").getValue(String.class);
        String date = dataSnapshot.child("Aeg").getValue(String.class);
        //kui objekt on tööl sama ja (sellist hooldusttööd pole veel lisatud hooldustööde kuulaja poolt või olemasolev töö on varem tehtud kui uue väärtuse oma)
        if (workObject.equals(object) && (!works.containsKey(workName) || works.get(workName).isBefore(date))) {
            works.put(workName, new Work(workName, object, username, date)); //asendame vana töö uuega
            adapter.notifyDataSetChanged(); //teavitame adapterit
        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        //eeldame et andmed ei muudeta
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) { //kui väärtus kustutatakse
        // TODO: 26.05.2017 ??
        System.out.println("Done works removed one"); //võime viimase ära kustutada aga me ei tea mis enne seda oli (mapis ainult viimane kirjas)
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

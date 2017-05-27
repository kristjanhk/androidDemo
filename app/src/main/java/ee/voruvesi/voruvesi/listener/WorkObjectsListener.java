package ee.voruvesi.voruvesi.listener;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.List;

//kuulab andmebaasi "Objektid" väärtusi
public class WorkObjectsListener implements ChildEventListener {
    private final List<String> workObjects;
    private final RecyclerView.Adapter adapter;
    private final ProgressBar bar;

    //saame MainActivityst objektide listi, adapteri ja laadimisikooni
    public WorkObjectsListener(List<String> workObjects, RecyclerView.Adapter adapter, ProgressBar bar) {
        this.workObjects = workObjects;
        this.adapter = adapter;
        this.bar = bar;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) { //kui lisatakse uus väärtus
        workObjects.add(dataSnapshot.getValue(String.class)); //lisame selle listi
        adapter.notifyDataSetChanged(); //anname adapterile teada et andmed muutusid -> rendardab ekraani uuesti
        if (bar.getVisibility() == View.VISIBLE) { // kui laadimisikoon veel nähtaval on
            bar.setVisibility(View.INVISIBLE); //teeme selle nähtamatuks
        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        //eeldame et andmed ei muudeta
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) { //kui väärtus kustutatakse
        String workName = (String) dataSnapshot.getValue(); //väärtus
        for (String object : workObjects) {
            if (object.equals(workName)) { //otsime selle listist üles
                workObjects.remove(object); //kustutame
                break;
            }
        }
        adapter.notifyDataSetChanged(); //anname adapterile teada et andmed muutusid -> rendardab ekraani uuesti
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        //eeldame et andmetid ei liigutata
    }

    @Override
    public void onCancelled(DatabaseError databaseError) { //vea korral logime
        Log.d("Firebase error", databaseError.getMessage(), databaseError.toException());
    }
}

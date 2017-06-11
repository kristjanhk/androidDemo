package ee.voruvesi.voruvesi.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import ee.voruvesi.voruvesi.R;
import ee.voruvesi.voruvesi.entity.Work;

import java.util.*;

//Maintenance hooldustööde vaate adapter
public class MaintenanceCardViewAdapter extends RecyclerView.Adapter<MaintenanceCardViewAdapter.ViewHolder> {
    private final Map<String, Work> works;
    private final Context context;
    private final DatabaseReference database;
    private final String user;
    private final String workObject;

    //MaintenanceActivityst saadud info
    public MaintenanceCardViewAdapter(Map<String, Work> works, Context context, String user, String workObject) {
        this.works = works;
        this.context = context;
        this.user = user;
        this.workObject = workObject;
        database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public MaintenanceCardViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);
        return new ViewHolder(v, context); //seame layouti
    }

    @Override
    public void onBindViewHolder(MaintenanceCardViewAdapter.ViewHolder holder, int position) {
        List<Map.Entry<String, Work>> entries = new ArrayList<>(works.entrySet()); //teeme mapi listiks, kuna map on sorteeritud siis list alati samas järjekorras
        Work work = entries.get(position).getValue(); //selles kohas olev töö
        holder.workName.setText(work.getWorkName()); //töö nimi
        holder.lastUser.setText(context.getString(R.string.last_maintenance_work_user, work.getUsername())); //viimati teostanud kasutaja
        holder.lastTime.setText(context.getString(R.string.last_maintenance_work_time, work.getDate())); //viimati tehtud aeg
        String today = Work.toDateString(System.currentTimeMillis()); //tänane kuupäev
        if (work.getDate().equals(today)) { //kui töö kuupäev on täna
            holder.tickImage.setVisibility(View.VISIBLE); //teeme pildi nähtavaks
        }
    }

    @Override
    public int getItemCount() {
        return works.size(); //hooldustööde koguarv sellele antud objektile
    }

    public class ViewHolder extends RecyclerView.ViewHolder { //tegeleb vaatega
        private CardView cv;
        private TextView lastTime;
        private TextView workName;
        private TextView lastUser;
        private ImageView tickImage;

        public ViewHolder(View v, final Context context) {
            super(v);
            cv = (CardView) v.findViewById(R.id.cardView); //kaart
            lastTime = (TextView) v.findViewById(R.id.maintenance_last_time); //aeg
            workName = (TextView) v.findViewById(R.id.maintenance_work); //hooldustöö nimi
            lastUser = (TextView) v.findViewById(R.id.maintenance_last_user); //viimane teostaja
            tickImage = (ImageView) v.findViewById(R.id.maintenance_work_tick); //pilt
            cv.setOnClickListener(new View.OnClickListener() { //klikil
                @Override
                public void onClick(View view) {
                    Work currentWork = works.get(workName.getText().toString()); //hetkel oleva töö nimi
                    Work newWork = new Work(currentWork.getWorkName(), workObject, user, System.currentTimeMillis()); //uus loodav töö
                    if (currentWork.isBefore(newWork.getDate())) { //kui hetkel olev on vanem kui uus
                        DatabaseReference workRef = database.child("Tehtud tööd").push(); //lisame uue kirje firebasei
                        Map<String, Object> map = new HashMap<>(); //selle kirje väärtused
                        map.put("Aeg", newWork.getDate());
                        map.put("Hooldustöö", newWork.getWorkName());
                        map.put("Objekt", newWork.getWorkObject());
                        map.put("Töötaja", newWork.getUsername());
                        workRef.setValue(map); //saadame väärtused
                        tickImage.setVisibility(View.VISIBLE); //teeme pildi nähtavaks
                        //anname teada et kõik õnnestus
                        Toast.makeText(context, workName.getText() + context.getString(R.string.work_updated),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        //anname teada et täna juba kirjas töö
                        Toast.makeText(context, workName.getText() + context.getString(R.string.work_already_done),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}

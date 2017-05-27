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

public class MaintenanceCardViewAdapter extends RecyclerView.Adapter<MaintenanceCardViewAdapter.ViewHolder> {
    private final Map<String, Work> works;
    private final Context context;
    private final DatabaseReference database;
    private final String user;
    private final String workObject;

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
        return new ViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(MaintenanceCardViewAdapter.ViewHolder holder, int position) {
        List<Map.Entry<String, Work>> entries = new ArrayList<>(works.entrySet());
        Work work = entries.get(position).getValue();
        holder.workName.setText(work.getWorkName());
        holder.lastUser.setText(context.getString(R.string.last_maintenance_work_user, work.getUsername()));
        holder.lastTime.setText(context.getString(R.string.last_maintenance_work_time, work.getDate()));
        String today = Work.toDateString(System.currentTimeMillis());
        if (work.getDate().equals(today)) {
            holder.tickImage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return works.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        private TextView lastTime;
        private TextView workName;
        private TextView lastUser;
        private ImageView tickImage;

        public ViewHolder(View v, final Context context) {
            super(v);
            cv = (CardView) v.findViewById(R.id.cardView);
            lastTime = (TextView) v.findViewById(R.id.maintenance_last_time);
            workName = (TextView) v.findViewById(R.id.maintenance_work);
            lastUser = (TextView) v.findViewById(R.id.maintenance_last_user);
            tickImage = (ImageView) v.findViewById(R.id.maintenance_work_tick);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Work currentWork = works.get(workName.getText().toString());
                    Work newWork = new Work(currentWork.getWorkName(), workObject, user, System.currentTimeMillis());
                    if (currentWork.isBefore(newWork.getDate())) {
                        DatabaseReference workRef = database.child("Tehtud tööd").push();
                        Map<String, Object> map = new HashMap<>();
                        map.put("Aeg", newWork.getDate());
                        map.put("Hooldustöö", newWork.getWorkName());
                        map.put("Objekt", newWork.getWorkObject());
                        map.put("Töötaja", newWork.getUsername());
                        workRef.setValue(map);
                        tickImage.setVisibility(View.VISIBLE);
                        Toast.makeText(context, workName.getText() + " updated!",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, workName.getText() + " is already done today!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}

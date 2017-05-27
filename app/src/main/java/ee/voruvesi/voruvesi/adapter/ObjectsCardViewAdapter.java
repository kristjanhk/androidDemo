package ee.voruvesi.voruvesi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ee.voruvesi.voruvesi.MaintenanceActivity;
import ee.voruvesi.voruvesi.R;

import java.util.List;

public class ObjectsCardViewAdapter extends RecyclerView.Adapter<ObjectsCardViewAdapter.ViewHolder> {
    private final List<String> workObjects;
    private final Context context;

    public ObjectsCardViewAdapter(List<String> workObjects, Context context) {
        this.workObjects = workObjects;
        this.context = context;
    }

    @Override
    public ObjectsCardViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.work_objects_cardview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ObjectsCardViewAdapter.ViewHolder holder, int position) {
        holder.object.setText(workObjects.get(position));
    }

    @Override
    public int getItemCount() {
        return workObjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        private TextView object;

        public ViewHolder(View v) {
            super(v);
            cv = (CardView) v.findViewById(R.id.work_cardview);
            object = (TextView) v.findViewById(R.id.object_name);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent intent = new Intent(context, MaintenanceActivity.class);
                    intent.putExtra("workObject", object.getText().toString());
                    context.startActivity(intent);
                }
            });
        }
    }
}

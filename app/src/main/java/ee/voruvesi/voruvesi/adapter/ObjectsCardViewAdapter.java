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

//MainActivity objektide vaate adapter
public class ObjectsCardViewAdapter extends RecyclerView.Adapter<ObjectsCardViewAdapter.ViewHolder> {
    private final List<String> workObjects;
    private final Context context;

    //MainActivityst saadud info
    public ObjectsCardViewAdapter(List<String> workObjects, Context context) {
        this.workObjects = workObjects;
        this.context = context;
    }

    @Override
    public ObjectsCardViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.work_objects_cardview, parent, false);
        return new ViewHolder(v); //seame layouti
    }

    @Override
    public void onBindViewHolder(ObjectsCardViewAdapter.ViewHolder holder, int position) {
        holder.object.setText(workObjects.get(position)); //seame antud kaardi väljale objekti nime
    }

    @Override
    public int getItemCount() {
        return workObjects.size(); //objektide koguarv
    }

    public class ViewHolder extends RecyclerView.ViewHolder { //tegeleb vaatega
        private CardView cv;
        private TextView object;

        public ViewHolder(View v) {
            super(v);
            cv = (CardView) v.findViewById(R.id.work_cardview); //kaart
            object = (TextView) v.findViewById(R.id.object_name); //objekti nimi
            cv.setOnClickListener(new View.OnClickListener() { //kaardile klikkides
                @Override
                public void onClick(View v) {
                    final Intent intent = new Intent(context, MaintenanceActivity.class);
                    intent.putExtra("workObject", object.getText().toString());
                    context.startActivity(intent); //avame hooldustööe ekraani antud objektile ja anname objekti nime kaasa
                }
            });
        }
    }
}

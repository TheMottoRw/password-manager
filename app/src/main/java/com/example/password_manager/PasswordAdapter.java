package com.example.password_manager;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.ThemedSpinnerAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.password_manager.helper.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PasswordAdapter  extends RecyclerView.Adapter<PasswordAdapter.MyViewHolder> {
    public LinearLayout v;
    public Context ctx;
    public Utils utils;
    private JSONArray mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public PasswordAdapter(Context context, JSONArray myDataset) {
        mDataset = myDataset;
        ctx = context;
        utils = new Utils(ctx);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PasswordAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_passwords, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        try {
            JSONObject currentObj = mDataset.getJSONObject(position);
            holder.tvPlatform.setText(currentObj.getString("platform"));
            holder.tvEmail.setText(currentObj.getString("email"));
            holder.tvPassword.setText("******");
            holder.tvPasswordHidden.setText(currentObj.getString("password"));
            holder.lnLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager clipboard = (ClipboardManager) ctx.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("password", holder.tvPasswordHidden.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(ctx,"Password copied successful",Toast.LENGTH_SHORT).show();
                }
            });
            holder.lnLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Intent intent = new Intent(ctx,UpdatePassword.class);
                    try {
                        intent.putExtra("_id",currentObj.getString("_id"));
                        intent.putExtra("platform",currentObj.getString("platform"));
                        intent.putExtra("email",currentObj.getString("email"));
                        intent.putExtra("password",currentObj.getString("password"));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ctx.startActivity(intent);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    return false;
                }
            });
            holder.imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.tvPassword.setText(holder.tvPasswordHidden.getText().toString());
                    holder.imgView.setVisibility(View.GONE);
                    holder.imgHide.setVisibility(View.VISIBLE);
                }
            });
            holder.imgHide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.tvPassword.setText("********");
                    holder.imgView.setVisibility(View.VISIBLE);
                    holder.imgHide.setVisibility(View.GONE);
                }
            });

        } catch (JSONException ex) {

        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvPlatform,tvEmail,tvPassword,tvPasswordHidden;
        public LinearLayout lnLayout;
        public ImageView imgHide,imgView;

        public MyViewHolder(LinearLayout lny) {
            super(lny);
            lnLayout = lny.findViewById(R.id.lnyLayout);
            tvPlatform = lny.findViewById(R.id.tvPlatform);
            tvEmail = lny.findViewById(R.id.tvEmail);
            tvPassword = lny.findViewById(R.id.tvPassword);
            tvPasswordHidden = lny.findViewById(R.id.tvPasswordHidden);
            imgView = lny.findViewById(R.id.imgView);
            imgHide = lny.findViewById(R.id.imgHide);
            //tvMsg = lny.findViewById(R.id.tvRecyclerDate);
        }
    }
}

package com.example.MireaProject.room;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MireaProject.R;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {

    private Context context;
    private List<User> userList;

    public UserListAdapter(Context context) {
        this.context = context;
    }
    public void setUserList(List<User> userList){
        this.userList = userList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public UserListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.MyViewHolder holder, int position) {
        holder.tvId.setText(Integer.toString(this.userList.get(position).uid));
        holder.tvName.setText(this.userList.get(position).Name);
    }

    @Override
    public int getItemCount() {
        return this.userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvId;
        TextView tvName;
        public MyViewHolder(View view){
            super(view);
            tvId = view.findViewById(R.id.id);
            tvName = view.findViewById(R.id.Name);
        }

    }
}

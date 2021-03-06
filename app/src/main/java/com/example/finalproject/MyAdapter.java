package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.models.UpLoad;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {



    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTieuDe, txtGia, txtTime, txtDiaChi;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTieuDe = itemView.findViewById(R.id.txtTieuDe);
            txtGia = itemView.findViewById(R.id.txtGia);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtDiaChi = itemView.findViewById(R.id.txtDiaChi);
            imageView = itemView.findViewById(R.id.imageView);
        }

    }

    private Context context;
    private ArrayList<UpLoad> data;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(
                R.layout.single_row,
                viewGroup,
                false
        );
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder viewHolder, final int position) {

        viewHolder.txtTieuDe.setText(data.get(position).getTieuDe());
        NumberFormat formatter = new DecimalFormat("#,###");
        long myNumber = data.get(position).getGiaBan();
        String formattedNumber = formatter.format(myNumber);
        viewHolder.txtGia.setText(formattedNumber+ " VNĐ");

        String address = "Địa chỉ: " + data.get(position).getWard().name  + ", " + data.get(position).getDistricts().name  + ", " + data.get(position).getProvince().name;
        viewHolder.txtDiaChi.setText(address);
        String pattern = "dd/MM/yyyy hh:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        viewHolder.txtTime.setText(simpleDateFormat.format(data.get(position).getDate()));
        Picasso.get()
                .load(data.get(position).getmImageUrl().get(0))
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .into(viewHolder.imageView);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Detail.class);
                UpLoad get_data = data.get(position);
                intent.putExtra("data", get_data);
                if (context.getClass() == SellerDetail.class) {
                    intent.putExtra("enable_detail", false);
                }
                else if (context.getClass() == Liked_posts.class) {
                    intent.putExtra("enable_liked", View.GONE);
                }
                context.startActivity(intent);
            }
        });
    }

    public MyAdapter(Context context, ArrayList<UpLoad> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getItemCount() {

        return data.size();
    }
    public void filterlist(ArrayList<UpLoad> filterList) {
        data = filterList;
        notifyDataSetChanged();
    }
}

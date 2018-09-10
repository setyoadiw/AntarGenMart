package com.example.asus.tugasakhir2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.tugasakhir2.R;
import com.example.asus.tugasakhir2.activities.TroliActivity;
import com.example.asus.tugasakhir2.config.URLConfig;
import com.example.asus.tugasakhir2.models.Troli;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TroliAdapter extends RecyclerView.Adapter<TroliAdapter.MyViewHolder>{
    private List<Troli> troliList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tnama, tharga, tkategori, ttotal;
        RelativeLayout tcontainer;
        ImageView tfoto, tdelete;

        public MyViewHolder(View view) {
            super(view);
            tnama = (TextView) itemView.findViewById(R.id.nama);
            tkategori = (TextView) itemView.findViewById(R.id.kategori);
            tharga = (TextView) itemView.findViewById(R.id.harga);
            ttotal = (TextView) itemView.findViewById(R.id.total);
            tcontainer = (RelativeLayout) itemView.findViewById(R.id.container);
            tfoto = (ImageView) itemView.findViewById(R.id.foto);
            tdelete = (ImageView) itemView.findViewById(R.id.delete);
        }

    }

    public TroliAdapter(List<Troli> troliList) {
        this.troliList = troliList;
    }

    @Override
    public TroliAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.troli_item, parent, false);

        return new TroliAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TroliAdapter.MyViewHolder holder, final int position) {
        final Context mContext = holder.tcontainer.getContext();
        final Troli item = troliList.get(position);

        holder.tnama.setText(item.getNama());
        holder.tkategori.setText(item.getKategori());
        holder.tharga.setText(item.getJumlah() + " x Rp. " + item.getHarga());
        holder.ttotal.setText("Rp. " + (Integer.parseInt(item.getJumlah()) * Integer.parseInt(item.getHarga())));
        if (!item.getFoto().equals("")){
            String url = URLConfig.FOTO_URL + item.getFoto().replaceAll(" ", "%20");
            Picasso.get().load(url).into(holder.tfoto);
        }
        holder.tdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                troliList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, troliList.size());
                Troli troli = Troli.findById(Troli.class, item.getId());
                troli.delete();
                if(mContext instanceof TroliActivity){
                    ((TroliActivity)mContext).updateTotal(troliList);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return troliList.size();
    }

}

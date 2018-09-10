package com.example.asus.tugasakhir2.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.tugasakhir2.R;
import com.example.asus.tugasakhir2.activities.ProdukDetailActivity;
import com.example.asus.tugasakhir2.config.URLConfig;
import com.example.asus.tugasakhir2.models.Produk;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.MyViewHolder>{
    private List<Produk> produkList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tnama, tharga, tstok;
        ImageView tfoto;
        CardView tcontainer;

        public MyViewHolder(View view) {
            super(view);
            tnama = (TextView) itemView.findViewById(R.id.Nama_Produk);
            tharga = (TextView) itemView.findViewById(R.id.Harga);
            tstok = (TextView) itemView.findViewById(R.id.Stok);
            tfoto = (ImageView) itemView.findViewById(R.id.gambar);
            tcontainer = (CardView) itemView.findViewById(R.id.container);
        }
    }

    public ProdukAdapter(List<Produk> produkList) {
        this.produkList = produkList;
    }

    @Override
    public ProdukAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.produk_item, parent, false);

        return new ProdukAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProdukAdapter.MyViewHolder holder, final int position) {
        final Context Context = holder.tcontainer.getContext();
        final Produk produk = produkList.get(position);

        holder.tnama.setText(produk.getNamaProduk());
        holder.tharga.setText("Rp. " + produk.getHarga());
        holder.tstok.setText("Stok: " + produk.getStok());
        holder.tcontainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Context, ProdukDetailActivity.class);
                intent.putExtra("NAMA", produk.getNamaProduk());
                intent.putExtra("HARGA", produk.getHarga());
                intent.putExtra("STOK", produk.getStok());
                intent.putExtra("KATEGORI", produk.getKategori());
                intent.putExtra("FOTO", produk.getFoto());
                intent.putExtra("ID", produk.getId_produk());
                Context.startActivity(intent);
            }
        });

        if (!produk.getFoto().equals("")){
            String url = URLConfig.FOTO_URL + produk.getFoto().replaceAll(" ", "%20");
            Picasso.get().load(url).into(holder.tfoto);
        }
    }

    @Override
    public int getItemCount() {
        return produkList.size();
    }
}

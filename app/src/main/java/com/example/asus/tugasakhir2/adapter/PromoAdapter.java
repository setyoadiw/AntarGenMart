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
import com.example.asus.tugasakhir2.models.ProduksItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.MyViewHolder> {

    private List<ProduksItem> promoList;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tnama, tharga, tstok ,thargaturun,tdiskon;
        ImageView tfoto;
        CardView tcontainer;

        public MyViewHolder(View itemView) {
            super(itemView);
            tdiskon = (TextView) itemView.findViewById(R.id.tvdiskon);
            tnama = (TextView) itemView.findViewById(R.id.pNama_Produk);
            tharga = (TextView) itemView.findViewById(R.id.pHarga);
            thargaturun = (TextView) itemView.findViewById(R.id.pHargaTurun);
            tstok = (TextView) itemView.findViewById(R.id.pStok);
            tfoto = (ImageView) itemView.findViewById(R.id.pgambar);
            tcontainer = (CardView) itemView.findViewById(R.id.pcontainer);
        }
    }

    public PromoAdapter(List<ProduksItem> produksPromo) {
        this.promoList = produksPromo;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.promo_item, parent, false);

        return new PromoAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Context mContext = holder.tcontainer.getContext();
        final ProduksItem produk = promoList.get(position);

        //set flag untuk strike
        holder.tharga.setPaintFlags(holder.tharga.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        //end
        //mengubah stok ke string
        final int stok = produk.getStok();
        final String strstok = String.valueOf(stok).toString().trim();
        //end

        //hitung diskon
        int hargaAsli = Integer.parseInt(produk.getHargaSebelum());
        int hargaTurun = Integer.parseInt(produk.getHarga());
        int diskon = ((hargaAsli - hargaTurun) * 100)/hargaAsli;

        final String harga = String.valueOf(hargaTurun).trim();
        final String id = String.valueOf(produk.getId()).trim();
        holder.tnama.setText(produk.getNamaProduk());
        holder.tharga.setText("Rp. " + produk.getHargaSebelum());
        holder.thargaturun.setText("Rp. " + harga);
        holder.tdiskon.setText(String.valueOf(diskon) + "%");
        holder.tstok.setText("Stok: " + stok);
        holder.tcontainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent promointent = new Intent(mContext, ProdukDetailActivity.class);
                promointent.putExtra("NAMA", produk.getNamaProduk());
                promointent.putExtra("HARGA", harga);
                promointent.putExtra("STOK", strstok);
                promointent.putExtra("KATEGORI", produk.getKategori());
                promointent.putExtra("FOTO", produk.getFoto());
                promointent.putExtra("ID", id);
                mContext.startActivity(promointent);
            }
        });

        if (!produk.getFoto().equals("")){
            String url = URLConfig.FOTO_URL + produk.getFoto().replaceAll(" ", "%20");
            Picasso.get().load(url).into(holder.tfoto);
        }

    }


    @Override
    public int getItemCount() {
        return promoList.size();
    }


}

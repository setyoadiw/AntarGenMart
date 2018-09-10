package com.example.asus.tugasakhir2.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.tugasakhir2.R;
import com.example.asus.tugasakhir2.activities.PosisiKurirActivity;
import com.example.asus.tugasakhir2.models.Pesanan;

import java.util.List;

public class PesananAdapter extends RecyclerView.Adapter<PesananAdapter.MyViewHolder> {
    private List<Pesanan> pesananList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTvKode;
        TextView mTvKurir;
        TextView mTvTanggal;
        TextView mTvProduk;
        TextView mTvJumlah;
        TextView mTvStatus;
        TextView mLacakKurir;

        public MyViewHolder(View view) {
            super(view);
            mTvKode = (TextView) itemView.findViewById(R.id.tv_kode);
            mTvKurir = (TextView) itemView.findViewById(R.id.tv_kurir);
            mTvTanggal = (TextView) itemView.findViewById(R.id.tv_tanggal);
            mTvProduk = (TextView) itemView.findViewById(R.id.tv_produk);
            mTvJumlah = (TextView) itemView.findViewById(R.id.tv_jumlah);
            mTvStatus = (TextView) itemView.findViewById(R.id.tv_status);
            mLacakKurir = (TextView) itemView.findViewById(R.id.lacak_kurir);

        }

    }

    public PesananAdapter(List<Pesanan> pesananList) {
        this.pesananList = pesananList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pesanan_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Pesanan pesanan = pesananList.get(position);
        final Context mContext = holder.mTvKode.getContext();

        holder.mTvKode.setText("Pesanan #" + pesanan.getKodePesanan());
        holder.mTvKurir.setText(pesanan.getNamaKurir());
        holder.mTvTanggal.setText(pesanan.getUpdatedAt().substring(0, 10));
        holder.mTvProduk.setText("");
        String valueProduk = "";
        for (int i = 0; i < pesanan.getNamaProduk().size(); i++) {
            if (i == pesanan.getNamaProduk().size() - 1){
                valueProduk = valueProduk + "•" + pesanan.getNamaProduk().get(i);
            } else {
                valueProduk = valueProduk + "•" + pesanan.getNamaProduk().get(i) + "\n";
            }
        }
        holder.mTvProduk.setText(valueProduk);
        String valueTotal = "";
        for (int i = 0; i < pesanan.getTotal().size(); i++) {
            if (i == pesanan.getTotal().size() - 1){
                valueTotal = valueTotal + "Rp. " + pesanan.getTotal().get(i);
            } else {
                valueTotal = valueTotal + "Rp. " + pesanan.getTotal().get(i) + "\n";
            }
        }
        holder.mTvJumlah.setText(valueTotal);
        if (pesanan.getStatus().equals("0")) {
            holder.mTvStatus.setText("Sedang Diproses");
        } else if (pesanan.getStatus().equals("1")){
            holder.mTvStatus.setText("Pickup Barang");
        } else if (pesanan.getStatus().equals("2")){
            holder.mTvStatus.setText("Dalam Pengiriman");
            holder.mLacakKurir.setVisibility(View.VISIBLE);
            holder.mLacakKurir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, PosisiKurirActivity.class);
                    intent.putExtra("ID_KURIR", pesanan.getIdKurir());
                    mContext.startActivity(intent);
                }
            });
        } else {
            holder.mTvStatus.setText("Telah Diterima");
        }

    }

    @Override
    public int getItemCount() {
        return pesananList.size();
    }

}

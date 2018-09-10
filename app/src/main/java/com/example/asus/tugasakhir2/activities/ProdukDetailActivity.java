package com.example.asus.tugasakhir2.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.tugasakhir2.R;
import com.example.asus.tugasakhir2.config.URLConfig;
import com.example.asus.tugasakhir2.models.Troli;
import com.squareup.picasso.Picasso;

public class ProdukDetailActivity extends AppCompatActivity {

    //Deklarasi global variabel
    private Toolbar mActionBarToolbar;
    private String nama, harga, stok, kategori, foto, id;
    private ImageView mFotoProduk;
    private TextView mNamaProduk;
    private TextView mHargaProduk;
    private TextView mStokProduk;
    private Button mButtonTroli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Menampilkan icon panah kembali di action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Mengganti judul action bar
        getSupportActionBar().setTitle("Detail Produk");

        //Memanggil fungsi initView
        initView();

        //Cek apakah ada intent extra
        if (getIntent().getExtras() != null) {
            //Ambil intent extra dan simpan pada variabel
            nama = getIntent().getStringExtra("NAMA");
            harga = getIntent().getStringExtra("HARGA");
            stok = getIntent().getStringExtra("STOK");
            kategori = getIntent().getStringExtra("KATEGORI");
            foto = getIntent().getStringExtra("FOTO");
            id = getIntent().getStringExtra("ID");

            //Menampilkan detail produk
            mNamaProduk.setText(nama);
            mHargaProduk.setText("Rp. " + harga);
            mStokProduk.setText("Stok: " + stok);
            String url = URLConfig.FOTO_URL + foto.replaceAll(" ", "%20");
            Picasso.get().load(url).into(mFotoProduk);

            //Ketika button Tambah Ke Troli ditekan
            mButtonTroli.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Memanggil fungsi showDialog
                    showDialog();
                }
            });
        }

    }

    //Fungsi deklarasi view yang ada pada layout
    private void initView() {
        mFotoProduk = (ImageView) findViewById(R.id.foto_produk);
        mNamaProduk = (TextView) findViewById(R.id.nama_produk);
        mHargaProduk = (TextView) findViewById(R.id.harga_produk);
        mStokProduk = (TextView) findViewById(R.id.stok_produk);
        mButtonTroli = (Button) findViewById(R.id.button_troli);
    }

    //Memunculkan popup untuk konfirmasi jumlah pembelian
    private void showDialog(){
        //Membuat dialog dengan kustom layout
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_jumlah_beli);
        Button min = (Button) dialog.findViewById(R.id.button_min);
        Button plus = (Button) dialog.findViewById(R.id.button_plus);
        Button troli = (Button) dialog.findViewById(R.id.button_troli);
        final EditText jumlah = (EditText) dialog.findViewById(R.id.jumlah);

        //Ketika button minus(-) ditekan
        min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Cek supaya jumlah beli tidak boleh dibawah satu
                if (Integer.parseInt(jumlah.getText().toString()) > 1){
                    //Jumlah berli sekarang dikurang satu
                    int jumlah_beli = Integer.parseInt(jumlah.getText().toString()) - 1;
                    jumlah.setText(String.valueOf(jumlah_beli));
                }
            }
        });

        //Ketika button plus(+) ditekan
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Jumlah beli sekarang ditambah satu
                int jumlah_beli = Integer.parseInt(jumlah.getText().toString()) + 1;
                jumlah.setText(String.valueOf(jumlah_beli));
            }
        });

        //Ketika button Tambah Ke Troli ditekan
        troli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Simpan detail pembelian di sqlite
                Troli item = new Troli(id, nama, kategori, stok, harga, jumlah.getText().toString(), foto);
                Troli.save(item);

                //Tutup popup
                dialog.dismiss();
                Toast.makeText(ProdukDetailActivity.this, "Produk Anda Berhasil Dimasukkan Ke Troli", Toast.LENGTH_LONG).show();
            }
        });

        //Tampilkan popup
        dialog.show();
    }

    //Fungsi menampilkan icon di actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //Fungsi ketika icon di actionbar ditekan
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
            Intent intent = new Intent(ProdukDetailActivity.this, TroliActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Fungsi ketika icon panah kembali ditekan
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

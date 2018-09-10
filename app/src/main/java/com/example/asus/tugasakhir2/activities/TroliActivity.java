package com.example.asus.tugasakhir2.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.tugasakhir2.R;
import com.example.asus.tugasakhir2.adapter.TroliAdapter;
import com.example.asus.tugasakhir2.models.Kurir;
import com.example.asus.tugasakhir2.models.KurirResponse;
import com.example.asus.tugasakhir2.models.Troli;
import com.example.asus.tugasakhir2.network.APIService;
import com.example.asus.tugasakhir2.network.RetrofitClient;
import com.example.asus.tugasakhir2.test.User;
import com.example.asus.tugasakhir2.test.UserResponse;
import com.google.gson.JsonObject;
import com.orm.SugarContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TroliActivity extends AppCompatActivity {

    //Deklarasi variabel global
    private RecyclerView recyclerView;
    private TroliAdapter adapter;
    List<Troli> trolis = new ArrayList<>();
    //private ArrayList<User> listUser;
    public String Telepon;
    public Integer finalTotal = 0;
    public String Alamat;
    public String TeleponKirim;
    public String AlamatKirim;
    public int TeleponLain = 0 ;
    public int AlamatLain = 0 ;
    private TextView mTotal;
    private Button mButtonPengiriman;
    private List<String> namaKurir = new ArrayList<String>();
    private HashMap<String, Integer> idKurir =  new HashMap<String, Integer>();
    RadioButton rbAlmtLainnya;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_troli);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         RadioButton rbAlmtSekarang = (RadioButton) findViewById(R.id.rb_al_sekarang);
         rbAlmtLainnya = (RadioButton) findViewById(R.id.rb_al_lainnya);
         RadioButton rbTelpSekarang = (RadioButton) findViewById(R.id.rb_telp_sekarang);
         RadioButton rbTelpLainnya = (RadioButton) findViewById(R.id.rb_telp_lainnya);

        //Menampilkan icon panah kembali di action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Mengganti judul action bar
        getSupportActionBar().setTitle("Troli Saya");

        //Memanggil fungsi initView
        initView();

        //load data user untuk mendapat alamat & telepon
        loadDataUser();

        //Deklarasi dan konfigurasi recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recycler_troli);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        //Ambil data dari tabel Troli di sqlite
        SugarContext.init(TroliActivity.this);
        trolis = Troli.listAll(Troli.class);

        //Tampilkan item troli di recyclerview
        adapter = new TroliAdapter(trolis);
        recyclerView.setAdapter(adapter);

        //Hitung total keseluruhan harga item
        Integer total = 0;
        for (int l = 0; l < trolis.size(); l++) {
            Troli item = trolis.get(l);
            total = total + Integer.parseInt(item.getJumlah()) * Integer.parseInt(item.getHarga());
        }
        mTotal.setText("Rp. " + total);

        finalTotal = total;
        mButtonPengiriman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mTotal.getText().equals("Rp. 0") && finalTotal >=50000){
                    showDialogPengiriman();
                }else if(!mTotal.getText().equals("Rp. 0") && finalTotal < 50000 ){
                    Toast.makeText(TroliActivity.this, "Minimal Belanja Adalah Rp 50.000", Toast.LENGTH_SHORT).show();
                }else if(mTotal.getText().equals("Rp. 0")){
                    Toast.makeText(TroliActivity.this, "Troli Belanjaan Anda Kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //Fungsi deklarasi view yang ada pada layout
    private void initView() {
        mTotal = (TextView) findViewById(R.id.total);
        mButtonPengiriman = (Button) findViewById(R.id.button_pengiriman);
    }

    private void showDialogPengiriman(){
        //Membuat dialog dengan kustom layout
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_pengiriman);

        final Spinner mKurir = (Spinner) dialog.findViewById(R.id.spn_kurir);
        loadListKurir(mKurir);

        final EditText mAlamat = (EditText) dialog.findViewById(R.id.et_alamat);
        final EditText mTelepon = (EditText) dialog.findViewById(R.id.et_telepon);
        final RadioGroup rgAlamat = (RadioGroup) dialog.findViewById(R.id.rg_alamat);
        final RadioGroup rgTelp = (RadioGroup) dialog.findViewById(R.id.rg_telp);
        final RadioButton rbAlmtSekarang = (RadioButton) dialog.findViewById(R.id.rb_al_sekarang);
        final RadioButton rbAlmtLainnya = (RadioButton) dialog.findViewById(R.id.rb_al_lainnya);
        final RadioButton rbTelpSekarang = (RadioButton) dialog.findViewById(R.id.rb_telp_sekarang);
        final RadioButton rbTelpLainnya = (RadioButton) dialog.findViewById(R.id.rb_telp_lainnya);
        final TextView tvAlamat = dialog.findViewById(R.id.tvalamat);
        final TextView tvAlamatContent = dialog.findViewById(R.id.tvalamatsekarangcontent);
        final TextView tvTeleponContent = dialog.findViewById(R.id.tvteleponsekarangcontent);

        //Menyembunyikan editext alamat dan telepon lainnya
        mAlamat.setVisibility(View.GONE);
        mTelepon.setVisibility(View.GONE);

        //Mengisi nilai textview alamatcontent & teleponcontent dari server
        try {
            tvAlamatContent.setText(Alamat.toString().trim());
            tvTeleponContent.setText(Telepon.toString().trim());
        } catch (Exception e) {
            e.printStackTrace();
            tvAlamatContent.setVisibility(View.GONE);
            tvTeleponContent.setVisibility(View.GONE);
        }


        //radiogroup alamat dan telepon
        rgAlamat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_al_lainnya:
                        mAlamat.setVisibility(View.VISIBLE);
                        tvAlamatContent.setVisibility(View.GONE);
                        AlamatLain =1;
                        break;
                    case R.id.rb_al_sekarang:
                        mAlamat.setVisibility(View.GONE);
                        tvAlamatContent.setVisibility(View.VISIBLE);
                        break;
                }

            }
        });


        rgTelp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_telp_lainnya:
                        mTelepon.setVisibility(View.VISIBLE);
                        TeleponLain = 1;
                        tvTeleponContent.setVisibility(View.GONE);
                        break;
                    case R.id.rb_telp_sekarang:
                        mTelepon.setVisibility(View.GONE);
                        tvTeleponContent.setVisibility(View.VISIBLE);
                        break;
                }

            }
        });


        Button mKirim = (Button) dialog.findViewById(R.id.btn_kirim);

        mKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Melakukan pengecekan apakah menggunakan Radio Sekarang atau Lainnya
                //Mengisikan nilai TeleponKirim & Alamat Kirim yg akan dipesan
                if (TeleponLain ==1){
                    //Jika menggunakan Telepon Lainnya ,mengambil dari EditText
                    TeleponKirim = mTelepon.getText().toString();
                }else {
                    //Jika menggunakan Telepon Sekarang ,mengambil dari Server
                    TeleponKirim = Telepon;
                }
                if (AlamatLain == 1){
                    //Jika menggunakan Alamat Lainnya ,mengambil dari EditText
                    AlamatKirim = mAlamat.getText().toString().trim();
                }else {
                    //Jika menggunakan Alamat Sekarang ,mengambil dari Server
                    AlamatKirim = Alamat;
                }

                SugarContext.init(TroliActivity.this);
                trolis = Troli.listAll(Troli.class);
                for (int l = 0; l < trolis.size(); l++) {
                    SharedPreferences preferences = getSharedPreferences("SESSION",MODE_PRIVATE);
                    Troli item = trolis.get(l);
                    addPesanan(
                            preferences.getString("ID", ""),
                            String.valueOf(idKurir.get(mKurir.getSelectedItem().toString())),
                            item.getId_produk(),
                            AlamatKirim.toString().trim(),
                            String.valueOf(Integer.parseInt(item.getJumlah()) * Integer.parseInt(item.getHarga())),
                            "0",
                            TeleponKirim.toString().trim(),
                            item.getJumlah());
                }

                dialog.dismiss();
            }
        });

        dialog.show();
    }
    //mengambil data alamat dan telepon
    private void loadDataUser() {

        SharedPreferences preferences = getSharedPreferences("SESSION",MODE_PRIVATE);
        String id = preferences.getString("ID", "");

        APIService requestData = RetrofitClient.getClient().create(APIService.class);
        Call<UserResponse> userRespos = requestData.getAlamat(id);

        userRespos.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()){
                    Alamat = (String) response.body().getUser().getAlamat();
                    Telepon = (String) response.body().getUser().getNoTelp();
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }


    public void loadListKurir(final Spinner mKurir){
        APIService service = RetrofitClient.getClient().create(APIService.class);
        Call<KurirResponse> userCall = service.getAllKurir();

        userCall.enqueue(new Callback<KurirResponse>() {
            @Override
            public void onResponse(Call<KurirResponse> call, Response<KurirResponse> response) {
                if (response.isSuccessful()){
                    List<Kurir> kurirs = response.body().getData();

                    namaKurir.add("Pilih Kurir");
                    idKurir.put("Pilih Kurir", 0);
                    for (int i=0; i<kurirs.size(); i++){
                        namaKurir.add(kurirs.get(i).getName());
                        idKurir.put(kurirs.get(i).getName(), kurirs.get(i).getId());
                    }

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(TroliActivity.this, android.R.layout.simple_spinner_item, namaKurir);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mKurir.setAdapter(dataAdapter);
                }
            }

            @Override
            public void onFailure(Call<KurirResponse> call, Throwable t) {

            }
        });
    }

    public void addPesanan(String id_user, String id_kurir, String id_produk, String alamat_pesanan, String total, String status_pesanan, String telp_pesanan, String jumlah){
        mButtonPengiriman.setEnabled(false);
        mButtonPengiriman.setText("memproses...");
        APIService service = RetrofitClient.getClient().create(APIService.class);
        Call<JsonObject> userCall = service.addPesanan(id_user, id_kurir, id_produk, alamat_pesanan, total, status_pesanan, telp_pesanan, jumlah);

        userCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){
                    mButtonPengiriman.setEnabled(true);
                    mButtonPengiriman.setText("lanjutkan ke pengiriman");
                    Troli.deleteAll(Troli.class);
                    Toast.makeText(TroliActivity.this, "Pesanan Anda Telah Kami Proses", Toast.LENGTH_LONG).show();
                    TroliActivity.this.recreate();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                mButtonPengiriman.setEnabled(true);
                mButtonPengiriman.setText("lanjutkan ke pengiriman");
                Toast.makeText(TroliActivity.this, "Gagal Tehubung Ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Fungsi update total keseluruhan
    //Fungsi ini wajib dipanggil ketika hapus item dari recyclerview
    public void updateTotal(List<Troli> troliList){
        Integer total = 0;
        for (int l = 0; l < troliList.size(); l++) {
            Troli item = troliList.get(l);
            total = total + Integer.parseInt(item.getJumlah()) * Integer.parseInt(item.getHarga());
        }
        mTotal.setText("Rp. " + total);
        finalTotal = total;
    }

    //Fungsi ketika icon panah kembali ditekan
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TroliActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

}


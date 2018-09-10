package com.example.asus.tugasakhir2.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.tugasakhir2.R;
import com.example.asus.tugasakhir2.fragments.HalamanKRT;
import com.example.asus.tugasakhir2.fragments.HalamanKesehatan;
import com.example.asus.tugasakhir2.fragments.HalamanMakanan;
import com.example.asus.tugasakhir2.fragments.HalamanMinuman;
import com.example.asus.tugasakhir2.fragments.HalamanUtama;
import com.example.asus.tugasakhir2.models.Troli;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displaySelectedScreen(R.id.nav_utama);

        //Ambil session login dari shared preference
        SharedPreferences preferences = getSharedPreferences("SESSION",MODE_PRIVATE);

        //Ubah TextView header sesuai nama dan email di session login
        View headerView = navigationView.getHeaderView(0);
        TextView headerNama = (TextView) headerView.findViewById(R.id.header_nama);
        TextView headerEmail = (TextView) headerView.findViewById(R.id.header_email);
        headerNama.setText(preferences.getString("NAME", "").toString());
        headerEmail.setText(preferences.getString("EMAIL", "").toString());
    }

    public void displaySelectedScreen(int itemId){
        //creating fragment object
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        switch (itemId) {
            case R.id.nav_utama:
                fragment = new HalamanUtama();
                break;
            case R.id.nav_makanan:
                fragment = new HalamanMakanan();
                break;
            case R.id.nav_minuman:
                fragment = new HalamanMinuman();
                break;
            case R.id.nav_obat:
                fragment = new HalamanKesehatan();
                break;
            case R.id.nav_pesanan:
                startActivity(new Intent(MainActivity.this, PesananSayaActivity.class));
                break;
            case R.id.nav_logout:
                //Hapus session login dari shared preference
                SharedPreferences.Editor editor = getSharedPreferences("SESSION", MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();

                //Hapus tabel troli dari sqlite
                Troli.deleteAll(Troli.class);
                Troli.executeQuery("DELETE FROM SQLITE_SEQUENCE WHERE NAME = 'TROLI'");

                //Intent untuk pindah activity ke LoginActivity
                Intent intent = new Intent(MainActivity.this, loginutama.class);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_alat:
                fragment = new HalamanKRT();
                break;
        }


        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Ketika icon troli di klik
        if (id == R.id.action_cart) {

            //Intent untuk pindah activity ke TroliActivity
            Intent intent = new Intent(MainActivity.this, TroliActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return false;
    }
}

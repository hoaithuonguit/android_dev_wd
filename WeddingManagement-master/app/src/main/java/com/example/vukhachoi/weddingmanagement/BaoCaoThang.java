package com.example.vukhachoi.weddingmanagement;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import adapter.AdapterBaoCao;
import model.BaoCao;
import sqlite.Databasehelper;

public class BaoCaoThang extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Databasehelper myDatabase = new Databasehelper(this);
    SQLiteDatabase database;

    ArrayList<BaoCao>dsbc;
    ListView lv_baocao;
    AdapterBaoCao adapterBaoCao;
    TextView txtThang;
    TextView txtDoanhThu;
    TextView txttile;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_cao_thang);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_navi);
        toolbar.setTitle("Báo cáo");
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_5);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        int code= Integer.parseInt(getIntent().getStringExtra("code"));
        navigationView.getMenu().getItem(code).setChecked(true);
        myDatabase.Khoitai();
        database=myDatabase.getMyDatabase();
        //myDatabase.db_delete();
        addControls();
        addEvents();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_5);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.sanh_item) {
            Intent intent=new Intent(this,HallsActivity.class);
            intent.putExtra("code","0");
            startActivity(intent);
            finish();
        } else if (id == R.id.tracuu_item) {
            Intent intent=new Intent(this,activityTraCuu.class);
            intent.putExtra("code","1");
            startActivity(intent);
            finish();
        } else if (id == R.id.hoadon_item) {
            Intent intent=new Intent(this,LapHoaDon.class);
            intent.putExtra("code","2");
            startActivity(intent);
            finish();
        } else if (id == R.id.baocao_item) {
            Intent intent=new Intent(this,BaoCaoThang.class);
            intent.putExtra("code","3");
            startActivity(intent);
            finish();

        } else if (id == R.id.quydinh_item) {
            Intent intent=new Intent(this,quy_dinh_activity.class);
            intent.putExtra("code","4");
            startActivity(intent);
            finish();


        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_5);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addEvents() {

    }

    private void addControls() {
        txtThang= (TextView) findViewById(R.id.txtThang);
        txtDoanhThu= (TextView) findViewById(R.id.txtDoanhThu);
        lv_baocao= (ListView) findViewById(R.id.lv_baocao);
        txttile= (TextView) findViewById(R.id.txttile);

        dsbc=new ArrayList<>();
        adapterBaoCao=new AdapterBaoCao(BaoCaoThang.this,R.layout.item_baocao,dsbc);
        lv_baocao.setAdapter(adapterBaoCao);

        Calendar calendar=Calendar.getInstance();
        DateFormat th = new SimpleDateFormat("MM");
        int thangnay= Integer.parseInt(th.format(calendar.getTime()));
        int thangtruoc=thangnay-1;
        if(thangnay==1)
        {
            thangtruoc=12;
        }


        txtThang.setText(thangnay+"");
        Cursor cursor=database.rawQuery("select ngay,count(mahd),sum(tongtien) from hoadon where thang="+thangnay+" group by ngay",null);
        float doanhthu=0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            int ngay=cursor.getInt(0);
            int sl=cursor.getInt(1);
            float tongtien=cursor.getFloat(2);
            doanhthu+=(float)tongtien;
            BaoCao bc=new BaoCao(ngay,sl,tongtien);
            dsbc.add(bc);
            adapterBaoCao.notifyDataSetChanged();
            cursor.moveToNext();
        }
        cursor.close();




        Cursor cursor1=database.rawQuery("select sum(tongtien) from hoadon where thang="+thangtruoc+" group by thang",null);
        cursor1.moveToFirst();
        if(!cursor1.isAfterLast())
        {
            float doanhthutruoc = cursor1.getFloat(0);
            cursor1.close();
            DecimalFormat x = new DecimalFormat("#.##");
            txtDoanhThu.setText(x.format(doanhthu) + "");
            if (doanhthutruoc > 0) {
                float tl = (doanhthu / doanhthutruoc) * 100;
                txttile.setText(x.format(tl) + "");
            }
        }
    }
}

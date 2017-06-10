package com.example.vukhachoi.weddingmanagement;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.RecyclerViewAdapter;
import hall.wedding.management.HallDetail;
import sqlite.Databasehelper;

public class HallsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView rcListHall;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halls);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_navi);
        toolbar.setTitle("Loại sảnh");
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        int code= Integer.parseInt(getIntent().getStringExtra("code"));
        navigationView.getMenu().getItem(code).setChecked(true);
        addControl();
        addEvent();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_1);
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
            finish();}



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_1);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onRestart() {
        recreate();
        super.onRestart();
    }

    private void addEvent() {

    }

    private void addControl() {
        rcListHall= (RecyclerView) findViewById(R.id.rclListHall);

        Databasehelper myDatabase = new Databasehelper(this);

        try {
            myDatabase.createDatabase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");
        }

        try {
            myDatabase.openDatabase();

        }catch(SQLException sqle){

            throw sqle;
        }
        SQLiteDatabase database = myDatabase.getMyDatabase();

        Context context;
        context = getApplicationContext();
        RecyclerView.LayoutManager recyclerViewLayoutManager;
        recyclerViewLayoutManager=new GridLayoutManager(context, 2);
        List<HallDetail> hallDetail = new ArrayList<>();
        rcListHall.setLayoutManager(recyclerViewLayoutManager);
        rcListHall.setHasFixedSize(true);
        RecyclerViewAdapter adapter=new RecyclerViewAdapter(hallDetail,this);
        rcListHall.setAdapter(adapter);
String tinhtrang="1";

        Cursor cursor=database.rawQuery(" SELECT distinct Loaisanh,tinhtrang FROM Sanh where tinhtrang=?",new String[]{tinhtrang});
        cursor.moveToFirst();


        while (!cursor.isAfterLast())
        {
            HallDetail Hall=new HallDetail();
            Hall.setNameHall(cursor.getString(0));
            Hall.setActive(true);
            hallDetail.add(Hall);
            cursor.moveToNext();
            adapter.notifyDataSetChanged();
        }
        cursor.close();

        Cursor cursor1=database.rawQuery("SELECT distinct Loaisanh,tinhtrang FROM sanh where tinhtrang=0",null);
        cursor1.moveToFirst();
        while (!cursor1.isAfterLast())
        {
            HallDetail Hall=new HallDetail();
            Hall.setNameHall(cursor1.getString(0));
            Hall.setActive(false);
           int i=0;
            for(HallDetail h:hallDetail )
            {
                if(h.getNameHall().toString().equals(Hall.getNameHall().toString())) {
                    h.setActive(false);
                   i=1;
                    break;
                }
            }
         if(i==0)   hallDetail.add(Hall);
            cursor1.moveToNext();
            adapter.notifyDataSetChanged();
        }
        cursor1.moveToFirst();
        cursor1.close();
        // LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

    }
}

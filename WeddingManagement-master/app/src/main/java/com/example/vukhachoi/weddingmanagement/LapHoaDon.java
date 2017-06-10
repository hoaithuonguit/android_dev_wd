package com.example.vukhachoi.weddingmanagement;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;

import adapter.Adapter_HoaDon;
import adapter.Adapter_HoaDon_Lichsu;
import model.Dichvu;
import model.Hoadon;
import model.TiecCuoi;
import sqlite.Databasehelper;

public class LapHoaDon extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TabHost tabHost;

    ListView lv_hoadon;
    ArrayAdapter<TiecCuoi>adapterHoaDon;
    ArrayList<TiecCuoi>dsHoaDon;

    ArrayList<Hoadon>dsls;
    Adapter_HoaDon_Lichsu ls;
    ListView lvls;

    Databasehelper myDatabase = new Databasehelper(this);
    SQLiteDatabase database;

    ListView lv_hoadonDaThanhToan;
    private Toolbar mToolbar;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;

    AutoCompleteTextView txtSearch1;
    ArrayList<String>makh_mahd;
    ArrayAdapter<String>dsmakh_mahd;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lap_hoa_don);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_navi);
        mToolbar.setTitle("Lập hóa đơn");
        setSupportActionBar(mToolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_4);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        int code= Integer.parseInt(getIntent().getStringExtra("code"));
        navigationView.getMenu().getItem(code).setChecked(true);
        addControls();
        addEvents();

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_4);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_4);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addEvents() {
        lv_hoadon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(LapHoaDon.this,LapHoaDon_ThanhToan.class);
                intent.putExtra("tieccuoi",dsHoaDon.get(i));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        recreate();
        super.onRestart();
    }

    private void AddTabhost()
    {
        tabHost= (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tab1=tabHost.newTabSpec("t1");
        tab1.setIndicator("Lập hóa đơn");
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2=tabHost.newTabSpec("t2");
        tab2.setIndicator("Lịch sử");
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab2);

        //tabHost.setCurrentTab(0);
    }
    private void Xulytab1()
    {
        lv_hoadon= (ListView) findViewById(R.id.lv_hoadon);

        dsHoaDon=new ArrayList<>();
        adapterHoaDon=new Adapter_HoaDon(LapHoaDon.this, R.layout.item_hoadon,dsHoaDon);


        Cursor hoadon_dalap=database.rawQuery("select makh from hoadon",null);
        hoadon_dalap.moveToFirst();
        ArrayList<String>dshoadondalap=new ArrayList<>();
        while (!hoadon_dalap.isAfterLast())
        {
            dshoadondalap.add(hoadon_dalap.getString(0));
            hoadon_dalap.moveToNext();
        }
        hoadon_dalap.close();

        Cursor cursor=database.rawQuery("SELECT   makh,tenchure,tencodau,tensanh,ngay FROM THONGTIN",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int flag=1;
            String makh = cursor.getString(0);
            for(String test:dshoadondalap)
            {
                if(makh.equals(test))
                {
                    flag=0;
                }
            }
            if(flag==1)
            {
                String chure = cursor.getString(1);
                String codau = cursor.getString(2);
                String sanh = cursor.getString(3);
                String ngay = cursor.getString(4);
                dsHoaDon.add(new TiecCuoi(makh, chure, codau, sanh, ngay));
                makh_mahd.add(makh);
                dsmakh_mahd.notifyDataSetChanged();
                adapterHoaDon.notifyDataSetChanged();
            }
            cursor.moveToNext();
        }
        cursor.close();

        lv_hoadon.setAdapter(adapterHoaDon);

        //Lấy tiền bàn
        Cursor cursor1=database.rawQuery("select thongtin.makh,sum(dongia),dongiatoithieu" +
                " from  sanh join thongtin on sanh.tensanh=thongtin.tensanh join datmonan on thongtin.makh=datmonan.makh join monan on monan.madatmonan=datmonan.mamonan" +
                " group by thongtin.makh",null);

        cursor1.moveToFirst();
        while (!cursor1.isAfterLast())
        {
            String makh = cursor1.getString(0);
            int tienan = cursor1.getInt(1)+cursor1.getInt(2);
            for (TiecCuoi tc : dsHoaDon)
            {
                if(makh.equals(tc.getMakh()))
                    tc.setTienban(tienan);

            }
            cursor1.moveToNext();
        }
        cursor1.close();

        Cursor cursor2=database.rawQuery("select thongtin.makh,tendv,soluong,dongia,tiendatcoc " +
                "from thongtin join datdichvu on thongtin.makh=datdichvu.makh " +
                "join dichvu on dichvu.madatdichvu=datdichvu.madv",null);
        cursor2.moveToFirst();

        while (!cursor2.isAfterLast())
        {
            String makh_dv = cursor2.getString(0);
            String tendv=cursor2.getString(1);
            int datcoc=cursor2.getInt(4);
            int sl=cursor2.getInt(2);
            int dongia=cursor2.getInt(3);
            for (TiecCuoi tc : dsHoaDon)
            {
                if(makh_dv.equals(tc.getMakh()))
                {
                    Dichvu dvtemp=new Dichvu(makh_dv,tendv,sl,dongia);
                    tc.adddv(dvtemp);
                    tc.setTiendatcoc(datcoc);
                }

            }
            cursor2.moveToNext();
        }
        cursor2.close();
    }

    private void Xulytab2()
    {
        dsls=new ArrayList<>();
        ls=new Adapter_HoaDon_Lichsu(LapHoaDon.this,R.layout.item_hoadon_lichsu,dsls);

        lvls= (ListView) findViewById(R.id.lv_hoadon_lichsu);
        lvls.setAdapter(ls);

        Cursor cursor=database.rawQuery("select mahd,makh,soluongban,dongia,tiendatcoc,tongtien,nghd from hoadon",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String mahd=cursor.getString(0);
            String makh=cursor.getString(1);
            int sl=cursor.getInt(2);
            int dongia=cursor.getInt(3);
            int datcoc=cursor.getInt(4);
            int tongtien=cursor.getInt(5);
            String nghd=cursor.getString(6);
            dsls.add(new Hoadon(mahd,makh,nghd,sl,dongia,datcoc,tongtien));
            makh_mahd.add(makh);
            dsmakh_mahd.notifyDataSetChanged();
            ls.notifyDataSetChanged();
            cursor.moveToNext();
        }
        cursor.close();
    }
    private void addControls() {
        myDatabase.Khoitai();
        database = myDatabase.getMyDatabase();
        //myDatabase.db_delete();
        makh_mahd=new ArrayList<>();
        dsmakh_mahd=new ArrayAdapter(LapHoaDon.this,android.R.layout.select_dialog_item,makh_mahd);
        AddTabhost();
        Xulytab1();
        Xulytab2();



    }
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hoadon, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(android.view.Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search_hoadon);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_search_hoadon:
                handleMenuSearch();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void handleMenuSearch(){
        ActionBar action = getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(txtSearch1.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(android.R.drawable.ic_menu_search));

            isSearchOpened = false;
            recreate();
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.searchbar_hoadon);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title


            txtSearch1= (AutoCompleteTextView)  action.getCustomView().findViewById(R.id.txtSearch_hoadon);
            txtSearch1.setThreshold(1);
            txtSearch1.setAdapter(dsmakh_mahd);

            txtSearch1.requestFocus();

            txtSearch1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String temp=txtSearch1.getText().toString();
                    for(TiecCuoi tc:dsHoaDon)
                    {
                        if(tc.getMakh().equals(temp))
                        {
                            TiecCuoi test=tc;
                            dsHoaDon.remove(tc);
                            test.setCheck(1);
                            dsHoaDon.add(0,test);
                            tabHost.setCurrentTab(0);
                            adapterHoaDon.notifyDataSetChanged();
                            break;
                        }
                    }
                    for(Hoadon hd:dsls)
                    {
                        if(hd.getMakh().equals(temp))
                        {
                            Hoadon test=hd;
                            dsls.remove(hd);
                            test.setCheck(1);
                            dsls.add(0,test);
                            ls.notifyDataSetChanged();
                            tabHost.setCurrentTab(1);
                            break;
                        }
                    }
                }
            });

//            //add the close icon
            mSearchAction.setIcon(getResources().getDrawable(android.R.drawable.ic_menu_close_clear_cancel));
            isSearchOpened = true;
        }
    }


}

package com.example.vukhachoi.weddingmanagement;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import java.util.ArrayList;

import adapter.adapterTraCuu;
import model.TiecCuoi;
import sqlite.Databasehelper;

public class activityTraCuu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    ListView lvTraCuu;
    ArrayAdapter<TiecCuoi>arrayAdapter;
    ArrayList<TiecCuoi>ds;

    ArrayList<String> tenvc;
    AutoCompleteTextView txtSearch;
    ArrayAdapter<String>adaptertenvc;

    private Toolbar mToolbar;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;

    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tra_cuu);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_navi);
        mToolbar.setTitle("Tra cứu");
        setSupportActionBar(mToolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_2);
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
    public boolean onPrepareOptionsMenu(android.view.Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_search:
                handleMenuSearch();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_2);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_2);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addControls() {
        Databasehelper myDatabase = new Databasehelper(this);

        myDatabase.Khoitai();

        tenvc=new ArrayList<>();
        adaptertenvc=new ArrayAdapter(activityTraCuu.this,android.R.layout.select_dialog_item,tenvc);

        lvTraCuu= (ListView) findViewById(R.id.lvTraCuu);
        ds=new ArrayList<TiecCuoi>();
        arrayAdapter=new adapterTraCuu(activityTraCuu.this,R.layout.info,ds);
        lvTraCuu.setAdapter(arrayAdapter);


        SQLiteDatabase database = myDatabase.getMyDatabase();
        Cursor cursor=database.rawQuery("SELECT * FROM THONGTIN",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String makh = cursor.getString(0);
            String chure = cursor.getString(1);
            String codau = cursor.getString(2);
            String sanh = cursor.getString(9);
            String ngay = cursor.getString(4);
            String ca = cursor.getString(5);
            int soban = cursor.getInt(7);
            int flagtenchure = 1;
            int flagtencodau=1;
            for(String ten:tenvc)
            {
                if(chure.equals(ten))
                {
                    flagtenchure=0;
                }
                if(codau.equals(ten))
                {
                    flagtencodau=0;
                }

            }
            if(flagtenchure==1)
                tenvc.add(chure);
            if(flagtencodau==1)
                tenvc.add(codau);
            ds.add(new TiecCuoi(makh, chure, codau, sanh, ngay, ca, soban));
            adaptertenvc.notifyDataSetChanged();
            arrayAdapter.notifyDataSetChanged();
            cursor.moveToNext();
        }
        cursor.close();

  }

    private void addEvents() {
        //Bắt sự kiện chọn


    }
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void handleMenuSearch(){
        ActionBar action = getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(txtSearch.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(android.R.drawable.ic_menu_search));

            isSearchOpened = false;
            recreate();
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title
            txtSearch= (AutoCompleteTextView) action.getCustomView().findViewById(R.id.txtSearch);;
            txtSearch.setThreshold(1);
            txtSearch.setAdapter(adaptertenvc);



            txtSearch.requestFocus();

            txtSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String temp=txtSearch.getText().toString();
                    for(int a=0;a<ds.size();a++)
                    {
                        if(ds.get(a).getCodau().equals(temp) || ds.get(a).getChure().equals(temp))
                        {
                            TiecCuoi tc=ds.get(a);
                            ds.remove(a);
                            tc.setCheck(1);
                            ds.add(0,tc);
                            arrayAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });

            //add the close icon
            mSearchAction.setIcon(getResources().getDrawable(android.R.drawable.ic_menu_close_clear_cancel));
            isSearchOpened = true;
        }
    }

    private void doSearch() {

    }
}


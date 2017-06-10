package com.example.vukhachoi.weddingmanagement;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageView imgTiepNhan,imgDatTiec,imgTraCuu,imgHoaDon,imgBaoCao,imgThayDoi;
    NavigationView navigationView;
    int check=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_navi);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        addControls();
        addEvents();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
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
            Intent intent=new Intent(MainActivity.this,HallsActivity.class);
            intent.putExtra("code","0");
            startActivity(intent);
        } else if (id == R.id.tracuu_item) {
            Intent intent=new Intent(MainActivity.this,activityTraCuu.class);
            intent.putExtra("code","1");
            startActivity(intent);
        } else if (id == R.id.hoadon_item) {
            Intent intent=new Intent(MainActivity.this,LapHoaDon.class);
            intent.putExtra("code","2");
            startActivity(intent);

        } else if (id == R.id.baocao_item) {
            Intent intent=new Intent(MainActivity.this,BaoCaoThang.class);
            intent.putExtra("code","3");
            startActivity(intent);
;

        } else if (id == R.id.quydinh_item) {
            Intent intent = new Intent(MainActivity.this, quy_dinh_activity.class);
            intent.putExtra("code", "4");
            startActivity(intent);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onRestart() {


        super.onRestart();
        recreate();

    }

    private void addEvents() {
        HoatAnh();

    }

    private void addControls() {
        imgTiepNhan= (ImageView) findViewById(R.id.imgTiepNhan);
        imgTraCuu= (ImageView) findViewById(R.id.imgTraCuu);
        imgHoaDon= (ImageView) findViewById(R.id.imgHoaDon);
        imgBaoCao= (ImageView) findViewById(R.id.imgBaoCao);
        imgThayDoi= (ImageView) findViewById(R.id.imgThayDoi);

    }
    private void HoatAnh()
    {
        imgBaoCao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThayDoiAnh(imgBaoCao,R.drawable.baocao2);
                //navigationView.getMenu().getItem(3).setChecked(true);
                Intent intent=new Intent(MainActivity.this,BaoCaoThang.class);
                intent.putExtra("code","3");
                startActivity(intent);
            }
        });
        imgHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThayDoiAnh(imgHoaDon,R.drawable.hoadon2);
                //navigationView.getMenu().getItem(2).setChecked(true);
                Intent intent=new Intent(MainActivity.this,LapHoaDon.class);
                intent.putExtra("code","2");
                startActivity(intent);
            }
        });


        imgTraCuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThayDoiAnh(imgTraCuu,R.drawable.tiec2);
                //navigationView.getMenu().getItem(1).setChecked(true);
                Intent intent=new Intent(MainActivity.this,activityTraCuu.class);
                intent.putExtra("code","1");
                startActivity(intent);
            }
        });

        imgTiepNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThayDoiAnh(imgTiepNhan,R.drawable.sanh2);
                //navigationView.getMenu().getItem(0).setChecked(true);

                Intent intent=new Intent(MainActivity.this,HallsActivity.class);
                intent.putExtra("code","0");
                startActivity(intent);
            }
        });
        imgThayDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThayDoiAnh(imgThayDoi,R.drawable.thaydoi2);
               // navigationView.getMenu().getItem(4).setChecked(true);
                Intent intent=new Intent(MainActivity.this,quy_dinh_activity.class);
                intent.putExtra("code","4");
                startActivity(intent);
            }
        });
    }

    private void ThayDoiAnh(final ImageView id1, final int id2)
    {

        id1.setImageResource(id2);
    }
}

package com.example.vukhachoi.weddingmanagement;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.Adapter_CapNhat;
import adapter.Adapter_CapNhat_DichVu;
import model.Dichvu;
import model.MonAn;
import sqlite.Databasehelper;

public class quy_dinh_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TabHost tabHost;
    TextView txtnoidung;
    Switch swcquydinh;
    Databasehelper myDatabase;
    SQLiteDatabase database;
    final int THEMMONAN=0,XOAMONAN=1,SUAMONAN=2,THEMDICHVU=4,XOADICHVU=5,SUADICHVU=6,MACDINH=7;
    Adapter_CapNhat adapter;
    Adapter_CapNhat_DichVu adapterDIchVu;
    List<MonAn> listMonAn;
    List<Dichvu> listDichVu;
    int  luachon=MACDINH;
    String TenMonAn;
    int Gia;
    int Position=0;
    int PositionDichVu=0;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quy_dinh);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_navi);
        toolbar.setTitle("Quy định");
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_6);
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
        AddTabhost();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_6);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_6);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addControl() {
        myDatabase = new Databasehelper(this);
        myDatabase.Khoitai();
        database = myDatabase.getMyDatabase();
        tabHost= (TabHost) findViewById(R.id.tabhost);
        txtnoidung= (TextView) findViewById(R.id.txtNoiDung);
        txtnoidung.setText("     Quốc có quốc pháp, gia có gia quy, bất kể loại hình kinh doanh của bạn là nhà hàng, quán ăn hay quán cafe, cũng đều cần có một bộ nội quy thật chuẩn và phù hợp với thực tế nhà hàng của bạn. Nó không những giúp nhà hàng của bạn tránh khỏi những rắc rối vụn vặt trong hoạt động thường ngày mà còn giúp nhân viên của bạn có cái nhìn tổng quan về quyền lợi và trách nhiệm của mình trong hoạt động công tác tại nhà hàng.Nhà hàng sẽ áp dụng những hình thức thưởng phạt thích hợp cho những cặp cô dâu chú rể khi đặt tiệc cưới tại nhà hàng . Đơn giá thanh toán các dich vụ của nhà hàng se được tính theo đơn giá trong phiếu đặt tiệc cưới. Ngày thanh toán trùng với ngày đãi tiệc, thanh toán trễ phạt 1% ngày. " );
        swcquydinh= (Switch) findViewById(R.id.swcquydinh);
        Cursor c=database.rawQuery("select DatQuyDinh from quydinh",null);
        c.moveToFirst();
        if(c.getString(0).toString().equals("1"))
            swcquydinh.setChecked(true);
        else swcquydinh.setChecked(false);
        c.close();


        //tab cap nhat
        ListView lsvMonAn= (ListView) findViewById(R.id.lsv_CapNhatMonAn);
        ListView lsvDichvu= (ListView) findViewById(R.id.lsv_CapNhatDichVu);
        listMonAn=new ArrayList<>();
        listDichVu=new ArrayList<>();
        //adapter mon an
        Cursor cursor=database.rawQuery("select TenMonAn,DonGia from MonAn",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MonAn m=new MonAn();
            m.setTenMonAn(cursor.getString(0).toString());
            m.setGia(Integer.parseInt(cursor.getString(1).toString()));
m.setXoa(false);
            m.setVisible(false);
            listMonAn.add(m);
            cursor.moveToNext();
        }
        cursor.close();
         adapter=new Adapter_CapNhat(quy_dinh_activity.this,R.layout.item_cap_nhat_mon_an,listMonAn);
        lsvMonAn.setAdapter(adapter);
        //adapter dich vu
        Cursor cursordichvu=database.rawQuery("select TenDV,DonGia from dichvu",null);
        cursordichvu.moveToFirst();
        while (!cursordichvu.isAfterLast())
        {
            Dichvu m=new Dichvu();
            m.setTendichvu(cursordichvu.getString(0).toString());
            m.setDongia(Integer.parseInt(cursordichvu.getString(1).toString()));
m.setXoa(false);
            m.setVisible(false);
            listDichVu.add(m);
            cursordichvu.moveToNext();
        }
        cursordichvu.close();
        adapterDIchVu=new Adapter_CapNhat_DichVu(quy_dinh_activity.this,R.layout.item_cap_nhat_mon_an,listDichVu);
        lsvDichvu.setAdapter(adapterDIchVu);
    }

    private void addEvent() {
        swcquydinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values=new ContentValues();
                values.put("DatQuyDinh",((Switch)v).isChecked());
                database.update("QuyDinh",values,null,null);
                if(((Switch)v).isChecked())
                    Toast.makeText(quy_dinh_activity.this, "Đã đặt hình phạt", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(quy_dinh_activity.this, "Đã hủy hình phạt", Toast.LENGTH_SHORT).show();


            }
        });
        ChinhSuaMonAn();
        ChinhSuaDichVu();
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog dialog =null;
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogview;
        AlertDialog.Builder dialogbuilder;
                dialogview = inflater.inflate(R.layout.dialog_mon_an_dich_vu, null);
                dialogbuilder = new AlertDialog.Builder(this);
                dialogbuilder.setView(dialogview);
                dialog = dialogbuilder.create();
        luachon=MACDINH;
        return dialog;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        final AlertDialog alertDialog;
        TextView txtTen;
        TextView txtNhapGia;
        final    EditText edtNhapTen;
        final    EditText edtNhapGia;
        Button btnXong;
        switch(id)
        {
            case THEMMONAN:
                alertDialog = (AlertDialog) dialog;
                txtTen= (TextView) alertDialog.findViewById(R.id.txtTen);
            edtNhapTen= (EditText) alertDialog.findViewById(R.id.edtNhapTen);
               txtNhapGia = (TextView) alertDialog.findViewById(R.id.txtNhapGia);
             edtNhapGia= (EditText) alertDialog.findViewById(R.id.edtNhapGia);
               btnXong= (Button) alertDialog.findViewById(R.id.btnXong);
                txtTen.setText("Nhập tên món ăn");
                txtNhapGia.setText("Nhập Giá yêu cầu");


        btnXong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtNhapGia.getText().toString().equals(null)||edtNhapTen.getText().toString().equals(null))
                {
                    Toast.makeText(quy_dinh_activity.this, "Các trường không được bỏ trống", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    try
                    {
                ContentValues values = new ContentValues();
                values.put("DonGia", Integer.parseInt(edtNhapGia.getText().toString()));
                values.put("TenMonAn",edtNhapTen.getText().toString());
                database.insertWithOnConflict("MonAn",null,values,SQLiteDatabase.CONFLICT_FAIL);
                        MonAn monan=new MonAn();
                        monan.setGia(Integer.parseInt(edtNhapGia.getText().toString()));
                        monan.setTenMonAn(edtNhapTen.getText().toString());
                        monan.setVisible(false);
                        monan.setXoa(false);
                        listMonAn.add(monan);
                        adapter.notifyDataSetChanged();
                        edtNhapTen.setText(null);
                        edtNhapGia.setText(null);
                        alertDialog.dismiss();

                    }
                    catch (SQLiteConstraintException Sqle)
                    {
                        Toast.makeText(quy_dinh_activity.this, "Thêm món ăn thất bại", Toast.LENGTH_SHORT).show();
                    }

                }
                luachon=MACDINH;
            }
        });





                break;
            case SUAMONAN:
                alertDialog = (AlertDialog) dialog;
                txtTen= (TextView) alertDialog.findViewById(R.id.txtTen);
                edtNhapTen= (EditText) alertDialog.findViewById(R.id.edtNhapTen);
                txtNhapGia = (TextView) alertDialog.findViewById(R.id.txtNhapGia);
                 edtNhapGia= (EditText) alertDialog.findViewById(R.id.edtNhapGia);
                btnXong= (Button) alertDialog.findViewById(R.id.btnXong);
                txtTen.setText("Tên món ăn");
                txtNhapGia.setText("Nhập Giá yêu cầu");
                edtNhapTen.setEnabled(false);
                edtNhapTen.setText(TenMonAn);
                edtNhapGia.setText(Gia+"");
                btnXong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edtNhapGia.getText().toString().equals(null)||edtNhapTen.getText().toString().equals(null))
                        {
                            Toast.makeText(quy_dinh_activity.this, "Các trường không được bỏ trống", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            try
                            {
                                ContentValues values = new ContentValues();
                                values.put("DonGia", Integer.parseInt(edtNhapGia.getText().toString()));

                                database.updateWithOnConflict("MonAn",values,"TenMonAn=?",new String[]{TenMonAn},SQLiteDatabase.CONFLICT_FAIL);
                               listMonAn.get(Position).setGia(Integer.parseInt(edtNhapGia.getText().toString()));
                                adapter.notifyDataSetChanged();
                                alertDialog.dismiss();

                            }
                            catch (SQLiteConstraintException Sqle)
                            {
                                Toast.makeText(quy_dinh_activity.this, "Thêm món ăn thất bại", Toast.LENGTH_SHORT).show();
                            }

                        }

                    }
                });

                break;
            case THEMDICHVU:
                alertDialog = (AlertDialog) dialog;
                txtTen= (TextView) alertDialog.findViewById(R.id.txtTen);
                edtNhapTen= (EditText) alertDialog.findViewById(R.id.edtNhapTen);
                txtNhapGia = (TextView) alertDialog.findViewById(R.id.txtNhapGia);
                edtNhapGia= (EditText) alertDialog.findViewById(R.id.edtNhapGia);
                btnXong= (Button) alertDialog.findViewById(R.id.btnXong);
                txtTen.setText("Nhập tên dịch vụ");
                txtNhapGia.setText("Nhập Giá yêu cầu");


                btnXong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(edtNhapGia.getText().toString().equals(null)||edtNhapTen.getText().toString().equals(null))
                        {
                            Toast.makeText(quy_dinh_activity.this, "Các trường không được bỏ trống", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            try
                            {
                                ContentValues values = new ContentValues();
                                values.put("DonGia", Integer.parseInt(edtNhapGia.getText().toString()));
                                values.put("TenDV",edtNhapTen.getText().toString());
                                database.insertWithOnConflict("DichVu",null,values,SQLiteDatabase.CONFLICT_FAIL);
                                Dichvu dichvu=new Dichvu();
                                dichvu.setDongia(Integer.parseInt(edtNhapGia.getText().toString()));
                                dichvu.setTendichvu(edtNhapTen.getText().toString());
                                dichvu.setVisible(false);
                                dichvu.setXoa(false);
                                listDichVu.add(dichvu);
                                adapterDIchVu.notifyDataSetChanged();
                                edtNhapTen.setText(null);
                                edtNhapGia.setText(null);
                                alertDialog.dismiss();

                            }
                            catch (SQLiteConstraintException Sqle)
                            {
                                Toast.makeText(quy_dinh_activity.this, "Thêm dịch vụ thất bại", Toast.LENGTH_SHORT).show();
                            }

                        }
                        luachon=MACDINH;
                    }
                });





                break;

            case SUADICHVU:
                alertDialog = (AlertDialog) dialog;
                txtTen= (TextView) alertDialog.findViewById(R.id.txtTen);
                edtNhapTen= (EditText) alertDialog.findViewById(R.id.edtNhapTen);
                txtNhapGia = (TextView) alertDialog.findViewById(R.id.txtNhapGia);
                edtNhapGia= (EditText) alertDialog.findViewById(R.id.edtNhapGia);
                btnXong= (Button) alertDialog.findViewById(R.id.btnXong);
                txtTen.setText("Tên dịch vụ");
                txtNhapGia.setText("Nhập Giá yêu cầu");
                edtNhapTen.setEnabled(false);
                edtNhapTen.setText(TenMonAn);
                edtNhapGia.setText(Gia+"");
                btnXong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edtNhapGia.getText().toString().equals(null)||edtNhapTen.getText().toString().equals(null))
                        {
                            Toast.makeText(quy_dinh_activity.this, "Các trường không được bỏ trống", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            try
                            {
                                ContentValues values = new ContentValues();
                                values.put("DonGia", Integer.parseInt(edtNhapGia.getText().toString()));

                                database.updateWithOnConflict("DichVu",values,"TenDV=?",new String[]{TenMonAn},SQLiteDatabase.CONFLICT_FAIL);
                                listDichVu.get(PositionDichVu).setDongia(Integer.parseInt(edtNhapGia.getText().toString()));
                                adapterDIchVu.notifyDataSetChanged();
                                alertDialog.dismiss();

                            }
                            catch (SQLiteConstraintException Sqle)
                            {
                                Toast.makeText(quy_dinh_activity.this, "Thêm dịch vụ thất bại", Toast.LENGTH_SHORT).show();
                            }

                        }

                    }
                });

                break;
        }


    }


int xoa=0;
    private  void ChinhSuaMonAn()
    {
        Button btnXoaMonAn= (Button) findViewById(R.id.btnXoaMonAn);
        Button btnThemMonAn= (Button) findViewById(R.id.btnThemMonAn);
        Button btnSuaMonAn= (Button) findViewById(R.id.btnSuaMonAn);
        ListView lsvMonAn= (ListView) findViewById(R.id.lsv_CapNhatMonAn);
        btnThemMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(THEMMONAN);
            }
        });
        btnXoaMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(luachon==MACDINH) {
                   for (MonAn monAn : listMonAn) {
                       monAn.setVisible(true);
                   }
                   xoa=1;
               }

                if(luachon==XOAMONAN)
                {
                  adapter.Remove();
                    for(MonAn monAn:listMonAn)
                    {
                        monAn.setVisible(false);
                    }
                    luachon= MACDINH;
                    xoa=0;
                }


                adapter.notifyDataSetChanged();
              if(xoa==1) {
                  luachon = XOAMONAN;
              }
            }
        });


        btnSuaMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               luachon=SUAMONAN;
            }
        });

lsvMonAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Position=position;
        if(luachon==SUAMONAN)
        {

            TenMonAn=listMonAn.get(position).getTenMonAn();
            Gia=listMonAn.get(position).getGia();
           showDialog(SUAMONAN);
        }
    }
});

    }


int xoaDichVu=0;
    private  void ChinhSuaDichVu()
    {
        Button btnXoaDichVu= (Button) findViewById(R.id.btnXoaDichVu);
        Button btnThemDichVu= (Button) findViewById(R.id.btnThemDichVu);
        Button btnSuaDichVu= (Button) findViewById(R.id.btnSuaDichVu);
        final ListView lsvDichVu= (ListView) findViewById(R.id.lsv_CapNhatDichVu);
        btnThemDichVu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(THEMDICHVU);
            }
        });
        btnXoaDichVu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(luachon==MACDINH) {
                    for (Dichvu dichvu : listDichVu) {
                        dichvu.setVisible(true);
                    }
                    xoaDichVu=1;
                }

                if(luachon==XOADICHVU)
                {
                    adapterDIchVu.Remove();
                    for(Dichvu dichvu : listDichVu)
                    {
                        dichvu.setVisible(false);
                    }
                    luachon= MACDINH;
                    xoaDichVu=0;
                }


                adapterDIchVu.notifyDataSetChanged();
                if(xoaDichVu==1) {
                    luachon = XOADICHVU;
                }
            }

        });


        btnSuaDichVu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luachon=SUADICHVU;
            }
        });

        lsvDichVu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PositionDichVu =position;
                if(luachon==SUADICHVU)
                {

                    TenMonAn=listDichVu.get(position).getTendichvu();
                    Gia=listDichVu.get(position).getDongia();
                    showDialog(SUADICHVU);
                }
            }
        });

    }
    //addtabhost
    private void AddTabhost()
    {

        tabHost.setup();
        TabHost.TabSpec tab1=tabHost.newTabSpec("t1");
        tab1.setIndicator("Quy Định");
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2=tabHost.newTabSpec("t2");
        tab2.setIndicator("Cập Nhật");
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab2);

//        tabHost.setCurrentTab(0);
    }
}

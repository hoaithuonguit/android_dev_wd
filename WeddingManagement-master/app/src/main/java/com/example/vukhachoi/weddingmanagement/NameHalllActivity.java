package com.example.vukhachoi.weddingmanagement;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.RecyclerNameHallAdapter;
import adapter.RecyclerViewItemClickInterface;
import hall.wedding.management.NameHall;
import sqlite.Databasehelper;

import static android.view.View.VISIBLE;

public class NameHalllActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Databasehelper myDatabase;
    SQLiteDatabase database;
    RecyclerNameHallAdapter adapterHallName;
    RecyclerView rcHallName;
    Button btnxoa;
    List<NameHall> nameHalls;
    Toolbar toolbar;
    MenuItem myMenu;
    ListView lv_edit;
    int flag=0;
    View temp;
    ArrayAdapter<String> adapter;
    ArrayList<String> ds;
    final int THEM=0;
    final int SUA=2;
    final  int XOA=1;
    final  int MACDINH=3;
    final  int THAYDOIMATKHAU=4;
    int luachon=MACDINH;
    Bundle extras;
    String LoaiSanh;
    String banToiDa;
    String giaToiThieu;
    String TenSanh;
    RelativeLayout rltbackground;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_halll);
        toolbar= (Toolbar) findViewById(R.id.toolbar_namehall);
        toolbar.setTitle("Thông tin sảnh");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_3);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_3);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_3);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onResume() {
        lv_edit.setVisibility(View.INVISIBLE);

        super.onResume();
    }

    private void addEvents() {
        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==1)
                {
                    lv_edit.setVisibility(View.INVISIBLE);
                    flag=0;
                }
            }
        });
        lv_edit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i)
                {
                    case 0:
                        luachon=THEM;
                        showDialog(-1);
                        break;
                    case 1:
                        //Xóa
                        luachon=XOA;
                        showDialog(-1);

                        break;
                    case 2:
                        //Sửa
                        luachon=SUA;
                        showDialog(-1);
                        break;
                    case 3:
                        luachon=MACDINH;
                        for(NameHall hall:nameHalls)
                        {
                            hall.setVisible(false);
                        }
                        adapterHallName.notifyDataSetChanged();
                        break;
                }

                lv_edit.setVisibility(View.INVISIBLE);
                flag=0;
            }
        });
    }

    private void addControls() {
        myDatabase = new Databasehelper(this);
        extras=getIntent().getExtras();
        LoaiSanh= extras.getString("NameHall");
        myDatabase.Khoitai();
        database = myDatabase.getMyDatabase();

        Context context;
        context = getApplicationContext();
        RecyclerView.LayoutManager recyclerViewLayoutManager;
        recyclerViewLayoutManager=new GridLayoutManager(context, 2);
        Bundle extras=getIntent().getExtras();
        String data= extras.getString("NameHall");

        btnxoa= (Button) findViewById(R.id.btnxoa);
        rltbackground= (RelativeLayout) findViewById(R.id.rltbackground);
        nameHalls = new ArrayList<>();
        Cursor cursor =database.rawQuery("Select * from sanh where loaisanh=? ",new String[]{data});
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            NameHall nameHall=new NameHall();
            nameHall.setActive(Boolean.parseBoolean(cursor.getString(5)));
            nameHall.setBanToiDa(Integer.parseInt(cursor.getString(2)));
            nameHall.setGiaToiThieu(Integer.parseInt(cursor.getString(3)));
            nameHall.setNamehall(cursor.getString(1));
            nameHall.setVisible(false);
            nameHall.setCheck(false);
            nameHalls.add(nameHall);
            if(cursor.getString(5).equals("1"))
            {
                nameHall.setImgActive(R.drawable.red);
            }
            else
            {
                nameHall.setImgActive(R.drawable.active);
            }
            cursor.moveToNext();
        }

        cursor.close();
        rcHallName= (RecyclerView) findViewById(R.id.rcHallName);
        // LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcHallName.setLayoutManager(recyclerViewLayoutManager);
        rcHallName.setHasFixedSize(true);
        adapterHallName =new RecyclerNameHallAdapter(nameHalls,this);
        rcHallName.setAdapter(adapterHallName);

        adapterHallName.setOnItemClickListener(new RecyclerViewItemClickInterface() {
            @Override
            public void onItemclick(View v, NameHall viewModel) {
                if(luachon==MACDINH)
                {
                    Intent intent=new Intent(NameHalllActivity.this, DetailWeddingActivity.class);
                    intent.putExtra("Tensanh",viewModel.getNamehall().toString());
                    v.getContext().startActivity(intent);
                }
                else if(luachon==SUA)
                {
                    giaToiThieu=viewModel.getGiaToiThieu()+"";
                    banToiDa=viewModel.getBanToiDa()+"";
                    TenSanh=viewModel.getNamehall()+"";
                    showDialog(SUA);
                }
            }
        });


        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(NameHall hall:nameHalls)
                {
                    hall.setVisible(false);
                }
                adapterHallName.notifyDataSetChanged();
                adapterHallName.remove();
                rltbackground.setVisibility(View.INVISIBLE);

            }
        });

        lv_edit= (ListView) findViewById(R.id.lv_edit);
        ds=new ArrayList<>();
        ds.add("Thêm");
        ds.add("Xóa");
        ds.add("Sửa");
        ds.add("Mặc định");
        adapter=new ArrayAdapter<String>(NameHalllActivity.this,android.R.layout.simple_list_item_1,ds);
        lv_edit.setAdapter(adapter);
        temp=findViewById(R.id.activity_name_halll);
    }

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.mane_namehall, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(android.view.Menu menu) {
        myMenu = menu.findItem(R.id.edit_menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onRestart() {
        recreate();
        super.onRestart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.edit_menu:
                if(flag==0)
                {
                    lv_edit.setVisibility(VISIBLE);
                    flag=1;
                }
                else
                {
                    lv_edit.setVisibility(View.INVISIBLE);
                    flag=0;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog dialog =null;
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogview;
        AlertDialog.Builder dialogbuilder;
        switch (id)
        {
            case -1:
                dialogview = inflater.inflate(R.layout.dialog_login, null);
                dialogbuilder = new AlertDialog.Builder(this);
                dialogbuilder.setView(dialogview);

                dialog = dialogbuilder.create();

                break;
            case THEM:
                dialogview = inflater.inflate(R.layout.dialog_them_sua_sanh, null);
                dialogbuilder = new AlertDialog.Builder(this);
                dialogbuilder.setView(dialogview);

                dialog = dialogbuilder.create();
                luachon=MACDINH;
                break;
            case SUA:
                dialogview = inflater.inflate(R.layout.dialog_them_sua_sanh, null);
                dialogbuilder = new AlertDialog.Builder(this);
                dialogbuilder.setView(dialogview);

                dialog = dialogbuilder.create();
                break;

            case THAYDOIMATKHAU:

                dialogview = inflater.inflate(R.layout.change_password, null);
                dialogbuilder = new AlertDialog.Builder(this);
                dialogbuilder.setView(dialogview);

                dialog = dialogbuilder.create();


                break;
        }

        return dialog;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        final AlertDialog alertDialog;
        Button btnThem;
        Button btnSua;
        final    EditText edtgiatoithieu;
        final   EditText edtbantoida;
        final    EditText edtTensanh;
        switch (id) {
            case -1:
                //dang nhap
                alertDialog = (AlertDialog) dialog;
                Button btndangnhap= (Button) alertDialog.findViewById(R.id.btnDangNhap);
                Button btnHuy= (Button) alertDialog.findViewById(R.id.btnHuy);
                TextView txtforgot= (TextView) alertDialog.findViewById(R.id.txtforgot);
                final EditText edtpass= (EditText) alertDialog.findViewById(R.id.edtpass);
                btndangnhap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor cursor=database.rawQuery("select  * from user",null);
                        cursor.moveToFirst();
                        if(edtpass.getText().toString().equals(cursor.getString(1).toString())) {
                            alertDialog.dismiss();
                            switch (luachon) {
                                case THEM:
                                    showDialog(THEM);
                                    luachon = MACDINH;
                                    break;

                                case -1:
                                    luachon = MACDINH;
                                    break;
                                case XOA:
                                    for (NameHall hall : nameHalls) {
                                        hall.setVisible(true);
                                    }
                                    adapterHallName.notifyDataSetChanged();

                                    rltbackground.setVisibility(VISIBLE);

                                    break;
                            }
                            edtpass.setText(null);
                        }
                        else Toast.makeText(NameHalllActivity.this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                        cursor.close();
                    }
                });
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        luachon=MACDINH;
                    }
                });
                txtforgot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        showDialog(THAYDOIMATKHAU);
                    }
                });






                break;
            case THEM:
                //them sanh
                alertDialog = (AlertDialog) dialog;
                btnThem= (Button) alertDialog.findViewById(R.id.btnThem);
                btnSua= (Button) alertDialog.findViewById(R.id.btnSua);
                btnSua.setVisibility(View.INVISIBLE);
                edtgiatoithieu= (EditText) alertDialog.findViewById(R.id.edtgiatoithieu);
                edtbantoida= (EditText) alertDialog.findViewById(R.id.edtbantoida);
                edtTensanh= (EditText) alertDialog.findViewById(R.id.edttensanh);

                btnThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ContentValues values=new ContentValues();

                        if(edtTensanh.getText().toString().isEmpty()||edtbantoida.getText().toString().isEmpty()||edtgiatoithieu.getText().toString().isEmpty() ) {

                            Toast.makeText(NameHalllActivity.this, "Không được bỏ trông các trường", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            try {
                                values.put("LoaiSanh", LoaiSanh);
                                values.put("TenSanh", edtTensanh.getText().toString());
                                values.put("SoBanToiDa", edtbantoida.getText().toString());
                                values.put("DonGiaToiThieu", edtgiatoithieu.getText().toString());
                                values.put("GhiChu", "");
                                values.put("TinhTrang", "0");
                                database.insertWithOnConflict("Sanh", null, values, SQLiteDatabase.CONFLICT_FAIL);
                                Toast.makeText(NameHalllActivity.this, "Thêm sảnh thành công", Toast.LENGTH_SHORT).show();
                                recreate();
                                alertDialog.dismiss();
                            } catch (SQLiteConstraintException SQLe) {
                                Toast.makeText(NameHalllActivity.this, "Thêm sảnh thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                break;
            case SUA:
                //sua sanh
                alertDialog = (AlertDialog) dialog;
                btnThem= (Button) alertDialog.findViewById(R.id.btnThem);
                btnSua= (Button) alertDialog.findViewById(R.id.btnSua);
                btnThem.setVisibility(View.INVISIBLE);
                edtgiatoithieu= (EditText) alertDialog.findViewById(R.id.edtgiatoithieu);
                edtbantoida= (EditText) alertDialog.findViewById(R.id.edtbantoida);
                edtTensanh= (EditText) alertDialog.findViewById(R.id.edttensanh);
                edtbantoida.setText(banToiDa);
                edtgiatoithieu.setText(giaToiThieu);
                edtTensanh.setText(TenSanh);
                edtTensanh.setEnabled(false);
                edtTensanh.setKeyListener(null);

                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ContentValues values=new ContentValues();
                        Bundle extras=getIntent().getExtras();
                        String Loaisanh= extras.getString("NameHall");
                        if(edtTensanh.getText().toString().isEmpty()||edtbantoida.getText().toString().isEmpty()||edtgiatoithieu.getText().toString().isEmpty() ) {

                            Toast.makeText(NameHalllActivity.this, "Không được bỏ trông các trường", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            try {

                                values.put("TenSanh", edtTensanh.getText().toString());
                                values.put("SoBanToiDa", edtbantoida.getText().toString());
                                values.put("DonGiaToiThieu", edtgiatoithieu.getText().toString());

                                database.updateWithOnConflict("Sanh", values, "TenSanh=? and LoaiSanh=?", new String[]{edtTensanh.getText().toString(), Loaisanh}, SQLiteDatabase.CONFLICT_FAIL);
                                Toast.makeText(NameHalllActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                recreate();
                                alertDialog.dismiss();
                            } catch (SQLiteConstraintException SQLe) {
                                Toast.makeText(NameHalllActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                break;
            case THAYDOIMATKHAU:
                alertDialog = (AlertDialog) dialog;
                final EditText edtMatKhauCu,edtMatKhauMoi,edtXacNhanMatKhau;
                final TextView txtWrongpasscu,txtWrongXacNhan;
                Button btnchange;
                edtMatKhauCu= (EditText) alertDialog.findViewById(R.id.edtmatkhaucu);
                edtMatKhauMoi= (EditText) alertDialog.findViewById(R.id.edtmatkhaumoi);
                edtXacNhanMatKhau= (EditText) alertDialog.findViewById(R.id.edtxacnhan);
                txtWrongpasscu= (TextView) alertDialog.findViewById(R.id.txtWrongpasscu);
                txtWrongXacNhan= (TextView) alertDialog.findViewById(R.id.txtWrongXacNhan);
                btnchange= (Button) alertDialog.findViewById(R.id.btnchange);
                final String matKhauCu;
                Cursor cursor=database.rawQuery("select  * from user",null);
                cursor.moveToFirst();
                matKhauCu=cursor.getString(1).toString();
                cursor.close();
                edtMatKhauCu.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(!s.toString().equals(matKhauCu))
                        {
                            txtWrongpasscu.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            txtWrongpasscu.setVisibility(View.GONE);
                            edtMatKhauMoi.setEnabled(true);
                            edtXacNhanMatKhau.setEnabled(true);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                edtXacNhanMatKhau.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(!edtXacNhanMatKhau.getText().toString().equals(edtMatKhauMoi.getText().toString()))
                        {
                            txtWrongXacNhan.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            txtWrongXacNhan.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                btnchange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ContentValues values=new ContentValues();
                        values.put("MatKhau", edtMatKhauMoi.getText().toString());
                        database.update("User",values,null,null);
                        alertDialog.dismiss();
                        showDialog(-1);
                        Toast.makeText(NameHalllActivity.this, "Thay đỏi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        edtMatKhauCu.setText(null);
                        edtMatKhauMoi.setText(null);
                        edtXacNhanMatKhau.setText(null);
                    }
                });

                break;


        }
//        if(luachon!=SUA &&luachon!=-1)   luachon=MACDINH;
    }

}

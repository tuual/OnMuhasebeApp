package com.example.sqlliteuygulama;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.sqlliteuygulama.databinding.ActivityMainBinding;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Connection connect;
    ConnectionHelper dbHelper;
    listModel listModel;

    ActivityMainBinding binding;
    SimpleAdapter adapter;
    int sayac = 0;
    private String kullaniciAdi,sifre,email,id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbHelper = new ConnectionHelper(getApplicationContext());
        listModel = new listModel(MainActivity.this);


        binding.button.setOnClickListener(view -> {
            VeriEkleme();
        });
        binding.buttonID.setOnClickListener(view -> {
            VeriOkuma();
        });


        binding.listview1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                List<Map<String, String>> veriler = listModel.getList();
                id = veriler.get(i).get("ID");
                kullaniciAdi = veriler.get(i).get("KullaniciAdi");
                sifre = veriler.get(i).get("Sifre");
                email = veriler.get(i).get("Email");

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Veri Silme");
                builder.setMessage("Bilgileri " + "\n" + "Kullanıcı Adı:" + " " + kullaniciAdi + "\n" + "Şifre:" + " " + sifre + " \n" + "Email:" + " " + email + "\n " + "Olan Bilgileri Silmek İstiyor musunuz ?");                builder.setNegativeButton("Hayır", null);
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String query = "DELETE FROM kullanicilar WHERE ID="+id;

                        try {
                            dbHelper.execSQL(query,MainActivity.this);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }


                        Toast.makeText(MainActivity.this, "Kayıt Silindi", Toast.LENGTH_SHORT).show();
                        VeriOkuma();
                    }
                });
                builder.show();
                return false;
            }
        });

        binding.listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                List<Map<String, String>> veriler = listModel.getList();
                id = veriler.get(i).get("ID");
                kullaniciAdi = veriler.get(i).get("KullaniciAdi");
                sifre = veriler.get(i).get("Sifre");
                email = veriler.get(i).get("Email");


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Veri Güncellemesi");
                builder.setMessage("Bu Veriyi Güncellemek İstiyor musunuz ?");
                builder.setNegativeButton("Hayır",null);
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getApplicationContext(),updateActivity.class);
                        intent.putExtra("kAdi",kullaniciAdi);
                        intent.putExtra("id",id);
                        intent.putExtra("kSifre",sifre);
                        intent.putExtra("kEmail",email);
                        startActivity(intent);



                    }
                });
             builder.show();
            }
        });
    }

    public void VeriOkuma(){
        List<Map<String,String>> VeriListesi;
        listModel listModel = new listModel(getApplicationContext());
        VeriListesi = listModel.getList();
        String[] Sutun={"KullaniciAdi","Sifre","Email"};
        int[] idler ={R.id.tvKullanici,R.id.tvSifre,R.id.tvEmail};
        adapter = new SimpleAdapter(MainActivity.this,VeriListesi,R.layout.listviewdesign,Sutun,idler);
        binding.listview1.setAdapter(adapter);
        sayac = 1;



    }
    public void VeriEkleme() {

        try {
            if (!binding.etkullaniciadi.getText().toString().matches("") && !binding.etsifre.getText().toString().matches("") && !binding.etemail.getText().toString().matches("")) {
                String query = "INSERT INTO kullanicilar (kullaniciAdi, sifre, email) VALUES ( '"
                        + binding.etkullaniciadi.getText().toString() + "','"
                        + binding.etsifre.getText().toString() + "','"
                        + binding.etemail.getText().toString() + "')";
                Toast.makeText(this, "Kayıt Oluşturuldu", Toast.LENGTH_SHORT).show();

                dbHelper.execSQL(query, MainActivity.this);
                binding.etemail.setText(null);
                binding.etsifre.setText(null);
                binding.etkullaniciadi.setText(null);
                if (sayac == 1) {
                    VeriOkuma();
                }
                else {

                }

            }
            else{
                binding.etkullaniciadi.setError("Boş Geçilemez");
                binding.etemail.setError("Boş Geçilemez");
                binding.etsifre.setError("Boş Geçilemez");

            }

        } catch (Exception se) {
            Toast.makeText(this, se.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

}

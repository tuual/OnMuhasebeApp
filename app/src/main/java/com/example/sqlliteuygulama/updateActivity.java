package com.example.sqlliteuygulama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.sqlliteuygulama.databinding.ActivityUpdateBinding;

import java.sql.SQLException;

public class updateActivity extends AppCompatActivity {

    private ActivityUpdateBinding binding;
    private String kadi,ksifre,kemail,etKadi,etSifre,etEmail;
    private ConnectionHelper dbHelper;

    private String id;
    Boolean actionButtonVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbHelper = new ConnectionHelper(getApplicationContext());


        GelenVeriler();


        binding.personList.setVisibility(View.GONE);
        binding.addPerson.setVisibility(View.GONE);
        binding.personListText.setVisibility(View.GONE);
        binding.addPersonText.setVisibility(View.GONE);
        actionButtonVisible = false;

        binding.add.setOnClickListener(view -> {
            if (!actionButtonVisible){
                binding.personList.show();
                binding.addPerson.show();
                binding.personListText.setVisibility(View.VISIBLE);
                binding.addPersonText.setVisibility(View.VISIBLE);
                actionButtonVisible = true;

            }
            else{
                binding.personList.hide();
                binding.addPerson.hide();
                binding.personListText.setVisibility(View.GONE);
                binding.addPersonText.setVisibility(View.GONE);
                actionButtonVisible = false;

            }

        });

        binding.addPerson.setOnClickListener(view -> {
            Intent intent = new Intent(this,addPersonActivity.class);
            startActivity(intent);
            finish();
        });
        binding.personList.setOnClickListener(view -> {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        });
        binding.btnUpdate.setOnClickListener(view -> {
            try {
                dataUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });


    }

    private void dataUpdate() throws SQLException {
        etEmail = binding.etemail.getText().toString().trim();
        etKadi = binding.etkullaniciadi.getText().toString().trim();
        etSifre = binding.etsifre.getText().toString().trim();

        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            String query = "UPDATE kullanicilar SET kullaniciAdi = '" + etKadi + "',sifre = '" + etSifre + "',email = '" + etEmail + "' WHERE ID = " + id;
            dbHelper.execSQL(query,getApplicationContext());
            db.close();
            Toast.makeText(this, "Güncelleme Başarılı", Toast.LENGTH_SHORT).show();


        } catch (android.database.SQLException ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }


    private void GelenVeriler(){
        Bundle bundle = getIntent().getExtras();
        kadi = bundle.getString("kAdi");
        id = bundle.getString("id");
        ksifre = bundle.getString("kSifre");
        kemail = bundle.getString("kEmail");

        binding.etkullaniciadi.setText(kadi);
        binding.etsifre.setText(ksifre);
        binding.etemail.setText(kemail);


    }
}
package com.example.my_java_project_of_contact;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.provider.ContactsContract;
import android.provider.Settings;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.my_java_project_of_contact.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

         if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
             ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_CONTACTS},1);
         }

        NavController navController = Navigation.findNavController(this, R.id.rcyc);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_CONTACTS)==PackageManager.PERMISSION_GRANTED){
                    String[] projection={ContactsContract.Contacts.DISPLAY_NAME};
                    ContentResolver resolver=getContentResolver();
                    Cursor cursor=resolver.query(ContactsContract.Contacts.CONTENT_URI,projection,null,null,ContactsContract.Contacts.DISPLAY_NAME);
                    ArrayList<Contact> contactArrayList =new ArrayList<Contact>();
                    if(cursor!=null){
                        String column1=ContactsContract.Contacts.DISPLAY_NAME;

                        while (cursor.moveToNext()){
                             String k1=cursor.getString(cursor.getColumnIndex(column1));
                            System.out.println(k1+" ");
                           Contact contact=new Contact(k1);
                           contactArrayList.add(contact);
                        }
                        Adapter adapter=new Adapter(contactArrayList);
                        binding.rcyc1.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        binding.rcyc1.setAdapter(adapter);
                        cursor.close();

                    }
                }else {
                    Snackbar.make(view,"Icazə lazımdır",Snackbar.LENGTH_INDEFINITE).
                            setAction("Icazə istə", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_CONTACTS)){
                                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_CONTACTS},1);
                                    } else {
                                        Intent intent=new Intent();
                                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri=Uri.fromParts("package",MainActivity.this.getPackageName(),null);
                                        intent.setData(uri);
                                        MainActivity.this.startActivity(intent);
                                    }
                                }
                            }).show();

                }
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.rcyc);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
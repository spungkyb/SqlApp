package com.spungkyb.sqlapp;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText edtId, edtName, edtSurname, edtMarks;
    Button btnAdd;
    Button btnViewAll;
    Button btnUpdate;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        edtId = (EditText)findViewById(R.id.edt_id);
        edtName = (EditText)findViewById(R.id.edt_name);
        edtSurname = (EditText)findViewById(R.id.edt_surname);
        edtMarks = (EditText)findViewById(R.id.edt_marks);
        btnAdd = (Button)findViewById(R.id.btn_add_data);
        btnViewAll = (Button)findViewById(R.id.btn_view_all);
        btnUpdate = (Button)findViewById(R.id.btn_update);
        btnDelete = (Button)findViewById(R.id.btn_delete);
        addData();
        viewAll();
        updateData();
        deleteData();

    }
    public void addData(){
        btnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted = myDb.insertData(edtName.getText().toString(),edtSurname.getText().toString(),edtMarks.getText().toString());
                        if (isInserted==true){
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public void updateData(){
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isUpdate = myDb.updateData(edtId.getText().toString(),edtName.getText().toString(),edtSurname.getText().toString(),edtMarks.getText().toString());
                        if (isUpdate==true){
                            Toast.makeText(MainActivity.this,"Data Updated",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this,"Data not Updated",Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        );
    }

    public void deleteData(){
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int deleteRow = myDb.deleteData(edtId.getText().toString());
                        if(deleteRow > 0){
                            Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this,"Data not Deleted",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
    public void viewAll(){
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor res = myDb.getAllData();
                        if (res.getCount()==0){
                            //show message
                            showMessage("Error","Nothing Found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()){
                            buffer.append("Id : "+res.getString(0)+"\n");
                            buffer.append("Name : "+res.getString(1)+"\n");
                            buffer.append("Surname : "+res.getString(2)+"\n");
                            buffer.append("Marks : "+res.getString(3)+"\n\n");
                        }

                        //show all data
                        showMessage("Data ",buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}

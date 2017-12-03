package com.example.addy.db_sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText inputName, inputMail, inputId;
    Button btnAdd, btnRead, btnClear, btnUpdate, btnDelete;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputName = findViewById(R.id.inputName);
        inputMail = findViewById(R.id.inputMail);
        inputId = findViewById(R.id.inputId);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);

        btnRead = findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        dbHelper = new DBHelper(this);

    }

    @Override
    public void onClick(View v) {
        String name = inputName.getText().toString();
        String mail = inputMail.getText().toString();
        String id = inputId.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues`s structure = 1 row from DB

        switch (v.getId()) {
            case R.id.btnAdd:
                contentValues.put(DBHelper.KEY_MAIL, mail);
                contentValues.put(DBHelper.KEY_NAME, name);
                database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
                inputName.setText(null);
                inputMail.setText(null);
                inputName.requestFocus();
                Toast.makeText(this, "row with name = " + name + " was added", Toast.LENGTH_LONG).show();
                Log.d("myLog", "row with name " + name + " and email  = " + mail + " was added");
                break;
            case R.id.btnRead:
                Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                    int mailIndex = cursor.getColumnIndex(DBHelper.KEY_MAIL);

                    do {
                        Log.d("myLog", "ID = " + cursor.getInt(idIndex)
                                + ", NAME = " + cursor.getString(nameIndex)
                                + ", MAIL = " + cursor.getString(mailIndex));

                    } while (cursor.moveToNext());
                } else {
                    Log.d("myLog", "0 rows");
                }
                cursor.close();
                break;
            case R.id.btnClear:
                database.delete(DBHelper.TABLE_CONTACTS, null, null);
                break;
            case R.id.btnUpdate:
                if(id.equalsIgnoreCase(""))
                    break;
                contentValues.put(DBHelper.KEY_NAME, name);
                contentValues.put(DBHelper.KEY_MAIL, name);
                int updCount = database.update(DBHelper.TABLE_CONTACTS, contentValues, DBHelper.KEY_ID + "=?", new String[] {id});
                Log.d("myLog", "updates rows count = " + updCount);
                break;
            case R.id.btnDelete:
                if(id.equalsIgnoreCase(""))
                    break;
                int delCount = database.delete(DBHelper.TABLE_CONTACTS, DBHelper.KEY_ID + "=" + id, null);
                Log.d("myLog", "deleted rows count = " + delCount);
                break;
            default:
                break;
        }
        dbHelper.close();
    }
}

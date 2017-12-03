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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText inputName, inputMail;
    Button btnAdd, btnRead, btnClear;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputName = findViewById(R.id.inputName);
        inputMail = findViewById(R.id.inputMail);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

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
            default:
                break;
        }
        dbHelper.close();
    }
}

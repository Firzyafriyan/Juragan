package com.cadenza.bottomnavigation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Registrations.db";
    private static final int DATABASE_VERSION = 1;

    //Registrasi Jamaah
    public static final String JAMAAH = "reg_jamaah";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FULL_NAME = "full_name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_NIK = "nik";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_ADDRESS = "address";

    //Registrasi Agen
    public static final String AGEN = "reg_agen";
    public static final String ID = "id";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String PHONE = "phone";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Membuat tabel reg_jamaah dalam database
        String table1 = "CREATE TABLE " + JAMAAH + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FULL_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_NIK + " TEXT, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_GENDER + " TEXT, " +
                COLUMN_ADDRESS + " TEXT);";
        db.execSQL(table1);

        // Membuat tabel reg_agen dalam database
        String table2 = "CREATE TABLE " + AGEN + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USERNAME + " TEXT, " +
                EMAIL + " TEXT, " +
                PASSWORD + " TEXT, " +
                PHONE + " TEXT);";
        db.execSQL(table2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Menghapus tabel lama saat upgrade dan membuat tabel baru
        db.execSQL("DROP TABLE IF EXISTS " + JAMAAH);
        db.execSQL("DROP TABLE IF EXISTS " + AGEN);
        onCreate(db);
    }

    // Metode untuk menyimpan data pendaftaran
    public long insertRegistration(String fullName, String email, String nik, String phone, String gender, String address) {
        SQLiteDatabase insertReg = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FULL_NAME, fullName);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_NIK, nik);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_ADDRESS, address);

        // Memasukkan data pendaftaran ke dalam tabel
        long newRowId = insertReg.insert(JAMAAH, null, values);
        insertReg.close();

        return newRowId;
    }

    // Metode untuk menyimpan data pendaftaran
    public long insertRegAgen(String USERNAME, String EMAIL, String PASSWORD, String PHONE) {
        SQLiteDatabase insertAgen = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USERNAME, USERNAME);
        values.put(EMAIL, EMAIL);
        values.put(PASSWORD, PASSWORD);
        values.put(PHONE, PHONE);

        // Memasukkan data pendaftaran ke dalam tabel
        long RegAgen = insertAgen.insert(AGEN, null, values);
        insertAgen.close();

        return RegAgen;
    }

    public boolean checkNIK(String nik) {
        // Memeriksa apakah NIK sudah ada dalam database
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_NIK + " = ?";
        String[] selectionArgs = {nik};
        Cursor cursor = db.query(JAMAAH, columns, selection, selectionArgs, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean checkPassword(String password) {
        // Memeriksa apakah NIK sudah ada dalam database
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {ID};
        String selection = PASSWORD + " = ?";
        String[] selectionArgs = {password};
        Cursor cursor = db.query(AGEN, columns, selection, selectionArgs, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Boolean checkEmail(String email) {
        // Memeriksa apakah email sudah ada dalam database
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + JAMAAH + " WHERE " + COLUMN_EMAIL + " = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}

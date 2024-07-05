package com.example.vezbamobilne.repo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.vezbamobilne.models.Knjiga;
import com.example.vezbamobilne.models.Komentar;
import com.example.vezbamobilne.models.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 4;

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_USERNAME = "username";

    private static final String TABLE_KOMENTARI = "komentari";
    private static final String COLUMN_KOMENTAR = "komentar";
    private static final String TABLE_KNJIGE = "knjige";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NASLOV = "naslov";
    private static final String COLUMN_BRSTRANICA = "brStranica";
    private static final String COLUMN_POVEZ = "povez";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_EMAIL + " TEXT PRIMARY KEY, "
                + COLUMN_PASSWORD + " TEXT, "
                + COLUMN_USERNAME + " TEXT )";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_KNJIGE_TABLE = "CREATE TABLE " + TABLE_KNJIGE + "("
                + COLUMN_ID + " integer PRIMARY KEY,"
                + COLUMN_NASLOV + " TEXT,"
                + COLUMN_BRSTRANICA + " integer,"
                + COLUMN_POVEZ + " TEXT)";
        db.execSQL(CREATE_KNJIGE_TABLE);
        String CREATE_KOMENTARI_TABLE = "CREATE TABLE " + TABLE_KOMENTARI + "("
                + COLUMN_ID + " integer PRIMARY KEY,"
                + COLUMN_KOMENTAR + " TEXT,"
                + COLUMN_USERNAME + " TEXT)";
        db.execSQL(CREATE_KOMENTARI_TABLE);

        // Insert predefined users
        insertUser(db, new User("test@gmail.com", "test", "test1"));
        insertUser(db, new User("test2@gmail.com", "test", "test2"));
        insertUser(db, new User("test3@gmail.com", "test", "test3"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KNJIGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KOMENTARI);
        onCreate(db);
    }

    private void insertUser(SQLiteDatabase db, User user) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_USERNAME,user.getUsername());
        db.insert(TABLE_USERS, null, values);
    }

    public void insertKnjiga(SQLiteDatabase db, Knjiga knjiga){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NASLOV,knjiga.getNaslov());
        values.put(COLUMN_BRSTRANICA,knjiga.getBrStranica());
        values.put(COLUMN_POVEZ,knjiga.getPovez());
        db.insert(TABLE_KNJIGE,null,values);
    }

    public void insertKomentar(Komentar komentar){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_KOMENTAR,komentar.getKomentar());
        values.put(COLUMN_USERNAME,komentar.getUsername());
        db.insert(TABLE_KOMENTARI,null,values);
        db.close();
    }

    public User getUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_EMAIL, COLUMN_PASSWORD, COLUMN_USERNAME},
                COLUMN_EMAIL + "=?", new String[]{email}, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            User user = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2));
            cursor.close();
            return user;
        }
        return null;
    }

    public List<Komentar> getKomentar(String username) {
        List<Komentar> komentari = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_KOMENTARI, new String[]{COLUMN_KOMENTAR, COLUMN_USERNAME},
                COLUMN_USERNAME + "=?", new String[]{username}, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Komentar komentar = new Komentar(cursor.getString(0), cursor.getString(1));
                komentari.add(komentar);
            }
            cursor.close();
        }
        return komentari;
    }

    public Knjiga getKnjiga(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_KNJIGE,
                new String[]{COLUMN_NASLOV, COLUMN_BRSTRANICA, COLUMN_POVEZ},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int naslovIndex = cursor.getColumnIndex(COLUMN_NASLOV);
            int brStranicaIndex = cursor.getColumnIndex(COLUMN_BRSTRANICA);
            int povezIndex = cursor.getColumnIndex(COLUMN_POVEZ);

            // Proverite da li su indeksi validni
            if (naslovIndex != -1 && brStranicaIndex != -1 && povezIndex != -1) {
                String naslov = cursor.getString(naslovIndex);
                int brStranica = cursor.getInt(brStranicaIndex);
                String povez = cursor.getString(povezIndex);
                cursor.close();
                return new Knjiga(id, naslov, brStranica, povez);
            } else {
                // Ako je bilo koja kolona ne postoji, bacite izuzetak ili prijavite grešku
                cursor.close();
                throw new IllegalArgumentException("Jedna ili više kolona ne postoje u tabeli.");
            }
        } else {
            if (cursor != null) {
                cursor.close();
            }
            return null;
        }
    }

    public List<Knjiga> getAllKnjige() {
        List<Knjiga> knjige = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_KNJIGE, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Knjiga knjiga = new Knjiga(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_NASLOV)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_BRSTRANICA)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_POVEZ))
                );
                knjige.add(knjiga);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return knjige;
    }

    public List<Komentar> getAllKomentari(){
        List<Komentar> komentari = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_KOMENTARI,null);
        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") Komentar komentar = new Komentar(
                        cursor.getString(cursor.getColumnIndex(COLUMN_KOMENTAR)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME))
                );
                komentari.add(komentar);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return komentari;
    }




}

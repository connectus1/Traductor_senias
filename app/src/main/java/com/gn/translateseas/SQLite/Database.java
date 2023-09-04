package com.gn.translateseas.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    private static final String DB_NAME = "speaksign_application";
    private static final int DB_VERSION = 3;
    private SQLiteDatabase sqLiteDatabase;
    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
        this.sqLiteDatabase.execSQL(createTableFriends());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists friends");
        sqLiteDatabase.execSQL("drop table if exists chat");
    }

    private String createTableFriends() {
        String table_friends = "CREATE TABLE friends("
                + "id_friends integer auto_increment primary key,"
                + "id integer,"
                + "correo varchar(100),"
                + "usuario varchar(50)"
                + ");";
        return table_friends;
    }


    public void createTableMessage(SQLiteDatabase sqLiteDatabase, String user){
        String table_message = "CREATE TABLE speaksign_friend_" + user + "(" +
                    "id_mensaje integer auto_increment primary key," +
                    "mensaje text," +
                    "fecha date," +
                    "flag int);";
        sqLiteDatabase.execSQL(table_message);
        //this.sqLiteDatabase.execSQL(table_message);
    }

}

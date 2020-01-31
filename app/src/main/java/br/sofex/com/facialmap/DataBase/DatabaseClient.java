package br.sofex.com.facialmap.DataBase;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {

    private Context mCtx;
    private static DatabaseClient mInstance;

    //our app database object
    private FacialMapRoomDatabase mapaRoomDatabase;

    private DatabaseClient(Context mCtx) {
        this.mCtx = mCtx;

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        mapaRoomDatabase = Room.databaseBuilder(mCtx, FacialMapRoomDatabase.class, "FacialMap").build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public FacialMapRoomDatabase getAppDatabase() {
        return mapaRoomDatabase;
    }

}

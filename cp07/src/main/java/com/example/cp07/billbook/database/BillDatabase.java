package com.example.cp07.billbook.database;

import android.util.Log;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


import com.example.cp07.billbook.MainApplication;
import com.example.cp07.billbook.dao.BillDao;
import com.example.cp07.billbook.entity.Bill;
import com.example.cp07.billbook.entity.DateConverter;


@Database(entities = {Bill.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class BillDatabase extends RoomDatabase{
    private static volatile BillDatabase instance;
    public static BillDatabase getInstance(){
        if (instance==null){
            synchronized (BillDatabase.class){
                if (instance==null){
                    Log.d("lxl",MainApplication.getAppContext()+"");
                    instance = Room.databaseBuilder(MainApplication.getAppContext(), BillDatabase.class,"bill_database")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return instance;
    }
    public abstract BillDao getBillDao();
}

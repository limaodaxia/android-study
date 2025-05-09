package com.example.cp07.billbook.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.example.cp07.billbook.MainApplication;
import com.example.cp07.billbook.dao.BillDao;
import com.example.cp07.billbook.entity.Bill;


@Database(entities = {Bill.class}, version = 1, exportSchema = false)
public abstract class BillDatabase extends RoomDatabase{
    private static volatile BillDatabase instance;
    public static BillDatabase getInstance(){
        if (instance==null){
            synchronized (BillDatabase.class){
                if (instance==null){
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

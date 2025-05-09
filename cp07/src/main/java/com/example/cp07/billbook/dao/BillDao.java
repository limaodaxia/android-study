package com.example.cp07.billbook.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.cp07.billbook.entity.Bill;

import java.util.List;

@Dao
public interface BillDao {
    @Query("SELECT * FROM bills")
    List<Bill> getAllBills();

    @Insert
    int addBill(Bill bill);

    @Delete
    int deleteBillById(int id);


    List<Bill> getBillsByMonth(int mMonth);
}

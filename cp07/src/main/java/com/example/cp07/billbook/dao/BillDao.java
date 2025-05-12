package com.example.cp07.billbook.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.cp07.billbook.entity.Bill;

import java.time.LocalDateTime;
import java.util.List;

@Dao
public interface BillDao {
    @Query("SELECT * FROM bills")
    List<Bill> getAllBills();

    @Insert
    Long addBill(Bill bill);

    @Delete
    int deleteBill(Bill bill);

    @Query("SELECT * FROM bills WHERE createTime >= :startTime AND createTime <= :endTime")
    List<Bill> getBillsByTimeRange(LocalDateTime startTime, LocalDateTime endTime);
}

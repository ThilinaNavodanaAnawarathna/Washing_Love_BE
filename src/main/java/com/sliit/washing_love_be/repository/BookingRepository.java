package com.sliit.washing_love_be.repository;

import com.sliit.washing_love_be.entity.Booking;
import com.sliit.washing_love_be.enumz.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,String> {
//    @Query(value="select * from booking where booking_status=:state and booking_date=:date and start_time between :startTime and :endTime",nativeQuery=true)
    @Query(value="select * from booking where booking_status=:status and booking_date=:date and start_time BETWEEN :startTime AND :endTime",nativeQuery=true)
    List<Booking> searchBookingByTimeAndDate(Date date, Time startTime, Time endTime, BookingStatus status);
}

package com.sliit.washing_love_be.repository;

import com.sliit.washing_love_be.entity.Booking;
import com.sliit.washing_love_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query(value = "select * from booking where booking_status=:status and booking_date=:date and :startTime BETWEEN start_time AND end_time", nativeQuery = true)
    List<Booking> searchBookingByTimeAndDate(String status, String date, Time startTime);
    List<Booking> findAllByUser(User user);
}

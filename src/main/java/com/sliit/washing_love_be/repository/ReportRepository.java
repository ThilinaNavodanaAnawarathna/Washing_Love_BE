package com.sliit.washing_love_be.repository;

import com.sliit.washing_love_be.entity.Booking;
import com.sliit.washing_love_be.entity.Report;
import com.sliit.washing_love_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByBooking(Booking booking);
    List<Report> findByUser(User user);
}

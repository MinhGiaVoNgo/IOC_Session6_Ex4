package com.example.parkinglot.repository;

import com.example.parkinglot.dto.TicketSummaryResponse;
import com.example.parkinglot.entity.ParkingTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, Long> {

    /**
     * DTO Projection (Constructor Expression) trong JPQL:
     * Chỉ SELECT các trường cần thiết (id, licensePlate, zoneName, checkInTime, checkOutTime)
     * Tránh tải toàn bộ entity Vehicle và Zone, loại bỏ hoàn toàn vấn đề N+1 Query.
     */
    @Query("SELECT new com.example.parkinglot.dto.TicketSummaryResponse(" +
           "    t.id, " +
           "    v.licensePlate, " +
           "    z.name, " +
           "    t.checkInTime, " +
           "    t.checkOutTime) " +
           "FROM ParkingTicket t " +
           "JOIN t.vehicle v " +
           "JOIN t.zone z " +
           "WHERE t.checkInTime >= :startOfDay AND t.checkInTime < :endOfDay " +
           "ORDER BY t.checkInTime DESC")
    List<TicketSummaryResponse> findTodayTicketsSummary(@Param("startOfDay") LocalDateTime startOfDay,
                                                        @Param("endOfDay") LocalDateTime endOfDay);
}

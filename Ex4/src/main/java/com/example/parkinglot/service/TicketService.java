package com.example.parkinglot.service;

import com.example.parkinglot.dto.TicketSummaryResponse;
import com.example.parkinglot.repository.ParkingTicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {

    private final ParkingTicketRepository parkingTicketRepository;

    public TicketService(ParkingTicketRepository parkingTicketRepository) {
        this.parkingTicketRepository = parkingTicketRepository;
    }

    /**
     * Lấy toàn bộ danh sách vé xe trong ngày hiện tại sử dụng DTO Projection.
     * Không cần phân trang, tối ưu hiệu năng và tránh N+1 Query.
     */
    @Transactional(readOnly = true)
    public List<TicketSummaryResponse> getTodayTicketsSummary() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay();

        return parkingTicketRepository.findTodayTicketsSummary(startOfDay, endOfDay);
    }
}

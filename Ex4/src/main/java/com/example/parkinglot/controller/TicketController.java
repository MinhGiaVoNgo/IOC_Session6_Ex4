package com.example.parkinglot.controller;

import com.example.parkinglot.dto.ApiResponse;
import com.example.parkinglot.dto.TicketSummaryResponse;
import com.example.parkinglot.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * Endpoint lấy danh sách vé trong ngày sử dụng DTO Projection:
     * GET /api/v1/tickets/summary
     */
    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<List<TicketSummaryResponse>>> getTodayTicketsSummary() {
        List<TicketSummaryResponse> summaryList = ticketService.getTodayTicketsSummary();

        ApiResponse<List<TicketSummaryResponse>> response = ApiResponse.<List<TicketSummaryResponse>>builder()
                .success(true)
                .message("Lấy danh sách vé trong ngày thành công")
                .data(summaryList)
                .build();

        return ResponseEntity.ok(response);
    }
}

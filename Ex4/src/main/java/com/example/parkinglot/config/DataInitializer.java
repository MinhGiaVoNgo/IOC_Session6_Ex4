package com.example.parkinglot.config;

import com.example.parkinglot.entity.ParkingTicket;
import com.example.parkinglot.entity.Vehicle;
import com.example.parkinglot.entity.Zone;
import com.example.parkinglot.enums.VehicleType;
import com.example.parkinglot.repository.ParkingTicketRepository;
import com.example.parkinglot.repository.VehicleRepository;
import com.example.parkinglot.repository.ZoneRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ZoneRepository zoneRepository;
    private final VehicleRepository vehicleRepository;
    private final ParkingTicketRepository parkingTicketRepository;

    public DataInitializer(ZoneRepository zoneRepository,
                           VehicleRepository vehicleRepository,
                           ParkingTicketRepository parkingTicketRepository) {
        this.zoneRepository = zoneRepository;
        this.vehicleRepository = vehicleRepository;
        this.parkingTicketRepository = parkingTicketRepository;
    }

    @Override
    public void run(String... args) {
        if (parkingTicketRepository.count() == 0) {
            // 1. Tạo các Zone
            Zone zoneA = Zone.builder().name("Khu A").capacity(50).occupiedSpots(2).build();
            Zone zoneB = Zone.builder().name("Khu B").capacity(30).occupiedSpots(1).build();
            zoneRepository.saveAll(List.of(zoneA, zoneB));

            // 2. Tạo các Vehicle
            Vehicle v1 = Vehicle.builder().licensePlate("29A-11111").color("Đỏ").type(VehicleType.CAR).build();
            Vehicle v2 = Vehicle.builder().licensePlate("30E-22222").color("Trắng").type(VehicleType.CAR).build();
            Vehicle v3 = Vehicle.builder().licensePlate("18B-33333").color("Đen").type(VehicleType.BIKE).build();
            Vehicle v4 = Vehicle.builder().licensePlate("51F-44444").color("Xanh").type(VehicleType.CAR).build();
            vehicleRepository.saveAll(List.of(v1, v2, v3, v4));

            // 3. Tạo các ParkingTicket trong ngày hôm nay
            LocalDateTime now = LocalDateTime.now();
            ParkingTicket t1 = ParkingTicket.builder()
                    .vehicle(v1).zone(zoneA).checkInTime(now.minusHours(4)).checkOutTime(now.minusHours(2)).build();
            ParkingTicket t2 = ParkingTicket.builder()
                    .vehicle(v2).zone(zoneA).checkInTime(now.minusHours(3)).checkOutTime(null).build();
            ParkingTicket t3 = ParkingTicket.builder()
                    .vehicle(v3).zone(zoneB).checkInTime(now.minusHours(2)).checkOutTime(null).build();
            ParkingTicket t4 = ParkingTicket.builder()
                    .vehicle(v4).zone(zoneA).checkInTime(now.minusHours(1)).checkOutTime(null).build();

            parkingTicketRepository.saveAll(List.of(t1, t2, t3, t4));
            System.out.println("✅ [Ex4] Khởi tạo dữ liệu vé xe trong ngày thành công cho DTO Projection!");
        }
    }
}

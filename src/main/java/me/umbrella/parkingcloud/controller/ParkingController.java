package me.umbrella.parkingcloud.controller;

import me.umbrella.parkingcloud.controller.dto.ParkingDTO;
import me.umbrella.parkingcloud.controller.mapper.ParkingMapper;
import me.umbrella.parkingcloud.model.Parking;
import me.umbrella.parkingcloud.service.ParkingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    //padrao recomendado para ingessão de dependencia - pelo construtor
    private final ParkingService parkingService;
    private final ParkingMapper parkingMapper;

    public ParkingController (ParkingService parkingService, ParkingMapper parkingMapper) {
        this.parkingService = parkingService;
        this.parkingMapper = parkingMapper;
    }

    @GetMapping
    public List<ParkingDTO> findAll() {
        List<Parking> parkingList = parkingService.findAll();
        List<ParkingDTO> result = parkingMapper.toParkingDTOList(parkingList);

        return result;
    }
}

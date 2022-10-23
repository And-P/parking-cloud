package me.umbrella.parkingcloud.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.umbrella.parkingcloud.controller.dto.ParkingCreateDTO;
import me.umbrella.parkingcloud.controller.dto.ParkingDTO;
import me.umbrella.parkingcloud.controller.mapper.ParkingMapper;
import me.umbrella.parkingcloud.model.Parking;
import me.umbrella.parkingcloud.service.ParkingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking")
@Api(tags= "Parking Controller")
public class ParkingController {

    //padrao recomendado para ingessão de dependencia - pelo construtor
    private final ParkingService parkingService;
    private final ParkingMapper parkingMapper;

    public ParkingController (ParkingService parkingService, ParkingMapper parkingMapper) {
        this.parkingService = parkingService;
        this.parkingMapper = parkingMapper;
    }

    @GetMapping
    @ApiOperation("Busca todos os Parkings")
    public ResponseEntity<List<ParkingDTO>> findAll() {
        List<Parking> parkingList = parkingService.findAll();
        List<ParkingDTO> result = parkingMapper.toParkingDTOList(parkingList);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @ApiOperation("Busca um Parking pelo ID")
    public ResponseEntity<ParkingDTO> findById(@PathVariable String id) {
        Parking parking = parkingService.findById(id);
        ParkingDTO result = parkingMapper.toParkingDTO(parking);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    @ApiOperation("Cria um novo Parking")
    public ResponseEntity<ParkingDTO> create(@RequestBody ParkingCreateDTO dto) {
        Parking parkingConvert = parkingMapper.toParkingCreate(dto);
        Parking parking = parkingService.create(parkingConvert);
        ParkingDTO result = parkingMapper.toParkingDTO(parking);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}

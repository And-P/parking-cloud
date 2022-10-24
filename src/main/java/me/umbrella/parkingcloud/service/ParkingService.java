package me.umbrella.parkingcloud.service;

import me.umbrella.parkingcloud.exception.ParkingNotFoundException;
import me.umbrella.parkingcloud.model.Parking;
import me.umbrella.parkingcloud.repository.ParkingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;

    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Parking> findAll() {
        return parkingRepository.findAll();
    }
    @Transactional(readOnly = true)
    public Parking findById(String id) {
        return parkingRepository.findById(id).orElseThrow( () ->
                new ParkingNotFoundException(id));
    }
    @Transactional
    public Parking create(Parking parkingConvert) {
        String uuid = getUUID();
        parkingConvert.setId(uuid);
        parkingConvert.setEntryDate(LocalDateTime.now());

        parkingRepository.save(parkingConvert);

        return parkingConvert;
    }
    @Transactional
    public void delete(String id) {
        findById(id);
        parkingRepository.deleteById(id);
    }
    @Transactional
    public Parking update(String id, Parking parkingUpdate) {
        Parking parking = findById(id);
        parking.setColor(parkingUpdate.getColor());
        parking.setState(parkingUpdate.getState());
        parking.setModel(parkingUpdate.getModel());
        parking.setLicense(parkingUpdate.getLicense());

        parkingRepository.save(parking);

        return parking;
    }

    @Transactional
    public Parking checkOut(String id) {
        Parking parking = findById(id);
        parking.setExitDate(LocalDateTime.now());
        parking.setBill(ParkingCheckOut.getBill(parking));
        parkingRepository.save(parking);
        return parking;
    }
}

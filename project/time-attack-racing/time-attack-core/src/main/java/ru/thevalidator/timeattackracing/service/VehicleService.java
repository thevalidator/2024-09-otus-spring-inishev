package ru.thevalidator.timeattackracing.service;

import ru.thevalidator.timeattackracing.dto.VehicleCreateRequest;
import ru.thevalidator.timeattackracing.entity.VehicleEntity;

import java.util.List;
import java.util.UUID;

public interface VehicleService {

    VehicleEntity getById(Long vehicleId);

    VehicleEntity createVehicle(VehicleCreateRequest rq);

    List<VehicleEntity> getByUserId(UUID userId);

}

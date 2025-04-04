package ru.thevalidator.timeattackracing.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.thevalidator.timeattackracing.converter.VehicleConverter;
import ru.thevalidator.timeattackracing.dto.VehicleCreateRequest;
import ru.thevalidator.timeattackracing.entity.UserEntity;
import ru.thevalidator.timeattackracing.entity.VehicleEntity;
import ru.thevalidator.timeattackracing.exception.ItemNotFoundException;
import ru.thevalidator.timeattackracing.repository.VehicleRepository;
import ru.thevalidator.timeattackracing.service.UserService;
import ru.thevalidator.timeattackracing.service.VehicleService;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {

    private static final Logger log = LoggerFactory.getLogger(VehicleServiceImpl.class);

    private final UserService userService;

    private final VehicleRepository vehicleRepository;

    private final VehicleConverter vehicleConverter;

    public VehicleServiceImpl(UserService userService,
                              VehicleRepository vehicleRepository,
                              VehicleConverter vehicleConverter) {
        this.userService = userService;
        this.vehicleRepository = vehicleRepository;
        this.vehicleConverter = vehicleConverter;
    }

    @Override
    public VehicleEntity getById(Long vehicleId) {
        return vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ItemNotFoundException(String.format("Vehicle not found [id=%s]", vehicleId)));
    }

    @Override
    public VehicleEntity createVehicle(VehicleCreateRequest rq) {
        UserEntity user = userService.getUserById(rq.getUserId());
        VehicleEntity entity = vehicleConverter.toVehicleEntity(rq, user);
        entity = vehicleRepository.save(entity);
        log.info("Vehicle created [id={}]", entity.getId());
        return entity;
    }

    @Override
    public List<VehicleEntity> getByUserId(UUID userId) {
        UserEntity user = userService.getUserById(userId);
        return vehicleRepository.findAllByUser(user);
    }

}

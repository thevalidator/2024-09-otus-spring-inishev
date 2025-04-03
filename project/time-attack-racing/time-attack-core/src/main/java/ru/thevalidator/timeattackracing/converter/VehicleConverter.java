package ru.thevalidator.timeattackracing.converter;

import org.springframework.stereotype.Component;
import ru.thevalidator.timeattackracing.dto.VehicleCreateRequest;
import ru.thevalidator.timeattackracing.dto.VehicleDto;
import ru.thevalidator.timeattackracing.entity.UserEntity;
import ru.thevalidator.timeattackracing.entity.VehicleEntity;

@Component
public class VehicleConverter {

    public VehicleDto toVehicleDto(VehicleEntity entity) {
        VehicleDto dto = new VehicleDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setMake(entity.getMake());
        dto.setModel(entity.getModel());
        dto.setYear(entity.getYear());
        return dto;
    }

    public VehicleEntity toVehicleEntity(VehicleCreateRequest dto, UserEntity user) {
        VehicleEntity entity = new VehicleEntity();
        entity.setUser(user);
        entity.setMake(dto.getMake());
        entity.setModel(dto.getModel());
        entity.setYear(dto.getYear());
        entity.setDeleted(false);
        return entity;
    }

}

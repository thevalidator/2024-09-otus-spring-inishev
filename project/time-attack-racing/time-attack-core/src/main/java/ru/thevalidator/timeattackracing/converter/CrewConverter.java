package ru.thevalidator.timeattackracing.converter;

import org.springframework.stereotype.Component;
import ru.thevalidator.timeattackracing.dto.CrewDto;
import ru.thevalidator.timeattackracing.dto.EventCategoryCrewsDto;
import ru.thevalidator.timeattackracing.dto.GroupedCrewListDto;
import ru.thevalidator.timeattackracing.entity.ClassificationCategoryEntity;
import ru.thevalidator.timeattackracing.entity.CrewEntity;
import ru.thevalidator.timeattackracing.entity.UserEntity;
import ru.thevalidator.timeattackracing.entity.VehicleEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CrewConverter {

    public CrewDto toCrewDto(CrewEntity reg) {
        UserEntity user = reg.getVehicle().getUser();
        VehicleEntity vehicle = reg.getVehicle();
        CrewDto dto = new CrewDto();
        dto.setId(reg.getUser().getId());
        dto.setPilotName(user.getLastName() + " " + user.getFirstName());
        dto.setVehicleName(vehicle.getMake() + " " + vehicle.getModel());
        dto.setRacingNumber(reg.getRacingNumber());
        return dto;
    }

    public GroupedCrewListDto toEventCategoryCrewDto(List<CrewEntity> registrations) {
        List<EventCategoryCrewsDto> data = new ArrayList<>();
        Map<ClassificationCategoryEntity, List<CrewDto>> groupedCrews = groupCrewsByCategory(registrations);

        groupedCrews.keySet().forEach(category -> {
            var dto = new EventCategoryCrewsDto();
            dto.setCategory(category);
            dto.setCrews(groupedCrews.get(category));
            data.add(dto);
        });

        return new GroupedCrewListDto(data);
    }

    public Map<ClassificationCategoryEntity, List<CrewDto>> groupCrewsByCategory(List<CrewEntity> registrations) {

        if (registrations.isEmpty()) {
            return new HashMap<>();
        }

        Map<ClassificationCategoryEntity, List<CrewDto>> groupedCrews = new HashMap<>();
        CrewEntity reg = registrations.getFirst();
        List<ClassificationCategoryEntity> categories = reg.getEvent().getCategories();
        categories.forEach(category -> groupedCrews.put(category, new ArrayList<>()));
        registrations.forEach(r -> {
            CrewDto dto = toCrewDto(r);
            groupedCrews.get(r.getCategory()).add(dto);
        });

        return groupedCrews;
    }

}

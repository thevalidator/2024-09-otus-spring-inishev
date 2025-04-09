package ru.thevalidator.timeattackracing.controller.v1;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.thevalidator.timeattackracing.converter.VehicleConverter;
import ru.thevalidator.timeattackracing.dto.VehicleCreateRequest;
import ru.thevalidator.timeattackracing.dto.VehicleDto;
import ru.thevalidator.timeattackracing.service.VehicleService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class VehicleController {

    private final VehicleService vehicleService;

    private final VehicleConverter vehicleConverter;

    public VehicleController(VehicleService vehicleService, VehicleConverter vehicleConverter) {
        this.vehicleService = vehicleService;
        this.vehicleConverter = vehicleConverter;
    }

    @PostAuthorize("hasAuthority('SCOPE_READ_USER_DATA') or returnObject.userId.toString() == authentication.name")
    @GetMapping("/vehicles/{vehicle_id}")
    public VehicleDto getVehicleById(@PathVariable(name = "vehicle_id") Long vehicleId) {
        var vehicle = vehicleService.getById(vehicleId);
        return vehicleConverter.toVehicleDto(vehicle);
    }

    @PreAuthorize("hasAuthority('SCOPE_WRITE_USER_DATA') or #rq.userId.toString() == authentication.name")
    @PostMapping("/vehicles")
    public VehicleDto createVehicle(@Valid @RequestBody VehicleCreateRequest rq) {
        var vehicle = vehicleService.createVehicle(rq);
        return vehicleConverter.toVehicleDto(vehicle);
    }

    @PreAuthorize("hasAuthority('SCOPE_READ_USER_DATA') or #userId.toString() == authentication.name")
    @GetMapping("/{user_id}/vehicles")
    public List<VehicleDto> getVehiclesByUserId(@PathVariable(name = "user_id") UUID userId) {
        var vehicles = vehicleService.getByUserId(userId);
        return vehicles.stream().map(vehicleConverter::toVehicleDto).toList();
    }

}

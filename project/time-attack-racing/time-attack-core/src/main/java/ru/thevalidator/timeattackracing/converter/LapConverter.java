package ru.thevalidator.timeattackracing.converter;

import org.springframework.stereotype.Component;
import ru.thevalidator.timeattackracing.dto.LapDto;
import ru.thevalidator.timeattackracing.entity.LapEntity;
import ru.thevalidator.timeattackracing.entity.SessionEntity;

@Component
public class LapConverter {

    public LapEntity toLapEntity(LapDto dto, SessionEntity session) {
        LapEntity lap = new LapEntity();
        lap.setLapTime(dto.getLapTime());
        lap.setRacingNumber(dto.getRacingNumber());
        lap.setSession(session);
        return lap;
    }

}

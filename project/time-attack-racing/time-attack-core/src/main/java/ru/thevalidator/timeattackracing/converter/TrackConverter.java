package ru.thevalidator.timeattackracing.converter;

import org.springframework.stereotype.Component;
import ru.thevalidator.timeattackracing.dto.TrackDto;
import ru.thevalidator.timeattackracing.entity.TrackEntity;

@Component
public class TrackConverter {

    public TrackDto toTrackDto(TrackEntity track) {
        TrackDto trackDto = new TrackDto();
        trackDto.setId(track.getId());
        trackDto.setTrackName(track.getName());
        return trackDto;
    }

}

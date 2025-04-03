package ru.thevalidator.timeattackracing.service;

import ru.thevalidator.timeattackracing.dto.CreateTrackRequest;
import ru.thevalidator.timeattackracing.entity.TrackEntity;

import java.util.List;

public interface TrackService {

    TrackEntity getTrackById(long id);

    TrackEntity createTrack(CreateTrackRequest rq);

    List<TrackEntity> getAllTracks();

}

package ru.thevalidator.timeattackracing.service.impl;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.thevalidator.timeattackracing.dto.CreateTrackRequest;
import ru.thevalidator.timeattackracing.entity.TrackEntity;
import ru.thevalidator.timeattackracing.exception.ConstraintViolationErrorException;
import ru.thevalidator.timeattackracing.exception.ItemNotFoundException;
import ru.thevalidator.timeattackracing.repository.TrackRepository;
import ru.thevalidator.timeattackracing.service.TrackService;

import java.util.List;

@Service
@Transactional
public class TrackServiceImpl implements TrackService {

    private final TrackRepository trackRepository;

    public TrackServiceImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    public TrackEntity createTrack(CreateTrackRequest rq) {
        TrackEntity track = new TrackEntity();
        track.setName(rq.getTrackName());
        try {
            return trackRepository.save(track);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintViolationErrorException("Duplicate track name");
        }
    }

    @Override
    public List<TrackEntity> getAllTracks() {
        return trackRepository.findAll();
    }

    @Override
    public TrackEntity getTrackById(long trackId) {
        return trackRepository.findById(trackId)
                .orElseThrow(() -> new ItemNotFoundException(String.format("Track not found [id=%s].", trackId)));
    }

}

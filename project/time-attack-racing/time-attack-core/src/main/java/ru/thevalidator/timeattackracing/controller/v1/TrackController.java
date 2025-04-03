package ru.thevalidator.timeattackracing.controller.v1;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.thevalidator.timeattackracing.converter.TrackConverter;
import ru.thevalidator.timeattackracing.dto.CreateTrackRequest;
import ru.thevalidator.timeattackracing.dto.TrackDto;
import ru.thevalidator.timeattackracing.service.TrackService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TrackController {

    private final TrackService trackService;

    private final TrackConverter trackConverter;

    public TrackController(TrackService trackService, TrackConverter trackConverter) {
        this.trackService = trackService;
        this.trackConverter = trackConverter;
    }

    @PreAuthorize("hasAuthority('SCOPE_CREATE_TRACKS')")
    @PostMapping("/tracks")
    public TrackDto createTrack(@Valid @RequestBody CreateTrackRequest rq) {
        var track = trackService.createTrack(rq);
        return trackConverter.toTrackDto(track);
    }

    @GetMapping("/tracks")
    public List<TrackDto> getAllTracks() {
        var tracks = trackService.getAllTracks();
        return tracks.stream().map(trackConverter::toTrackDto).toList();
    }

    @GetMapping("/tracks/{track_id}")
    public TrackDto createTrack(@PathVariable("track_id") Long trackId) {
        var track = trackService.getTrackById(trackId);
        return trackConverter.toTrackDto(track);
    }

}

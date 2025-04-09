package ru.thevalidator.timeattackracing.serializer;

import java.time.Duration;

public class LapTimeFormatter {

    public static String formatLapTime(Long lapTime) {
        if (lapTime == null) {
            return "PENALTY";
        }
        Duration time = Duration.ofMillis(lapTime);
        return String.format("%02d:%02d.%03d",
                time.toMinutesPart(),
                time.toSecondsPart(),
                time.toMillisPart());
    }

}

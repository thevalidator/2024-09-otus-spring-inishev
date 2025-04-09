package ru.thevalidator.timeattackracing.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;

public class LapTimesSerializer extends JsonSerializer<List<Long>> {

    @Override
    public void serialize(List<Long> strings, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeArray(strings.stream()
                .map(LapTimeFormatter::formatLapTime)
                .toArray(String[]::new), 0, strings.size());
    }

}

package ru.otus.hw.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class AppProperties implements TestConfig, TestFileNameProvider {

    @Value("${test.rightAnswersCountToPass}")
    private int rightAnswersCountToPass;

    @Value("${test.fileName}")
    private String testFileName;
}

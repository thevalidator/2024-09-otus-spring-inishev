package ru.otus.hw.runner;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Import;
import ru.otus.hw.Application;
import ru.otus.hw.service.TestRunnerService;

@Import(Application.class)
@RequiredArgsConstructor
public class AppRunner implements ApplicationRunner {

    private final TestRunnerService testRunnerService;

    @Override
    public void run(ApplicationArguments args) {
        testRunnerService.run();
    }

}

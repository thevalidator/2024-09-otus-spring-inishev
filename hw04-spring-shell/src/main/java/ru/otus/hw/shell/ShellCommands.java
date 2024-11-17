package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.service.TestRunnerService;

@ShellComponent(value = "Application commands")
@RequiredArgsConstructor
public class ShellCommands {

    private final TestRunnerService testRunnerService;

    @ShellMethod(value = "Start test", key = {"start", "test"})
    public void startTest() {
        testRunnerService.run();
    }

}

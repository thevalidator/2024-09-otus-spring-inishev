package ru.otus.hw;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.otus.hw.service.IOService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
public class TestConfiguration {

    public static final String STUDENT_NAME = "Leo";
    public static final String STUDENT_LASTNAME = "Gilbert";

    @Bean
    @Primary
    public IOService ioService() {
        var ioServiceMock = mock(IOService.class);
        when(ioServiceMock.readStringWithPrompt("Please input your first name")).thenReturn(STUDENT_NAME);
        when(ioServiceMock.readStringWithPrompt("Please input your last name")).thenReturn(STUDENT_LASTNAME);
        return ioServiceMock;
    }

}

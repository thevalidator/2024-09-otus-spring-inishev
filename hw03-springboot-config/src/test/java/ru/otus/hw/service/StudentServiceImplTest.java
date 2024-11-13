package ru.otus.hw.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    public static final String STUDENT_NAME = "Leo";
    public static final String STUDENT_LASTNAME = "Gilbert";

    @Mock
    private LocalizedIOService ioServiceMock;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void determineCurrentStudent() {
        when(ioServiceMock.readStringWithPromptLocalized("StudentService.input.first.name"))
                .thenReturn(STUDENT_NAME);
        when(ioServiceMock.readStringWithPromptLocalized("StudentService.input.last.name"))
                .thenReturn(STUDENT_LASTNAME);
        var student = studentService.determineCurrentStudent();
        Assertions.assertEquals(STUDENT_NAME, student.firstName());
        Assertions.assertEquals(STUDENT_LASTNAME, student.lastName());
    }

}
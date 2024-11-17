package ru.otus.hw.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = StudentServiceImpl.class)
class StudentServiceImplTest {

    public static final String STUDENT_NAME = "Leo";
    public static final String STUDENT_LASTNAME = "Gilbert";

    @MockBean
    private LocalizedIOService ioServiceMock;

    @Autowired
    private StudentService studentService;

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
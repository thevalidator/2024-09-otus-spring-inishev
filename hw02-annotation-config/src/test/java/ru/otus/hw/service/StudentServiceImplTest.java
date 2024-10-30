package ru.otus.hw.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw.Application;
import ru.otus.hw.TestConfiguration;

import static ru.otus.hw.TestConfiguration.STUDENT_LASTNAME;
import static ru.otus.hw.TestConfiguration.STUDENT_NAME;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Application.class, TestConfiguration.class})
class StudentServiceImplTest {

    @Autowired
    @InjectMocks
    private StudentService studentService;

    @Test
    void determineCurrentStudent() {
        var student = studentService.determineCurrentStudent();
        Assertions.assertEquals(STUDENT_NAME, student.firstName());
        Assertions.assertEquals(STUDENT_LASTNAME, student.lastName());
    }

}
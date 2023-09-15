package io.javatab.microservices.core.student;

import io.javatab.microservices.api.core.student.Student;
import io.javatab.microservices.core.student.persistence.StudentEntity;
import io.javatab.microservices.core.student.persistence.StudentRepository;
import io.javatab.microservices.core.student.service.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Integers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.com.google.common.base.Optional;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

@Transactional(propagation = NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(properties = {"spring.jpa.hibernate.ddl-auto=update", "spring.cloud.config.enabled=false"})
public class PersistenceTests extends PostgresTestBase {

    @Autowired
    private StudentRepository repository;

    private StudentEntity savedEntity;

    @Mock
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        StudentEntity entity = new StudentEntity(1, "Student Name", "student@email.com");
        savedEntity = repository.save(entity);
        assertEquals(entity, savedEntity);
    }

    @Test
    void create() {
        StudentEntity newEntity = new StudentEntity(1, "Student Name", "student@email.com");
        repository.save(newEntity);

        Optional<StudentEntity> foundEntity = repository.findById(Integers.valueOf(newEntity.getId()));
        assertEquals(newEntity.getId(), newEntity.getId());
    }

    @Test
    void getStudent(){
        StudentEntity newEntity = new StudentEntity(2, "Student TestName", "test@email.com");
        repository.save(newEntity);

        Mono<Student> student = studentService.getStudent(newEntity.getId());
        assertNotNull(student);
        assertEquals(newEntity.getId(), student.block().studentId());
    }
}

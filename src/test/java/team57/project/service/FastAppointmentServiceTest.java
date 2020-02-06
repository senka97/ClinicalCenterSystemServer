package team57.project.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import team57.project.model.FastAppointment;
import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

public class FastAppointmentServiceTest {

    @Autowired
    FastAppointmentService fastAppointmentService;

    @Test
    public void testFindAll() {
        FastAppointment fa = fastAppointmentService.findOne(1L);
        assertThat(fa).isEqualTo(null);
    }
}

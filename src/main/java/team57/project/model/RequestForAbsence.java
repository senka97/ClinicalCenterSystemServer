package team57.project.model;

import javax.persistence.*;
import java.util.Date;
@Entity
public class RequestForAbsence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "startDate", nullable = false)
    private Date startDate;
    @Column(name = "endDate", nullable = false)
    private Date endDate;
    @Column(name = "serialNumber", nullable = false)
    private String serialNumber;
    @Column(name = "type", nullable = false)
    private String type;
}

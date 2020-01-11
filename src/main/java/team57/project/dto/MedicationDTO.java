package team57.project.dto;

import team57.project.model.Medication;

public class MedicationDTO {

    private Long id;
    private String code;
    private String description;

    private MedicationDTO()
    {

    }

    public MedicationDTO(Long id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }

    public MedicationDTO(Medication m)
    {
        this(m.getId(), m.getCode(), m.getDescription());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

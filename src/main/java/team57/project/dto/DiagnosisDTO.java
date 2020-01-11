package team57.project.dto;


import team57.project.model.Diagnose;

public class DiagnosisDTO {

    private Long id;
    private String code;
    private String description;

    private DiagnosisDTO()
    {

    }

    public DiagnosisDTO(Long id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }

    public DiagnosisDTO(Diagnose d)
    {
        this(d.getId(), d.getCode(), d.getDescription());
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

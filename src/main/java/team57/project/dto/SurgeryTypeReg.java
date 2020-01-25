package team57.project.dto;

import team57.project.model.SurgeryType;

public class SurgeryTypeReg {

    private Long id;
    private String name;

    public SurgeryTypeReg() {

    }

    public SurgeryTypeReg(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SurgeryTypeReg(SurgeryType surgeryType) {
        this.id = surgeryType.getId();
        this.name = surgeryType.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
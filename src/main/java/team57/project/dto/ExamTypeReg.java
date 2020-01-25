package team57.project.dto;

import team57.project.model.ExamType;

public class ExamTypeReg {

    private Long id;
    private String name;

    public ExamTypeReg() {

    }

    public ExamTypeReg(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ExamTypeReg(ExamType examType) {
        this.id = examType.getId();
        this.name = examType.getName();
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
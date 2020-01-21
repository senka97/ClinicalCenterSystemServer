package team57.project.dto;

import team57.project.model.SurgeryType;

import java.util.ArrayList;
import java.util.List;

public class TypeRegDoctor {

    private List<ExamTypeReg> examTypeRegs;
    private List<SurgeryTypeReg>  surgeryTypeRegs;

    public TypeRegDoctor(){
        this.examTypeRegs = new ArrayList<ExamTypeReg>();
        this.surgeryTypeRegs = new ArrayList<SurgeryTypeReg>();
    }

    public List<ExamTypeReg> getExamTypeRegs() {
        return examTypeRegs;
    }

    public void setExamTypeRegs(List<ExamTypeReg> examTypeRegs) {
        this.examTypeRegs = examTypeRegs;
    }

    public List<SurgeryTypeReg> getSurgeryTypeRegs() {
        return surgeryTypeRegs;
    }

    public void setSurgeryTypeRegs(List<SurgeryTypeReg> surgeryTypeRegs) {
        this.surgeryTypeRegs = surgeryTypeRegs;
    }
}

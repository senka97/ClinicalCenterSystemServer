package team57.project.dto;

import team57.project.model.Medication;

public class PrescriptionDTO {

    private Long id;
    private boolean verified;
    private Medication medication;
    private DoctorDTO doctor;
    private NurseDTO nurse;

    public PrescriptionDTO()
    {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public DoctorDTO getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorDTO doctor) {
        this.doctor = doctor;
    }

    public NurseDTO getNurse() {
        return nurse;
    }

    public void setNurse(NurseDTO nurse) {
        this.nurse = nurse;
    }
}

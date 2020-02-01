package team57.project.dto;



import java.util.List;

public class AllFastAppointments {

    public List<FastAppointmentDTO> finished;
    public List<FastAppointmentDTO> scheduled;
    public List<FastAppointmentDTO> free;

    public AllFastAppointments(){

    }

    public AllFastAppointments(List<FastAppointmentDTO> finished, List<FastAppointmentDTO> scheduled, List<FastAppointmentDTO> free) {
        this.finished = finished;
        this.scheduled = scheduled;
        this.free = free;
    }

    public List<FastAppointmentDTO> getFinished() {
        return finished;
    }

    public void setFinished(List<FastAppointmentDTO> finished) {
        this.finished = finished;
    }

    public List<FastAppointmentDTO> getScheduled() {
        return scheduled;
    }

    public void setScheduled(List<FastAppointmentDTO> scheduled) {
        this.scheduled = scheduled;
    }

    public List<FastAppointmentDTO> getFree() {
        return free;
    }

    public void setFree(List<FastAppointmentDTO> free) {
        this.free = free;
    }
}

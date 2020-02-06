package team57.project.dto;

public class MERoomRequest {

    private MedicalExamRequest examStart;
    private MedicalExamRequest examEnd;
    private RoomME roomME;

    public MERoomRequest(){

    }

    public MERoomRequest(MedicalExamRequest examStart, MedicalExamRequest examEnd, RoomME roomME) {
        this.examStart = examStart;
        this.examEnd = examEnd;
        this.roomME = roomME;
    }

    public MedicalExamRequest getExamStart() {
        return examStart;
    }

    public void setExamStart(MedicalExamRequest examStart) {
        this.examStart = examStart;
    }

    public MedicalExamRequest getExamEnd() {
        return examEnd;
    }

    public void setExamEnd(MedicalExamRequest examEnd) {
        this.examEnd = examEnd;
    }

    public RoomME getRoomME() {
        return roomME;
    }

    public void setRoomME(RoomME roomME) {
        this.roomME = roomME;
    }
}

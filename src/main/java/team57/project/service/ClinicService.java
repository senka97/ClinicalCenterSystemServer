package team57.project.service;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team57.project.dto.*;
import team57.project.model.*;
import team57.project.repository.ClinicRepository;
import team57.project.repository.PatientRepository;
import team57.project.repository.RoomRepository;
import team57.project.repository.TermRoomRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ClinicService {

    @Autowired
    private ClinicRepository clinicRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private TermRoomRepository termRoomRepository;
    @Autowired
    private RoomRepository roomRepository;

    public Clinic findOne(Long id) {
        return clinicRepository.findById(id).orElseGet(null);
    }

    public List<Clinic> findAll() {
        return clinicRepository.findAll();
    }

    public void updateClinic(Clinic existClinic, ClinicDTO clinicDTO) {
        existClinic.setName(clinicDTO.getName());
        existClinic.setDescription(clinicDTO.getDescription());
        existClinic.setAddress(clinicDTO.getAddress());
        clinicRepository.save(existClinic);
    }

    public boolean clinicNameExists(String name, Long id) {

        List<Clinic> allClinics = this.findAll();
        for (Clinic c : allClinics) {
            if (c.getName().equals(name) && c.getId() != id) {
                return true;
            }
        }
        return false;
    }

    public void addNewRoom(Clinic clinic, RoomDTO roomDTO) {

        Room room = new Room(roomDTO.getName(), roomDTO.getNumber(), roomDTO.getRoomType(), false);
        clinic.getRooms().add(room);
        clinicRepository.save(clinic);
        Room r = roomRepository.findByNameAndNumber(roomDTO.getName(),roomDTO.getNumber());

        LocalDate nowDate = LocalDate.now();
        int today = nowDate.getDayOfWeek().getValue(); //redni broj dana u nedelji
        LocalDate temp = LocalDate.now();
        temp = temp.plusDays(1); //termini se prave od sutra pa do kraja sledece nedelje
        for(int i=0;i<12-today;i++){
            int n = temp.getDayOfWeek().getValue();
            if(n == 6 || n == 7){ //ako je dan subota ili nedelja nema termina
                System.out.println("Subota ili nedelja, ne kreiraju se termini.");
            }else{
                LocalTime startTime = LocalTime.of(6,0);
                LocalTime endTime = LocalTime.of(22,0);
                while(startTime.isBefore(endTime)){
                    TermRoom term = new TermRoom(temp,startTime,startTime.plusHours(1),true,r);
                    termRoomRepository.save(term);
                    startTime = startTime.plusHours(1);
                }
            }
            temp = temp.plusDays(1);
        }
    }



    public Clinic findByName(String name) {
        return clinicRepository.findByName(name);
    }

    public Clinic saveClinic(Clinic clinic) {
        return clinicRepository.save(clinic);

    }
    
    public Clinic rateClinic(Long clinicId, RateDTO rate) {
        try {

            System.out.println(rate);
            Patient p = this.patientRepository.findById(rate.getPatient_id()).orElse(null);
            System.out.println(p);
            Clinic c = this.clinicRepository.findById(clinicId).orElse(null);

            System.out.println("Rate: " + rate + "Before: " + c.getRating() * c.getNumberOfReviews());
            Double rated = c.getRating() * c.getNumberOfReviews() + rate.getRate();
            System.out.println("After: " + rated);
            c.setNumberOfReviews(c.getNumberOfReviews() + 1);
            rated = rated / c.getNumberOfReviews();
            c.setRating(rated);
            System.out.println("Final: " + c.getRating() + "  " + c.getNumberOfReviews());
            this.clinicRepository.save(c);
            p.getClinics().add(c);
            System.out.println(p);
            this.patientRepository.save(p);
            return c;
        } catch (NullPointerException e) {
            return null;
        }

    }

    public List<ClinicDTO> findFreeClinics(AvailableDoctorRequest adr) {

        if(adr.getIdExamType() == null){
            return null;
        }
        if(adr.getDate() == null){
            return null;
        }

        System.out.println(adr);
        List<ClinicDTO> clinicDTOS = new ArrayList<>();
        List<Clinic> clinics = new ArrayList<>();

        //doctors that have 1 or more free terms
        clinics = this.clinicRepository.getFreeClinics(adr.getIdExamType(),adr.getDate());

        for (Clinic c : clinics) {
            for ( Doctor doctor : c.getDoctors()){
                boolean isAbsent = isDoctorAbsent(adr, doctor);
                if (!isAbsent) {
                    boolean add = true;
                    for(ClinicDTO dto : clinicDTOS){
                        if(dto.getId().equals(c.getId())){
                            add = false;
                        }
                    }
                    if(add)
                        clinicDTOS.add(new ClinicDTO(c.getId(),c.getName(),c.getAddress(),c.getDescription(),c.getRating(),c.getNumberOfReviews()));

                }
            }

        }
        System.out.println(clinicDTOS);

        return clinicDTOS;
    }
    private boolean isDoctorAbsent(AvailableDoctorRequest adr, Doctor doctor) {
        boolean isAbsent = false;
        for (Absence a : doctor.getAbsences()) {
            if (a.getStatusOfAbsence().equals("APPROVED")) {
                if (a.getStartDate().minusDays(1).isBefore(adr.getDate()) && a.getEndDate().plusDays(1).isAfter(adr.getDate())) {
                    isAbsent = true;
                }
            }
        }
        return isAbsent;
    }

    public double getIncome(Clinic clinic, IncomeDate incomeDate) {

        List<FastAppointment> fa = clinicRepository.findFAIncome(clinic.getId(),incomeDate.getStartDate(),incomeDate.getEndDate());
        List<MedicalExam> me = clinicRepository.findMEIncome(clinic.getId(),incomeDate.getStartDate(),incomeDate.getEndDate());
        List<Surgery> s = clinicRepository.findSIncome(clinic.getId(),incomeDate.getStartDate(),incomeDate.getEndDate());

        double income = 0;
        for(FastAppointment f:fa){
            income += f.getPrice() - f.getPrice()*(f.getDiscount()/100);
        }
        for(MedicalExam m:me){
            income += m.getExamType().getPrice() - m.getExamType().getPrice()*(m.getExamType().getDiscount()/100);
        }
        for(Surgery d:s){
            income += d.getSurgeryType().getPrice() - d.getSurgeryType().getPrice()*(d.getSurgeryType().getDiscount()/100);
        }
        return income;
    }

    public List<Hour> getDailyReport(Clinic clinic,LocalDate date){

        LocalDate nowDate = LocalDate.now();
        nowDate = nowDate.minusDays(1);
        List<MedicalExam> me = clinicRepository.getAllMedicalExams(clinic.getId(),date);
        List<FastAppointment> fa = clinicRepository.getAllFastAppointments(clinic.getId(),date);

        int h6 = 0;int h7 = 0;int h8 = 0;int h9 = 0;
        int h10 = 0;int h11 = 0;int h12 = 0;int h13 = 0;
        int h14 = 0;int h15 = 0;int h16 = 0;int h17 = 0;
        int h18 = 0;int h19 = 0;int h20 = 0;int h21 = 0;
        for(MedicalExam m:me){
            if(m.getStartTime().getHour() == 6){
                h6++;
            }
            if(m.getStartTime().getHour() == 7){
                h7++;
            }
            if(m.getStartTime().getHour() == 8){
                h8++;
            }
            if(m.getStartTime().getHour() == 9){
                h9++;
            }
            if(m.getStartTime().getHour() == 10){
                h10++;
            }
            if(m.getStartTime().getHour() == 11){
                h11++;
            }
            if(m.getStartTime().getHour() == 12){
                h12++;
            }
            if(m.getStartTime().getHour() == 13){
                h13++;
            }
            if(m.getStartTime().getHour() == 14){
                h14++;
            }
            if(m.getStartTime().getHour() == 15){
                h15++;
            }
            if(m.getStartTime().getHour() == 16){
                h16++;
            }
            if(m.getStartTime().getHour() == 17){
                h17++;
            }
            if(m.getStartTime().getHour() == 18){
                h18++;
            }
            if(m.getStartTime().getHour() == 19){
                h19++;
            }
            if(m.getStartTime().getHour() == 20){
                h20++;
            }
            if(m.getStartTime().getHour() == 21){
                h21++;
            }
        }

        for(FastAppointment f:fa){
            if(f.getTimeFA().getHour() == 6){
                h6++;
            }
            if(f.getTimeFA().getHour() == 7){
                h7++;
            }
            if(f.getTimeFA().getHour() == 8){
                h8++;
            }
            if(f.getTimeFA().getHour() == 9){
                h9++;
            }
            if(f.getTimeFA().getHour() == 10){
                h10++;
            }
            if(f.getTimeFA().getHour() == 11){
                h11++;
            }
            if(f.getTimeFA().getHour() == 12){
                h12++;
            }
            if(f.getTimeFA().getHour() == 13){
                h13++;
            }
            if(f.getTimeFA().getHour() == 14){
                h14++;
            }
            if(f.getTimeFA().getHour() == 15){
                h15++;
            }
            if(f.getTimeFA().getHour() == 16){
                h16++;
            }
            if(f.getTimeFA().getHour() == 17){
                h17++;
            }
            if(f.getTimeFA().getHour() == 18){
                h18++;
            }
            if(f.getTimeFA().getHour() == 19){
                h19++;
            }
            if(f.getTimeFA().getHour() == 20){
                h20++;
            }
            if(f.getTimeFA().getHour() == 21){
                h21++;
            }
        }
        List<Hour> hours = new ArrayList<Hour>();
        hours.add(new Hour("6h",h6));
        hours.add(new Hour("7h",h7));
        hours.add(new Hour("8h",h8));
        hours.add(new Hour("9h",h9));
        hours.add(new Hour("10h",h10));
        hours.add(new Hour("11h",h11));
        hours.add(new Hour("12h",h12));
        hours.add(new Hour("13h",h13));
        hours.add(new Hour("14h",h14));
        hours.add(new Hour("15h",h15));
        hours.add(new Hour("16h",h16));
        hours.add(new Hour("17h",h17));
        hours.add(new Hour("18h",h18));
        hours.add(new Hour("19h",h19));
        hours.add(new Hour("20h",h20));
        hours.add(new Hour("21h",h21));

        return hours;

    }

    public List<Week> getMonthlyReport(Clinic clinic,LocalDate date){
        int dayOfMonth = date.getDayOfMonth();
        LocalDate startMonth = date.minusDays(dayOfMonth-1);
        //System.out.println(startMonth.toString());
        int lenghtMonth = date.lengthOfMonth();
        LocalDate endMonth = startMonth.plusDays(lenghtMonth-1);
        //System.out.println(endMonth);

        List<MedicalExam> me = clinicRepository.findMEDate(clinic.getId(),startMonth,endMonth);
        List<FastAppointment> fa = clinicRepository.findFADate(clinic.getId(),startMonth,endMonth);

        int w1 = 0;
        int w2 = 0;
        int w3 = 0;
        int w4 = 0;

        for(MedicalExam m : me){
            if(m.getDate().getDayOfMonth()>=1 && m.getDate().getDayOfMonth()<=7){
                w1++;
            }
            if(m.getDate().getDayOfMonth()>=8 && m.getDate().getDayOfMonth()<=14){
                w2++;
            }
            if(m.getDate().getDayOfMonth()>=15 && m.getDate().getDayOfMonth()<=21){
                w3++;
            }
            if(m.getDate().getDayOfMonth()>=22 && m.getDate().getDayOfMonth()<=lenghtMonth){
                w4++;
            }
        }

        for(FastAppointment f : fa){
            if(f.getDateFA().getDayOfMonth()>=1 && f.getDateFA().getDayOfMonth()<=7){
                w1++;
            }
            if(f.getDateFA().getDayOfMonth()>=8 && f.getDateFA().getDayOfMonth()<=14){
                w2++;
            }
            if(f.getDateFA().getDayOfMonth()>=15 && f.getDateFA().getDayOfMonth()<=21){
                w3++;
            }
            if(f.getDateFA().getDayOfMonth()>=22 && f.getDateFA().getDayOfMonth()<=lenghtMonth){
                w4++;
            }
        }

        List<Week> weeks = new ArrayList<Week>();
        weeks.add(new Week("Week 1",w1));
        weeks.add(new Week("Week 2",w2));
        weeks.add(new Week("Week 3",w3));
        weeks.add(new Week("Week 4",w4));

        return weeks;

    }

    public List<Month> getAnnualReport(Clinic clinic,LocalDate date) {

        int year = date.getYear();
        int month = date.getMonthValue();
        LocalDate startDate = LocalDate.of(year,1,1);
        LocalDate endDate = LocalDate.of(year,12,31);
        List<MedicalExam> me = clinicRepository.findMEDate(clinic.getId(),startDate,endDate);
        List<FastAppointment> fa = clinicRepository.findFADate(clinic.getId(),startDate,endDate);


        int m1 = 0;int m2 = 0;int m3 = 0;
        int m4 = 0;int m5 = 0;int m6 = 0;
        int m7 = 0;int m8 = 0;int m9 = 0;
        int m10 = 0;int m11 = 0;int m12 = 0;
        for(MedicalExam m:me){
            if(m.getDate().getMonthValue() == 1){
                m1++;
            }
            if(m.getDate().getMonthValue() == 2){
                m2++;
            }
            if(m.getDate().getMonthValue() == 3){
                m3++;
            }
            if(m.getDate().getMonthValue() == 4){
                m4++;
            }
            if(m.getDate().getMonthValue() == 5){
                m5++;
            }
            if(m.getDate().getMonthValue() == 6){
                m6++;
            }
            if(m.getDate().getMonthValue() == 7){
                m7++;
            }
            if(m.getDate().getMonthValue() == 8){
                m8++;
            }
            if(m.getDate().getMonthValue() == 9){
                m9++;
            }
            if(m.getDate().getMonthValue() == 10){
                m10++;
            }
            if(m.getDate().getMonthValue() == 11){
                m11++;
            }
            if(m.getDate().getMonthValue() == 12){
                m12++;
            }
        }

        for(FastAppointment f:fa){
            if(f.getDateFA().getMonthValue() == 1){
                m1++;
            }
            if(f.getDateFA().getMonthValue() == 2){
                m2++;
            }
            if(f.getDateFA().getMonthValue() == 3){
                m3++;
            }
            if(f.getDateFA().getMonthValue() == 4){
                m4++;
            }
            if(f.getDateFA().getMonthValue() == 5){
                m5++;
            }
            if(f.getDateFA().getMonthValue() == 6){
                m6++;
            }
            if(f.getDateFA().getMonthValue() == 7){
                m7++;
            }
            if(f.getDateFA().getMonthValue() == 8){
                m8++;
            }
            if(f.getDateFA().getMonthValue() == 9){
                m9++;
            }
            if(f.getDateFA().getMonthValue() == 10){
                m10++;
            }
            if(f.getDateFA().getMonthValue() == 11){
                m11++;
            }
            if(f.getDateFA().getMonthValue() == 12){
                m12++;
            }
        }

        List<Month> months = new ArrayList<Month>();
        months.add(new Month("Jan",m1));
        months.add(new Month("Feb",m2));
        months.add(new Month("Mar",m3));
        months.add(new Month("Apr",m4));
        months.add(new Month("May",m5));
        months.add(new Month("Jun",m6));
        months.add(new Month("Jul",m7));
        months.add(new Month("Avg",m8));
        months.add(new Month("Sept",m9));
        months.add(new Month("Oct",m10));
        months.add(new Month("Nov",m11));
        months.add(new Month("Dec",m12));

        return months;
    }
}

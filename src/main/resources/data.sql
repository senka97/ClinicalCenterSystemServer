-- noinspection SqlNoDataSourceInspectionForFile
insert into authority (name) value ('ROLE_CLINICAL_CENTER_ADMIN');
insert into authority (name) value ('ROLE_CLINIC_ADMIN');
insert into authority (name) value ('ROLE_PATIENT');
insert into authority (name) value ('ROLE_DOCTOR');
insert into authority (name) value ('ROLE_NURSE');

insert into clinical_center(name) value ('Dr Cvjetkovic');
insert into diagnose (code, description) values ('#00000023', 'Sinusitis');
insert into diagnose (code, description) values ('#0B048020', 'Pneumonia');
insert into diagnose (code, description) values ('#A0000023', 'Gastritis');
insert into diagnose (code, description) values ('#ABC00800', 'Cystitis');
insert into medical_record (organ_donor,height,weight,diopter,blood_type,date_of_birth) value ('Yes',182,70,'L-0; R-0','A-','04.09.1997.');
insert into clinic (name, address,description,rating,number_of_reviews) value ('Neurologija', 'Narodnog Fronta 76 Novi Sad', 'Klinika za neurologiju',4.5,10);
insert into clinic (name, address,description,rating,number_of_reviews) value ('Rehabilitacija', 'Kralja Konstantina 33 Novi Sad', 'Klinika za rehabilitaciju',3,1);
insert into clinic (name, address,description,rating,number_of_reviews) value ('Pulmologija', 'Brace Ribnikar 49 Novi Sad', 'Klinika za pulmologiju',4,2);
insert into clinic (name, address,description,rating,number_of_reviews) value ('Kardiologija', 'Kralja Petra 37 Novi Sad', 'Klinika za kardiologiju',5,25);
insert into user (name, surname, email, password, address, city, country, phone_number, serial_number,number_of_reviews, rating, activated_account, enabled, last_password_reset_date, password_changed, working_hours_start, working_hours_end, removed, type) values ('Mika', 'Mikic', 'mika@gmail.com','$2a$10$.iwB3NV6S83aXegd8HmYweflprG3x8lMvXMevv1oYEtmnArEaE1iW','Alekse Santica 47','Novi Sad','Srbija','0635984156','032163256332',-1,-1,-1,TRUE,'2019-11-19 04:00:00',FALSE,'06:00', '14:00',FALSE,'CLINICAL_CENTER_ADMIN');
insert into user (name, surname, email, password, address, city, country, phone_number, serial_number,number_of_reviews, rating, activated_account, enabled, last_password_reset_date, password_changed,clinic_id, working_hours_start, working_hours_end, removed, type) values ('Zika', 'Zikic', 'zika.zikic789@gmail.com','$2a$10$laKrspGgEPZFXKWh6R0v3O7HGV7FCOYCgFvCtKCPseId0jk.glGY6','Alekse Santica 47','Novi Sad','Srbija','0635984156','032163256332',-1,-1,-1,TRUE,'2019-11-19 04:00:00',FALSE,1,'06:00', '14:00',FALSE,'CLINIC_ADMIN');
insert into user (name, surname, email, password, address, city, country, phone_number, serial_number, number_of_reviews, rating, activated_account, enabled, last_password_reset_date, password_changed, working_hours_start, working_hours_end, removed, clinic_id, type) values ('Petar', 'Petrovic', 'amanda.pitt97@gmail.com','$2a$10$.4TlEcGSbL2ibKLkKMBqo.otZTShm8UqMjkpOK3Lpl42y3BH0m56q','Alekse Santica 47','Novi Sad','Srbija','0635984156','032163256332',0,0,-1,TRUE,'2019-11-19 04:00:00',FALSE, '06:00', '14:00', FALSE, 1, 'DOCTOR');
insert into user (name, surname, email, password, address, city, country, phone_number, serial_number,number_of_reviews, rating, activated_account, enabled, last_password_reset_date, password_changed, working_hours_start, working_hours_end, removed, clinic_id, type) values ('Mira', 'Miric', 'mira@gmail.com','$2a$10$KE8DhbZ9044d9fMIDIJopOc70vGQpjGzKjIRWrVlCDiyoSxJGEJ2G','Alekse Santica 47','Novi Sad','Srbija','0635984156','032163256332',-1,-1,-1,TRUE,'2019-11-19 04:00:00',FALSE,'06:00', '14:00', FALSE, 1, 'NURSE');
insert into user (name, surname, email, password, address, city, country, phone_number, serial_number,number_of_reviews, rating, activated_account, enabled, last_password_reset_date, password_changed,medical_record_id,working_hours_start, working_hours_end, removed, type) values ('Pera', 'Peric', 'pera@gmail.com','$2a$10$ys.u36Wk7e/9y.dkUb0svOD5I.vaFRtdDwaXQlR5K/O42EYrlWGq6','Alekse Santica 47','Novi Sad','Srbija','0635984156','032163256332',-1,-1,'ACCEPTED',TRUE,'2019-11-19 04:00:00',TRUE,1,'06:00', '14:00', FALSE,'PATIENT');

insert into user_authority(user_id, authority_id) value (1,1);
insert into user_authority(user_id, authority_id) value (2,2);
insert into user_authority(user_id, authority_id) value (3,4);
insert into user_authority(user_id, authority_id) value (4,5);
insert into user_authority(user_id, authority_id) value (5,3);


insert into exam_type (name, description, price,discount,removed,duration) value ('CT snimanje glave', 'Snimanje glave',10000,10, FALSE,1);
insert into exam_type (name, description, price,discount,removed,duration) value ('Ultrazvuk kicenog stuba', 'Kompletan ultrazvuk celog kicmenog stuba.',15000, 10, FALSE,1);
insert into exam_type (name, description, price,discount,removed,duration) value ('Provera motornih sposobnosti', 'Provera motornih sposobnosti uz pomoc savremenih uredjaja.',7000,10, FALSE,1);
insert into surgery_type(name,description,price,discount,removed,duration) value ('Operacija slepog creva','Najnovija laserska metoda.',100000,5,FALSE,1);
insert into surgery_type(name,description,price,discount,removed,duration) value ('Operacija ledjnih prsljena','Najnovija laserska metoda.',90000,5,FALSE,1);
insert into surgery_type(name,description,price,discount,removed,duration) value ('Operacija kicmene mozdine','Najnovija laserska metoda.',150000,5,FALSE,1);

insert into doctors_exam_types(doctor_id, exam_type_id) value (3,1);
insert into doctors_surgery_types(doctor_id, surgery_type_id) value (3,1);
insert into room (name, number,room_type,removed) value ('Room 1',1,'Medical exam',FALSE);
insert into medical_exam (date, start_time, end_time,reserved,doctor_id,exam_room_id,exam_type_id,patient_id) values ('2018-07-31', '1:05:04','4:34:12',FALSE,3,1,1,5);
insert into clinic_rooms (clinic_id, rooms_id) value (1,1);

insert into clinics_exam_types (exam_type_id, clinic_id) value (1,1);
insert into clinics_exam_types (exam_type_id, clinic_id) value (2,1);
insert into clinics_exam_types (exam_type_id, clinic_id) value (3,1);
insert into clinics_surgery_types(surgery_type_id, clinic_id) value (1,1);
insert into clinics_surgery_types(surgery_type_id, clinic_id) value (2,1);
insert into clinics_surgery_types(surgery_type_id, clinic_id) value (3,1);

insert into fast_appointment(date_time, done, duration, price, clinic_id, doctor_id, exam_type_id, patient_id, room_id) values ('2020-12-02 13:45:00', FALSE, 45, 5000, 1, 3, 1, 5, 1);
insert into room_reservation_time(start_date_time, end_date_time, done, room_id) value ('2020-12-02 13:45:00','2020-12-02 14:30:00', FALSE, 1);


insert into medical_report (description,date,time,doctor_id) value ('Ceste migrene.','2019-04-07','12:45:00',3);
insert into medication (code, description) value ('#ABC12300','Bensedin 500 mg');
insert into medication (code, description) value ('#AF000025','Nolpaza 40 mg');
insert into medication (code, description) value ('#AB250020','Vitamin D 2000 UI');
insert into medication (code, description) value ('#BD803021','Ciprocinal 500 mg');
insert into prescription (verified, medication_id, nurse_id, patient_id, doctor_id, clinic_id) value (FALSE,1,null,5,3,1);
insert into prescription (verified, medication_id, nurse_id, patient_id, doctor_id, clinic_id) value (FALSE,2,null,5,3,1);
insert into prescription (verified, medication_id, nurse_id, patient_id, doctor_id, clinic_id) value (FALSE,3,null,5,3,2);

insert into medical_report_prescriptions (medical_report_id, prescriptions_id) value(1,1);
insert into medical_report_prescriptions (medical_report_id, prescriptions_id) value(1,2);
insert into medical_report_prescriptions (medical_report_id, prescriptions_id) value(1,3);

insert into request_for_absence (start_date,end_date,serial_number,type) value ('2019-10-15','2019-11-01','032163256332','paid vacation');
insert into surgery (date,start_time, end_time,patient_id,surgery_type_id) value ('2019-04-12','12:45:00','15:45:00',5,1);
insert into doctors_surgeries (doctor_id, surgery_id) value (3,1);
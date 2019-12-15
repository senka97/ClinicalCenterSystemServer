-- noinspection SqlNoDataSourceInspectionForFile
insert into authority (name) value ('ROLE_CLINICAL_CENTER_ADMIN');
insert into authority (name) value ('ROLE_CLINIC_ADMIN');
insert into authority (name) value ('ROLE_PATIENT');
insert into authority (name) value ('ROLE_DOCTOR');
insert into authority (name) value ('ROLE_NURSE');

insert into clinical_center(name) value ('Dr Cvjetkovic');
insert into diagnose (code, description) values ('#00000023', 'Hipohondrija');
insert into medical_record (organ_donor,height,weight,diopter,blood_type) value (true,182,70,0.0,'A-');
insert into clinic (name, address,description,rating,number_of_reviews) value ('Neurologija', 'Narodnog Fronta 76', 'Klinika za neurologiju',4.5,10);
insert into clinic (name, address,description,rating,number_of_reviews) value ('Rehabilitacija', 'Kralja Petra 33', 'Klinika za rehabilitaciju',3,1);
insert into clinic (name, address,description,rating,number_of_reviews) value ('Pulmologija', 'Brace Ribnikar 49', 'Klinika za pulmologiju',4,2);
insert into clinic (name, address,description,rating,number_of_reviews) value ('Kardiologija', 'Kralja Petra 37', 'Klinika za kardiologiju',5,25);
insert into user (name, surname, email, password, address, city, country, phone_number, serial_number,number_of_reviews, rating, activated_account, enabled, last_password_reset_date, password_changed,type) values ('Mika', 'Mikic', 'mika@gmail.com','$2a$10$.iwB3NV6S83aXegd8HmYweflprG3x8lMvXMevv1oYEtmnArEaE1iW','Alekse Santica 47','Novi Sad','Srbija','0635984156','032163256332',-1,-1,-1,TRUE,'2019-11-19 04:00:00',FALSE,'CLINICAL_CENTER_ADMIN');
insert into user (name, surname, email, password, address, city, country, phone_number, serial_number,number_of_reviews, rating, activated_account, enabled, last_password_reset_date, password_changed,clinic_id,type) values ('Zika', 'Zikic', 'zika@gmail.com','$2a$10$laKrspGgEPZFXKWh6R0v3O7HGV7FCOYCgFvCtKCPseId0jk.glGY6','Alekse Santica 47','Novi Sad','Srbija','0635984156','032163256332',-1,-1,-1,TRUE,'2019-11-19 04:00:00',FALSE,1,'CLINIC_ADMIN');
insert into user (name, surname, email, password, address, city, country, phone_number, serial_number, number_of_reviews, rating, activated_account, enabled, last_password_reset_date, password_changed,type) values ('Petar', 'Petrovic', 'petar@gmail.com','$2a$10$.4TlEcGSbL2ibKLkKMBqo.otZTShm8UqMjkpOK3Lpl42y3BH0m56q','Alekse Santica 47','Novi Sad','Srbija','0635984156','032163256332',0,0,-1,TRUE,'2019-11-19 04:00:00',FALSE,'DOCTOR');
insert into user (name, surname, email, password, address, city, country, phone_number, serial_number,number_of_reviews, rating, activated_account, enabled, last_password_reset_date, password_changed,type) values ('Mira', 'Miric', 'mira@gmail.com','$2a$10$KE8DhbZ9044d9fMIDIJopOc70vGQpjGzKjIRWrVlCDiyoSxJGEJ2G','Alekse Santica 47','Novi Sad','Srbija','0635984156','032163256332',-1,-1,-1,TRUE,'2019-11-19 04:00:00',FALSE,'NURSE');
insert into user (name, surname, email, password, address, city, country, phone_number, serial_number,number_of_reviews, rating, activated_account, enabled, last_password_reset_date, password_changed,type,medical_record_id) values ('Pera', 'Peric', 'pera@gmail.com','$2a$10$ys.u36Wk7e/9y.dkUb0svOD5I.vaFRtdDwaXQlR5K/O42EYrlWGq6','Alekse Santica 47','Novi Sad','Srbija','0635984156','032163256332',-1,-1,'ACCEPTED',TRUE,'2019-11-19 04:00:00',TRUE,'PATIENT',1);

insert into user_authority(user_id, authority_id) value (1,1);
insert into user_authority(user_id, authority_id) value (2,2);
insert into user_authority(user_id, authority_id) value (3,4);
insert into user_authority(user_id, authority_id) value (4,5);
insert into user_authority(user_id, authority_id) value (5,3);


insert into exam_type (name, description, price,discount) values ('CT snimanje glave', 'Snimanje glave',10000,10);
insert into room (name, number,room_type,occupied) value ('Room 1',1,'Medical exam',FALSE);
insert into medical_exam (date, start_time, end_time,reserved,doctor_id,exam_room_id,exam_type_id,patient_id) values ('2018-07-31', '1:05:04','4:34:12',FALSE,3,1,1,5);
insert into clinic_rooms (clinic_id, rooms_id) value (1,1);

insert into prescription (verified,nurse_id) value (FALSE,4);
insert into medical_report (description,date,time,doctor_id,prescription_id) value ('Ceste migrene.','2019-04-07','12:45:00',3,1);
insert into medication (code, description) value ('ad89','Bensedin 500 mg');
insert into request_for_absence (start_date,end_date,serial_number,type) value ('2019-10-15','2019-11-01','032163256332','paid vacation');
insert into surgery_type(name,description,price,discount) value ('Operacija slepog creva','Najnovija laserska metoda.',100000,5);
insert into surgery (date,start_time, end_time,patient_id,surgery_type_id) value ('2019-04-12','12:45:00','15:45:00',5,1);
insert into clinics_surgery_types (clinic_id, surgery_type_id) value (1,1);
insert into doctors_surgeries (doctor_id, surgery_id) value (3,1);
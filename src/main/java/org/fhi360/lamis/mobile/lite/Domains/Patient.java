package org.fhi360.lamis.mobile.lite.Domains;

import lombok.Data;

@Data
public class Patient {
    private long patientId;
    private Facility2 facility;
    private long htsId;
    private String hospitalNum;
    private String uniqueId;
    private String surname;
    private String otherNames;
    private String gender;
    private String dateBirth;
    private String ageUnit;
    private int age;
    private String maritalStatus;
    private String education;
    private String occupation;
    private String address;
    private String phone;
    private String state;
    private String lga;
    private String nextKin;
    private String addressKin;
    private String phoneKin;
    private String relationKin;
    private String entryPoint;
    private String targetGroup;
    private String dateConfirmedHiv;
    private String tbStatus;
    private int pregnant;
    private int userId;
    private int breastfeeding;
    private String timeStamp;
    private String uploaded;
    private String timeUploaded;
    private String dateRegistration;
    private String statusRegistration;
    private long deviceconfigId;
    private int biometric = 0;
    private String uuid;
    private String facilityName;
    private Long id;
    private Long pid;


}

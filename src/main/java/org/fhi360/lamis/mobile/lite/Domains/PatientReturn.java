package org.fhi360.lamis.mobile.lite.Domains;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PatientReturn {
    private Long id;
    private Facility2 facility;
    private String clientCode;
    private Long htsId;
    private String hospitalNum;
    private String uniqueId;
    private String surname;
    private String otherNames;
    private String gender;
    private String dateBirth;
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
    private Boolean pregnant;
    private Boolean breastfeeding;
    private String dateRegistration;
    private String uuid;
    private long deviceconfigId;
}

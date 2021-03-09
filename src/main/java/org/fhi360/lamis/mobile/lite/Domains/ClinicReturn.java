package org.fhi360.lamis.mobile.lite.Domains;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class ClinicReturn {
    private Patient2 patient;
    private Facility2 facility;
    private String dateVisit;
    private String clinicStage;
    private String funcStatus;
    private String tbStatus;
    private Double viralLoad;
    private String regimenTypeId;
    private String regimen;
    private Double bodyWeight;
    private Double height;
    private Double waist;
    private String bp;
    private Boolean pregnant;
   // private LocalDate lmp;
    private Boolean breastfeeding;
    private long deviceconfigId;
    private String nextAppointment;
    private String uuid;
    private String clinicUuid;

}

package org.fhi360.lamis.mobile.lite.Domains;

import lombok.Data;

@Data
public class Clinic {
    private long clinicId;
    private Patient2 patient;
    private long facilityId;
    private String dateVisit;
    private String clinicStage;
    private String funcStatus;
    private String tbStatus;
    private String viralLoad;
    private String regimentype;
    private String regimen;
    private double bodyWeight;
    private double height;
    private double waist;
    private String bp;
    private String pregnant;
    private String lmp;
    private int breastfeeding;
    private String nextAppointment;
    private String notes;
    private long userId;
    private String timeStamp;
    private int uploaded;
    private String timeUploaded;
    private long deviceconfigId;
    private String uuid;
    private String clinicUuid;

}

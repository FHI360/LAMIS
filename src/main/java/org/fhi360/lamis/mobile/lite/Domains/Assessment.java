package org.fhi360.lamis.mobile.lite.Domains;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Assessment {
    private long assessmentId;
    private String clientCode;
    private long facilityId;
    private Facility facility;
    private String dateVisit;
    private String question1;
    private int question2;
    private int question3;
    private int question4;
    private int question5;
    private int question6;
    private int question7;
    private int question8;
    private int question9;
    private int question10;
    private int question11;
    private int question12;
    private long deviceconfigId;
    private int sti1;
    private int sti2;
    private int sti3;
    private int sti4;
    private int sti5;
    private int sti6;
    private int sti7;
    private int sti8;
    private String uuid;

}

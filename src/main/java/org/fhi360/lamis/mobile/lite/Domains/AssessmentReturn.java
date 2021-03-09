package org.fhi360.lamis.mobile.lite.Domains;

import lombok.Data;

@Data
public class AssessmentReturn {
    private String dateVisit;
    private String clientCode;
    private Facility facility;
    private String question1;
    private String question2;
    private Integer question3;
    private Integer question4;
    private Integer question5;
    private Integer question6;
    private Integer question7;
    private Integer question8;
    private Integer question9;
    private Integer question10;
    private Integer question11;
    private Integer question12;
    private Integer sti1;
    private Integer sti2;
    private Integer sti3;
    private Integer sti4;
    private Integer sti5;
    private Integer sti6;
    private Integer sti7;
    private Integer sti8;
    private Long deviceconfigId;
}

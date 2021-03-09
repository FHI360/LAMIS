package org.fhi360.lamis.mobile.lite.Domains;
import lombok.Data;
@Data
public class Recency {
    private Long id;
    private String recencyNumber;
    private Hts2 hts;
    private String testName;
    private String dateSampleTest;
    private String testDate;
    private Long controlLine;
    private Long verificationLine;
    private Long longTimeLine;
    private String recencyInterpretation;
}

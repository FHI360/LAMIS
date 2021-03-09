package org.fhi360.lamis.mobile.lite.Domains;

import lombok.Data;

@Data
public class Recency2 {
    private Long id;
    private String recencyNumber;
    private Long htsId;
    private String testName;
    private String dateSampleTest;
    private String testDate;
    private Long controlLine;
    private Long verificationLine;
    private Long longTimeLine;
    private String recencyInterpretation;
}

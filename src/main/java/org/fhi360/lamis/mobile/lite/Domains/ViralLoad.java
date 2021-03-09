package org.fhi360.lamis.mobile.lite.Domains;

import lombok.Data;

@Data
public class ViralLoad {
    private Long id;
    private String recencyNumber;
    private String viralLoadResultClassification;
    private String sampleReferenceNumber;
    private String dateSampleCollected;
    private String typeSample;
    private String dateSampleTest;
    private String dateTestDone;
    private String viralLoadResult;
    private String finalResult;
}

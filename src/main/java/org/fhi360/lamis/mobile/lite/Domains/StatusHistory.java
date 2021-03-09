package org.fhi360.lamis.mobile.lite.Domains;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class StatusHistory {
    private long historyId;
    private Patient patient;
    private long facilityId;
    private String currentStatus;
    private String dateCurrentStatus;
    private String reasonInterrupt;
    private String causeDeath;
    private String dateTracked;
    private String outcome;
    private String agreedDate;
    private String timeStamp;
    private Integer uploaded;
    private String timeUploaded;
}

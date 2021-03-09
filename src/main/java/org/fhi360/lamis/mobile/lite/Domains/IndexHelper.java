package org.fhi360.lamis.mobile.lite.Domains;

import lombok.Data;

@Data
public class IndexHelper {
    private int id;
    private long htsId;
    private String indexType;
    private String dateConfirmHivStatus;
    private String dateEnrolledHivCase;
    private String clientIndexCode;
    private String huspitalNum;
    private String notificationCounseling;
    private String agreeNotifications;
    private String numberPartner;
    private long deviceconfigId;
}

package org.fhi360.lamis.mobile.lite.Domains;

import lombok.Data;

@Data
public class IndexContact {
    private long indexcontactId;
    private Hts2 hts;
    private String clientCode;
    private long facilityId;
    private String contactType;
    private String surname;
    private String otherNames;
    private Integer age;
    private String gender;
    private String address;
    private String phone;
    private String relationship;
    private String gbv;
    private Integer durationPartner;
    private String phoneTracking;
    private String homeTracking;
    private String outcome;
    private String dateHivTest;
    private String hivStatus;
    private String linkCare;
    private String partnerNotification;
    private String modeNotification;
    private String serviceProvided;
    private String uuid;
    private String indexContactCode;
    private long deviceconfigId;

}

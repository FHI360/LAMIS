package org.fhi360.lamis.mobile.lite.Domains;

import lombok.Data;

@Data
public class PatientDto {
    private String othernames;
    private String surname;
    private String hospitalnum;
    private String uuid;
    private Long facilityid;
    private String facilityname;
    private Long pid;

}

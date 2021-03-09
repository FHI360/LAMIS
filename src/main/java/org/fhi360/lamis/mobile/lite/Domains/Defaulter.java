package org.fhi360.lamis.mobile.lite.Domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Defaulter {
    private Long defaulterId;
    private Long patient_id;
    private String hospital_num;
    private Long facility_id;
    private String surname;
    private String other_names;
    private String address;
    private String phone;
    private String phone_kin;
    private String address_kin;
    private String next_kin;
    private String current_status;
    private String date_current_status;
    private String date_next_clinic;
    private String date_next_refill;

}

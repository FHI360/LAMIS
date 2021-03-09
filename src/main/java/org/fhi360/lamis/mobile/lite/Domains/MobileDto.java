package org.fhi360.lamis.mobile.lite.Domains;

import java.util.List;

import lombok.Data;

@Data
public class MobileDto {
    private List<PatientReturn> patients;
    private List<ClinicReturn> clinics;
}

package org.fhi360.lamis.mobile.lite.Domains;

import java.util.List;

public class PatientList {
    List<Patient> patient;

    public void setPatient(List<Patient> patient) {
        this.patient = patient;
    }

    public List<Patient> getPatient() {
        return patient;
    }
}

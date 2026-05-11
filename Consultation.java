package opd;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Records the details of a medical consultation (post-appointment).
 */
public class Consultation {

    // ── Fields ────────────────────────────────────────────────────────────────
    private static int consCounter = 9000;

    private final String        consultationId;
    private final Patient       patient;
    private final Doctor        doctor;
    private final Appointment   appointment;
    private final LocalDateTime consultationDateTime;

    private String       symptoms;
    private String       diagnosis;
    private String       treatment;
    private List<String> prescribedMedicines;
    private String       labTests;
    private String       followUpDate;
    private String       doctorNotes;
    private double       billAmount;

    // ── Constructors ─────────────────────────────────────────────────────────

    /** Full constructor */
    public Consultation(Patient patient, Doctor doctor, Appointment appointment,
                        String symptoms, String diagnosis, String treatment,
                        List<String> medicines, String labTests,
                        String followUpDate, String doctorNotes) {
        this.consultationId       = "C" + (++consCounter);
        this.patient              = patient;
        this.doctor               = doctor;
        this.appointment          = appointment;
        this.consultationDateTime = LocalDateTime.now();
        this.symptoms             = symptoms;
        this.diagnosis            = diagnosis;
        this.treatment            = treatment;
        this.prescribedMedicines  = medicines != null ? medicines : new ArrayList<>();
        this.labTests             = labTests;
        this.followUpDate         = followUpDate;
        this.doctorNotes          = doctorNotes;
        this.billAmount           = doctor.getConsultationFee();

        // Mark appointment as completed and register consultation
        if (appointment != null) appointment.complete();
        patient.addConsultation(this);
    }

    /** Simplified constructor */
    public Consultation(Patient patient, Doctor doctor, Appointment appointment,
                        String symptoms, String diagnosis, String treatment) {
        this(patient, doctor, appointment, symptoms, diagnosis, treatment,
             new ArrayList<>(), "None", "As required", "");
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    public String        getConsultationId()      { return consultationId; }
    public Patient       getPatient()             { return patient; }
    public Doctor        getDoctor()              { return doctor; }
    public Appointment   getAppointment()         { return appointment; }
    public LocalDateTime getConsultationDateTime(){ return consultationDateTime; }
    public String        getSymptoms()            { return symptoms; }
    public String        getDiagnosis()           { return diagnosis; }
    public String        getTreatment()           { return treatment; }
    public List<String>  getPrescribedMedicines() { return prescribedMedicines; }
    public String        getLabTests()            { return labTests; }
    public String        getFollowUpDate()        { return followUpDate; }
    public String        getDoctorNotes()         { return doctorNotes; }
    public double        getBillAmount()          { return billAmount; }

    // ── Setters ───────────────────────────────────────────────────────────────
    public void setSymptoms(String s)              { this.symptoms = s; }
    public void setDiagnosis(String d)             { this.diagnosis = d; }
    public void setTreatment(String t)             { this.treatment = t; }
    public void addMedicine(String med)            { prescribedMedicines.add(med); }
    public void setLabTests(String tests)          { this.labTests = tests; }
    public void setFollowUpDate(String date)       { this.followUpDate = date; }
    public void setDoctorNotes(String notes)       { this.doctorNotes = notes; }
    public void setBillAmount(double amount)       { this.billAmount = amount; }

    // ── Display Methods ──────────────────────────────────────────────────────
    /** Compact row for history lists */
    public void displaySummary() {
        System.out.printf("    [%s] %s  Dr.%-16s  %-25s%n",
            consultationId,
            consultationDateTime.toLocalDate(),
            doctor.getName(),
            diagnosis);
    }

    /** Full consultation report */
    public void displayFullReport() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.printf ("║         CONSULTATION REPORT  %-23s║%n", "");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.printf ("║  Consultation ID : %-33s║%n", consultationId);
        System.out.printf ("║  Date & Time     : %-33s║%n", consultationDateTime);
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.printf ("║  Patient         : %s (%s)%n", patient.getName(), patient.getPatientId());
        System.out.printf ("║  Age / Gender    : %d / %-29s║%n", patient.getAge(), patient.getGender());
        System.out.printf ("║  Blood Group     : %-33s║%n", patient.getBloodGroup());
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.printf ("║  Doctor          : Dr. %-29s║%n", doctor.getName());
        System.out.printf ("║  Specialization  : %-33s║%n", doctor.getSpecialization());
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.printf ("║  Symptoms        : %-33s║%n", symptoms);
        System.out.printf ("║  Diagnosis       : %-33s║%n", diagnosis);
        System.out.printf ("║  Treatment       : %-33s║%n", treatment);
        System.out.println("╠══════════════════════════════════════════════════════╣");

        System.out.println("║  Prescribed Medicines:                               ║");
        if (prescribedMedicines.isEmpty()) {
            System.out.println("║    None                                              ║");
        } else {
            for (String med : prescribedMedicines) {
                System.out.printf("║    ▸ %-47s║%n", med);
            }
        }

        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.printf ("║  Lab Tests       : %-33s║%n", labTests);
        System.out.printf ("║  Follow-Up Date  : %-33s║%n", followUpDate);
        System.out.printf ("║  Doctor's Notes  : %-33s║%n",
            doctorNotes.isEmpty() ? "—" : doctorNotes);
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.printf ("║  Bill Amount     : ₹%-32.2f║%n", billAmount);
        System.out.println("╚══════════════════════════════════════════════════════╝");
    }

    @Override
    public String toString() {
        return String.format("Consultation[%s | %s | Dr.%s | %s]",
            consultationId, patient.getName(), doctor.getName(), diagnosis);
    }
}

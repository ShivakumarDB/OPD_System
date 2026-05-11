🏥 OPD Management System
A Java-based Outpatient Department (OPD) Management System that manages patient registrations, doctor profiles, appointment scheduling, and medical consultations using Object-Oriented Programming principles.

📋 Table of Contents

Project Overview
Features
Project Structure
Class Description
OOP Concepts Used
Prerequisites
How to Run
Sample Output
Menu Navigation


📌 Project Overview
The OPD Management System is a console-based Java application developed to digitize and streamline the operations of an Outpatient Department in a hospital. It allows hospital staff to:

Register and manage patients
Maintain doctor profiles and availability
Schedule and track appointments
Record consultation details including diagnosis, medicines, and follow-ups
Generate reports and statistics


✨ Features
FeatureDescriptionPatient RegistrationRegister new patients with full details and auto-generated IDsDoctor ManagementAdd doctors with specialization, availability, and fee detailsAppointment SchedulingBook, cancel, and track appointmentsConsultation RecordingRecord diagnosis, medicines, lab tests, and follow-up datesSearchSearch patients by ID or nameReportsOPD dashboard with totals and revenue summaryDemo DataPre-loaded sample data for testing

📁 Project Structure
AJ_PROJECT/
└── opd/
    ├── Patient.java              # Patient entity class
    ├── Doctor.java               # Doctor entity class
    ├── Appointment.java          # Appointment entity class
    ├── Consultation.java         # Consultation entity class
    └── OPDManagementSystem.java  # Main controller class

📦 Class Description
1. Patient.java
Represents a patient registered in the OPD.
Fields: patientId, name, age, gender, contactNumber, address, bloodGroup, registrationDate, appointments, consultations
Key Methods:

displaySummary() — prints compact patient row
displayFullProfile() — prints full patient details with history
addAppointment() / addConsultation() — links records to patient


2. Doctor.java
Represents a doctor working in the OPD.
Fields: doctorId, name, specialization, qualification, contactNumber, availableDays, consultingHours, consultationFee, appointments
Key Methods:

displaySummary() — prints compact doctor row
displayFullProfile() — prints full doctor details
getPendingAppointmentsCount() — returns count of scheduled appointments


3. Appointment.java
Links a patient and doctor for a scheduled visit.
Fields: appointmentId, patient, doctor, date, time, reason, status, bookedOn
Status Enum: SCHEDULED, COMPLETED, CANCELLED, NO_SHOW
Key Methods:

cancel() / complete() / markNoShow() — status management
displaySummary() / displayDetails() — display methods
isToday() — checks if appointment is today


4. Consultation.java
Records full medical consultation details.
Fields: consultationId, patient, doctor, appointment, symptoms, diagnosis, treatment, prescribedMedicines, labTests, followUpDate, doctorNotes, billAmount
Key Methods:

displaySummary() — compact history row
displayFullReport() — complete consultation report with bill


5. OPDManagementSystem.java
Main controller class with the menu-driven interface.
Responsibilities:

Manages all lists (patients, doctors, appointments, consultations)
Provides all CRUD operations via menus
Generates reports and statistics
Loads demo data on startup


🧠 OOP Concepts Used
ConceptWhere UsedClasses & ObjectsPatient, Doctor, Appointment, ConsultationConstructorsFull and basic constructors in all classesEncapsulationAll fields are private with getters/settersAbstractionInternal logic hidden inside methodsEnumAppointment.Status for type-safe statusCollectionsList<> for appointments and consultationsStatic FieldsAuto-incrementing ID countersMethod OverloadingMultiple constructors per classStream APIFiltering, searching, and aggregating data

⚙️ Prerequisites

Java JDK 17 or above — Download from https://adoptium.net
VS Code (recommended) with Extension Pack for Java
OR any Java IDE (IntelliJ IDEA, Eclipse, NetBeans)

Verify Java Installation
bashjava -version
Expected output:
openjdk version "21.0.x" ...

▶️ How to Run
Using VS Code

Open the AJ_PROJECT folder in VS Code
Open OPDManagementSystem.java
Click the ▶ Run Java button above the main method

Using Command Line
Navigate to the project root folder (AJ_PROJECT) and run:
Step 1 — Compile
bashjavac opd/Patient.java opd/Doctor.java opd/Appointment.java opd/Consultation.java opd/OPDManagementSystem.java
Step 2 — Run
bashjava opd.OPDManagementSystem

🖥️ Sample Output
  ╔══════════════════════════════════════════════════╗
  ║      OPD MANAGEMENT SYSTEM  v1.0                 ║
  ║      Outpatient Department — City Hospital        ║
  ╚══════════════════════════════════════════════════╝

  ✔ Demo data loaded: 3 doctors, 3 patients, 3 appointments, 1 consultation.

  ┌──────────────────────────────────┐
  │           MAIN  MENU             │
  ├──────────────────────────────────┤
  │  1. Patient Management           │
  │  2. Doctor Management            │
  │  3. Appointment Management       │
  │  4. Consultation Management      │
  │  5. Reports & Statistics         │
  │  0. Exit                         │
  └──────────────────────────────────┘

🗂️ Menu Navigation
Main Menu
├── 1. Patient Management
│   ├── 1. Register New Patient
│   ├── 2. View All Patients
│   ├── 3. Search Patient by ID
│   ├── 4. Search Patient by Name
│   └── 5. View Patient Full Profile
│
├── 2. Doctor Management
│   ├── 1. Add New Doctor
│   ├── 2. View All Doctors
│   ├── 3. Search Doctor by ID
│   └── 4. View Doctor Full Profile
│
├── 3. Appointment Management
│   ├── 1. Schedule New Appointment
│   ├── 2. View All Appointments
│   ├── 3. View Today's Appointments
│   ├── 4. Cancel Appointment
│   └── 5. Mark as No-Show
│
├── 4. Consultation Management
│   ├── 1. Record New Consultation
│   ├── 2. View Consultation by ID
│   └── 3. View All Consultations
│
└── 5. Reports & Statistics
    ├── 1. OPD Summary Dashboard
    ├── 2. Doctor-wise Appointment Count
    └── 3. Patient Medical History

👨‍💻 Author
AJ Project
Java OPD Management System
Built with ❤️ using Core Java

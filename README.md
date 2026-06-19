# 🏥 Hospital Management System (HMS)

A full-stack, containerized **Hospital Management System** built to digitize and streamline core hospital operations — patient registration, doctor management, appointments, admissions/discharges, prescriptions, and medical records — under a single, scalable monolithic architecture.

> **Status:** Backend entities, repositories, services, and controllers in active development. Frontend (React) planned next. Dockerization & AWS deployment planned post-MVP.

---

## 📌 Table of Contents

- [Why This Project](#-why-this-project)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Database Schema](#-database-schema)
- [Core Modules & Features](#-core-modules--features)
- [Edge Cases Handled](#-edge-cases-handled)
- [Project Structure](#-project-structure)
- [Getting Started](#-getting-started)
- [API Overview](#-api-overview)
- [Docker & Deployment Plan](#-docker--deployment-plan)
- [Roadmap](#-roadmap)
- [What I Learned / Talking Points](#-what-i-learned--talking-points)
- [License](#-license)

---

## 🎯 Why This Project

Most hospitals — especially small to mid-sized ones — still rely on fragmented spreadsheets or paper-based registers to manage patients, appointments, and room allocation. This leads to:

- Double-booked appointment slots
- No single source of truth for a patient's medical history
- Manual, error-prone room/bed allocation during admission
- No audit trail for prescriptions or discharge summaries

**HMS** solves this with a single, well-modeled relational system that ties together **patients, doctors, departments, appointments, admissions, prescriptions, and medical records** — enforcing the real-world business rules a hospital actually needs (e.g., a room can't be double-allotted, a doctor can't be double-booked, a patient can't be discharged without an active admission).

This project was built specifically to demonstrate **strong backend system design, relational data modeling, and real-world constraint handling**

---

## 🛠 Tech Stack

| Layer | Technology |
|---|---|
| **Backend** | Java, Spring Boot (Spring Web, Spring Data JPA, Spring Validation) |
| **Frontend** | React (planned) |
| **Database** | MySQL |
| **Architecture** | Monolithic (single deployable Spring Boot app serving REST APIs) |
| **Containerization** | Docker (separate images for backend, frontend, and DB) |
| **Deployment Target** | AWS (EC2 / ECS) |
| **Build Tool** | Maven |
| **API Testing** | Postman |

---

## 🏗 Architecture

HMS follows a classic **layered monolithic architecture**:

```
┌─────────────────────────────────────────────┐
│                   Client                    │
│         React SPA (Browser / Mobile)        │
└───────────────────────┬─────────────────────┘
                        │ REST (JSON over HTTPS)
┌───────────────────────▼───────────────────────┐
│              Spring Boot Application          │
│  ┌──────────────────────────────────────────┐ │
│  │  Controller Layer  → REST endpoints      │ │
│  ├──────────────────────────────────────────┤ │
│  │  Service Layer     → Business logic      │ │
│  ├──────────────────────────────────────────┤ │
│  │  Repository Layer  → Spring Data JPA     │ │
│  └──────────────────────────────────────────┘ │
└───────────────────────┬───────────────────────┘
                        │ JDBC
┌───────────────────────▼───────────────────────┐
│                  MySQL Database               │
└───────────────────────────────────────────────┘
```

**Why monolithic and not microservices?**
For a project of this domain size, a monolith keeps transactional integrity simple (e.g., admission + room status update happen in one ACID transaction), avoids the operational overhead of distributed systems, and is the right architectural trade-off until scale genuinely demands service decomposition. This is a deliberate, explainable decision — not a limitation.

---

## 🗄 Database Schema

The schema is built around 9 core entities:

| Entity | Purpose |
|---|---|
| **User** | Login/auth identity, linked to a `UserRole` enum (e.g., `ADMIN`, `DOCTOR`, `RECEPTIONIST`, `PATIENT`) |
| **Patient** | Demographic & contact info for each patient |
| **Doctor** | Doctor profile, specialization, linked to a `Department` |
| **Department** | Hospital departments (Cardiology, Orthopedics, etc.) |
| **Appointment** | Links a `Patient` ↔ `Doctor` at a scheduled date/time with a status |
| **Admission** | Tracks a patient's stay — admit date, discharge date, assigned `Room` |
| **Room** | Hospital rooms/beds with type (General/ICU/Private) and occupancy status |
| **Prescription** | Medicines prescribed during an appointment/admission |
| **Medicine** | Master list of medicines (name, dosage form, stock, etc.) |
| **MedicalRecord** | Longitudinal medical history per patient (diagnosis, notes, history) |

### Entity Relationship Overview

```
User (1) ────────< UserRole (enum)

Department (1) ──────< (many) Doctor

Patient (1) ──────< (many) Appointment >────── (1) Doctor
Patient (1) ──────< (many) Admission   >────── (1) Room
Patient (1) ──────< (many) MedicalRecord
Patient (1) ──────< (many) Prescription

Appointment (1) ───< (many) Prescription
Prescription (many) ───< >─── (many) Medicine   [via join entity]

Admission (many) ────── (1) Room
Doctor (1) ──────< (many) Prescription
```

**Key relationships explained:**
- A `Patient` can have many `Appointments`, but each `Appointment` belongs to exactly one `Doctor` and one `Patient` → **Many-to-One** on both sides.
- A `Room` can have multiple `Admissions` over time, but only **one active (non-discharged) Admission at a time** — enforced at the service layer, not just the schema.
- `Prescription` connects to `Medicine` via a join table to support **many-to-many** (one prescription, multiple medicines; one medicine, used in many prescriptions).
- `User` holds authentication identity; `UserRole` is an enum (`ADMIN`, `DOCTOR`, `RECEPTIONIST`, `PATIENT`) controlling access — separating **"who can log in"** from **"who receives care"** (a `Patient` row is not necessarily a `User` row, and vice versa).

---

## 🧩 Core Modules & Features

### 👤 User & Role Management
- Role-based access via `UserRole` enum: `ADMIN`, `DOCTOR`, `RECEPTIONIST`, `PATIENT`
- Each role sees a different scope of data (e.g., a doctor only sees their own appointments)

### 🧍 Patient Management
- CRUD for patient demographics
- Each patient's full history (appointments, admissions, prescriptions, medical records) is queryable from one profile

### 🩺 Doctor & Department Management
- Doctors are grouped under departments
- Doctor availability feeds into appointment scheduling

### 📅 Appointment Management
- Book, reschedule, and cancel appointments
- Prevents **double-booking** a doctor for an overlapping time slot
- Status lifecycle: `SCHEDULED` → `COMPLETED` / `CANCELLED`

### 🛏 Admission & Discharge
- Admit a patient to an available `Room`
- Room status flips to `OCCUPIED` on admission and back to `AVAILABLE` only after discharge
- Discharge requires an active admission record (cannot discharge twice)
- Length-of-stay and room-history tracking

### 💊 Prescription & Medicine
- Doctors prescribe one or more medicines per appointment/admission
- Medicine master table tracks stock, dosage form, and category
- Prevents prescribing a medicine that's out of stock (validation layer)

### 📋 Medical Records
- Persistent, append-only history of diagnoses and notes per patient
- Designed so records are **never deleted**, only added — preserving an audit trail (a real hospital cannot lose medical history)

---

## ⚠️ Edge Cases Handled

These are the constraint-driven decisions worth raising in interviews — they show you modeled the *real* problem, not just the happy path:

| Edge Case | How It's Handled |
|---|---|
| Double-booking a doctor for overlapping appointments | Service-layer check against existing `Appointment` records for that doctor & time window before persisting |
| Admitting a patient to an already-occupied room | Room status checked before admission; throws a domain-specific exception if `OCCUPIED` |
| Discharging a patient with no active admission | Validated against existing open `Admission` record (where `dischargeDate IS NULL`) |
| Re-discharging an already-discharged patient | Idempotency check — admission must have a null discharge date to be eligible |
| Prescribing a medicine with zero stock | Stock validation in the service layer before prescription is saved |
| Deleting a patient with existing medical history | Soft-delete / restrict-delete strategy instead of hard delete, to preserve referential integrity and audit trail |
| Appointment booked for a date in the past | Date validation at the DTO/service layer |
| Orphaned records (e.g., deleting a doctor with active appointments) | Foreign key constraints + cascade rules defined deliberately per relationship (not blanket `CASCADE ALL`) |
| Role mismatch (e.g., a `PATIENT` role hitting a doctor-only endpoint) | Role-based authorization checks at the controller/security layer |

> 💬 **Interview tip:** When asked "what was the hardest part of this project?" — this table is your answer. Constraint enforcement (not CRUD) is what separates a toy project from a system that models real-world rules.

---

## 📂 Project Structure

```
hospital-management-system/
├── backend/
│   ├── src/main/java/com/
│   │   ├── controller/        # REST controllers (one per entity)
│   │   ├── service/           # Business logic & validation
│   │   ├── repository/        # Spring Data JPA interfaces
│   │   ├── entity/            # JPA entities (Patient, Doctor, Room, etc.)
│   │   ├── enums/             # UserRole, AppointmentStatus, RoomStatus, etc.
│   │   ├── dto/                # Request/response DTOs
│   │   ├── exception/         # Custom exceptions + global handler
│   │   └── config/            # Security, CORS, Swagger config
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
│
├── frontend/                  # React app (planned)
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/           # Axios API calls
│   │   └── App.jsx
│   └── package.json
│
├── docker/
│   ├── backend.Dockerfile
│   ├── frontend.Dockerfile
│   └── docker-compose.yml
│
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8+
- Node.js 18+ (for frontend, once added)
- Docker & Docker Compose (for containerized setup)

---

## 📡 API Overview

All endpoints are versioned under `/api/v1`. Example resource routes:

| Resource | Endpoint | Methods |
|---|---|---|
| Patients | `/api/v1/patients` | GET, POST, PUT, DELETE |
| Doctors | `/api/v1/doctors` | GET, POST, PUT, DELETE |
| Departments | `/api/v1/departments` | GET, POST, PUT, DELETE |
| Appointments | `/api/v1/appointments` | GET, POST, PUT, DELETE |
| Admissions | `/api/v1/admissions` | GET, POST, PUT (discharge) |
| Rooms | `/api/v1/rooms` | GET, POST, PUT |
| Prescriptions | `/api/v1/prescriptions` | GET, POST |
| Medicines | `/api/v1/medicines` | GET, POST, PUT |
| Medical Records | `/api/v1/medical-records` | GET, POST |
| Auth/Users | `/api/v1/auth` | POST (login/register) |

> Full request/response contracts and sample payloads to be documented via Swagger/OpenAPI (planned).

---

## 🐳 Docker & Deployment Plan

The end goal is a fully containerized, cloud-deployed system:

1. **Backend image** — Spring Boot app packaged as a Docker image (multi-stage build: Maven build → slim JRE runtime).
2. **Frontend image** — React app built and served via an Nginx-based image.
3. **Database image** — Official MySQL image with a seeded init script for demo/edge-case data.
4. **Orchestration** — `docker-compose.yml` to spin up backend + frontend + MySQL together locally, with a defined network and named volumes for DB persistence.
5. **AWS Deployment** — Containers pushed to **Amazon ECR** and run on **EC2** (or ECS for orchestration), with environment-specific configs (DB credentials, CORS origins) injected via environment variables — not hardcoded.
6. **Demo dataset** — A small, intentionally curated dataset designed to *showcase* the edge cases above (e.g., one fully booked room, one doctor with overlapping appointment attempts, one out-of-stock medicine) — built specifically to **walk through in interviews**.

---

## 🗺 Roadmap

- [x] Design ER schema across 9 core entities
- [x] Build entities, repositories, services, controllers for backend
- [ ] Add Spring Security (JWT-based auth, role-based access control)
- [ ] Add input validation & global exception handling
- [ ] Add Swagger/OpenAPI documentation
- [ ] Build React frontend (patient portal, doctor dashboard, admin panel)
- [ ] Write Dockerfiles for backend & frontend
- [ ] Write `docker-compose.yml` for local multi-container setup
- [ ] Seed demo dataset highlighting edge cases
- [ ] Deploy containers to AWS (EC2/ECS)
- [ ] Add unit & integration tests (JUnit, Mockito)
- [ ] Add CI/CD pipeline (GitHub Actions)

---

## 💡 What I Learned / Talking Points

Notes for interview prep — things this project demonstrates beyond "I can write CRUD APIs":

- **Relational modeling under real constraints**: one-to-many, many-to-many (Prescription↔Medicine), and enum-driven role modeling (`UserRole`).
- **Service-layer business rules vs. database constraints**: knowing what to enforce in code (e.g., "no double booking") vs. what the schema enforces (FKs, unique constraints).
- **Layered architecture discipline**: controllers stay thin, services own logic, repositories stay dumb — a clean separation that's easy to defend in a design discussion.
- **Monolith-first decision making**: explaining *why* a monolith was the right call here, not microservices, shows architectural judgment.
- **Designing for auditability**: medical records and admissions are append-only/state-tracked rather than overwritten — mirroring real compliance needs in healthcare systems.
- **Containerization & cloud deployment**: packaging a 3-tier app (frontend/backend/DB) into Docker images and deploying to AWS demonstrates DevOps fundamentals beyond just coding.

---

## 📄 License

This project is open-sourced for learning and portfolio purposes. Feel free to fork and adapt.

---

### 🙋 About This Project

Built as a portfolio project to demonstrate backend system design, relational database modeling, and containerized full-stack deployment — created with the explicit goal of having concrete, defensible engineering decisions to discuss in technical interviews.


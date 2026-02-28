# Setup Instructions for MediTrack Clinic Management System

## Prerequisites

### System Requirements
- **Operating System**: Windows/macOS/Linux
- **RAM**: Minimum 2GB (4GB recommended)
- **Disk Space**: 500MB free space

### Required Software

#### 1. Java Development Kit (JDK)
MediTrack requires **JDK 21 or lower or higher** (JDK 1.8/11/17/21 recommended).

**Download JDK:**
- Visit [Oracle JDK Download](https://www.oracle.com/java/technologies/downloads/)

**Installation Steps:**

**Windows:**
1. Download JDK installer (.exe file)
2. Run the installer and follow the setup wizard
3. Choose installation path (e.g., `C:\Program Files\Java\jdk-21`)
4. Complete installation

#### 2. Verify Java Installation

Open terminal/command prompt and run:

```bash
java -version
javac -version
```

Expected output:
```
java 21.0.2 2024-01-16 LTS
```

#### 3. Set JAVA_HOME Environment Variable

**Windows:**
1. Right-click `This PC` or `My Computer` → Properties
2. Click `Advanced system settings`
3. Click `Environment Variables`
4. Click `New` under System variables
5. Variable name: `JAVA_HOME`
6. Variable value: `C:\Program Files\Java\jdk-21` (adjust path as needed)
7. Click OK and apply

Verify:
```bash
echo $JAVA_HOME
```

## Project Setup

### Step 1: Clone/Extract Repository

```bash
# If using git
git clone <repository-url>
cd meditrack-core-java

# Or extract ZIP file to a folder
cd meditrack-core-java
```

### Step 2: Compile and run the Application

**Main Application (Menu-Driven):**
```bash
# Windows
java -cp out com.airtribe.meditrack.Main

```

**With Sample Data:**
```bash
java -cp out com.airtribe.meditrack.Main --loadData
```

**Test Suite:**
```bash
# Windows
java -cp out com.airtribe.meditrack.test.TestRunner

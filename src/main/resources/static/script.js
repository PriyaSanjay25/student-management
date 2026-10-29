
const API_URL = "/students";

const form = document.getElementById("studentForm");
const table = document.getElementById("studentTable");
const message = document.getElementById("message");
const studentId = document.getElementById("studentId");
const formTitle = document.getElementById("formTitle");
const saveButton = document.getElementById("saveButton");
const cancelButton = document.getElementById("cancelButton");

async function loadStudents() {
    try {
        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error("Unable to load students");
        }

        const students = await response.json();
        table.replaceChildren();

        if (students.length === 0) {
            const row = table.insertRow();
            const cell = row.insertCell();
            cell.colSpan = 6;
            cell.textContent = "No students found.";
            return;
        }

        students.forEach(student => {
            const row = table.insertRow();

            [
                student.id,
                student.name,
                student.email,
                student.phone,
                student.course
            ].forEach(value => {
                const cell = row.insertCell();
                cell.textContent = value ?? "";
            });

            const actions = row.insertCell();

            const editButton = document.createElement("button");
            editButton.textContent = "Edit";
            editButton.addEventListener("click", () => editStudent(student));

            const deleteButton = document.createElement("button");
            deleteButton.textContent = "Delete";
            deleteButton.addEventListener("click", () => deleteStudent(student.id));

            actions.append(editButton, deleteButton);
        });
    } catch (error) {
        table.replaceChildren();
        const row = table.insertRow();
        const cell = row.insertCell();
        cell.colSpan = 6;
        cell.textContent = error.message;
        message.textContent = "Could not load student records.";
    }
}

form.addEventListener("submit", async function (event) {
    event.preventDefault();

    const id = studentId.value;

    const student = {
        name: document.getElementById("name").value.trim(),
        email: document.getElementById("email").value.trim(),
        phone: document.getElementById("phone").value.trim(),
        course: document.getElementById("course").value.trim()
    };

    if (Object.values(student).some(value => !value)) {
        message.textContent = "Please fill in all fields.";
        return;
    }

    try {
        const response = await fetch(
            id ? `${API_URL}/${id}` : API_URL,
            {
                method: id ? "PUT" : "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(student)
            }
        );

        if (!response.ok) {
            throw new Error(`Request failed: ${response.status}`);
        }

        message.textContent = id
            ? "Student updated successfully!"
            : "Student added successfully!";

        resetForm();
        await loadStudents();
    } catch (error) {
        message.textContent = error.message;
    }
});

function editStudent(student) {
    studentId.value = student.id;
    document.getElementById("name").value = student.name ?? "";
    document.getElementById("email").value = student.email ?? "";
    document.getElementById("phone").value = student.phone ?? "";
    document.getElementById("course").value = student.course ?? "";

    formTitle.textContent = "Edit Student";
    saveButton.textContent = "Update Student";
    cancelButton.hidden = false;

    form.scrollIntoView({ behavior: "smooth" });
}

function resetForm() {
    form.reset();
    studentId.value = "";
    formTitle.textContent = "Add New Student";
    saveButton.textContent = "Add Student";
    cancelButton.hidden = true;
}

async function deleteStudent(id) {
    if (!confirm("Are you sure you want to delete this student?")) {
        return;
    }

    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: "DELETE"
        });

        if (!response.ok) {
            throw new Error(`Delete failed: ${response.status}`);
        }

        message.textContent = "Student deleted successfully!";
        await loadStudents();
    } catch (error) {
        message.textContent = error.message;
    }
}

cancelButton.addEventListener("click", resetForm);
document.getElementById("refreshButton")
    .addEventListener("click", loadStudents);

loadStudents();

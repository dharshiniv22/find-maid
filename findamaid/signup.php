<?php
header('Content-Type: application/json');
include __DIR__ . '/db.php';

$response = [
    "status" => false,
    "message" => "Unknown error"
];

// Check required fields
if (
    isset($_POST['name'], $_POST['username'], $_POST['email'],
    $_POST['password'], $_POST['contact_number'])
) {
    $name     = trim($_POST['name']);
    $username = trim($_POST['username']);
    $email    = trim($_POST['email']);
    $password = password_hash($_POST['password'], PASSWORD_DEFAULT);
    $contact  = trim($_POST['contact_number']);
    $imagePath = null;

    // ✅ Profile image upload (optional)
    if (isset($_FILES['profile_image']) && $_FILES['profile_image']['error'] == 0) {
        $uploadDir = __DIR__ . '/uploads/';
        if (!is_dir($uploadDir)) {
            mkdir($uploadDir, 0777, true);
        }

        $ext = pathinfo($_FILES['profile_image']['name'], PATHINFO_EXTENSION);
        $base = pathinfo($_FILES['profile_image']['name'], PATHINFO_FILENAME);
        $cleanName = preg_replace('/[^a-zA-Z0-9_-]/', '_', $base);
        $imageName = uniqid() . '_' . $cleanName . '.' . $ext;
        $targetPath = $uploadDir . $imageName;

        if (move_uploaded_file($_FILES['profile_image']['tmp_name'], $targetPath)) {
            $imagePath = "uploads/" . $imageName;
        } else {
            $response['message'] = "Failed to upload image";
            echo json_encode($response);
            exit;
        }
    }

    // ✅ Check uniqueness
    $checkSql = "SELECT * FROM signup WHERE username = ? OR email = ? OR contact_number = ?";
    $checkStmt = $conn->prepare($checkSql);
    $checkStmt->bind_param("sss", $username, $email, $contact);
    $checkStmt->execute();
    $result = $checkStmt->get_result();

    if ($result->num_rows > 0) {
        $existing = $result->fetch_assoc();
        if ($existing['username'] === $username) {
            $response['message'] = "Username already exists";
        } elseif ($existing['email'] === $email) {
            $response['message'] = "Email ID already exists";
        } elseif ($existing['contact_number'] === $contact) {
            $response['message'] = "Contact number already exists";
        }
        echo json_encode($response);
        exit;
    }

    // ✅ Insert into database
    $stmt = $conn->prepare(
        "INSERT INTO signup (name, username, email, password, contact_number, profile_image)
        VALUES (?, ?, ?, ?, ?, ?)"
    );
    $stmt->bind_param("ssssss", $name, $username, $email, $password, $contact, $imagePath);

    if ($stmt->execute()) {
        $response['status'] = true;
        $response['message'] = "Signup successful";
        $response['user_id'] = $stmt->insert_id;
    } else {
        $response['message'] = "Signup failed: " . $stmt->error;
    }
} else {
    $response['message'] = "Missing required fields";
}

echo json_encode($response);
$conn->close();
?>

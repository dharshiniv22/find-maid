<?php
header('Content-Type: application/json');
ini_set('display_errors', 1);
error_reporting(E_ALL);

include __DIR__ . '/db.php';

// Default response
$response = [
    "success" => false,
    "message" => "Unknown error occurred"
];

// Check if username is provided
if (!isset($_POST['username']) || empty(trim($_POST['username']))) {
    $response['message'] = "Username is required";
    echo json_encode($response);
    exit;
}

$username = trim($_POST['username']);

// Prepare SQL query
$sql = "SELECT id, name, email, contact_number, username, profile_image FROM signup WHERE username = ?";
$stmt = $conn->prepare($sql);

if (!$stmt) {
    $response['message'] = "SQL Error: " . $conn->error;
    echo json_encode($response);
    exit;
}

// Bind and execute
$stmt->bind_param("s", $username);

if ($stmt->execute()) {
    $result = $stmt->get_result();
    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();

        // Only return the filename for profile image
        $profileImage = $row['profile_image'] ? basename($row['profile_image']) : null;

        $response = [
            "success" => true,
            "data" => [
                "user_id" => $row['id'],           // ✅ Added user_id
                "name" => $row['name'],
                "email" => $row['email'],
                "contact_number" => $row['contact_number'],
                "username" => $row['username'],
                "profile_image" => $profileImage
            ]
        ];
    } else {
        $response['message'] = "User not found";
    }
} else {
    $response['message'] = "Query failed: " . $stmt->error;
}

$stmt->close();
$conn->close();

// Return JSON response
echo json_encode($response);

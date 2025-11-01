<?php
header('Content-Type: application/json');

// Enable full error reporting
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

// Optional: Custom error handler to return JSON on runtime warnings/errors
set_error_handler(function($errno, $errstr, $errfile, $errline) {
    echo json_encode([
        "status" => false,
        "message" => "$errstr in $errfile on line $errline"
    ]);
    exit;
});

include __DIR__ . '/db.php';

$response = [
    "status" => false,
    "message" => "Unknown error",
    "data" => null
];

// ✅ Required input fields
if (isset($_POST['username'], $_POST['password'])) {
    $username = trim($_POST['username']);
    $password = $_POST['password'];

    // ✅ Prepared statement for security (avoid SQL injection)
    $stmt = $conn->prepare("SELECT * FROM signup WHERE username = ? OR email = ?");
    $stmt->bind_param("ss", $username, $username);
    $stmt->execute();
    $result = $stmt->get_result();

    // ✅ If user found
    if ($result && $result->num_rows === 1) {
        $row = $result->fetch_assoc();

        if (password_verify($password, $row['password'])) {
            $response['status'] = true;
            $response['message'] = "Login successful";
            $response['data'] = [
                "id" => $row['id'],
                "name" => $row['name'],
                "username" => $row['username'],
                "email" => $row['email'],
                "contact_number" => $row['contact_number'],
                "profile_image" => $row['profile_image'] ? "http://localhost/findmaid/" . $row['profile_image'] : null
            ];
        } else {
            $response['message'] = "Invalid password";
        }
    } else {
        $response['message'] = "User not found";
    }

    $stmt->close();
} else {
    $response['message'] = "Missing required fields (username/password)";
}

echo json_encode($response);
$conn->close();
?>

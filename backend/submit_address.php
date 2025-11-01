<?php
header('Content-Type: application/json');
ini_set('display_errors', 1);
error_reporting(E_ALL);

// Database connection
$host = "localhost";
$user = "root";
$password = "";
$database = "findamaid";

$conn = new mysqli($host, $user, $password, $database);
if ($conn->connect_error) {
    echo json_encode(["status" => false, "message" => "Connection failed: " . $conn->connect_error]);
    exit;
}

// Get POST data
$user_id        = $_POST['user_id'] ?? null;
$maid_post_id   = $_POST['maid_post_id'] ?? null;
$name           = $_POST['name'] ?? '';
$phone_number   = $_POST['phone_number'] ?? '';
$address        = $_POST['address'] ?? null;
$pincode        = $_POST['pincode'] ?? null;
$latitude       = $_POST['latitude'] ?? null;
$longitude      = $_POST['longitude'] ?? null;
$preferred_time = $_POST['preferred_time'] ?? null;
$notes          = $_POST['notes'] ?? null;

// Validate required fields
if (!$user_id || !$maid_post_id || !$address || !$pincode || !$name || !$phone_number || !$preferred_time) {
    echo json_encode([
        "status" => false,
        "message" => "Missing required fields"
    ]);
    exit;
}

// Insert into user_addresses
$sql = "INSERT INTO user_addresses 
        (user_id, name, phone_number, maid_post_id, address, pincode, latitude, longitude, preferred_time, notes)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

$stmt = $conn->prepare($sql);
if (!$stmt) {
    echo json_encode(["status" => false, "message" => "Database error: " . $conn->error]);
    exit;
}

$stmt->bind_param(
    "ississssss",
    $user_id,
    $name,
    $phone_number,
    $maid_post_id,
    $address,
    $pincode,
    $latitude,
    $longitude,
    $preferred_time,
    $notes
);

if ($stmt->execute()) {
    echo json_encode([
        "status" => true,
        "message" => "Booking request submitted successfully.",
        "request_id" => $stmt->insert_id
    ]);
} else {
    echo json_encode(["status" => false, "message" => "Insert failed: " . $stmt->error]);
}

$stmt->close();
$conn->close();
?>

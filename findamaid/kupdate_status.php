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

// POST parameters
$id = isset($_POST['id']) ? intval($_POST['id']) : 0;
$status = isset($_POST['status']) ? strtolower(trim($_POST['status'])) : '';

if ($id <= 0 || !in_array($status, ['pending', 'accepted', 'rejected'])) {
    echo json_encode(["status" => false, "message" => "Invalid parameters"]);
    exit;
}

$stmt = $conn->prepare("UPDATE user_addresses SET status = ? WHERE id = ?");
$stmt->bind_param("si", $status, $id);

if ($stmt->execute()) {
    $res = $conn->query("SELECT * FROM user_addresses WHERE id = $id");
    $row = $res->fetch_assoc();
    echo json_encode(["status" => true, "message" => "Status updated successfully", "data" => $row]);
} else {
    echo json_encode(["status" => false, "message" => "Failed to update status"]);
}

$stmt->close();
$conn->close();

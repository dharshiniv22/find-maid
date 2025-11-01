<?php
header('Content-Type: application/json');
include 'db.php';

$id = isset($_POST['request_id']) ? intval($_POST['request_id']) : 0;
$status = isset($_POST['status']) ? strtolower(trim($_POST['status'])) : '';

if ($id <= 0 || !in_array($status, ['pending','accepted','rejected'])) {
    echo json_encode(["status" => false, "message" => "Invalid parameters"]);
    exit;
}

$stmt = $conn->prepare("SELECT * FROM user_addresses WHERE id = ?");
$stmt->bind_param("i", $id);
$stmt->execute();
$res = $stmt->get_result();
if ($res->num_rows === 0) {
    echo json_encode(["status" => false, "message" => "No record found for the given request_id"]);
    exit;
}

$stmt = $conn->prepare("UPDATE user_addresses SET status = ? WHERE id = ?");
$stmt->bind_param("si", $status, $id);
if ($stmt->execute()) {
    $row = $conn->query("SELECT * FROM user_addresses WHERE id = $id")->fetch_assoc();
    echo json_encode(["status" => true, "message" => "Status updated successfully", "data" => $row]);
} else {
    echo json_encode(["status" => false, "message" => "Failed to update status"]);
}
?>

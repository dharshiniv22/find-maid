<?php
header('Content-Type: application/json');
error_reporting(E_ALL);
ini_set('display_errors', 1);

include __DIR__ . '/db.php'; // Ensure this contains $conn

// Input data from POST
$booking_id = $_POST['id'] ?? null; // request_id
$new_status = $_POST['status'] ?? null;

$allowed_statuses = ['accepted', 'rejected'];

// Validate required fields
if (!$booking_id || !$new_status) {
    echo json_encode([
        "status" => false,
        "message" => "Missing required parameters"
    ]);
    exit;
}

if (!in_array($new_status, $allowed_statuses)) {
    echo json_encode([
        "status" => false,
        "message" => "Invalid status value"
    ]);
    exit;
}

// Step 1: Confirm booking exists in booking_requests
$checkBooking = "SELECT id FROM booking_requests WHERE id = ?";
$stmt = $conn->prepare($checkBooking);
$stmt->bind_param("i", $booking_id);
$stmt->execute();
$res = $stmt->get_result();

if ($res->num_rows === 0) {
    echo json_encode([
        "status" => false,
        "message" => "Invalid booking ID"
    ]);
    $stmt->close();
    exit;
}
$stmt->close();

// Step 2: Update the booking status
$updateQuery = "UPDATE booking_requests SET status = ? WHERE id = ?";
$updateStmt = $conn->prepare($updateQuery);
$updateStmt->bind_param("si", $new_status, $booking_id);

if ($updateStmt->execute()) {
    echo json_encode([
        "status" => true,
        "message" => "Booking status updated to '$new_status'"
    ]);
} else {
    echo json_encode([
        "status" => false,
        "message" => "Failed to update booking status",
        "error" => $updateStmt->error
    ]);
}

$updateStmt->close();
$conn->close();
?>

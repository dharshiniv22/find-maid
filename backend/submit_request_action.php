<?php
header('Content-Type: application/json');
include "db.php"; // your DB connection file

$response = [];

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $username = $_POST['username'] ?? '';
    $action = $_POST['action'] ?? '';

    if ($username !== '' && in_array($action, ['Accept', 'Reject'])) {
        // Update status in your request table
        $stmt = $conn->prepare("UPDATE maid_requests SET status = ? WHERE username = ?");
        $stmt->bind_param("ss", $action, $username);

        if ($stmt->execute()) {
            $response['status'] = "success";
            $response['message'] = "Request $action for $username";
        } else {
            $response['status'] = "error";
            $response['message'] = "Failed to update request.";
        }

        $stmt->close();
    } else {
        $response['status'] = "error";
        $response['message'] = "Invalid username or action.";
    }
} else {
    $response['status'] = "error";
    $response['message'] = "Invalid request method.";
}

echo json_encode($response);
?>

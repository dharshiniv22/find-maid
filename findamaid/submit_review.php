<?php
header('Content-Type: application/json');

// Database connection
$host = "localhost";
$user = "root";
$password = "";
$db = "findamaid";

$conn = new mysqli($host, $user, $password, $db);

// Check DB connection
if ($conn->connect_error) {
    echo json_encode(["status" => false, "message" => "Connection failed"]);
    exit;
}

// Get POST data
$maid_post_id = $_POST['maid_post_id'] ?? null;
$user_id = $_POST['user_id'] ?? null;
$user_name = $_POST['user_name'] ?? 'Anonymous';
$rating = $_POST['rating'] ?? null;
$comment = $_POST['comment'] ?? '';

// Validate required fields
if ($maid_post_id && $user_id && $rating) {

    // Prepare SQL query
    $stmt = $conn->prepare("INSERT INTO reviews (maid_post_id, user_id, maid_name, rating, comment) VALUES (?, ?, ?, ?, ?)");

    // Handle prepare error
    if (!$stmt) {
        echo json_encode(["status" => false, "message" => "Prepare failed: " . $conn->error]);
        exit;
    }

    // Bind parameters
    $stmt->bind_param("iisds", $maid_post_id, $user_id, $user_name, $rating, $comment);

    // Execute and respond
    if ($stmt->execute()) {
        echo json_encode(["status" => true, "message" => "Review submitted successfully"]);
    } else {
        echo json_encode(["status" => false, "message" => "Failed to insert review"]);
    }

    $stmt->close();
} else {
    echo json_encode(["status" => false, "message" => "Missing required fields"]);
}

$conn->close();
?>

<?php
header('Content-Type: application/json');

// DB connection
$host = "localhost";
$user = "root";
$password = "";
$db = "findamaid";

$conn = new mysqli($host, $user, $password, $db);

if ($conn->connect_error) {
    echo json_encode(["status" => "error", "message" => "Connection failed"]);
    exit;
}

// Get maid_post_id from GET
$maid_post_id = isset($_GET['maid_post_id']) ? intval($_GET['maid_post_id']) : 0;

if ($maid_post_id <= 0) {
    echo json_encode(["status" => "error", "message" => "Maid post ID is required"]);
    exit;
}

// Fetch all review fields
$stmt = $conn->prepare("
    SELECT * 
    FROM reviews 
    WHERE maid_post_id = ?
    ORDER BY id DESC
");
$stmt->bind_param("i", $maid_post_id);
$stmt->execute();
$result = $stmt->get_result();

$reviews = [];
while ($row = $result->fetch_assoc()) {
    $reviews[] = $row;  // returns all columns
}

echo json_encode([
    "status" => "success",
    "reviews" => $reviews
]);

$stmt->close();
$conn->close();
?>

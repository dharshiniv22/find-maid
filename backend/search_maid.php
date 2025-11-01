<?php
header('Content-Type: application/json');
include __DIR__ . '/db.php';

ini_set('display_errors', 1);
error_reporting(E_ALL);

$filter = isset($_GET['filter']) ? trim($_GET['filter']) : '';

$response = [
    "status" => false,
    "data" => [],
    "message" => ""
];

if ($filter === '') {
    $response['message'] = "Missing or empty search filter";
    echo json_encode($response);
    exit;
}

// Base URL for images
$baseUrl = "https://12l7sjb3-80.inc1.devtunnels.ms/findamaid/uploads/";

$sql = "SELECT id, user_id as maid_id, name, age, location, category, expected_salary, working_hours, photo_url 
        FROM maid_posts 
        WHERE location LIKE ? OR category LIKE ? OR name LIKE ?";

$like = '%' . $filter . '%';

$stmt = $conn->prepare($sql);
if ($stmt) {
    $stmt->bind_param("sss", $like, $like, $like);
    $stmt->execute();
    $result = $stmt->get_result();

    $posts = [];

    while ($row = $result->fetch_assoc()) {
        // Use defaultmaid.jpg if photo_url is empty
        $row['photo_url'] = $row['photo_url']
            ? $baseUrl . $row['photo_url']
            : $baseUrl . "defaultmaid.jpg";

        $posts[] = $row;
    }

    $response['status'] = true;
    $response['data'] = $posts;
} else {
    $response['message'] = "Database error: " . $conn->error;
}

echo json_encode($response);
$conn->close();
?>

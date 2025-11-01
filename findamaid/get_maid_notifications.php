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
    echo json_encode(["status" => false, "message" => "Database connection failed: " . $conn->connect_error]);
    exit;
}

// Base URL for images
$base_url = "https://12l7sjb3-80.inc1.devtunnels.ms/findamaid/uploads/";

// Get owner_id (from POST or GET)
$owner_id = isset($_POST['owner_id']) ? intval($_POST['owner_id']) : (isset($_GET['owner_id']) ? intval($_GET['owner_id']) : 0);

if ($owner_id <= 0) {
    echo json_encode(["status" => false, "message" => "Missing or invalid owner_id"]);
    exit;
}

// Prepare the query
$query = "
SELECT 
    ua.id AS request_id,
    ua.address,
    ua.pincode,
    ua.status,
    ua.submitted_at,
    ua.created_at,
    mp.id AS maid_post_id,
    mp.name AS maid_name,
    mp.age,
    mp.location,
    mp.experience,
    mp.phone_number,
    mp.expected_salary,
    mp.working_hours,
    mp.category,
    mp.photo AS maid_photo,
    s.name AS maid_owner_name,
    s.contact_number AS maid_owner_contact,
    s.profile_image AS maid_owner_profile
FROM user_addresses ua
JOIN maid_posts mp ON ua.maid_post_id = mp.id
JOIN signup s ON mp.user_id = s.id
WHERE ua.user_id = ?
ORDER BY ua.created_at DESC
";

$stmt = $conn->prepare($query);

if (!$stmt) {
    echo json_encode(["status" => false, "message" => "SQL prepare failed: " . $conn->error]);
    exit;
}

$stmt->bind_param("i", $owner_id);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows === 0) {
    echo json_encode(["status" => true, "message" => "No notifications found", "notifications" => []]);
    exit;
}

$notifications = [];
while ($row = $result->fetch_assoc()) {
    $notifications[] = [
        "request_id" => $row["request_id"],
        "address" => $row["address"],
        "pincode" => $row["pincode"],
        "status" => $row["status"],
        "submitted_at" => $row["submitted_at"],
        "maid_post_id" => $row["maid_post_id"],
        "maid_name" => $row["maid_name"],
        "age" => $row["age"],
        "location" => $row["location"],
        "experience" => $row["experience"],
        "phone_number" => $row["phone_number"],
        "expected_salary" => $row["expected_salary"],
        "working_hours" => $row["working_hours"],
        "category" => $row["category"],
        "maid_photo" => $row["maid_photo"],
        "maid_image_url" => $row["maid_photo"] ? $base_url . $row["maid_photo"] : null,
        "maid_owner_name" => $row["maid_owner_name"],
        "maid_owner_contact" => $row["maid_owner_contact"],
        "maid_owner_profile" => $row["maid_owner_profile"] ? $base_url . $row["maid_owner_profile"] : null
    ];
}

echo json_encode([
    "status" => true,
    "message" => "Maid notifications retrieved successfully",
    "count" => count($notifications),
    "notifications" => $notifications
], JSON_PRETTY_PRINT);

$stmt->close();
$conn->close();

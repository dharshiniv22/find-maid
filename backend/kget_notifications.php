<?php
header('Content-Type: application/json');
include 'db.php';

// Enable error reporting for debugging
ini_set('display_errors', 1);
error_reporting(E_ALL);

$user_id = $_GET['user_id'] ?? '';
if (empty($user_id)) {
    echo json_encode([
        "status" => false,
        "message" => "Missing user_id",
        "data" => []
    ]);
    exit;
}

// Fetch notifications for this maid owner
$sql = "
    SELECT 
        ua.id,
        ua.user_id AS requester_id,
        ua.name AS requester_name,
        ua.phone_number,              
        ua.address,
        ua.pincode,
        ua.preferred_time,
        ua.notes,
        ua.status,
        ua.latitude,
        ua.longitude,
        ua.submitted_at,
        mp.id AS maid_post_id,
        mp.user_id AS maid_user_id,
        mp.name AS maid_name,
        mp.category AS maid_category,
        mp.location AS maid_location,
        CONCAT('https://12l7sjb3-80.inc1.devtunnels.ms/findamaid/uploads/', mp.photo) AS photo_full_url
    FROM user_addresses ua
    JOIN maid_posts mp ON ua.maid_post_id = mp.id
    WHERE mp.user_id = ?
    ORDER BY ua.submitted_at DESC
";

$stmt = $conn->prepare($sql);
$stmt->bind_param("i", $user_id);
$stmt->execute();
$result = $stmt->get_result();

$data = [];
while ($row = $result->fetch_assoc()) {
    $data[] = [
        "id" => (int)$row['id'],
        "requester_id" => (int)$row['requester_id'],
        "name" => $row['requester_name'],
        "phone_number" => $row['phone_number'],
        "maid_post_id" => (int)$row['maid_post_id'],
        "address" => $row['address'],
        "pincode" => $row['pincode'],
        "preferred_time" => $row['preferred_time'],
        "notes" => $row['notes'],
        "status" => $row['status'],
        "latitude" => $row['latitude'],
        "longitude" => $row['longitude'],
        "maid_user_id" => (int)$row['maid_user_id'],
        "maid_name" => $row['maid_name'],
        "maid_category" => $row['maid_category'],
        "maid_location" => $row['maid_location'],
        "photo_full_url" => $row['photo_full_url']
    ];
}

$response = [
    "status" => true,
    "message" => "Notifications fetched successfully",
    "count" => count($data),
    "data" => $data
];

echo json_encode($response, JSON_PRETTY_PRINT);
?>

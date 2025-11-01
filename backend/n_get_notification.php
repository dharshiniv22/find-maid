<?php
header('Content-Type: application/json');
ini_set('display_errors', 1);
error_reporting(E_ALL);

include __DIR__ . '/db.php'; // your DB connection file

// Get user_id from POST
$user_id = $_POST['user_id'] ?? '';

if (empty($user_id)) {
    echo json_encode([
        "status" => false,
        "message" => "Missing user_id",
        "notifications" => []
    ]);
    exit;
}

// Fetch only this user's notifications
$sql = "
    SELECT ua.id AS request_id, mp.name AS maid_name, mp.category, 
           ua.submitted_at AS request_date, ua.status, mp.photo_url
    FROM user_addresses ua
    JOIN maid_posts mp ON ua.maid_post_id = mp.id
    WHERE ua.user_id = ?
    ORDER BY ua.id DESC
";

$stmt = $conn->prepare($sql);
$stmt->bind_param("i", $user_id);
$stmt->execute();
$result = $stmt->get_result();

$notifications = [];

while ($row = $result->fetch_assoc()) {
    $imageFile = !empty($row['photo_url']) ? $row['photo_url'] : 'defaultmaid.jpg';
    $imageUrl = "https://12l7sjb3-80.inc1.devtunnels.ms/findamaid/uploads/" . urlencode($imageFile);

    $notifications[] = [
        "requestId"   => $row['request_id'],
        "maidName"    => $row['maid_name'],
        "category"    => $row['category'],
        "requestDate" => $row['request_date'],
        "status"      => $row['status'],
        "photo_url"   => $imageUrl
    ];
}

echo json_encode([
    "status" => !empty($notifications),
    "notifications" => $notifications,
    "message" => !empty($notifications) ? "Notifications found" : "No notifications found"
], JSON_PRETTY_PRINT);

$stmt->close();
$conn->close();
?>

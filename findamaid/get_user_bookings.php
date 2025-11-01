<?php
header('Content-Type: application/json');
include 'db.php';

$user_id = $_GET['user_id'] ?? null;
if (!$user_id) {
    echo json_encode(["status" => false, "message" => "User ID required"]);
    exit;
}

$query = "
    SELECT ua.id AS request_id, ua.address, ua.pincode, ua.status, ua.created_at,
           m.name AS maid_name, m.category, m.photo_url
    FROM user_addresses ua
    JOIN maid_posts m ON ua.maid_post_id = m.id
    WHERE ua.user_id = ?
    ORDER BY ua.created_at DESC
";

$stmt = $conn->prepare($query);
$stmt->bind_param("i", $user_id);
$stmt->execute();
$result = $stmt->get_result();

$data = [];
while ($row = $result->fetch_assoc()) {
    $data[] = [
        "request_id" => $row['request_id'],
        "maid_name" => $row['maid_name'],
        "category" => $row['category'],
        "address" => $row['address'],
        "pincode" => $row['pincode'],
        "status" => $row['status'],
        "maid_photo" => $row['photo_url'] ? "http://localhost/findmaid/" . $row['photo_url'] : null,
        "requested_at" => $row['created_at']
    ];
}

echo json_encode(["status" => true, "bookings" => $data]);
?>

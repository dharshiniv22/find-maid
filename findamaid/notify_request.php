<?php
header('Content-Type: application/json');
ini_set('display_errors', 1);
error_reporting(E_ALL);

// DB connection
$host = "localhost";
$user = "root";
$password = "";
$database = "findamaid";
$conn = new mysqli($host, $user, $password, $database);

if ($conn->connect_error) {
    echo json_encode(["status" => false, "message" => "Connection failed: " . $conn->connect_error]);
    exit;
}

// Get user_id
$user_id = isset($_GET['user_id']) ? intval($_GET['user_id']) : (isset($_POST['user_id']) ? intval($_POST['user_id']) : 0);

if ($user_id <= 0) {
    echo json_encode(["status" => false, "message" => "Invalid or missing user_id"]);
    exit;
}

// Determine if user is a maid
$checkMaid = $conn->prepare("SELECT id FROM maid_posts WHERE user_id = ? LIMIT 1");
$checkMaid->bind_param("i", $user_id);
$checkMaid->execute();
$checkMaid->store_result();
$isMaid = $checkMaid->num_rows > 0;
$checkMaid->close();

if ($isMaid) {
    // If maid, show who booked them
    $query = "
        SELECT
            ua.id AS request_id,
            ua.address,
            ua.pincode,
            ua.status,
            ua.created_at AS requested_at,

            booker.name AS booker_name,
            booker.profile_image AS booker_photo,

            mp.id AS maid_post_id,
            mp.name AS maid_name,
            mp.photo AS maid_photo,
            mp.location AS maid_location,
            mp.expected_salary,
            mp.working_hours,
            mp.category AS maid_category

        FROM user_addresses ua
        JOIN signup booker ON ua.user_id = booker.id
        JOIN maid_posts mp ON ua.maid_post_id = mp.id
        WHERE mp.user_id = ?
        ORDER BY ua.created_at DESC
    ";
} else {
    // If user, show bookings they made
    $query = "
        SELECT
            ua.id AS request_id,
            ua.address,
            ua.pincode,
            ua.status,
            ua.created_at AS requested_at,

            mp.id AS maid_post_id,
            mp.name AS maid_name,
            mp.photo AS maid_photo,
            mp.location AS maid_location,
            mp.expected_salary,
            mp.working_hours,
            mp.category AS maid_category,

            owner.name AS maid_owner_name,
            owner.profile_image AS maid_owner_photo

        FROM user_addresses ua
        JOIN maid_posts mp ON ua.maid_post_id = mp.id
        JOIN signup owner ON mp.user_id = owner.id
        WHERE ua.user_id = ?
        ORDER BY ua.created_at DESC
    ";
}

$stmt = $conn->prepare($query);
$stmt->bind_param("i", $user_id);
$stmt->execute();
$result = $stmt->get_result();

$notifications = [];

while ($row = $result->fetch_assoc()) {
    $item = [
        "request_id" => $row['request_id'],
        "address" => $row['address'],
        "pincode" => $row['pincode'],
        "status" => $row['status'],
        "requested_at" => $row['requested_at'],
        "maid_post" => [
            "id" => $row['maid_post_id'],
            "name" => $row['maid_name'],
            "photo" => $row['maid_photo'] ? "http://localhost/findamaid/" . $row['maid_photo'] : null,
            "location" => $row['maid_location'],
            "expected_salary" => $row['expected_salary'],
            "working_hours" => $row['working_hours'],
            "category" => $row['maid_category']
        ]
    ];

    if ($isMaid) {
        $item["booker"] = [
            "name" => $row['booker_name'],
            "photo" => $row['booker_photo'] ? "http://localhost/findamaid/" . $row['booker_photo'] : null
        ];
    } else {
        $item["maid_owner"] = [
            "name" => $row['maid_owner_name'],
            "photo" => $row['maid_owner_photo'] ? "http://localhost/findamaid/" . $row['maid_owner_photo'] : null
        ];
    }

    $notifications[] = $item;
}

echo json_encode([
    "status" => true,
    "role" => $isMaid ? "maid" : "user",
    "user_id" => $user_id,
    "total" => count($notifications),
    "notifications" => $notifications
]);

$stmt->close();
$conn->close();

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

// Get owner_id from GET or POST
$owner_id = isset($_GET['owner_id']) ? intval($_GET['owner_id']) : (isset($_POST['owner_id']) ? intval($_POST['owner_id']) : 0);

if ($owner_id <= 0) {
    echo json_encode(["status" => false, "message" => "Invalid or missing owner_id"]);
    exit;
}

// Prepare and execute query securely
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
        mp.category AS maid_category,

        maidowner.name AS owner_name,
        maidowner.profile_image AS owner_photo

    FROM user_addresses ua
    JOIN signup booker ON ua.user_id = booker.id
    JOIN maid_posts mp ON ua.maid_post_id = mp.id
    JOIN signup maidowner ON mp.user_id = maidowner.id
    WHERE maidowner.id = ?
    ORDER BY ua.created_at DESC
";

$stmt = $conn->prepare($query);
$stmt->bind_param("i", $owner_id);
$stmt->execute();
$result = $stmt->get_result();

$bookings = [];

while ($row = $result->fetch_assoc()) {
    $bookings[] = [
        "request_id" => $row['request_id'],
        "address" => $row['address'],
        "pincode" => $row['pincode'],
        "status" => $row['status'],
        "requested_at" => $row['requested_at'],

        "booker" => [
            "name" => $row['booker_name'],
            "photo" => $row['booker_photo'] ? "https://4n6j9p4w-80.inc1.devtunnels.ms/findamaid/" . $row['booker_photo'] : null
        ],

        "maid_post" => [
            "id" => $row['maid_post_id'],
            "name" => $row['maid_name'],
            "photo" => $row['maid_photo'] ? "https://4n6j9p4w-80.inc1.devtunnels.ms/findamaid/" . $row['maid_photo'] : null,
            "location" => $row['maid_location'],
            "expected_salary" => $row['expected_salary'],
            "working_hours" => $row['working_hours'],
            "category" => $row['maid_category']
        ],

        "maid_owner" => [
            "name" => $row['owner_name'],
            "photo" => $row['owner_photo'] ? "https://4n6j9p4w-80.inc1.devtunnels.ms/findamaid/" . $row['owner_photo'] : null
        ]
    ];
}

echo json_encode([
    "status" => true,
    "owner_id" => $owner_id,
    "total_bookings" => count($bookings),
    "bookings" => $bookings
]);

$stmt->close();
$conn->close();

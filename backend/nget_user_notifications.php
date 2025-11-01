<?php
header('Content-Type: application/json');
include 'db.php';

// Get user_id from POST
$user_id = $_POST['user_id'] ?? '';

if (empty($user_id)) {
    echo json_encode([
        "status" => false,
        "message" => "User ID required"
    ]);
    exit;
}

// Query to join user_addresses and maid_posts
$query = "
    SELECT 
        ua.id AS request_id,
        ua.status,
        ua.submitted_at AS request_date,
        mp.name AS maid_name,
        mp.category,
        mp.photo AS original_photo,
        mp.photo_url AS photo_url -- clean URL column
    FROM user_addresses ua
    INNER JOIN maid_posts mp ON ua.maid_post_id = mp.id
    WHERE ua.user_id = ?
    ORDER BY ua.submitted_at DESC
";

$stmt = $conn->prepare($query);

if (!$stmt) {
    echo json_encode([
        "status" => false,
        "message" => "SQL Error: " . $conn->error
    ]);
    exit;
}

// Bind user_id and execute
$stmt->bind_param("i", $user_id);
$stmt->execute();
$result = $stmt->get_result();

if ($result && $result->num_rows > 0) {
    $notifications = [];

    while ($row = $result->fetch_assoc()) {

        // If photo_url is empty, generate a fallback clean URL
        $photo_url = $row['photo_url'];
        if (empty($photo_url) && !empty($row['original_photo'])) {
            // Replace spaces with _ and remove special chars if needed
            $clean_name = str_replace(' ', '_', $row['original_photo']);
            $photo_url = "https://12l7sjb3-80.inc1.devtunnels.ms/findamaid/uploads/" . $clean_name;
        }

        $notifications[] = [
            "requestId"   => $row['request_id'],
            "maidName"    => $row['maid_name'],
            "category"    => $row['category'],
            "requestDate" => $row['request_date'],
            "status"      => $row['status'],
            "photo_url"   => $photo_url // clean URL
        ];
    }

    echo json_encode([
        "status" => true,
        "notifications" => $notifications
    ]);
} else {
    echo json_encode([
        "status" => false,
        "notifications" => []
    ]);
}

$stmt->close();
$conn->close();
?>

<?php
// assign_maid_user_ids.php

// Enable error reporting
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

include 'db.php';

$query = "
    SELECT mp.id AS maid_id, mp.name AS maid_name, s.id AS user_id, s.name AS user_name
    FROM maid_posts mp
    JOIN signup s ON mp.name = s.name
    WHERE mp.user_id IS NULL
";

$result = $conn->query($query);

if ($result && $result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $maid_id = (int)$row['maid_id'];
        $user_id = (int)$row['user_id'];

        $update = $conn->prepare("UPDATE maid_posts SET user_id = ? WHERE id = ?");
        $update->bind_param("ii", $user_id, $maid_id);

        if ($update->execute()) {
            echo "✅ Updated maid_post ID {$maid_id} with user_id {$user_id} ({$row['user_name']})<br>";
        } else {
            echo "❌ Failed to update maid_post ID {$maid_id}<br>";
        }

        $update->close();
    }
} else {
    echo "ℹ️ No matching maid_posts with NULL user_id found.";
}

$conn->close();

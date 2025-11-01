<?php
include 'con.php';

$maid_post_id = $_GET['maid_post_id']; // maid profile id

$result = $con->query("SELECT id, message, created_at 
                       FROM notifications 
                       WHERE maid_post_id = $maid_post_id AND is_read = 0
                       ORDER BY created_at DESC");

$notifications = [];
while ($row = $result->fetch_assoc()) {
    $notifications[] = $row;
}

echo json_encode($notifications);
?>

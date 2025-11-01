<?php
include 'con.php';

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $notification_id = $_POST['notification_id'];

    $stmt = $con->prepare("UPDATE notifications SET is_read = 1 WHERE id = ?");
    $stmt->bind_param("i", $notification_id);
    $stmt->execute();

    echo "Notification marked as read!";
}
?>

<?php
include 'con.php';

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $user_id = $_POST['user_id'];
    $maid_post_id = $_POST['maid_post_id'];
    $address = $_POST['address'];
    $pincode = $_POST['pincode'];

    // Save booking
    $stmt = $con->prepare("INSERT INTO booking (user_id, maid_post_id, address, pincode, status, submitted_at, created_at)
                           VALUES (?, ?, ?, ?, 'pending', NOW(), NOW())");
    $stmt->bind_param("iiss", $user_id, $maid_post_id, $address, $pincode);
    $stmt->execute();

    // Save notification
    $message = "You got a new booking at $address";
    $stmt2 = $con->prepare("INSERT INTO notifications (maid_post_id, user_id, message) VALUES (?, ?, ?)");
    $stmt2->bind_param("iis", $maid_post_id, $user_id, $message);
    $stmt2->execute();

    echo "Booking submitted & notification sent!";
}
?>

<?php
// delete_account.php
session_start();
require 'db.php'; // include your database connection

if (!isset($_SESSION['user_id'])) {
    echo json_encode(["status" => "error", "message" => "User not logged in"]);
    exit;
}

$user_id = $_SESSION['user_id'];

// Delete related data first (because of foreign key constraints)
$conn->begin_transaction();

try {
    // Delete user reviews
    $stmt = $conn->prepare("DELETE FROM reviews WHERE user_id = ?");
    $stmt->bind_param("i", $user_id);
    $stmt->execute();

    // Delete user maid posts
    $stmt = $conn->prepare("DELETE FROM maid_posts WHERE user_id = ?");
    $stmt->bind_param("i", $user_id);
    $stmt->execute();

    // Delete user addresses
    $stmt = $conn->prepare("DELETE FROM user_addresses WHERE user_id = ?");
    $stmt->bind_param("i", $user_id);
    $stmt->execute();

    // Finally delete from signup (user table)
    $stmt = $conn->prepare("DELETE FROM signup WHERE id = ?");
    $stmt->bind_param("i", $user_id);
    $stmt->execute();

    $conn->commit();

    // Destroy session
    session_destroy();

    echo json_encode(["status" => "success", "message" => "Account and related data deleted successfully"]);
} catch (Exception $e) {
    $conn->rollback();
    echo json_encode(["status" => "error", "message" => "Something went wrong. Please try again later."]);
}
?>

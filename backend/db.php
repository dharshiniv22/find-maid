<?php
// Enable error reporting for development
ini_set('display_errors', 1);
error_reporting(E_ALL);

// Database connection details
$host = "localhost";
$user = "root";
$password = ""; // Leave empty in XAMPP default setup
$database = "findamaid";

// Create connection
$conn = new mysqli($host, $user, $password, $database);

// Check connection
if ($conn->connect_error) {
    die(json_encode([
        "status" => false,
        "message" => "Database Connection Failed: " . $conn->connect_error
    ]));
}
?>

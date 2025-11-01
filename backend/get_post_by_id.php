<?php
header('Content-Type: application/json');
include __DIR__ . '/db.php';

// Enable error reporting (remove in production)
ini_set('display_errors', 1);
error_reporting(E_ALL);

$response = [];

$baseURL = "https://12l7sjb3-80.inc1.devtunnels.ms/findamaid/uploads/"; // replace with your system IP

if (isset($_GET['id'])) {
    $id = (int)$_GET['id'];

    $query = "SELECT id, name, age, location, experience, language_known, phone_number, expected_salary, working_hours, category, photo_url
              FROM maid_posts
              WHERE id = ?";

    if ($stmt = $conn->prepare($query)) {

        $stmt->bind_param("i", $id);
        $stmt->execute();
        $result = $stmt->get_result();

        if ($row = $result->fetch_assoc()) {
            $response = [
                "status" => true,
                "maid" => [
                    "id" => $row['id'],
                    "name" => $row['name'] ?? "",
                    "age" => $row['age'] ?? "",
                    "location" => $row['location'] ?? "",
                    "experience" => $row['experience'] ?? "",
                    "language" => $row['language_known'] ?? "",
                    "phone_number" => $row['phone_number'] ?? "",
                    "expected_salary" => $row['expected_salary'] ?? "",
                    "working_hours" => $row['working_hours'] ?? "",
                    "category" => $row['category'] ?? "",
                    "photo_url" => $row['photo_url'] ? $baseURL . $row['photo_url'] : null
                ]
            ];
        } else {
            $response = [
                "status" => false,
                "message" => "Maid not found"
            ];
        }

        $stmt->close();

    } else {
        $response = [
            "status" => false,
            "message" => "Database error: " . $conn->error
        ];
    }

} else {
    $response = [
        "status" => false,
        "message" => "Maid ID is required"
    ];
}

echo json_encode($response);
$conn->close();

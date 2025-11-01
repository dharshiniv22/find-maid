<?php
header('Content-Type: application/json');
include __DIR__ . '/db.php';

// Enable error reporting
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

$response = [];

// Check if category is passed in GET
if (isset($_GET['category'])) {

    $category = trim($_GET['category']);

    // Allowed values as per DB enum
    $allowed = ["Clothes Washing", "Cooking", "Home Cleaning", "Dishwashing", "All work"];

    if (!in_array($category, $allowed)) {
        echo json_encode([
            "status" => false,
            "message" => "Invalid category selected: " . $category
        ]);
        exit;
    }

    // Prepare query with only required fields
    $query = "SELECT id, name, age, expected_salary, working_hours, photo_url,user_id 
              FROM maid_posts WHERE category = ?";
    $stmt = $conn->prepare($query);

    if ($stmt) {
        $stmt->bind_param("s", $category);
        $stmt->execute();
        $result = $stmt->get_result();

        $maids = [];
        while ($row = $result->fetch_assoc()) {
            $maids[] = [
                "id" => $row['id'],
                "name" => $row['name'],
                "maid_id" => $row['user_id'],
                "age" => $row['age'],
                "expected_salary" => $row['expected_salary'],
                "working_hours" => $row['working_hours'],
                "photo_url" => $row['photo_url'] ? "https://12l7sjb3-80.inc1.devtunnels.ms/findamaid/uploads/" . $row['photo_url'] : null
            ];
        }

        echo json_encode([
            "status" => true,
            "maids" => $maids
        ]);

        $stmt->close();
    } else {
        echo json_encode([
            "status" => false,
            "message" => "Database error: " . $conn->error
        ]);
    }

} else {
    echo json_encode([
        "status" => false,
        "message" => "Category is required in query string"
    ]);
}

$conn->close();
?>

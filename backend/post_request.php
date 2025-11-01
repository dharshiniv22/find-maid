<?php
header('Content-Type: application/json');
ini_set('display_errors', 1);
error_reporting(E_ALL);

// Include DB connection
include __DIR__ . '/db.php';

$response = ["status" => false, "message" => "Unknown error"];

// ✅ Check for required POST fields + uploaded photo
if (
    isset(
        $_POST['user_id'], $_POST['name'], $_POST['age'], $_POST['location'],
        $_POST['experience'], $_POST['language'], $_POST['phone_number'],
        $_POST['expected_salary'], $_POST['working_hours'], $_POST['category']
    ) && isset($_FILES['photo'])
) {
    $user_id    = (int)$_POST['user_id'];
    $name       = trim($_POST['name']);
    $age        = (int)$_POST['age'];
    $location   = trim($_POST['location']);
    $experience = trim($_POST['experience']);
    $language   = trim($_POST['language']);
    $phone      = trim($_POST['phone_number']);
    $salary     = (float)$_POST['expected_salary'];
    $hours      = trim($_POST['working_hours']);
    $category   = trim($_POST['category']);

    // ✅ Photo handling
    $photo      = $_FILES['photo'];
    $ext        = pathinfo($photo['name'], PATHINFO_EXTENSION);
    $baseName   = pathinfo($photo['name'], PATHINFO_FILENAME);
    $cleanName  = preg_replace('/[^a-zA-Z0-9_-]/', '_', $baseName);
    $finalPhoto = time() . '_' . $cleanName . '.' . $ext;

    $uploadDir  = __DIR__ . '/uploads/';
    $uploadPath = $uploadDir . $finalPhoto;

    if (!is_dir($uploadDir)) {
        mkdir($uploadDir, 0777, true);
    }

    // ✅ Prevent duplicate post by phone number
    $checkStmt = $conn->prepare("SELECT id FROM maid_posts WHERE phone_number = ?");
    $checkStmt->bind_param("s", $phone);
    $checkStmt->execute();
    $checkStmt->store_result();

    if ($checkStmt->num_rows > 0) {
        echo json_encode(["status" => false, "message" => "You have already submitted your details."]);
        exit;
    }

    // ✅ Move uploaded photo and insert record
    if (move_uploaded_file($photo['tmp_name'], $uploadPath)) {
        $stmt = $conn->prepare("INSERT INTO maid_posts 
            (user_id, name, age, location, experience, language_known, phone_number, expected_salary, working_hours, category, photo_url)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        $stmt->bind_param(
            "isissssdsss",
            $user_id,
            $name,
            $age,
            $location,
            $experience,
            $language,
            $phone,
            $salary,
            $hours,
            $category,
            $finalPhoto
        );

        if ($stmt->execute()) {
            echo json_encode([
                "status" => true,
                "message" => "Maid post submitted successfully",
                "maid_post_id" => $stmt->insert_id
            ]);
        } else {
            echo json_encode(["status" => false, "message" => "Database error: " . $stmt->error]);
        }
    } else {
        echo json_encode(["status" => false, "message" => "Failed to upload photo"]);
    }
} else {
    // Debug info to help in development
    echo json_encode([
        "status" => false,
        "message" => "Missing required fields or image",
        "received_post_keys" => array_keys($_POST),
        "received_file_keys" => array_keys($_FILES)
    ]);
}
?>

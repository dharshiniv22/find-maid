<?php
// privacy_location.php
include __DIR__ . "/db.php"; // correct filename

header("Content-Type: application/json");

// Example: replace 1 with logged-in user session ID
$user_id = 1;

$stmt = $conn->prepare("SELECT location FROM users WHERE id = ?");
$stmt->bind_param("i", $user_id);
$stmt->execute();
$result = $stmt->get_result();

$location = "Not Available";
if ($row = $result->fetch_assoc()) {
    $location = $row['location'];
}

$response = [
    "app" => "Find a Maid",
    "privacy_policy" => [
        "info_we_collect" => [
            "name","username","email","mobile","password",
            "booking requests","reviews","maid details"
        ],
        "how_we_use" => [
            "manage accounts","process bookings",
            "share details between users and maids",
            "reviews/ratings","improve services"
        ],
        "data_security" => "We implement appropriate measures but no method of transmission is 100% secure.",
        "sharing" => [
            "with maids" => "user booking details (name, contact, address, time)",
            "with users" => "maid details (name, experience, salary, contact)",
            "with service providers" => "for hosting, analytics, and support"
        ],
        "your_rights" => "You can request access, update, or delete your personal information by contacting us.",
        "contact_us" => [
            "name" => "V. Dharshini",
            "mobile" => "6369459958",
            "email" => "sujidharshini2004@gmail.com",
            "location" => $location
        ]
    ]
];

echo json_encode($response, JSON_PRETTY_PRINT);

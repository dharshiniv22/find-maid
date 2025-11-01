<?php
header('Content-Type: application/json');
include __DIR__ . '/db.php';

$response = [];

if ($_SERVER['REQUEST_METHOD'] === 'POST') {

    // ✅ Case 0: Insert new maid booking request
    if (isset($_POST['maid_id']) && isset($_POST['user_id']) && !isset($_POST['request_id']) && !isset($_POST['action'])) {
        $maid_id = (int)$_POST['maid_id'];
        $user_id = (int)$_POST['user_id'];

        $insertQuery = "INSERT INTO maid_request (maid_id, user_id, status) VALUES (?, ?, 'Pending')";
        $stmt = mysqli_prepare($conn, $insertQuery);

        if ($stmt) {
            mysqli_stmt_bind_param($stmt, "ii", $maid_id, $user_id);
            if (mysqli_stmt_execute($stmt)) {
                $response['status'] = "success";
                $response['message'] = "Booking request submitted.";
            } else {
                $response['status'] = "error";
                $response['message'] = "Insert failed: " . mysqli_error($conn);
            }
            mysqli_stmt_close($stmt);
        } else {
            $response['status'] = "error";
            $response['message'] = "Prepare failed: " . mysqli_error($conn);
        }

        echo json_encode($response);
        mysqli_close($conn);
        exit;
    }

    // ✅ Case 1: Update Request Status if action is provided
    if (isset($_POST['request_id']) && isset($_POST['action'])) {
        $request_id = (int)$_POST['request_id'];
        $action = $_POST['action'];

        if ($request_id <= 0 || ($action !== 'Accepted' && $action !== 'Rejected')) {
            $response['status'] = "error";
            $response['message'] = "Invalid request_id or action.";
            echo json_encode($response);
            exit;
        }

        $updateQuery = "UPDATE maid_request SET status = ? WHERE id = ?";
        $stmt = mysqli_prepare($conn, $updateQuery);
        if ($stmt) {
            mysqli_stmt_bind_param($stmt, "si", $action, $request_id);
            mysqli_stmt_execute($stmt);

            if (mysqli_stmt_affected_rows($stmt) > 0) {
                $response['status'] = "success";
                $response['message'] = "Request status updated successfully.";
            } else {
                $response['status'] = "error";
                $response['message'] = "No update performed or invalid request_id.";
            }

            mysqli_stmt_close($stmt);
        } else {
            $response['status'] = "error";
            $response['message'] = "Database error: " . mysqli_error($conn);
        }

        echo json_encode($response);
        mysqli_close($conn);
        exit;
    }

    // ✅ Case 2: Fetch Booking Requests based on user_id
    $user_id = isset($_POST['user_id']) ? (int)$_POST['user_id'] : 0;

    if ($user_id <= 0) {
        $response['status'] = "error";
        $response['message'] = "User ID is required.";
        echo json_encode($response);
        exit;
    }

    $query = "SELECT r.id AS request_id, r.status, p.id AS maid_id, p.name, p.age, p.location, p.experience, p.photo 
              FROM maid_request r
              JOIN post_request p ON r.maid_id = p.id
              WHERE r.user_id = ?";

    $stmt = mysqli_prepare($conn, $query);

    if ($stmt) {
        mysqli_stmt_bind_param($stmt, "i", $user_id);
        mysqli_stmt_execute($stmt);
        $result = mysqli_stmt_get_result($stmt);

        $requests = [];

        while ($row = mysqli_fetch_assoc($result)) {
            $requests[] = [
                "request_id" => $row['request_id'],
                "maid_id" => $row['maid_id'],
                "name" => $row['name'],
                "age" => $row['age'],
                "location" => $row['location'],
                "experience" => $row['experience'],
                "photo" => $row['photo'],
                "status" => $row['status']
            ];
        }

        if (count($requests) > 0) {
            $response['status'] = "success";
            $response['requests'] = $requests;
        } else {
            $response['status'] = "empty";
            $response['message'] = "No booking requests found.";
        }

        mysqli_stmt_close($stmt);

    } else {
        $response['status'] = "error";
        $response['message'] = "Database error: " . mysqli_error($conn);
    }

    echo json_encode($response);
    mysqli_close($conn);
}
?>

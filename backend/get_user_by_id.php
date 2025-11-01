<?php
header('Content-Type: application/json');
include 'db.php';
$id = $_GET['id'] ?? '';
if(empty($id)) { echo json_encode(["success"=>false,"message"=>"Missing id"]); exit; }
$sql = "SELECT id, username, name, email FROM signup WHERE id = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("i", $id);
$stmt->execute();
$result = $stmt->get_result();
if($row = $result->fetch_assoc()) {
    echo json_encode(["success"=>true,"data"=>$row]);
} else {
    echo json_encode(["success"=>false,"message"=>"User not found"]);
}
$stmt->close();
$conn->close();
?>


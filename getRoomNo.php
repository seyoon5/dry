<?php
//header("Conetent-type:application/json");

include_once("db.php");

$sender = $_POST['sender'];
$receiver = $_POST['receiver'];

$query = "select idx from chat_room where sender = '$sender' and receiver = '$receiver' or 
            receiver = '$sender' and sender = '$receiver'";
//$query = "select*from chat where room_num = $room_num";

$result = mysqli_query($con, $query);

if (mysqli_num_rows($result) > 0) {
    $row = mysqli_fetch_assoc($result);
    $idx = $row['idx'];
    echo json_encode(array(
        'idx' => $idx
    ));
} else {
    $status = "failed";
    echo json_encode(array(
        'status' => $status
    ));
}

mysqli_close($con);

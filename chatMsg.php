<?php
header("Conetent-type:application/json");

include_once("db.php");

$room_num = $_GET['room_num'];

$query = "select a.idx,  a.receiver_profile, a.sender,  a.receiver, a.sender_profile, b.contents, b.time, b.identity, b.profile
from chat_room a left join chat b on (a.idx = b.room_num) where a.idx = $room_num";
//$query = "select*from chat where room_num = $room_num";
$result = mysqli_query($con, $query);

$response = array();

while ($row = mysqli_fetch_assoc($result)) {
    array_push($response, array(
        'idx' => $row['idx'],
        'receiver_profile' => $row['receiver_profile'],
        'sender' => $row['sender'],
        'receiver' => $row['receiver'],
        'sender_profile' => $row['sender_profile'],
        'contents' => $row['contents'],
        'time' => $row['time'],
        'identity' => $row['identity'],
        'profile' => $row['profile']
    ));
}
echo json_encode($response);

mysqli_close($con);

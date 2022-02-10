<?php
header("Conetent-type:application/json");

include_once("db.php");

$sender = $_GET['sender'];

// $query = "update chat_room set chat_room.read_cnt = (select count(*)-count(readed) from chat where room_num = '$idx') where chat_room.idx = '$idx'";
// mysqli_query($con, $query);

$query = "select*from chat_room order by created desc";
$result = mysqli_query($con, $query);

$response = array();

while ($row = mysqli_fetch_assoc($result)) {
    array_push($response, array(
        'idx' => $row['idx'],
        'user' => $row['user'],
        'receiver_profile' => $row['receiver_profile'],
        'sender' => $row['sender'],
        'content' => $row['content'],
        'time' => $row['time'],
        'receiver' => $row['receiver'],
        'sender_profile' => $row['sender_profile'],
        'read_cnt_receiver' => $row['read_cnt_receiver'],
        'read_cnt_sender' => $row['read_cnt_sender'],
        'read_cnt_sender' => $row['read_cnt_sender']
    ));
}
echo json_encode($response);

mysqli_close($con);

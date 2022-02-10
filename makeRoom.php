<?php

include_once("db.php");

$user = $_POST['user'];
$receiverProfile = $_POST['receiverProfile'];
$sender = $_POST['sender'];
$receiver = $_POST['receiver'];
$content = $_POST['content'];
$senderProfile = $_POST['senderProfile'];
$time = $_POST['time'];
$count = $_POST['count'];

if (!$sender) {
    echo json_encode(array(
        "status" => false, "message" => 'content is not exist'
    ));
} else {
    $query = "select * from chat_room where sender ='$sender' and receiver ='$receiver' or sender ='$receiver' and receiver ='$sender'";
    $result = mysqli_query($con, $query);
    $row = mysqli_num_rows($result);

    if ($row > 0) {
        $query = "update chat_room set content='$content', time='$time', created=NOW() where
            sender ='$sender' and receiver ='$receiver' or sender ='$receiver' and receiver ='$sender'";
        mysqli_query($con, $query);
        $response = array();
        while ($row = mysqli_fetch_assoc($result)) {
            $response[] = $row;
        }
        echo json_encode(array(
            "message" => "exist success", "data" => $response
        ));
    } else {
        $query = "insert into chat_room(user, receiver_profile, sender, receiver, content, sender_profile, time, read_cnt_receiver, read_cnt_sender, people, created)
                values('$user', '$receiverProfile', '$sender', '$receiver', '$content', '$senderProfile', '$time', '$count', '$count', '0', NOW() )";
        $result = mysqli_query($con, $query);

        $query = "select * from chat_room where sender ='$sender' and receiver ='$receiver' or sender ='$receiver' and receiver ='$sender'";
        $result = mysqli_query($con, $query);
        $row = mysqli_num_rows($result);

        $response = array();
        while ($row = mysqli_fetch_assoc($result)) {
            $response[] = $row;
        }
        echo json_encode(array(
            "message" => "new success", "data" => $response
        ));
    }

    mysqli_query($con, $query);
}

mysqli_close($con);

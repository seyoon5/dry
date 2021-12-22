<?php

include_once("db.php");

$profile = $_POST['profile'];
$nick = $_POST['nick'];
$board_idx = $_POST['board_idx'];
$content = $_POST['content'];

if (!$content) {
    echo json_encode(array(
        "status" => false, "message" => 'content is not exist'
    ));
} else {
    echo json_encode(array(
        "status" => true, "message" => "reply save ok"
    ));
    $query = "insert into reply (board_idx, profile, nick, created, content) values ('$board_idx', '$profile', '$nick', NOW(), '$content')";
    mysqli_query($con, $query);
    $query = "update board set board.reply_cnt = (select count(*)-count(deleted) from reply where board_idx = '$board_idx') where board.idx = '$board_idx'";
    mysqli_query($con, $query);
}

mysqli_close($con);

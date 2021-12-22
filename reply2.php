<?php

include_once("db.php");

$profile = $_POST['profile'];
$nick = $_POST['nick'];
$board_idx = $_POST['board_idx'];
$content = $_POST['content'];
$parent = $_POST['parent'];
$idx = $_POST['idx'];

if (!$content) {
    echo json_encode(array(
        "status" => false, "message" => 'content is not exist'
    ));
} else {
    echo json_encode(array(
        "status" => true, "message" => "reply2 save ok"
    ));
    $query = "insert into reply (board_idx, profile, nick, created, content, parent) 
                values ('$board_idx', '$profile', '$nick', NOW(), '$content', '$idx')";
    mysqli_query($con, $query);
}

mysqli_close($con);

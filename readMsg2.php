<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    include_once("db.php");
    $idx = $_POST['idx'];

    if (!$idx) {
        echo json_encode(array(
            "message" => "readMsg is not operated"
        ));
    } else {
        $query = "update chat_room set read_cnt_sender = '0' where idx = '$idx'";
        mysqli_query($con, $query);
        echo json_encode(array(
            "message" => "readMsg updated"
        ));
    }
    mysqli_close($con);
}

// $query = "set SQL_SAFE_UPDATES = 0";
//         mysqli_query($con, $query);
//         $query = "update chat set readed = '$read' where room_num = '$idx'";
//         mysqli_query($con, $query);
//         $query = "set SQL_SAFE_UPDATES = 1";
//         mysqli_query($con, $query);
//         $query = "update chat_room set chat_room.read_cnt = (select count(*)-count(readed) from chat where room_num = '$idx') where chat_room.idx = '$idx'";

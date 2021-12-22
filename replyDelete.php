<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    include_once("db.php");
    $idx = $_POST['idx'];
    $board_idx = $_POST['board_idx'];
    $deleted = $_POST['deleted'];

    if (!$idx) {
        echo json_encode(array(
            "message" => "delete failed"
        ));
    } else {
        $query = "update reply set deleted = '$deleted' where idx = '$idx' ";
        mysqli_query($con, $query);
        $query = "update board set board.reply_cnt = (select count(*)-count(deleted) from reply where board_idx = '$board_idx') where board.idx = '$board_idx'";
        mysqli_query($con, $query);
        //update board set board.reply_cnt = (select count(*)-count(parent) from reply where board_idx = '$board_idx') where board.idx = '$board_idx'";
        echo json_encode(array(
            "message" => "delete success"
        ));
    }


    mysqli_close($con);
}

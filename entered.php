<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    include_once("db.php");
    $idx = $_POST['idx'];

    if (!$idx) {
        echo json_encode(array(
            "message" => "entered is not operated"
        ));
    } else {
        $query = "update chat_room set people = chat_room.people +1 where idx = '$idx'";
        mysqli_query($con, $query);
        echo json_encode(array(
            "message" => "entered updated"
        ));
    }
    mysqli_close($con);
}

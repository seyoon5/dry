<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    include_once("db.php");
    $idx = $_POST['idx'];

    if (!$idx) {
        echo json_encode(array(
            "message" => "delete failed"
        ));
    } else {
        $query = "delete from board where idx = '$idx' ";
        mysqli_query($con, $query);

        echo json_encode(array(
            "message" => "delete success"
        ));
    }


    mysqli_close($con);
}

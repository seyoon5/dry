<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    include_once("db.php");

    $idx = $_POST['idx'];
    $content = $_POST['content'];
    if (!$content) {
        echo json_encode(array(
            "message" => "content is not exist"
        ));
    } else {
        echo json_encode(array(
            "status" => true, "message" => "reply updated"
        ));
        $query = "update reply set content = '$content', created = NOW() where idx = '$idx'";
        mysqli_query($con, $query);
    }
    mysqli_close($con);
}

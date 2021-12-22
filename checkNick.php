<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    include_once("db.php");

    $nick = $_POST['nick'];

    $query = "select*from users where nick = '$nick'";
    $result = mysqli_query($con, $query);
    if (mysqli_num_rows($result) > 0) {
        echo json_encode(array(
            "status" => "exist",
            "message" => "동일한 닉네임이 존재합니다."
        ));
    } else {
        echo json_encode(array(
            "status" => "non_exist",
            "message" => "사용가능."
        ));
    }
}

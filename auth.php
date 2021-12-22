<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    include_once("db.php");

    $email = $_POST['email'];

    $query = "select*from users where email = '$email'";
    $result = mysqli_query($con, $query);
    if (mysqli_num_rows($result) > 0) {
        echo json_encode(array(
            "status" => "exist",
            "message" => "이미 가입된 email 입니다."
        ));
    } else {
        echo json_encode(array(
            "status" => "non_exist",
            "message" => "사용가능."
        ));
    }
}

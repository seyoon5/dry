<?php
include 'db.php';

$email = $_POST['email'];
$nick = $_POST['nick'];
$pw = $_POST['password'];

$query = "select*from users where email = '$email'";
$result = mysqli_query($con, $query);
if (mysqli_num_rows($result) == 1) {
    $row = mysqli_fetch_assoc($result);
    $hash = $row['password'];
    if (!password_verify($pw, $hash)) {
        echo json_encode(array(
            "status" => "false", "message" => "비밀번호가 일치하지 않습니다."
        ));
    } else {
        $query = "delete from users where email = '$email' ";
        mysqli_query($con, $query);
        $query = "delete from board where nick = '$nick' ";
        mysqli_query($con, $query);

        echo json_encode(array(
            "status" => "true", "message" => "회원탈퇴가 완료되었습니다."
        ));
    }
}

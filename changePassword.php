<?php
include 'db.php';

$email = $_POST['email'];
$cpw = $_POST['password'];
$npw = $_POST['newPassword'];
$pwc = $_POST['passwordCheck'];

$query = "select*from users where email = '$email'";
$result = mysqli_query($con, $query);
if (mysqli_num_rows($result) == 1) {
    $row = mysqli_fetch_assoc($result);
    $hash = $row['password'];
    if (!password_verify($cpw, $hash)) {
        echo json_encode(array(
            "status" => "false", "message" => "비밀번호가 일치하지 않습니다."
        ));
    } else if ($npw != $pwc) {
        echo json_encode(array(
            "status" => "false", "message" => "새 비밀번호가 일치하지 않습니다."
        ));
    } else {
        $password = password_hash($npw, PASSWORD_DEFAULT);
        $query = "update users set password = '$password' where email = '$email' ";
        mysqli_query($con, $query);
        echo json_encode(array(
            "status" => "true", "message" => "비밀번호가 변경되었습니다."
        ));
    }
}

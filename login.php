<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    include_once "db.php";
    $email = $_POST['email'];
    $password = $_POST['password'];
    if ($email == '' || $password == '') {
        echo json_encode(array(
            "status" => "false", "message" => "이메일 또는 패스워드를 입력해 주세요."
        ));
    } else {
        $query = "select*from users where email = '$email'";
        $result = mysqli_query($con, $query);
        if (mysqli_num_rows($result) == 1) {
            $row = mysqli_fetch_assoc($result);
            $hash = $row['password'];
            if (password_verify($password, $hash)) {
                $query = "select*from users where email = '$email'";
                $result = mysqli_query($con, $query);
                $emparray = array();
                if (mysqli_num_rows($result) > 0) {
                    while ($row = mysqli_fetch_assoc($result)) {
                        $emparray[] = $row;
                    }
                }
                echo json_encode(array(
                    "status" => "true", "message" => "로그인 되었습니다.", "data" => $emparray
                ));
            } else {
                echo json_encode(array(
                    "status" => "false", "message" => "비밀번호가 일치하지 않습니다."
                ));
            }
        } else {
            echo json_encode(array(
                "status" => "false", "message" => "회원정보가 없습니다."
            ));
        }
    }
} else {
    echo json_encode(array(
        "status" => "false", "message" => "오류가 발생했습니다. 다시 시도해 주세요"
    ));
}

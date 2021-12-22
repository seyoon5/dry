<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    include_once("db.php");

    $email = $_POST['email'];
    $password = $_POST['password'];
    $nick = $_POST['nick'];

    if ($email == '' || $password == '' || $nick == '') {
        //빈 항목
        echo json_encode(array(
            "status" => "false",
            "message" => "빈 항목이 존재합니다."
        ));
    } else {

        $query = "select*from users where email='$email'";
        $query1 = "select*from users where email='$nick'";

        $result = mysqli_query($con, $query);
        $result1 = mysqli_query($con, $query1);
        if (mysqli_num_rows($result) > 0 || mysqli_num_rows($result1) > 0) {
            //email, nick 중복검사
            echo json_encode(array(
                "status" => "false", "message" => "이미 존재하는 닉네임 입니다."
            ));
        } else {
            $password = password_hash($password, PASSWORD_DEFAULT);
            $query = "insert into users (email, password, nick) values ('$email', '$password', '$nick')";
            if (mysqli_query($con, $query)) {
                $query = "select*from users where nick='$nick'";
                $result = mysqli_query($con, $query);
                $emparray = array();
                if (mysqli_num_rows($result) > 0) {
                    while ($row = mysqli_fetch_assoc($result)) {
                        $emparray[] = $row;
                    }
                }
                echo json_encode(array(
                    //회원가입하고 가입된 회원정보 가져오기
                    "status" => "true", "message" => "회원가입을 축하합니다.", "data" => $emparray
                ));
            } else {
                //알 수 없는 오류
                echo json_encode(array(
                    "status" => "false", "message" => "각 항목을 다시 확인해 주세요."
                ));
            }
        }
        mysqli_close($con);
    }
}

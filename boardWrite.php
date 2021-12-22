<?php

include_once("db.php");

$nick = $_POST['nick'];
$content = $_POST['content'];
$size = $_POST['size'];
$target_path = dirname(__FILE__) . '/images/';
$dbImageList = "";
if (!$content) {
    echo json_encode(array(
        "status" => false, "message" => 'content is not exist'
    ));
} else {
    if (!empty($_FILES)) {
        for ($i = 0; $i < $size; $i++) {
            $newName = date('YmdHis', time()) . mt_rand() . '.jpg';
            $dbImageList .= $newName . " ";
            if (!move_uploaded_file($_FILES['image' . $i]['tmp_name'], $target_path . $newName)) {
                echo json_encode(array(
                    "status" => false, "message" => "could not move files"
                ));
            }
        }
        echo json_encode(array(
            "status" => true, "message" => "images uploaded"
        ));
    } else {
        echo json_encode(array(
            "status" => false, "message" => "maybe empty files"
        ));
    }
    $query = "select*from users where nick='$nick'";
    $result = mysqli_query($con, $query);
    $row = mysqli_fetch_assoc($result);
    $image = $row['image'];
    $query = "insert into board (profile_image, content, nick, created, content_image)
                values ('$image', '$content', '$nick', NOW(), '$dbImageList')";
    mysqli_query($con, $query);
    mysqli_close($con);
}

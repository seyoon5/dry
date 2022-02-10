<?php
include 'db.php';

//$data = json_decode(file_get_contents("php://input"), true); php로 들어온 모든 파일 연관배열로 디코딩한게 $data 인데 data 안써서 죽임

$fileName = $_FILES['image']['name'];
$tempPath = $_FILES['image']['tmp_name'];
$fileSize = $_FILES['image']['size'];
$newName = $fileName . date('YmdHis', time()) . mt_rand() . '.jpg';
if (empty($fileName)) {
    $errorMSG = json_encode(array(
        "message" => "please select image",
        "status" => "false"
    ));
    echo $errorMSG;
} else {
    $upload_path = 'images/';
    $fileExt = strtolower(pathinfo($fileName, PATHINFO_EXTENSION));
    $valid_extentions = array('jpeg', 'jpg', 'png', 'gif');

    if (in_array($fileExt, $valid_extentions)) {
        if ($fileSize < 5000000) {
            move_uploaded_file($tempPath, $upload_path . $newName);
        } else {
            $errorMSG = json_encode(array(
                "message" => "file is too large",
                "status" => "false"
            ));
            echo $errorMSG;
        }
    } else {
        $errorMSG = json_encode(array(
            "message" => "is not allow file extentions",
            "status" => "false"
        ));
        echo $errorMSG;
    }
}
if (!isset($errorMSG)) {
    echo json_encode(array(
        "message" => "upload success",
        "status" => true,
        "image" => "$newName"
    ));
}

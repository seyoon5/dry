<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    include_once("db.php");
    $idx = $_POST['idx'];
    $content = $_POST['content'];
    $size = $_POST['size'];
    $originImg = $_POST['originImg'];
    $target_path = dirname(__FILE__) . '/images/';
    $dbImageList = "";

    if (!$content) {
        echo json_encode(array(
            "message" => "content is not exist"
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
        $query = "update board set content = '$content',
        created = NOW(),
        content_image = '$dbImageList$originImg' where idx = '$idx' ";
        mysqli_query($con, $query);
    }
    mysqli_close($con);
}

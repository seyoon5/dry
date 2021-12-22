<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $idx = $_POST['idx'];

    include_once("db.php");

    $query = "select*from board where idx = '$idx'";
    $result = mysqli_query($con, $query);
    $row = mysqli_num_rows($result);

    if ($row > 0) {
        
        $row = mysqli_fetch_assoc($result);
        $content = $row['content'];
        $content_image = $row['content_image'];
        echo json_encode(array(
            'message' => 'succeed', 'content' => $content, 'contentImage' => $content_image
        ));
    } else {
        echo json_encode(array(
            "message" => "failed  !row>0"
        ));
    }
    mysqli_close($con);
} else {
    echo json_encode(array(
        "message" => "server connect failed"
    ));
}

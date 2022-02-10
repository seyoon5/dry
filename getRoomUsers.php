<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    include_once("db.php");
    $idx = $_POST['idx'];

    $query = "select * from chat_room where idx = '$idx'";
    $result = mysqli_query($con, $query);
    $row = mysqli_num_rows($result);

    if ($row > 0) {
        $response = array();
        while ($row = mysqli_fetch_assoc($result)) {
            $response[] = $row;
        }
        echo json_encode(array(
            "message" => "success", "data" => $response
        ));
    } else {
        echo json_encode(array(
            "message" => "false"
        ));
    }
} else {
    echo "post없음";
}

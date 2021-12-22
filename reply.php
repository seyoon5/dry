<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    include_once("db.php");
    $board_idx = $_POST['board_idx'];
    $query = "select * from reply where board_idx = '$board_idx' order by if(isnull(parent), idx, parent)";
    $result = mysqli_query($con, $query);
    $row = mysqli_num_rows($result);
    if ($row > 0) {
        $response = array();
        while ($row = mysqli_fetch_assoc($result)) {
            $response[] = $row;
        }
        echo json_encode(array(
            "message" => "success", "replyData" => $response
        ));
    } else {
        echo json_encode(array(
            "message" => "false"
        ));
    }
    mysqli_close($con);
} else {
    echo "post없음";
}

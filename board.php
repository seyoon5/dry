<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    include_once("db.php");
    $page = $_POST['page'];
    $limit = $_POST['limit'];
    //$query = "select * from board join reply on board.idx = reply.board_idx";
    $query = "select*from board order by idx desc limit $page, $limit";
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
    mysqli_close($con);
} else {
    echo "post없음";
}

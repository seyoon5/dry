<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    include_once("db.php");
    $idx = $_POST['idx'];

    $query = "select * from chat_room where idx = '$idx'";
    $result = mysqli_query($con, $query);
    $row = mysqli_num_rows($result);

    if ($row > 0) {
        $row = mysqli_fetch_assoc($result);
        $idx = $row['idx'];
        $user = $row['user'];
        $receiver_profile = $row['receiver_profile'];
        $sender = $row['sender'];
        $content = $row['content'];
        $receiver = $row['receiver'];
        $sender_profile = $row['sender_profile'];
        $people = $row['people'];
        echo json_encode(array(
            'idx' => $idx, 'user' => $user,
            'receiver_profile' => $receiver_profile, 'sender' => $sender, 'content' => $content,
            'receiver' => $receiver, 'sender_profile' => $sender_profile, 'people' => $people
        ));
    } else {
        echo "failed getIdx method";
    }
} else {
    echo "post없음";
}

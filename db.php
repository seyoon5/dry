
<?php

error_reporting(E_ALL);
ini_set("display_errors", 1);

$host = 'dry3.cwtqfevqzd7m.ap-northeast-2.rds.amazonaws.com';
$user = 'root';
$pw = '123qwe123';
$dbName = 'dry';

$con = new mysqli($host, $user, $pw, $dbName);

if (!$con) {
    echo 'DB 접속 오류';
    echo mysqli_connect_error();
    exit();
}
?>


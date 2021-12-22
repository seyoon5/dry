<?php
include 'db.php';

$lat = $_GET['lat'];
$lng = $_GET['lng'];
$m = $_GET['m'];

$ch = curl_init();

$searching = [
    'query' => '세탁',
    'x' => $lng,
    'y' => $lat,
    'radius' => $m
];

$url = "https://dapi.kakao.com/v2/local/search/keyword.json?";
$header = array(
    'Authorization: KakaoAK 4fc275bbc103e9f76517376be0ec4f29'
);

curl_setopt($ch, CURLOPT_URL, $url);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
curl_setopt($ch, CURLOPT_HTTPHEADER, $header);
curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($searching));

$response = curl_exec($ch);

if ($e = curl_error($ch)) {
    echo $e;
} else {
    echo $response;
}
curl_close($ch);

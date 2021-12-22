<?php
include 'db.php';

$ch = curl_init();

$searching = [
  'query' => '세탁',
  'x' => '126.971299',
  'y' => '37.484600',
  'radius' => '5000'
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

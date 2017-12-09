<?php

require_once 'Query.php';
$db = new Query(); 
$response = array();

$from = $_POST['from'];
$pom = array();

$response = $db->allRestorants();
foreach($response['results'] as $r) {
    $to = $r['address'];
    $data = file_get_contents("http://maps.googleapis.com/maps/api/distancematrix/json?origins=$from&destinations=$to&language=en-EN&sensor=false");

    $data = json_decode($data);

    $time = 0;
    $distance = 0;
    foreach($data->rows[0]->elements as $road) {
        $distance += $road->distance->value;
    }
    
    $r['distance'] = (int)$distance;
    array_push($pom, $r);
    
}
	
$d = array();
foreach ($pom as $key => $row)
{
    $d[$key] = $row['distance'];
}
array_multisort($d, SORT_ASC, $pom);
echo json_encode(array_slice($pom, 0, 3));


?>

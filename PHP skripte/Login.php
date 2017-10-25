<?php

require_once 'Query.php';
$db = new Query(); 
$responset = array();
$response["error"] = FALSE;

print_r($_POST); //ispis POST methode koja je prazna!! ne radi!!
$email = "mm@mail.pt";
$password = "mm";


/**
 * if (isset($_POST['username']) && isset($_POST['password'])){
    $username = $_POST['username'];
    $password = $_POST['password'];
    
    $user = $db->userLoginCheck($username, $password);
    if ($user != false) {
        // use is found
        $response["id"] = $user["id"];
        $response["name"] = $user["name"];
        $response["surname"] = $user["surname"];
        echo json_encode($response);
    } else {
        // user is not found with the credentials
        $response["error_msg"] = "Login credentials are wrong. Please try again!";
        echo json_encode($response);
    }

} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters email or password is missing!";
    echo json_encode($response);
}
*/

 $user = $db->checkUser($email, $password);
    if ($user != false) {
        // use is found
        $response["name"] = $user["name"];
        $response["surname"] = $user["surname"];
        echo json_encode($response);
    } else {
        // user is not found with the credentials
        $response["error_msg"] = "Login credentials are wrong. Please try again!";
        echo json_encode($response);
    }

?>
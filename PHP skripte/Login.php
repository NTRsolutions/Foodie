<?php

require_once 'Query.php';
$db = new Query(); 
$responset = array();
$response["error"] = FALSE;


if (isset($_POST['email']) && isset($_POST['password'])){
    $email = $_POST['email'];
    $password = $_POST['password'];
    
    $user = $db->checkUser($email, $password);
    if ($user != false) {
        // user is found
        $response["name"] = $user["name"];
        $response["surname"] = $user["surname"];
        $response["email"] = $user["email"];
        echo json_encode($response);
    } else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = "Login credentials are wrong. Please try again!";
        echo json_encode($response);
    }
} else {
    //required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters email or password is missing!";
    echo json_encode($response);
}


 

?>
<?php
    
require_once 'Query.php';
$db = new Query(); 
$response = array();
$response["error"] = FALSE;;
 
	$name = $_POST['name'];
	$surname = $_POST['surname'];
	$email = $_POST['email'];
	$telephone = $_POST['telephone'];
	$location = $_POST['location'];
	$restoran = $_POST['restoran'];
	$password = $_POST['password'];
	
	// check if user is already existed with the same email
	if ($db->isUserExisted($email)) {
		$response["error"] = TRUE;
		$response["error_msg"] = "User already existed with " . $email;
		echo json_encode($response);
	}  
	else  {
		//create a new user
		if(isset($_POST['restoran'])){
			//registration of restoran
			$user = $db->addRestoran($name, $surname, $email, $password, $telephone, $location, $restoran);
			if ($user) {
				// user stored successfully
				$response["error"] = FALSE;
				echo json_encode($response);
				
			} else {
				// user failed to store
				$response["error"] = TRUE;
				$response["error_msg"] = "Unknown error occurred in registration!";
				echo json_encode($response);
			}
		}  
		else  {
			// registration of user
			$user = $db->addUser($name, $surname, $email, $password );
			if ($user) {
				// user stored successfully
				$response["error"] = FALSE;
				echo json_encode($response);
			} else {
				// user failed to store
				$response["error"] = TRUE;
				$response["error_msg"] = "Unknown error occurred in registration!";
				echo json_encode($response);
			}
		}
	; 
	}
?>
<?php
    
require_once 'Query.php';
$db = new Query(); 
$response = array();
$response["error"] = FALSE;;
 
	$name = $_POST['name'];
	$surname = $_POST['surname'];
	$email = $_POST['email'];
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
			$user = $db->addUser($name, $email, $password, $surname, $telephone, $restoran);
			if ($user) {
				// user stored successfully
				$response["error"] = FALSE;
				$response["user"]["name"] = $user["name"];
				$response["user"]["surname"] = $user["surname"];
				$response["user"]["email"] = $user["email"];
				$response["user"]["telephone"] = $user["telephone"];
				$response["user"]["restoran"] = $user["restoran"];
				
				//TO DO dodat unos lokacije
				
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
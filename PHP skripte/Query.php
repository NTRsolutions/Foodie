<?php
class Query{
    private $conn;
    
    // constructor
    function __construct() {
        require_once 'Connect.php';
        $db = new Connect();
        $this->conn = $db->DBconnect();
    }
 
    // destructor
    function __destruct() {
         
    }
	
	//list of all users
    public function allUsers(){
        $result = $this->conn->query("SELECT * FROM user");
	    $r = array();
        if ($result->num_rows > 0) {
            // output data of each row
            while($row = mysqli_fetch_array($result)){
            	array_push($r,array(
            		"id"=>$row['id'],
            		"name"=>$row['name'],
            		"surname"=>$row['surname'],
            		"username"=>$row['username'],
            		"password"=>$row['password'],
            		"email"=>$row['email']
            		));
            	}
        echo json_encode(array('result'=>$r));
        } else {
            echo "0 results";
        }
    $this->conn->close();
    }
    
    //check user before login
    public function checkUser($email, $password){
        $result = $this->conn->prepare("SELECT name, surname FROM user WHERE email=? AND password=?");
        $result->bind_param("ss", $email, $password);
	    if ($result->execute()) {
            $user = $result->get_result()->fetch_assoc();
            $result->close();
            return $user;
        } else {
            return NULL;
        }
    }
}
?>
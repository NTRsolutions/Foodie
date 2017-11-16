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
        $result = $this->conn->prepare("SELECT name, surname, email FROM user WHERE email=? AND password=?");
        $result->bind_param("ss", $email, $password);
	    if ($result->execute()) {
            $user = $result->get_result()->fetch_assoc();
            $result->close();
            return $user;
        } else {
            return NULL;
        }
    }
  	
	//check if user exists
	public function isUserExisted($email){
        $query = $this->conn->prepare("SELECT email FROM user WHERE email=?");
        $query->bind_param("s", $email);
		$query->execute();
		$query->store_result();
        if($query->num_rows > 0){
			$query->close();
            return true; //user existed
        }else {
			$query->close();
            return false; //user not existed
        }
    }
			
	//new user
	public function addUser($name, $surname, $email, $password){
		$query = $this->conn->prepare("INSERT INTO user (name, surname, email, password) VALUES (?, ?, ?, ?)");
        $query->bind_param("ssss", $name, $surname, $email, $password); 
		$result = $query->execute();
		$query->close(); 
		if($result){
			return true;
        }else {
            return false;
        }
	}
	
	//new restoran  
}
?>
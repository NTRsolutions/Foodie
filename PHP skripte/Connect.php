<?php

/**
Connecting to database
*/

class Connect {
	private $conn;
	public function DBconnect(){
		require_once 'Config.php';
		$this->conn = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);
		return $this->conn;
	}
}

?>
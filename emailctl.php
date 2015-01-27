<?php  
$dsn = 'mysql:host=localhost;dbname=gbrf';
$username = 'root';
$password = '';


function nextPK() {
	$nextPK = 1;
	try {
	  	$conn = new PDO( $GLOBALS['dsn'], $GLOBALS['username'], $GLOBALS['password']);
	  	$conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	    $stmt = $conn->prepare("SELECT MAX(ID) FROM user"); 
	    $stmt->execute();

	 	$result = $stmt->fetchColumn();

	 	if($result > 0){
	 		$nextPK += $result;
	 	}
	}
	catch(PDOException $e) {
	    echo "Error: " . $e->getMessage();
	}
	$conn = null;
	return $nextPK;
}

function checkEmail($email) {
	$flag = TRUE;
	try {
	  	$conn = new PDO( $GLOBALS['dsn'], $GLOBALS['username'], $GLOBALS['password']);
	  	$conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	    $stmt = $conn->prepare("SELECT EMAIL FROM user where EMAIL = '$email'"); 
	    $stmt->execute();

	 	$result = $stmt->fetchColumn();
	 	
	 	if($result != null){
	 		$flag = FALSE;
	 	}
	}
	catch(PDOException $e) {
	    echo "Error: " . $e->getMessage();
	}
	$conn = null;
	return $flag;
}

function save($email){
		try {
			$emailCheck = checkEmail($email);
			if($emailCheck == TRUE){
				$nextPK = nextPK();
				$conn = new PDO( $GLOBALS['dsn'], $GLOBALS['username'], $GLOBALS['password']);
				$sql = "INSERT INTO user VALUES($nextPK,'','$email')";
				$conn->exec($sql);
			}
	    }catch(Exception $e){
	   		 echo $sql . "<br>" . $e->getMessage();
	    }
		$conn = null;
}
?>
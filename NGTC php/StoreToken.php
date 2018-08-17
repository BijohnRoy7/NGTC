<?php 

	require "Connection.php";
	
	//$token = "token";
	$token = $_POST["token"];
	
	$sql = "SELECT * FROM user_tokens WHERE token = '".$token."'; ";
	$result = mysqli_query($con, $sql);
	
	$response = array();
	
	if(mysqli_num_rows($result)>0){
		$response["message"] = "Token Already Stored";
	}else{
		
		$sql = "INSERT INTO user_tokens VALUES ('".$token."') ;";
		$result = mysqli_query($con, $sql);
		if($result){
			$response["message"] = "Token Stored successfully";

		}else{
			$response["message"] = "failed to Store Token";

		}
		
	}
	echo json_encode($response);
	mysqli_close($con);
	

?>
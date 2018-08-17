<?php

require "Connection.php";
	
	if($con){	
		
		//$image_id = 9;
		$image_id = $_POST["image_id"];
		
		$sql = "delete from item_info where id = '".$image_id."' ;";
		$result = mysqli_query($con, $sql);
		
		if($result){
			
			$response = "Deleted successfully";
			echo json_encode(array("response"=>$response));
			
		}else{
			$response = "Failed to delete. Please try later";
			echo json_encode(array("response"=>$response));
			
		}
		
	}else{
		$response = "Connection Failed ";
		echo json_encode(array("response"=>$response) );
	}
	
	mysqli_close($con);


?>
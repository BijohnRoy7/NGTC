<?php

	require "Connection.php";
	
	if($con){
			
		$sql = "Select * FROM item_info ; ";
		$result = mysqli_query($con, $sql);
		$response = array();

		while($row = mysqli_fetch_array($result)){
			
			$id = $row["id"];
			$image_name = $row["image_name"];
			$actual_path = $row["actual_path"];
			
			array_push($response, array("id"=>$id, "image_name"=>$image_name, "actual_path"=>$actual_path ) );
			
		}	
		
		echo json_encode($response);
		
	}else{
		$response = "Connection Failed ";
		echo json_encode(array("response"=>$response) );
	}
	
	mysqli_close($con);

?>
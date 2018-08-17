<?php

	require "Connection.php";
	
	if($con){
			
		$sql = "Select * FROM contact_us ; ";
		$result = mysqli_query($con, $sql);
		$response = array();

		while($row = mysqli_fetch_array($result)){
			
			
			$address = $row["address"];
			$contact_no1 = $row["contact_no1"];
			$contact_no2 = $row["contact_no2"];
			
			array_push($response, array("address"=>$address, "contact_no1"=>$contact_no1, "contact_no2"=>$contact_no2 ) );
			
		}	
		
		echo json_encode($response);
		
	}else{
		$response = "Connection Failed ";
		echo json_encode(array("response"=>$response) );
	}
	
	mysqli_close($con);

?>
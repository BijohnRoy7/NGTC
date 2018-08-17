<?php

require "Connection.php";
	
	if($con){
		/*	
		$buyer_name = "buyer_name";
		$contact_no = "contact_no";
		$quantity = "quantity";
		$city = "city";
		$item_id = "item_id";
		*/
		
		$buyer_name = $_POST["buyer_name"];
		$contact_no = $_POST["contact_no"];
		$quantity = $_POST["quantity"];
		$city = $_POST["city"];
		$item_id = $_POST["item_id"];
		
		
		$sql = " INSERT INTO orders (buyer_name, contact_no, quantity, city, item_id ) VALUES ( '".$buyer_name."', '".$contact_no."', '".$quantity."', '".$city."', '".$item_id."' ) ";
		$result = mysqli_query($con, $sql);
		
		if($result){
			$response = "Ordered Successfully ";
			echo json_encode(array("response"=>$response) );
		}else{
			$response = "Failed to Order ";
			echo json_encode(array("response"=>$response) );
		}
		
		
	}else{
		$response = "Connection Failed ";
		echo json_encode(array("response"=>$response) );
	}
	
	mysqli_close($con);


?>
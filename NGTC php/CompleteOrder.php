
<?php

	require "Connection.php";

	if($con){
		
		$item_id = $_POST["item_id"];
		$order_id = $_POST["order_id"];
		//$item_id = '6';
		
		$sql = "update`item_info` set sold = sold+1 where id= '".$item_id."'; ";
		$result = mysqli_query($con, $sql);
		
		if($result){
			
			$sql1 = "Delete from orders where order_id = '".$order_id."'; ";
			$result1 = mysqli_query($con, $sql1);
			
			if($result1){
				$response = "Order Completed Successfully";
				echo json_encode(array("response"=>$response));
			}else{
				$response = "Failed To Update Database Properly. Please Try Later...";
			echo json_encode(array("response"=>$response));
			}
			
			
			
		}else{
			$response = "Failed To Update Database. Please Try Later...";
			echo json_encode(array("response"=>$response));
		}
		
		mysqli_close($con);
		
	}

?>
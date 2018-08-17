
<?php

	require "Connection.php";

	if($con){
		
		//$order_id = 3;
		$order_id = $_POST["order_id"];
		
		$sql = "Delete from orders where order_id = '".$order_id."'; ";
		$result = mysqli_query($con, $sql);
		
		if($result){
			$response = "Order Deleted Succesfully";
			echo json_encode(array("response"=>$response));
		}else{
			$response = "Failed To Delete. Try Later...";
			echo json_encode(array("response"=>$response));
		}
		
		mysqli_close($con);
		
	}

?>
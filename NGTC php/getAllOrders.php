<?php


	require "Connection.php";

	if($con){
		
		$sql = "SELECT item_info.image_name, item_info.id, orders.order_id, orders.buyer_name, orders.contact_no, orders.quantity, orders.city 
				FROM item_info, orders WHERE item_info.id = orders.item_id";
		$result = mysqli_query($con, $sql);
		$response = array();
		
		while($row = mysqli_fetch_array($result)){
			
			$id = $row["id"];
			$image_name = $row["image_name"];
			$order_id = $row["order_id"];
			$buyer_name = $row["buyer_name"];
			$contact_no = $row["contact_no"];
			$quantity = $row["quantity"];
			$city = $row["city"];
			
			array_push($response, array( "item_id"=>$id, "image_name"=>$image_name, "order_id"=>$order_id, "buyer_name"=>$buyer_name, 
								"contact_no"=>$contact_no, "quantity"=>$quantity, "city"=>$city,  ) );
			
		}
		
		echo json_encode($response);
		mysqli_close($con);
	}
	

?>
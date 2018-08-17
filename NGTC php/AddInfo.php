<?php

	require "Connection.php";
	
	if($con){
		
/*		
		$image_title = "image_title";
		$name = "name";
*/
		$image = $_POST["image"];
		$image_title = $_POST["image_title"];
		$name = $_POST["name"];

		
		$upload_path = "images/$image_title.jpg";
		$actual_Image_path = "http://192.168.43.166/NGTC/".$upload_path; //creating image store path
		
		$sql = "INSERT INTO item_info (image_title, image_name, actual_path, sold) VALUES ('".$image_title."', '".$name."', '".$actual_Image_path."', '0') ; ";
		$result = mysqli_query($con, $sql);
		
		if($result){
			file_put_contents( $upload_path, base64_decode($image) );
			$response = "Information added successfully";
			echo json_encode(array("response"=>$response) );
			
		}else{
			$response = "Failed to add information";
			echo json_encode(array("response"=>$response) );
		
		}
		
	}else{
		$response = "Connection Failed ";
		echo json_encode(array("response"=>$response) );
	}
	
	mysqli_close($con);

?>
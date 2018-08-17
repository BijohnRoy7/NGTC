<?php

	require "connection.php";
	
	
	$title = $_POST["title"];
	$message = $_POST["message"];
	
	$path_to_fcm = "https://fcm.googleapis.com/fcm/send";
	$server_key = "AAAAYU6hIbQ:APA91bEnnnQTYI4j3nD6Po5TNXVpiA5-iKzWX-o0pheYdN5dZQfzkrO0mhQt8b8r60e18tlhuXMealbI023R8GC0cYKxnM8L68kgkIPODrARzEWIGpiXNZu1ohzU_w5YRd6t3cWbr3S7BYS7ZFjpDK-1fxZTiASD2g";
	
	
	$sql = "SELECT token FROM user_tokens;";
	$tokens = array();
	
	
	#############FOR getting all the tokens from the server #################
	if ($result=mysqli_query($con,$sql))
	  { 
	  // Fetch one and one row
	  while ($row=mysqli_fetch_row($result))
		{
			$tokens[] = $row[0];	
			
		}
	  mysqli_free_result($result);// Free result set
	}
	
	
	########## Sending notification to all tokens one by one ##############
	for($i=0; $i<sizeof($tokens);$i++){
		//echo $tokens[$i];
		//echo "               --------------------                 ";
		
		$token = $tokens[$i];
		echo $token."-----------------------------------------------------";
		
		########### prep the bundle ###########
		$msg = array
                        (
                        'body'  => $message,
                        'title' => $title
						
                        //'icon'  => 'myicon',/*Default Icon*/
                        //'sound' => 'mySound'/*Default sound*/
                        );
		//echo "$$$$$$$$$$$$$$$ ".$title;				
		
		$fields = array
                        (
                        'to'            => $token,
                        'notification'  => $msg
                        );	
						
			
		$payload = json_encode($fields);
		
		$headers  = array(
							"Authorization:key=".$server_key,
							"Content-type:application/json" 
						);	
			
			#Send Reponse To FireBase Server
            $curl_session = curl_init();
			curl_setopt($curl_session, CURLOPT_URL, $path_to_fcm);
			curl_setopt($curl_session, CURLOPT_POST, true);
			curl_setopt($curl_session, CURLOPT_HTTPHEADER, $headers);
			curl_setopt($curl_session, CURLOPT_RETURNTRANSFER, true);
			curl_setopt($curl_session, CURLOPT_SSL_VERIFYPEER, false);
			curl_setopt($curl_session, CURLOPT_IPRESOLVE, CURL_IPRESOLVE_V4);
			curl_setopt($curl_session, CURLOPT_POSTFIELDS, $payload);
			
			$result1 = curl_exec($curl_session);
			curl_close($curl_session);			
		
	}
	
	
	mysqli_close($con);
	
?>
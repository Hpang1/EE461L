<?php

require "init.php";

$quiz_name = $_POST['quiz_name'];
$quiz_creator = $_POST['quiz_creator'];
$quiz_object = $_POST['quiz_object'];
    
	
$sql = "INSERT INTO quiz (quiz_name, quiz_creator, quiz_object) VALUES ('$quiz_name', 	'$quiz_creator', '$quiz_object')";

if(mysqli_query($con,$sql)){
    echo 'success';
} else {
    echo 'failure';
}
mysqli_close($con);

?>	
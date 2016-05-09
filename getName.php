<?php header('content-type: application/json; charset=utf-8');

require "init.php";

$quiz_name = $_GET['quiz_name'];

$sql = "SELECT quiz_object FROM quiz WHERE quiz_name='$quiz_name';";

$result = mysqli_query($con, $sql);

$output = array();

while($row = mysqli_fetch_array($result)){
	array_push($output, $row[0]);
}

echo json_encode($output);

mysqli_close($con);
?>
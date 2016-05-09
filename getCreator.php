<?php header('content-type: application/json; charset=utf-8');

require "init.php";

$quiz_creator = $_GET['quiz_creator'];

$sql = "SELECT quiz_object FROM quiz WHERE quiz_creator='$quiz_creator';";

$result = mysqli_query($con, $sql);

$output = array();

while($row = mysqli_fetch_array($result)){
	array_push($output, $row[0]);
}

echo json_encode($output);

mysqli_close($con);
?>
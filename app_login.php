<?php

header('Access-Control-Allow-Origin: *');
include("conn.php"); 


$data = array(); 
$s_email = $_REQUEST['username']; 
$s_password = $_REQUEST['password'];

    $sql = "SELECT * FROM tb_user WHERE username='$s_email' AND password='$s_password' ";
    $objQuery = mysqli_query($conn,$sql)or die(mysql_error()); 
    $count = mysqli_num_rows($objQuery);

            if ($count>0)
            {
             if($row = mysqli_fetch_array($objQuery))
            {
                     if ($row['password'] == $s_password){
            
                         $data = array('nameis' => $row['fullname']);
                        // $data ="Success";
             }
             else
                     {
                 $data = "Not Found is1";
                  }
            }
            }
            else
            {
                $data = "Not Found is 2 ";
            }
            echo json_encode($data);

?>
<?php  
include 'emailctl.php';
error_reporting( error_reporting() & ~E_NOTICE );
$email = isset($_POST['email']) ? $_POST['email'] : '';

if ($email != null) {
    $subject ='I Accept Invitaion';
    $to = 'infogbrf@gmail.com';
    $message = $email.' has accepted Invitation.';
    $headers  = 'From: infogbrf@gmail.com' . "\r\n" .
    'Reply-To: infogbrf@gmail.com' . "\r\n" .
    'MIME-Version: 1.0' . "\r\n" .
    'Content-type: text/html; charset=iso-8859-1' . "\r\n" .
    'X-Mailer: PHP/' . phpversion();
    try{
        mail($to,$subject,$message,$headers);

        $message = "<html><body><b>Dear Guest of Honour,</b><br>Thank you for showing your interest and accepting the Invite.<br>We will stay in touch with you for the updates.<br><br><b>Administrator,</b><br>Global Book Release Forum<br><img src=http://globalbookreleaseforum.com/GBRF/img/logo.png height=67 width=184><br><a href=http://globalbookreleaseforum.com/GBRF/ target=_blank>www.globalbookreleaseforum.com</a></body></html>";
        $subject = 'Global Book Release Forum';
        mail($email,$subject,$message,$headers);
    }catch (Exception $e) {
        echo 'Caught exception: ',  $e->getMessage(), "\n";
    }
    save($email);
    header('Location: index-wl.php');    
}   
?>
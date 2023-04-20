<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>ChatRoom</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="/styles.css">
<script src="https://code.jquery.com/jquery-3.6.4.min.js" integrity="sha256-oP6HI9z1XaZNBrJURtCoUT5SUnxFr8s3BzRl+cbzUq8=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script src="/socket.io/socket.io.js"></script>
<script src="/index.js"></script>
</head>
<body class="bg-dark">

 <div id="name-from" class="bg-primary d-flex align-items-center">

  <div class="container-fluid">
  
  <div class="row">
  <div class="col-md-4 offset-md-4">
  
  <div class="input-group">
   <input type="text" placeholder="Enter your name" id="name-value" class="form-control" autofocus>
   <div class="input-group-append">
    <button class="btn btn-dark" id="login">Enter</button>
   
   </div>
  </div>
  
  </div>
  
  </div>
  
  </div>
 
 </div>
 
 <div id="chat-room" class="d-none">
 
   <div class="container-fluid">
     <div class="row">
     <div class="col-md-6 offset-md-3">
     <div class="card">
     <div class="card-body">
     
     <div class="input-group">
   <input type="text" placeholder="Enter your message" id="message-value" class="form-control" autofocus>
   <div class="input-group-append">
    <button class="btn btn-dark" id="send-btn">send</button>
    <button class="btn btn-dark" id="logout">Logout</button>
    
   
   </div>
  </div>
  
     
     <div class="table-responsive">
     <table id="message-container-table">
     
     </table>
     
     </div>
     </div>
     </div>
     </div>
     
     </div>
   
   </div>
 </div>

</body>
</html>
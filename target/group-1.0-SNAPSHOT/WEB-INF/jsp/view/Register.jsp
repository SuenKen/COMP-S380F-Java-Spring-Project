<!DOCTYPE html>
<html>
<title>Register Account</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <script>
        const queryString = window.location.search;
        const urlParams = new URLSearchParams(queryString);
        const status = urlParams.get('status');
        console.log(status);
        if (status == "fail") {
            alert("User already exist ");
        } 


    </script>
<body>


<!-- Navbar (sit on top) -->
<div class="w3-top">
  <div class="w3-bar w3-white w3-wide w3-padding w3-card">
    <a href="/group" class="w3-bar-item w3-button"><b>Gp</b> Project</a>
    <!-- Float links to the right. Hide them on small screens -->
    <div class="w3-right w3-hide-small">
        <security:authorize access="hasRole('USER') or hasRole('ADMIN')">
      	<c:url var="logoutUrl" value="/logout"/>
        <form action="${logoutUrl}" method="post">
            <a href="/group" class="w3-bar-item w3-button">Home</a>
             <security:authorize access="hasRole('ADMIN')">
            <a href="/group/admin" class="w3-bar-item w3-button">Admin Page</a>
            </security:authorize>
            <a href="/group/user/change?username=<security:authentication property="principal.username" />" class="w3-bar-item w3-button">Profile</a>
            <a href="/group/user/favorite?username=<security:authentication property="principal.username" />" class="w3-bar-item w3-button">Favorite </a>
            <a href="/group/cart?username=<security:authentication property="principal.username" />&status=" class="w3-bar-item w3-button">Cart </a>
            <a href="/group/user/orderHistory?username=<security:authentication property="principal.username" />" class="w3-bar-item w3-button">Order History </a>
            <input type="submit" value="Log out" class="w3-bar-item w3-button" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        </form>  
        </security:authorize>
        <security:authorize access="!hasAnyRole('USER','ADMIN')">
            <a href="/group" class="w3-bar-item w3-button">Home</a>
            <a href="/group/login" class="w3-bar-item w3-button">Login</a>
            <a href="/group/register" class="w3-bar-item w3-button">Register</a>
        </security:authorize>
    </div>
  </div>
</div>


<!-- Page content -->
<div class="w3-content w3-padding" style="max-width:1564px">
  <!-- About Section -->
  <div class="w3-container w3-padding-32" id="about">
    <h3 class="w3-border-bottom w3-border-light-grey w3-padding-16">Register Account</h3>
    <p>  
<form:form method="POST" 
           modelAttribute="registeruser">

    <form:input type="text" path="username" class="w3-input w3-section w3-border" placeholder="Username" required="true"/>
    <form:input type="text" path="password" class="w3-input w3-section w3-border" placeholder="Password" required="true"/>
    <form:input type="text" path="fullname" class="w3-input w3-section w3-border" placeholder="FullName" required="true"/>
    <form:input type="text" path="phone"  class="w3-input w3-section w3-border" placeholder="Phone No.  (Format: 12345678)" maxlength="8" pattern="[0-9]{8}" required="true"/>

    <form:input type="text" path="address"  class="w3-input w3-section w3-border" placeholder="Delivery Address" required="true"/>
    

    <input type="submit" value="Register Account" class="w3-button w3-black w3-section"/>
    <a href="<c:url value="/login" />" class="w3-button w3-black w3-section">Back</a>
</form:form>
        
    </p>
  </div>

<!-- End page content -->
</div>

</body>
</html>

<!DOCTYPE html>
<html>
<title>Login</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <script>
        const queryString = window.location.search;
        const urlParams = new URLSearchParams(queryString);
        const status = urlParams.get('status');
        console.log(status);
        if (status == "success") {
            alert("User create success ");
        } 
        else if (status == "fail") {
            alert("Login fail,Try again ");
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

  <!-- Contact Section -->
  <div class="w3-container w3-padding-32" id="contact">
    <h3 class="w3-border-bottom w3-border-light-grey w3-padding-16">Login</h3>
    <p>
    <form action="login" method='POST'>
      <input class="w3-input w3-border" type="text" placeholder="User:" required name="username">
      <input class="w3-input w3-section w3-border" type="password" placeholder="Password" required name="password">
      Remember Me:<input  type="checkbox" name="remember-me" style="width:50px;transform: scale(1.5)">
	  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/><br/>
      <button class="w3-button w3-black w3-section" type="submit">
        <i class="fa fa-paper-plane"></i> Login
      </button>
      <a href="<c:url value="/register" />" class="w3-button w3-black w3-section">Register</a>
    </form>
    </p>
  </div>


<!-- End page content -->
</div>

</body>
</html>

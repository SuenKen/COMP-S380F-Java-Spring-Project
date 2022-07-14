<!DOCTYPE html>
<html>
    <title>Update Personal information</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <script>
        const queryString = window.location.search;
        const urlParams = new URLSearchParams(queryString);
        const status = urlParams.get('status');
        console.log(status);
        if (status == "success") {
            alert("User Information update success ");
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
                <h3 class="w3-border-bottom w3-border-light-grey w3-padding-16">Update Personal information</h3>
                <p>  
                    <security:authorize access="principal.username=='${users.username}'">
                    <form:form method="POST" 
                               modelAttribute="users" action="change/${users.username}">
                        <form:label path="username" >Username</form:label><br/><br/>
                        <form:label path="username" > ${users.username}</form:label><br/><br/>
                        <form:label path="password">Password</form:label><br/>
                        <form:input type="text" path="password"  class="w3-input w3-section w3-border"/>
                        <form:label path="fullname">FullName</form:label><br/>
                        <form:input type="text" path="fullname" class="w3-input w3-section w3-border"/><br/>
                        <form:label path="phone">Phone No.</form:label><br/>
                        <form:input type="text" path="phone" class="w3-input w3-section w3-border" maxlength="8" pattern="[0-9]{8}"/><br/>
                        <form:label path="address">Delivery Address</form:label><br/>
                        <form:input type="text" path="address" class="w3-input w3-section w3-border"/>

    
                        <form:checkbox path="roles" value="ROLE_USER" style="width:50px;transform: scale(1.5)" hidden = "true"/>
                        <form:checkbox path="roles" value="ROLE_ADMIN" style="width:50px;transform: scale(1.5)" hidden = "true"/><br/>

                        <input type="submit" value="Edit User" class="w3-button w3-black w3-section"/>
                        <a href="<c:url value="/" />" class="w3-button w3-black w3-section">Back</a>
                    </form:form>
</security:authorize>
                    <security:authorize access="principal.username!='${users.username}'">
                        You can't edit other User Account<br/>
                        <a href="<c:url value="/" />" class="w3-button w3-black w3-section">Back</a>
                    </security:authorize>
                </p>
            </div>

            <!-- End page content -->
        </div>

    </body>
</html>

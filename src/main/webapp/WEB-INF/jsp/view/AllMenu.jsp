<!DOCTYPE html>
<html>
<title>List Account</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
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
    <h3 class="w3-border-bottom w3-border-light-grey w3-padding-16">Menu List</h3>
    <p>  

        <c:if test="${fn:length(menu) == 0}">
            <p>There is no Menu yet.</p>
        </c:if>
        <c:if test="${fn:length(menu) > 0}">
            <table border = "1"  style="font-size: 20px;" >
                <tr><th>Menu Name</th><th>Price</th><th>Available</th><th>Action</th></tr>
                        <c:forEach var="item" items="${menu}">
                    <tr><td> ${item.name}</td><td>${item.price}</td><td>${item.available}</td>

                        <td>
                            [<a href="<c:url value="/admin/menu/edit/${item.id}" />">Edit</a>] 
                            [<a href="<c:url value="/admin/menu/delete/${item.id}" />">Delete</a>]</td></tr>
                        </c:forEach>
            </table>


        </c:if>
        <p><a href="<c:url value="/admin/menu/add" />" class="w3-button w3-black w3-section">Add Menu</a> <a href="<c:url value="/admin" />" class="w3-button w3-black w3-section">Back to Admin Page</a></p>
        
    </p>
  </div>

<!-- End page content -->
</div>

</body>
</html>

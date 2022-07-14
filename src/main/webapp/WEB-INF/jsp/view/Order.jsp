<!DOCTYPE html>
<html>
    <title>Order</title>
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
      <h3 class="w3-border-bottom w3-border-light-grey w3-padding-16">Your Order</h3>
      
              <c:choose>
                  <c:when test="${fn:length(orderDatabase) == 0}">
                      <i>There are no menu in the shooping cart.</i>
                  </c:when>
                  <c:otherwise>
                            <table border='1'>
          <tr>
              <th>Item name</th>
              <th>Qty</th>
              <th>Price</th>
              <th>Total Price</th>
          </tr>
                      <c:forEach items="${orderDatabase}" var="entry">
                          <tr>
                          <td>${entry.menu_name}</td>
                          <td>${entry.qty}</td>
                         <td>${entry.price}</td>
                          <td>${entry.qty * entry.price}</td>
                          </tr>
                          
                      </c:forEach>
                          </table>
                                                <h3>
                    Personal Information<br/>
                    Fullname: ${orderDatabase[0].fullname}<br/>
                    Phone No.: ${orderDatabase[0].phone}<br/>
                    Delivery Address: ${orderDatabase[0].address}<br/>
</h3>
                  </c:otherwise>
              </c:choose>
    </div>
  </div>
    </body>
</html>

<!DOCTYPE html>
<html>
    <title>Menu #${menu.id}: <c:out value="${menu.name}" /></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <script>
        const queryString = window.location.search;
        const urlParams = new URLSearchParams(queryString);
        const result = urlParams.get('result');
        const status = urlParams.get('status');
        if (result == 1) {
            alert("Added to Favorite ");
        } else if (result == 0) {
            alert("You add this Menu already.");

        }
        
        if (status == "success") {
            alert("Deleted Favorite ");
        } else if (status == "fail") {
            alert("You haven't add this Menu.");

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
                <h3 class="w3-border-bottom w3-border-light-grey w3-padding-16">Menu #${menu.id}: <c:out value="${menu.name}" /></h3>
                <security:authorize access="hasAnyRole('USER','ADMIN')">
                    <form:form method="POST" 
                               modelAttribute="favorite" action="${menu.id}/addfavorite">

                        <form:input type="hidden" path="menu_id" value="${menu.id}" />
                        <form:input type="hidden" path="name" value="${menu.name}" />
                        <input type="hidden" id="username" name="username" value="<security:authentication property="principal.username" />"/>

                        <input type="submit" value="Add Favorite" class="w3-button w3-black w3-section"/>
                    </form:form>
                    <a href="${menu.id}/deletefavorite?username=<security:authentication property="principal.username" />"class="w3-button w3-black w3-section">Delete Favorite</a>
                </security:authorize>
                <p>  
                    <security:authorize access="hasRole('ADMIN')">
                        <a href="<c:url value="/admin/menu/edit/${menu.id}" />"class="w3-button w3-black w3-section">Edit</a>

                        <a href="<c:url value="/admin/menu/delete/${menu.id}" />"class="w3-button w3-black w3-section">Delete</a>
                    </security:authorize>   

                    <br /><br />
                    Menu Name - <c:out value="${menu.name}" /><br /><br />

                    <c:if test="${menu.numberOfAttachments > 0}">
                        Attachments:<br/>
                        <c:forEach items="${attachments}" var="attachment"
                                   varStatus="status">

                            <img src="<c:url value="/menu/detail/${menuId}/attachment/${attachment.name}" />" width="30%" height="30%"/>

                        </c:forEach>
                        <br /><br />
                    </c:if>




                    Description :<c:out value="${menu.description}" /><br /><br />
                    Price  :<c:out value="${menu.price}" /><br /><br />
                    <security:authorize access="hasAnyRole('USER','ADMIN')">
                        <form:form modelAttribute="cart" action="/group/cart">
                            <form:input type="hidden" path="menuId" value="${menu.id}"/>
                            <form:input type="hidden" path="menuName" value="${menu.name}"/>
                            <form:input type="hidden" path="price" value="${menu.price}"/>
                            <input type="hidden" id="username" name="username" value="<security:authentication property="principal.username" />"/>
                            Quantity: <form:input type="number" path="qty" min="1" required="true"/>
                            <input type="submit" value="Add to Shopping Cart" />
                        </form:form>
                    </security:authorize>
                    Comments:
                <table border='1' style="width: 100%;  border-collapse: collapse;  text-align: center;

                       ">
                    <tr><th style="width: 30%;">Username</th><th>Comment</th></tr>
                            <c:if test="${fn:length(Allcomments) == 0}">
                        <tr><td colspan="2">There is no Comment yet.</td></tr>
                    </c:if>
                    <c:forEach var="comment" items="${Allcomments}">

                        <tr><td>${comment.username} </td><td>${comment.context}     
                                <security:authorize access="hasRole('USER') ">
                                    <security:authorize access="hasRole('ADMIN') or principal.username=='${comment.username}'">
                                        [<a href="<c:url value="/menu/detail/${menu.id}/comment/delete?id=${comment.id}" />">Delete Comment</a>]
                                    </security:authorize></security:authorize></td></tr>      
                            </c:forEach>    
                </table>
                <security:authorize access="hasAnyRole('USER','ADMIN')">
                    <form:form method="POST" 
                               modelAttribute="comments">

                        <form:input type="hidden" path="menuId" value="${menu.id}" />
                        <input type="hidden" id="username" name="username" value="<security:authentication property="principal.username" />"/>
                        <form:input type="text" path="context" class="w3-input w3-section w3-border" placeholder="Comments" required="true"/>

                        <input type="submit" value="Leave Comments" class="w3-button w3-black w3-section"/>
                    </form:form>
                </security:authorize>
                <br /><a href="<c:url value="/" />" class="w3-button w3-black w3-section">Return to list Menu</a>

                </p>
            </div>

            <!-- End page content -->
        </div>

    </body>
</html>

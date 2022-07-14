<!DOCTYPE html>
<html>
<title>Edit Menu</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<script>
updateList = function() {
    var input = document.getElementById('attachments');
    var output = document.getElementById('fileList');
    var children = "";
    for (var i = 0; i < input.files.length; ++i) {
        children += '<li>' + input.files.item(i).name + '</li>';
    }
    output.innerHTML = '<ul>'+children+'</ul>';
}
</script>
<style>
    .file-area {
        width: 100%;
        position: relative;
        font-size: 18px;
    }
    .file-area input[type=file] {
        position: absolute;
        width: 100%;
        height: 100%;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        opacity: 0;
        cursor: pointer;
    }
    .file-area .file-dummy {
        width: 100%;
        padding: 50px 30px;
        border: 2px dashed #ccc;
        background-color: #fff;
        text-align: center;
        transition: background 0.3s ease-in-out;
    }
    .file-area .file-dummy .success {
        display: none;
    }
    .file-area:hover .file-dummy {
        border: 2px dashed #1abc9c;
    }
    .file-area input[type=file]:valid + .file-dummy {
        border-color: #1abc9c;
    }
    .file-area input[type=file]:valid + .file-dummy .success {
        display: inline-block;
    }
    .file-area input[type=file]:valid + .file-dummy .default {
        display: none;
    }
</style>
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
    <h3 class="w3-border-bottom w3-border-light-grey w3-padding-16">Edit Menu</h3>
    <p>  
<form:form method="POST" modelAttribute="menu" enctype="multipart/form-data">

    <form:input type="text" path="name" class="w3-input w3-section w3-border" placeholder="Menu Name"/><br/> 
    <form:textarea path="description" rows="5" cols="30" class="w3-input w3-section w3-border" placeholder="Description"/><br/>
    <form:label path="price" style ="font-size: 20px; ">Price:</form:label><br/>
    <form:input type="number" path="price" class="w3-input w3-section w3-border" placeholder="Price" /><br/>


    <label  style ="font-size: 20px; ">Photo Attachment:</label><br/><br/>
                              <c:forEach items="${menu.attachments}" var="attachment"
                                   varStatus="status">

                            <img src="<c:url value="/menu/detail/${menuId}/attachment/${attachment.name}" />" width="30%" height="30%"/>
                            [<a href="<c:url value="/admin/menu/detail/${menuId}/attachment/delete/${attachment.name}" />">Delete</a>]
                        </c:forEach>             
                        <br/><br/>
 
        <label  style ="font-size: 20px; ">Add Photo Attachment:</label><br/><br/>
    <div class="file-area">
<input type="file" multiple name="attachments" id="attachments" onchange="javascript:updateList()" accept="image/*" />
    <div class="file-dummy">
        <span class="success">Drop File or Click Here to upload</span>
    </div>
</div>
<p>Selected files:</p>
<div id="fileList"></div>
    <form:label path="available" style ="font-size: 20px; ">Available:</form:label>
    <form:checkbox path="available" value="Yes" style="width:50px;transform: scale(1.5)"/>


   
    <br /><br />
    <input type="submit" value="Update Menu" class="w3-button w3-black w3-section"/>
    <a href="<c:url value="/admin/menu/view" />" class="w3-button w3-black w3-section">Back</a><br/>
     <a href="<c:url value="/admin" />" class="w3-button w3-black w3-section">Back to Admin Page</a>
</form:form>
        
    </p>
  </div>

<!-- End page content -->
</div>

</body>
</html>

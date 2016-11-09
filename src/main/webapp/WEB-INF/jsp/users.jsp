<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<security:authorize access="!isAuthenticated()">
			<title>Sign Up</title>
		</security:authorize>
		<security:authorize access="hasAuthority('ADMIN')">
			<title>Create and Manage Users</title>
		</security:authorize>
	</head>
	<body>


		<security:authorize access="!isAuthenticated()">
			<form method="get" action="addUser">
				<p>Sign Up</p>
				<div>
					Name<input type="text" name="name">
					Surname<input type="text" name="surname">
					Email<input type="email" name="email">
					Password<input type="text" name="password">
					<p><input type="submit" value="Submit"></p>
				</div>
			</form>
		</security:authorize>

		<security:authorize access="hasAuthority('ADMIN')">
			<p>Manage Users:</p>
			<c:forEach items="${userList}" var="user">
				${user.id}
				${user.name}
				${user.surname}
				${user.email}
				<form action="deleteUser">
					<input name="id" value="${user.id}" type="hidden" />
					<input type="submit" value="Delete" />
				</form>
			</c:forEach>		
		
			<form method="get" action="addAnyUser">
				<p>Add User</p>
				<div>
					Name<input type="text" name="name">
					Surname<input type="text" name="surname">
					Email<input type="email" name="email">
					Password<input type="text" name="password">
					Role<select name="role" >
							<option value="USER">USER</option>
							<option value="ADMIN">ADMIN</option>
					<p><input type="submit" value="Submit"></p>
				</div>
			</form>
		</security:authorize>
		
	</body>
</html>
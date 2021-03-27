<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">

<%@ include file="head.jsp"%>

<body>

<jsp:include page="navigation.jsp">
    <jsp:param name="title" value="Customer"/>
</jsp:include>

<div class="container">
    <div class="row py-2">
        <div class="col-12">
            <c:url value="/customer/new" var="customerNewUrl"/>
            <a class="btn btn-primary" href="${customerNewUrl}">Add Customer</a>
        </div>

        <div class="col-12">
            <table class="table table-bordered my-2">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Name</th>
                    <th scope="col">Email</th>
                    <th scope="col">Phone</th>
                    <th scope="col">Address</th>
                    <th scope="col">Actions</th>
                </tr>
                </thead>
                <tbody>

                <c:choose>
                    <c:when test="${requestScope.customers.isEmpty()}">
                        <tr>
                            <td colspan="6">
                                No data
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="customer" items="${requestScope.customers}">
                            <tr>
                                <th scope="row">
                                    <c:out value="${customer.id}"/>
                                </th>
                                <td>
                                    <c:out value="${customer.name}"/>
                                </td>
                                <td>
                                    <c:out value="${customer.email}"/>
                                </td>
                                <td>
                                    <c:out value="${customer.phone}"/>
                                </td>
                                <td>
                                    <c:out value="${customer.address}"/>
                                </td>
                                <td>
                                    <c:url value="/customer/${customer.id}" var="customerUrl"/>
                                    <a class="btn btn-success" href="${customerUrl}"><i class="fas fa-edit"></i></a>
                                    <a class="btn btn-danger" href="#"><i class="far fa-trash-alt"></i></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@ include file="scripts.jsp"%>

</body>
</html>
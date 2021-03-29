<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">

<%@ include file="head.jsp"%>

<body>

<jsp:include page="navigation.jsp">
    <jsp:param name="title" value="Product"/>
</jsp:include>

<div class="container">
    <div class="row py-2">
        <div class="col-12">
            <c:url value="/product/new" var="productNewUrl"/>
            <a class="btn btn-primary" href="${productNewUrl}">Add Product</a>
        </div>

        <div class="col-12">
            <table class="table table-bordered my-2">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Name</th>
                    <th scope="col">Description</th>
                    <th scope="col">Actions</th>
                </tr>
                </thead>
                <tbody>

                <c:choose>
                    <c:when test="${requestScope.products.isEmpty()}">
                        <tr>
                            <td colspan="4">
                                No data
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="product" items="${requestScope.products}">
                            <tr>
                                <th scope="row">
                                    <c:out value="${product.id}"/>
                                </th>
                                <td>
                                    <c:out value="${product.name}"/>
                                </td>
                                <td>
                                    <c:out value="${product.description}"/>
                                </td>
                                <td>
                                    <c:url value="/product/${product.id}" var="productUrl"/>
                                    <c:url value="/product/delete/${product.id}" var="productDeleteUrl"/>
                                    <a class="btn btn-success" href="${productUrl}"><i class="fas fa-edit"></i></a>
                                    <form method="post" action="${productDeleteUrl}" class="d-inline">
                                        <button class="btn btn-danger">
                                            <i class="far fa-trash-alt"></i>
                                        </button>
                                    </form>
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
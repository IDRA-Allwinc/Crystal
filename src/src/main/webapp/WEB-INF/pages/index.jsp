<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<HTML lang="es-ES">
<html>
<head>
    <%@ include file="/WEB-INF/pages/shared/head.jsp" %>

    <sec:authorize access="isAuthenticated()">
        <script type="text/javascript">
            if (window.parent !== window.top ) {
                window.top.location.replace("/");
            }

        </script>
    </sec:authorize>
</head>
<body scroll="no" ng-app="crystal" class="pace-done">
<sec:authorize access="isAnonymous()">
    <%@ include file="/WEB-INF/pages/shared/login.jsp" %>
</sec:authorize>

<sec:authorize access="isAuthenticated()">
    <div id="wrapper">
        <div class="gray-bg">
            <%@ include file="/WEB-INF/pages/shared/header-bar.jsp" %>
        </div>
    </div>
</sec:authorize>

</body>
</html>


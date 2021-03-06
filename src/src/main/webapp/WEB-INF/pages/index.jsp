<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<HTML lang="es-ES">
<html>
<head>
    <%@ include file="/WEB-INF/pages/shared/head.jsp" %>

        <script type="text/javascript">
            var url = "<c:url value='/'/>";
            if (window.parent !== window.top) {
                window.top.location.replace(url);
            }
        </script>
</head>
<body scroll="no" ng-app="crystal" class="pace-done">
<sec:authorize access="isAnonymous()">
    <%@ include file="/WEB-INF/pages/shared/login.jsp" %>
</sec:authorize>

<sec:authorize access="isAuthenticated()">
    <%--<div id="wrapper">--%>
    <%--<div class="gray-bg">--%>
    <%--<%@ include file="/WEB-INF/pages/shared/header-bar.jsp" %>--%>
    <%@ include file="/WEB-INF/pages/shared/menu.jsp" %>
    <%@ include file="/WEB-INF/pages/shared/sharedSvc.jsp" %>
    <%--</div>--%>
    <%--</div>--%>
</sec:authorize>
</body>
</html>


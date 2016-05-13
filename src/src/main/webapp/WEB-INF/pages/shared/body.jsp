<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<% Authentication auth = SecurityContextHolder.getContext().getAuthentication();%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link href="https://framework-gb.cdn.gob.mx/assets/styles/main.css" rel="stylesheet">

    <script>
        function logout() {
            document.getElementById('logout').submit();
        }
    </script>
    <title></title>

</head>
<body>
<form id="logout" action="<c:url value='/logout'/>" method="post">
    <input type="hidden" id="token-csrf" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<main>
    <nav class="navbar navbar-inverse sub-navbar navbar-fixed-top">

        <div class="container">

            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#subenlaces">
                    <span class="sr-only"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <div>
                    <a class="navbar-brand" style="padding: 5px 5px !important; height: 35px !important;"
                       href="<c:url value='/login.html'/>" target="IContent">
                        CRYSTAL
                    </a>
                </div>
                <sec:authorize access="isAuthenticated()">
                    <div style="color:#fff !important; font-size: 12px; margin-left: -10px; clear: left; min-width: 400px; width: 400px; ">
                        <i class=""></i> <%=auth.getName()%> &nbsp;|&nbsp; Perfil: <%=auth.getAuthorities().iterator().next().getAuthority()%>
                    </div>
                </sec:authorize>

            </div>
            <div class="collapse navbar-collapse" id="subenlaces">
                <ul class="nav navbar-nav navbar-right">
                    <sec:authorize access="hasAuthority(T(com.crystal.model.shared.Constants).AUTHORITY_MANAGER)">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                               aria-expanded="false">Cat&aacute;logos<span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="<c:url value='/management/auditedEntity/index.html'/>"
                                       target="IContent"><i class="glyphicon glyphicon-asterisk"></i>&nbsp;Entes
                                    fiscalizados</a></li>
                                <li><a href="<c:url value='/management/supervisoryEntity/index.html'/>"
                                       target="IContent"><span class="glyphicon glyphicon-certificate"
                                                               aria-hidden="true"></span>&nbsp;&Oacute;rganos
                                    fiscalizadores</a></li>
                                <li><a href="<c:url value='/management/auditType/index.html'/>"
                                       target="IContent"><span class="glyphicon glyphicon-cog"
                                                               aria-hidden="true"></span>&nbsp;Tipos
                                    de auditor&iacute;a</a></li>
                                <li><a href="<c:url value='/management/eventType/index.html'/>"
                                       target="IContent"><span class="glyphicon glyphicon-dashboard"
                                                               aria-hidden="true"></span>&nbsp;Tipos
                                    de eventos</a></li>
                                <li><a href="<c:url value='/management/meetingType/index.html'/>"
                                       target="IContent"><span class="glyphicon glyphicon-bookmark"
                                                               aria-hidden="true"></span>&nbsp;Tipos de
                                    reuni&oacute;n</a></li>
                                <li><a href="<c:url value='/management/user/index.html'/>"
                                       target="IContent"><span class="glyphicon glyphicon-user"
                                                               aria-hidden="true"></span>&nbsp;Usuarios</a></li>
                            </ul>
                        </li>
                    </sec:authorize>
                    <sec:authorize access="hasAuthority(T(com.crystal.model.shared.Constants).AUTHORITY_DIRECTION)">
                        <%--<li class="dropdown">--%>
                            <%--<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"--%>
                               <%--aria-expanded="false">Reportes<span--%>
                                    <%--class="caret"></span></a>--%>
                            <%--<ul class="dropdown-menu" role="menu">--%>
                                <%--<li><a href="#" target="IContent"><i class="fa fa-shield"></i>Auditor&iacute;as</a></li>--%>
                            <%--</ul>--%>
                        <%--</li>--%>
                        <li>
                            <a href="<c:url value='/report/index.html'/>" target="IContent"><span
                                    class="glyphicon glyphicon-stats" aria-hidden="true"></span>&nbsp;Reportes</a>
                        </li>
                    </sec:authorize>


                    <sec:authorize
                            access="hasAnyAuthority(T(com.crystal.model.shared.Constants).AUTHORITY_LINK, T(com.crystal.model.shared.Constants).AUTHORITY_DGPOP)">
                        <li>
                            <a href="<c:url value='/shared/area/index.html'/>" target="IContent"><span
                                    class="glyphicon glyphicon-th" aria-hidden="true"></span>&nbsp;&Aacute;reas</a>
                        </li>
                        <li>
                            <a href="<c:url value='/previousRequest/letter/index.html'/>" target="IContent"><span
                                    class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>&nbsp;Requerimientos previos</a>
                        </li>
                        <li>
                            <a href="<c:url value='/audit/index.html'/>" target="IContent"><span
                                    class="glyphicon glyphicon-briefcase" aria-hidden="true"></span>&nbsp;Auditor&iacute;as</a>
                        </li>
                    </sec:authorize>

                    <sec:authorize access="hasAuthority(T(com.crystal.model.shared.Constants).AUTHORITY_DGPOP)">
                        <li>
                            <a href="<c:url value='/report/index.html'/>" target="IContent"><span
                                    class="glyphicon glyphicon-stats" aria-hidden="true"></span>&nbsp;Reportes</a>
                        </li>
                    </sec:authorize>


                    <sec:authorize access="isAuthenticated()">
                        <li><a href="javascript:document.getElementById('logout').submit()"><span
                                class="glyphicon glyphicon-log-out" aria-hidden="true"></span>&nbsp;Salir</a></li>
                    </sec:authorize>
                </ul>
            </div>
        </div>
    </nav>
</main>

<iframe id="IContent" name="IContent" src="<c:url value='/login.html'/>" frameborder="0" width="100%" height="1024px"
        scrolling="yes"></iframe>
<script src="https://framework-gb.cdn.gob.mx/gobmx.js"></script>
</body>
</html>
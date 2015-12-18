<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<% Authentication auth = SecurityContextHolder.getContext().getAuthentication();%>

<form id="logout" action="<c:url value="/logout" />" method="post" >
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
<nav class="navbar-default navbar-static-side" role="navigation">
    <div class="sidebar-collapse">
        <ul class="nav" id="side-menu">
            <li class="nav-header">
                <div class="dropdown profile-element">
                    <span>
                        <img alt="image" width="50" height="50" class="img-circle" src="${pageContext.request.contextPath}/assets/img/Logo.png" />
                    </span>
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        <span class="clear">
                            <span class="block m-t-xs">
                                <strong class="font-bold"><%=auth.getName()%></strong>
                            </span> <span class="text-muted block"> <%=auth.getAuthorities().iterator().next().getAuthority()%> <b class="caret"></b></span>
                        </span>
                    </a>
                    <ul class="dropdown-menu animated fadeInRight m-t-xs">
                        <li class="divider"></li>
                        <li><a href="javascript:document.getElementById('logout').submit()"><i class="fa fa-sign-out"></i>&nbsp;&nbsp;Salir</a></li>
                    </ul>
                </div>
                <div class="logo-element">
                    CR
                </div>
            </li>

            <sec:authorize access="hasAuthority('Administrador')">
                <li data-ng-class="mn.menu === 1 ? 'active' : ''">
                    <a href="#"><i class="fa fa-th"></i> <span class="nav-label">Cat&aacute;logos</span> </a>
                    <ul class="nav nav-second-level">
                        <li data-ng-class="mn.subMenu === 1001 ? 'active' : ''">
                            <a href="<c:url value='/management/user/index.html'/>">
                                <i class="fa fa-users"></i>Usuarios
                            </a>
                        </li>
                        <li data-ng-class="mn.subMenu === 1002 ? 'active' : ''">
                            <a href="<c:url value='/management/role/index.html'/>">
                                <i class="fa fa-cogs"></i>Perfiles
                            </a>
                        </li>
                    </ul>
                </li>
                <%--<li data-ng-class="mn.menu === 2 ? 'active' : ''">--%>
                    <%--<a href="#"><i class="fa fa-th"></i> <span class="nav-label">Cat&aacute;logos II</span> </a>--%>
                    <%--<ul class="nav nav-second-level">--%>
                        <%--<li data-ng-class="mn.subMenu === 1 ? 'active' : ''">--%>
                            <%--<a href="">--%>
                                <%--<i class="fa fa-shield"></i>Auditor&iacute;as II--%>
                            <%--</a>--%>
                        <%--</li>--%>
                    <%--</ul>--%>
                <%--</li>--%>
            </sec:authorize>
            <sec:authorize access="hasAuthority('Director')">
                <li>
                    <a href="#"><i class="fa fa-th"></i> <span class="nav-label">Reportes</span> </a>
                    <ul class="nav nav-second-level">
                        <li class="active">
                            <a href="">
                                <i class="fa fa-shield"></i>Auditor&iacute;as
                            </a>
                        </li>
                    </ul>
                </li>
            </sec:authorize>
        </ul>
    </div>
</nav>
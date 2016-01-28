<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="com.crystal.model.shared.Constants"%>
<% Authentication auth = SecurityContextHolder.getContext().getAuthentication();%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<form id="logout" action="<c:url value="/logout" />" method="post">
    <input type="hidden" id="token-csrf" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<nav class="navbar-default navbar-static-side" role="navigation" ng-init="mn.checkUrl= '<c:url value="/session/checkout.json"/>'; mn.extendUrl= '<c:url value="/session/extend.json"/>'; mn.logoutUrl= 'logout'">
    <div class="sidebar-collapse">
        <ul class="nav" id="side-menu">
            <li class="nav-header">
                <div class="dropdown profile-element">
                    <span>
                        <img alt="image" width="50" height="50" class="img-circle"
                             src="${pageContext.request.contextPath}/assets/img/Logo.png"/>
                    </span>
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        <span class="clear">
                            <span class="block m-t-xs">
                                <strong class="font-bold"><%=auth.getName()%>
                                </strong>
                            </span> <span
                                class="text-muted block"> <%=auth.getAuthorities().iterator().next().getAuthority()%> <b
                                class="caret"></b></span>
                        </span>
                    </a>
                    <ul class="dropdown-menu animated fadeInRight m-t-xs">
                        <li class="divider"></li>
                        <li><a href="javascript:document.getElementById('logout').submit()"><i
                                class="fa fa-sign-out"></i>&nbsp;&nbsp;Salir</a></li>
                    </ul>
                </div>
                <div class="logo-element">
                    CR
                </div>
            </li>

            <sec:authorize access="hasAuthority(T(com.crystal.model.shared.Constants).AUTHORITY_MANAGER)">
                <li data-ng-class="mn.menu === 1 ? 'active' : ''">
                    <a href="#"><i class="fa fa-th"></i> <span class="nav-label">Cat&aacute;logos</span> </a>
                    <ul class="nav nav-second-level">

                        <li data-ng-class="mn.subMenu === 1002 ? 'active' : ''">
                            <a class="sub-menu-lbl" href="<c:url value='/management/auditedEntity/index.html'/>">
                                <i class="fa fa-sitemap"></i>Entes fiscalizados
                            </a>
                        </li>

                        <li data-ng-class="mn.subMenu === 1005 ? 'active' : ''">
                            <a class="sub-menu-lbl" href="<c:url value='/management/supervisoryEntity/index.html'/>">
                                <i class="fa fa-eye"></i>&Oacute;rganos fiscalizadores
                            </a>
                        </li>

                        <li data-ng-class="mn.subMenu === 1006 ? 'active' : ''">
                            <a class="sub-menu-lbl" href="<c:url value='/management/auditType/index.html'/>">
                                <i class="fa fa-bars"></i>Tipos de auditor&iacute;a
                            </a>
                        </li>

                        <li data-ng-class="mn.subMenu === 1003 ? 'active' : ''">
                            <a class="sub-menu-lbl" href="<c:url value='/management/eventType/index.html'/>">
                                <i class="fa fa-th-large"></i>Tipos de eventos
                            </a>
                        </li>
                        <li data-ng-class="mn.subMenu === 1004 ? 'active' : ''">
                            <a class="sub-menu-lbl" href="<c:url value='/management/meetingType/index.html'/>">
                                <i class="fa fa-quote-right"></i>Tipos de reuni&oacute;n
                            </a>
                        </li>

                        <li data-ng-class="mn.subMenu === 1001 ? 'active' : ''">
                            <a class="sub-menu-lbl" href="<c:url value='/management/user/index.html'/>">
                                <i class="fa fa-users"></i>Usuarios
                            </a>
                        </li>

                    </ul>
                </li>
            </sec:authorize>
            <sec:authorize access="hasAuthority(T(com.crystal.model.shared.Constants).AUTHORITY_DIRECTION)">
                <li>
                    <a href="#"><i class="fa fa-th"></i> <span class="nav-label">Reportes</span> </a>
                    <ul class="nav nav-second-level">
                        <li class="active">
                            <a class="sub-menu-lbl" href="">
                                <i class="fa fa-shield"></i>Auditor&iacute;as
                            </a>
                        </li>
                    </ul>
                </li>
            </sec:authorize>

            <sec:authorize access="hasAuthority(T(com.crystal.model.shared.Constants).AUTHORITY_DGPOP)">
                <li data-ng-class="mn.menu === 1 ? 'active' : ''">
                    <a class="sub-menu-lbl" href="<c:url value='/shared/area/index.html'/>"><i class="fa fa-cubes"></i> <span class="nav-label">&Aacute;reas</span></a>
                </li>
                <li data-ng-class="mn.menu === 2 ? 'active' : ''">
                    <a class="sub-menu-lbl" href="<c:url value='/audit/letter/index.html'/>"><i class="fa fa-list"></i><span class="nav-label">Requerimientos previos</span></a>
                </li>
                <li data-ng-class="mn.menu === 3 ? 'active' : ''">
                    <a class="sub-menu-lbl" href="<c:url value='/audit/index.html'/>"><i class="fa fa-briefcase"></i><span class="nav-label">Auditor&iacute;as</span></a>
                </li>
            </sec:authorize>

            <sec:authorize access="hasAuthority(T(com.crystal.model.shared.Constants).AUTHORITY_LINK)">
                <li data-ng-class="mn.menu === 1 ? 'active' : ''">
                    <a class="sub-menu-lbl" href="<c:url value='/shared/area/index.html'/>"><i class="fa fa-cubes"></i> <span class="nav-label">&Aacute;reas</span></a>
                </li>
                <li data-ng-class="mn.menu === 2 ? 'active' : ''">
                    <a class="sub-menu-lbl" href="<c:url value='/audit/letter/index.html'/>"><i class="fa fa-list"></i><span class="nav-label">Requerimientos previos</span></a>
                </li>
                <li data-ng-class="mn.menu === 3 ? 'active' : ''">
                    <a class="sub-menu-lbl" href="<c:url value='/audit/index.html'/>"><i class="fa fa-briefcase"></i><span class="nav-label">Auditor&iacute;as</span></a>
                </li>
            </sec:authorize>

            <%--<sec:authorize access="hasAuthority('DGPOP')">--%>
                <%--<li>--%>
                    <%--<a href="#"><i class="fa fa-th"></i> <span class="nav-label">Reportes</span> </a>--%>
                    <%--<ul class="nav nav-second-level">--%>
                        <%--<li class="active">--%>
                            <%--<a href="">--%>
                                <%--<i class="fa fa-shield"></i>Auditor&iacute;as--%>
                            <%--</a>--%>
                        <%--</li>--%>
                    <%--</ul>--%>
                <%--</li>--%>
            <%--</sec:authorize>--%>

        </ul>
    </div>
</nav>
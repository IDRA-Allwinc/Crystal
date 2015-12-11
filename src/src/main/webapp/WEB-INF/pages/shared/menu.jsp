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
                                <strong class="font-bold">Usuario</strong>
                            </span> <span class="text-muted block"> Administrador <b class="caret"></b></span>
                        </span>
                    </a>
                    <ul class="dropdown-menu animated fadeInRight m-t-xs">
                        <li class="divider"></li>
                        <li><a href="javascript:document.getElementById('logout').submit()">Salir</a></li>
                    </ul>
                </div>
                <div class="logo-element">
                    CR
                </div>
            </li>

            <li class="active">
                <a href="#"><i class="fa fa-th"></i> <span class="nav-label">Cat&aacute;logos</span> </a>
                <ul class="nav nav-second-level">
                    <li><a href=""><i class="fa fa-shield"></i>Auditor&iacute;as</a></li>
                </ul>
            </li>

        </ul>
    </div>
</nav>
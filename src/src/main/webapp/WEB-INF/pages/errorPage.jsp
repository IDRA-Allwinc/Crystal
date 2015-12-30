<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<body scroll="no">

<!DOCTYPE html>
<html>

<head>
    <%@ include file="/WEB-INF/pages/shared/head.jsp" %>
    <title>CRYSTAL</title>
</head>

<body class="gray-bg">

<div class="element-center animated fadeInDown">
    <div class=" text-center col-xs-12">
        <img src="${pageContext.request.contextPath}/assets/img/cloud_white.png" , height="15%" width="15%">
        <h2 class="font-bold " style="color: white; !important;"><i
                class="fa fa-warning"></i>&nbsp;&nbsp;No ha sido posible procesar la solicitud.</h2>
    </div>
</div>


<div class="col-xs-8 col-xs-offset-2 animated fadeInDown">
    <div class="ibox float-e-margins">
        <div class="ibox-title">
            <h5>Es posible que el recurso al que intenta acceder:</h5>
        </div>

        <div class="ibox-content">
            <div class="well well-sm">
                <div class="ibox-content">
                    <ul>

                        <li><h4>No est&aacute; disponible.</h4></li>
                        <li><h4>No existe.</h4></li>
                        <li><h4>No tiene permisos suficientes.</h4></li>

                        </h5>
                    </ul>
                </div>
            </div>

            <div class="error-desc element-center">
                <a href="<c:url value='/index.html'></c:url>" class="btn btn-primary m-t">Regresar al Inicio</a>
            </div>
        </div>
    </div>

    <div class="col-xs-12">
        <%@ include file="/WEB-INF/pages/shared/footer.jsp" %>
    </div>
</div>


</body>

</html>
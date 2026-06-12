<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="content-wrapper">
    <div class="container-fluid">
        <h2 class="mb-4">${reportTitle}</h2>

        <div class="card mb-4">
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/admin/revenue" method="get" class="form-inline">
                    <div class="form-group mr-3">
                        <label class="mr-2">Từ ngày:</label>
                        <input type="date" name="startDate" value="${startDate}" class="form-control">
                    </div>
                    
                    <div class="form-group mr-3">
                        <label class="mr-2">Đến ngày:</label>
                        <input type="date" name="endDate" value="${endDate}" class="form-control">
                    </div>
                    
                    <div class="form-group mr-3">
                        <label class="mr-2">Loại báo cáo:</label>
                        <select name="action" class="form-control">
                            <option value="summary" ${reportType == 'summary' ? 'selected' : ''}>Tổng quan</option>
                            <option value="daily" ${reportType == 'daily' ? 'selected' : ''}>Theo ngày</option>
                            <option value="movie" ${reportType == 'movie' ? 'selected' : ''}>Theo phim</option>
                            <option value="cinema" ${reportType == 'cinema' ? 'selected' : ''}>Theo rạp</option>
                            <option value="payment" ${reportType == 'payment' ? 'selected' : ''}>Theo thanh toán</option>
                        </select>
                    </div>
                    
                    <button type="submit" class="btn btn-primary">Lọc</button>
                </form>
                
                <c:if test="${reportType != 'summary'}">
                    <form action="${pageContext.request.contextPath}/admin/revenue" method="post" class="mt-3">
                        <input type="hidden" name="action" value="exportCsv">
                        <input type="hidden" name="startDate" value="${startDate}">
                        <input type="hidden" name="endDate" value="${endDate}">
                        <input type="hidden" name="reportType" value="${reportType}">
                        <button type="submit" class="btn btn-success"><i class="fa fa-file-excel-o"></i> Xuất CSV</button>
                    </form>
                </c:if>
            </div>
        </div>

        <c:choose>
            <c:when test="${reportType == 'summary'}">
                <div class="row mb-4">
                    <div class="col-md-6">
                        <div class="card bg-info text-white mb-3">
                            <div class="card-body">
                                <h5>Tổng vé bán ra</h5>
                                <h3>${summary.totalTickets != null ? summary.totalTickets : 0}</h3>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card bg-success text-white mb-3">
                            <div class="card-body">
                                <h5>Tổng doanh thu</h5>
                                <h3><fmt:formatNumber value="${summary.totalRevenue != null ? summary.totalRevenue : 0}" type="currency" currencySymbol="VNĐ"/></h3>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="alert alert-info">
                    Vui lòng chọn các loại báo cáo chi tiết (Theo ngày, Theo phim, Theo rạp, Theo thanh toán) trong danh sách thả xuống ở trên để xem dữ liệu cụ thể và xuất file CSV.
                </div>
            </c:when>
            
            <c:when test="${reportType == 'daily'}">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped">
                        <thead class="thead-dark">
                            <tr>
                                <th>Ngày</th>
                                <th>Phim</th>
                                <th>Rạp</th>
                                <th>Số vé</th>
                                <th>Doanh thu</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${report}">
                                <tr>
                                    <td>${item.date}</td>
                                    <td>${item.movieName}</td>
                                    <td>${item.cinemaName}</td>
                                    <td>${item.ticketCount}</td>
                                    <td><fmt:formatNumber value="${item.revenue}" type="currency" currencySymbol="VNĐ"/></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>

            <c:when test="${reportType == 'movie'}">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped">
                        <thead class="thead-dark">
                            <tr>
                                <th>Phim</th>
                                <th>Thời lượng</th>
                                <th>Số vé</th>
                                <th>Doanh thu</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${report}">
                                <tr>
                                    <td>${item.movieName}</td>
                                    <td>${item.duration} phút</td>
                                    <td>${item.ticketCount}</td>
                                    <td><fmt:formatNumber value="${item.revenue}" type="currency" currencySymbol="VNĐ"/></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>

            <c:when test="${reportType == 'cinema'}">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped">
                        <thead class="thead-dark">
                            <tr>
                                <th>Rạp</th>
                                <th>Địa chỉ</th>
                                <th>Số vé</th>
                                <th>Doanh thu</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${report}">
                                <tr>
                                    <td>${item.cinemaName}</td>
                                    <td>${item.address}</td>
                                    <td>${item.ticketCount}</td>
                                    <td><fmt:formatNumber value="${item.revenue}" type="currency" currencySymbol="VNĐ"/></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>

            <c:when test="${reportType == 'payment'}">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped">
                        <thead class="thead-dark">
                            <tr>
                                <th>Phương thức thanh toán</th>
                                <th>Số vé</th>
                                <th>Doanh thu</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${report}">
                                <tr>
                                    <td>${item.paymentMethod}</td>
                                    <td>${item.ticketCount}</td>
                                    <td><fmt:formatNumber value="${item.revenue}" type="currency" currencySymbol="VNĐ"/></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>
        </c:choose>
    </div>
</div>

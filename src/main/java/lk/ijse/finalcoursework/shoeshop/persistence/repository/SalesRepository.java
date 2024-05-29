package lk.ijse.finalcoursework.shoeshop.persistence.repository;

import lk.ijse.finalcoursework.shoeshop.persistence.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @author: Vishal Sandakelum,
 * @Runtime version: 11.0.11+9-b1341.60 amd64
 **/

public interface SalesRepository extends JpaRepository<Sales,String> {
    Boolean existsByOrderNo(String id);
    Sales findByOrderNo(String id);
    void deleteByOrderNo(String id);
    List<Sales> findAllByPurchaseDate(Date date);
    @Query("SELECT o.purchaseDate FROM Sales o")
    List<Date>findAllPurchaseDate();
    @Query(value = "SELECT order_no FROM Sales ORDER BY order_no DESC LIMIT 1", nativeQuery = true)
    String findLatestOrderCode();
    @Query(value = "SELECT * FROM sales s WHERE DATE(s.purchase_date) = :today", nativeQuery = true)
    List<Sales> findTodaySales(@Param("today") String today);
    @Query(value = "SELECT COALESCE(SUM(total_price), 0) FROM sales WHERE MONTH(purchase_date) = MONTH(CURRENT_DATE()) AND YEAR(purchase_date) = YEAR(CURRENT_DATE())", nativeQuery = true)
    Double getCurrentMonthTotalRevenue();
}

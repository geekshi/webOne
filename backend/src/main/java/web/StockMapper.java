package web;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface StockMapper {

    @Select("select * from test.stock")
    List<Stock> getAllStocks();

    @Select("select * from test.stock where code = #{code}")
    Stock getStockByCode(String code);

    @Update("update test.stock set price = #{price} where code = #{code}")
    int updateStock(Stock stock);
}

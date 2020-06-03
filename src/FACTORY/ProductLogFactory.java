package FACTORY;


import SERVICE.ProductLogService;

public class ProductLogFactory {
    public static ProductLogService getDAOInstance() throws Exception{
        return new ProductLogService();
    }
}

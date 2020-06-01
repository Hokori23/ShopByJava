package FACTORY;


import SERVICE.ProductService;

public class ProductFactory {
    public static ProductService getDAOInstance() throws Exception{
        return new ProductService();
    }
}

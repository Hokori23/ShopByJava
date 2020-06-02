package VO;

public class ProductLog {
    private int product_id;
    private String product_name;
    private int price;
    private String user_id;
    private String user_name;
    private String time;
    private int count;
    private String category;

    public ProductLog(int product_id, String product_name, int price, String user_id, String user_name, String time, int count, String category) throws Exception {
        if (!checkParam(product_id, product_name, price, user_id, user_name, time, count, category)) {
            throw new Exception("参数错误,[product_id < 5, product_name < 100, price < 100000, user_id < 25, user_name < 15, time<255, count < 100000]");
        }
        this.product_id = product_id;
        this.product_name = product_name;
        this.price = price;
        this.user_id = user_id;
        this.user_name = user_name;
        this.time = time;
        this.count = count;
        this.category = category;
    }

    public boolean checkParam(int product_id, String product_name, int price, String user_id, String user_name, String time, int count, String category) {
        boolean flag = true;
        if (Integer.toString(product_id).length() >= 5 && "0".equals(String.valueOf(product_id))) {
            flag = false;
        } else if (product_name.length() >= 100 && product_name != null) {
            flag = false;
        } else if (Integer.toString(price).length() >= 5 && "0".equals(String.valueOf(price))) {
            flag = false;
        } else if (user_id.length() >= 25 && user_id != null) {
            flag = false;
        } else if (user_name.length() >= 15 && user_name != null) {
            flag = false;
        } else if (time.length() >= 255 && time != null) {
            flag = false;
        } else if (Integer.toString(count).length() >= 5 && "0".equals(String.valueOf(count))) {
            flag = false;
        } else if (category.length() >= 100 && category != null) {
            flag = false;
        }
        return flag;
    }

    public static boolean checkRequestParam(String user_id, String start_time, String end_time) {
        boolean flag = true;
        if (user_id == null || start_time == null || end_time == null) {
            return false;
        }
        if (user_id.length() >= 5) {
            flag = false;
        } else if (start_time.length() >= 255) {
            flag = false;
        } else if (end_time.length() >= 255) {
            flag = false;
        }
        return true;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

package VO;

public class Product {
    private int id;
    private String name;
    private String description;
    private int price;
    private String category;

    public Product(int id, String name, String description, int price, String category) throws Exception {
        if (!checkParam(id, name, description, price, category)) {
            throw new Exception("参数错误,[id < 5, name < 100, description < 255, price < 100000]");
        }
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public boolean checkParam(int id, String name, String description, int price, String category) {
        boolean flag = true;
        if (Integer.toString(id).length() >= 5 && "0".equals(String.valueOf(id))) {
            flag = false;
        } else if (name.length() >= 100 && name != null) {
            flag = false;
        } else if (description.length() >= 255 && description != null) {
            flag = false;
        } else if (Integer.toString(price).length() >= 5 && "0".equals(String.valueOf(price))) {
            flag = false;
        } else if (category.length() >= 100 && category != null) {
            flag = false;
        }
        return flag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

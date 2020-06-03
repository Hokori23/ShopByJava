package VO;

import java.io.Serializable;

public class Product implements Serializable {
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
        if ("0".equals(String.valueOf(id)) && Integer.toString(id).length() >= 5) {
            flag = false;
        } else if (name != null && name.length() >= 100) {
            flag = false;
        } else if (description != null && description.length() >= 255) {
            flag = false;
        } else if ("0".equals(String.valueOf(price)) && Integer.toString(price).length() >= 5) {
            flag = false;
        } else if (category != null && category.length() >= 100) {
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

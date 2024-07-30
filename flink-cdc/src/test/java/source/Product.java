package source;

public class Product {
    private String id;
    private String name;
    private String description;
    private double weight;

    // 无参构造函数
    public Product() {
    }

    // 带所有属性的构造函数
    public Product(String id, String name, String description, double weight) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.weight = weight;
    }

    // id的getter方法
    public String getId() {
        return id;
    }

    // id的setter方法
    public void setId(String id) {
        this.id = id;
    }

    // name的getter方法
    public String getName() {
        return name;
    }

    // name的setter方法
    public void setName(String name) {
        this.name = name;
    }

    // description的getter方法
    public String getDescription() {
        return description;
    }

    // description的setter方法
    public void setDescription(String description) {
        this.description = description;
    }

    // weight的getter方法
    public double getWeight() {
        return weight;
    }

    // weight的setter方法
    public void setWeight(double weight) {
        this.weight = weight;
    }

    // 重写 toString 方法，便于打印 Product 对象
    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", weight=" + weight +
                '}';
    }
}


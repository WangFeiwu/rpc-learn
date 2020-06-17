package server.register;

/**
 * @Author wfw
 * @Date 2020/06/17 10:18
 */
public class ServiceObject {
    private String name;
    private Class<?> interf;
    private Object object;

    public ServiceObject(String name, Class<?> interf, Object object) {
        super();
        this.name = name;
        this.interf = interf;
        this.object = object;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getInterf() {
        return interf;
    }

    public void setInterf(Class<?> interf) {
        this.interf = interf;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}

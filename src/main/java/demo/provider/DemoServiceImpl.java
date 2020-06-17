package demo.provider;

import demo.DemoService;

import java.awt.*;

/**
 * @Author wfw
 * @Date 2020/06/17 12:22
 */
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "Hello," + name;
    }

    @Override
    public Point multiPoint(Point p, int multi) {
        p.x = p.x * multi;
        p.y = p.y * multi;
        return p;
    }
}

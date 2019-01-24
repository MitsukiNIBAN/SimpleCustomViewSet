package com.mitsuki.falldownview.base;

/**
 * 装饰组件基类
 */
public class DecorateComponent implements BaseComponent {
    protected FallComponent fallComponent;

    public DecorateComponent(FallComponent fallComponent) {
        this.fallComponent = fallComponent;
    }

    @Override
    public void move() {
        fallComponent.move();
    }
}

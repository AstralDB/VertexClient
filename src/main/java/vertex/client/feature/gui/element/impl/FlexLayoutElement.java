/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.feature.gui.element.impl;

import vertex.client.feature.gui.element.Element;
import vertex.client.helper.render.ClipStack;
import vertex.client.helper.render.Scroller;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec2f;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FlexLayoutElement extends Element {
    List<Element> elements;
    LayoutDirection direction;
    double padding;
    double viewportHeight, viewportWidth;
    Scroller scroller = new Scroller(0);
    @Getter
    @Setter
    Vec2f heightMulMatrix = new Vec2f(1, 1);

    public FlexLayoutElement(FlexLayoutElement.LayoutDirection direction, double x, double y, double width, double height, double padding, Element... elements) {
        super(x, y, width, height);
        this.elements = List.of(elements);
        this.direction = direction;
        this.padding = padding;
        this.viewportHeight = getActualHeight();
        this.viewportWidth = getActualWidth();
        if (width < 0) {
            setWidth(viewportWidth);
        }
        if (height < 0) {
            setHeight(viewportHeight);
        }
    }

    public FlexLayoutElement(LayoutDirection direction, double x, double y, double padding, Element... elements) {
        super(x, y, 0, 0);
        this.elements = List.of(elements);
        this.direction = direction;
        this.padding = padding;
        this.viewportHeight = getActualHeight();
        this.viewportWidth = getActualWidth();
        setWidth(getActualWidth());
        setHeight(getActualHeight());
    }

    public List<Element> getElements() {
        return elements.stream().filter(Element::isActive).collect(Collectors.toList());
    }

    public double getActualHeight() {
        if (direction == LayoutDirection.DOWN) {
            return getElements().stream().map(element -> element.getHeight() + padding).reduce(Double::sum).orElse(padding) - padding;
        } else {
            return getElements().stream().map(Element::getHeight).max(Comparator.comparingDouble(value -> value)).orElse(0d);
        }
    }

    public double getActualWidth() {
        if (direction == LayoutDirection.RIGHT) {
            return getElements().stream().map(element -> element.getWidth() + padding).reduce(Double::sum).orElse(padding) - padding;
        } else {
            return getElements().stream().map(Element::getWidth).max(Comparator.comparingDouble(value -> value)).orElse(0d);
        }
    }

    @Override
    public void tickAnimations() {
        for (Element element : getElements()) {
            element.tickAnimations();
        }
        scroller.tick();
    }

    @Override
    public void render(MatrixStack stack, double mouseX, double mouseY) {
        double posX = 0, posY = 0;
        ClipStack.globalInstance.addWindow(stack, getBounds().multiplyWidthHeight(heightMulMatrix));
        stack.push();
        stack.translate(0, scroller.getScroll(), 0);
        for (Element element : getElements()) {
            element.setPositionX(getPositionX() + posX * direction.mulX);
            element.setPositionY(getPositionY() + posY * direction.mulY);
            if (element.getPositionY() + scroller.getScroll() <= getPositionY() + getHeight() && element.getPositionY() + element.getHeight() + scroller.getScroll() >= getPositionY() && element.getPositionX() >= getPositionX() && element.getPositionX() <= getPositionX() + getWidth()) {
                element.render(stack, mouseX, mouseY - scroller.getScroll());
            }
            posX += element.getWidth() + padding;
            posY += element.getHeight() + padding;
        }
        stack.pop();
        ClipStack.globalInstance.popWindow();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return iterateOverChildren(element -> {
            if (element.getPositionY() + scroller.getScroll() <= getPositionY() + getHeight() && element.getPositionY() + element.getHeight() + scroller.getScroll() >= getPositionY() && element.getPositionX() >= getPositionX() && element.getPositionX() <= getPositionX() + getWidth()) {
                return element.mouseClicked(mouseX, mouseY - scroller.getScroll(), button);
            } else {
                return false;
            }
        });
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return iterateOverChildren(element -> element.getPositionY() + scroller.getScroll() <= getPositionY() + getHeight() && element.getPositionY() + element.getHeight() + scroller.getScroll() >= getPositionY() && element.getPositionX() >= getPositionX() && element.getPositionX() <= getPositionX() + getWidth() && element.mouseReleased(mouseX,
                mouseY - scroller.getScroll(),
                button
        ));
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, double deltaX, double deltaY, int button) {
        return iterateOverChildren(element -> element.getPositionY() + scroller.getScroll() <= getPositionY() + getHeight() && element.getPositionY() + element.getHeight() + scroller.getScroll() >= getPositionY() && element.getPositionX() >= getPositionX() && element.getPositionX() <= getPositionX() + getWidth() && element.mouseDragged(mouseX,
                mouseY - scroller.getScroll(),
                deltaX,
                deltaY,
                button
        ));
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        return iterateOverChildren(element -> element.getPositionY() + scroller.getScroll() <= getPositionY() + getHeight() && element.getPositionY() + element.getHeight() + scroller.getScroll() >= getPositionY() && element.getPositionX() >= getPositionX() && element.getPositionX() <= getPositionX() + getWidth() && element.charTyped(chr,
                modifiers
        ));
    }

    @Override
    public boolean keyPressed(int keyCode, int modifiers) {
        return iterateOverChildren(element -> element.getPositionY() + scroller.getScroll() <= getPositionY() + getHeight() && element.getPositionY() + element.getHeight() + scroller.getScroll() >= getPositionY() && element.getPositionX() >= getPositionX() && element.getPositionX() <= getPositionX() + getWidth() && element.keyPressed(keyCode,
                modifiers
        ));
    }

    @Override
    public boolean keyReleased(int keyCode, int modifiers) {
        return iterateOverChildren(element -> element.getPositionY() + scroller.getScroll() <= getPositionY() + getHeight() && element.getPositionY() + element.getHeight() + scroller.getScroll() >= getPositionY() && element.getPositionX() >= getPositionX() && element.getPositionX() <= getPositionX() + getWidth() && element.keyReleased(keyCode,
                modifiers
        ));
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (iterateOverChildren(element -> element.getPositionY() + scroller.getScroll() <= getPositionY() + getHeight() && element.getPositionY() + element.getHeight() + scroller.getScroll() >= getPositionY() && element.getPositionX() >= getPositionX() && element.getPositionX() <= getPositionX() + getWidth() && element.mouseScrolled(mouseX,
                mouseY,
                amount
        ))) {
            return true;
        }
        if (inBounds(mouseX, mouseY)) {
            double viewport = getActualHeight();
            double current = getHeight();
            double scrollToAllow = Math.max(viewport - current, 0);
            scroller.setBounds(0, scrollToAllow);
            scroller.scroll(amount);
            return true;
        }
        return false;
    }

    public void updateScroller() {
        double viewport = getActualHeight();
        double current = getHeight();
        double scrollToAllow = Math.max(viewport - current, 0);
        scroller.setBounds(0, scrollToAllow);
        scroller.scroll(0);
    }

    private boolean iterateOverChildren(Function<Element, Boolean> supp) {
        for (Element element : getElements()) {
            if (supp.apply(element)) {
                return true;
            }
        }
        return false;
    }

    @AllArgsConstructor
    public enum LayoutDirection {
        DOWN(0, 1), RIGHT(1, 0);
        final int mulX, mulY;
    }

}
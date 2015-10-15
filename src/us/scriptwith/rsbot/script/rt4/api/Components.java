package us.scriptwith.rsbot.script.rt4.api;

import org.powerbot.script.AbstractQuery;
import org.powerbot.script.Filter;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Component;
import org.powerbot.script.rt4.Widget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class Components extends AbstractQuery<Components, Component, ClientContext> {
    public Components(ClientContext ctx) {
        super(ctx);
    }

    @Override
    protected Components getThis() {
        return this;
    }

    public Components select(final int widget) {
        return select(get(widget));
    }

    protected List<Component> get(final int widget) {
        LinkedList<Component> widgets = new LinkedList<Component>();
        List<Component> components = new ArrayList<Component>();
        Collections.addAll(widgets, ctx.widgets.widget(widget).components());
        while (!widgets.isEmpty()) {
            Component c = widgets.poll();
            if (c.components().length > 0) {
                Collections.addAll(widgets, c.components());
            } else {
                components.add(c);
            }
        }
        return components;
    }

    @Override
    protected List<Component> get() {
        LinkedList<Component> widgets = new LinkedList<Component>();
        List<Component> components = new ArrayList<Component>();
        for (Widget w : ctx.widgets.select()) {
            Collections.addAll(widgets, w.components());
        }
        while (!widgets.isEmpty()) {
            Component c = widgets.poll();
            if (c.components().length > 0) {
                Collections.addAll(widgets, c.components());
            } else {
                components.add(c);
            }
        }
        return components;
    }

    @Override
    public Component nil() {
        return ctx.widgets.component(0, 0);
    }

    public Components widget(final int index) {
        return select(new Filter<Component>() {
            @Override
            public boolean accept(Component component) {
                return component.widget().id() == index;
            }
        });
    }

    public Components visible() {
        return select(new Filter<Component>() {
            @Override
            public boolean accept(Component component) {
                return component.visible();
            }
        });
    }

    public Components inViewport() {
        return select(new Filter<Component>() {
            @Override
            public boolean accept(Component component) {
                return component.inViewport();
            }
        });
    }

    public Components text(final String... text) {
        return select(new Filter<Component>() {
            @Override
            public boolean accept(Component component) {
                for (String s : text) {
                    if (component.text().toLowerCase().contains(s.toLowerCase())) {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public Components text(final Pattern pattern) {
        return select(new Filter<Component>() {
            @Override
            public boolean accept(Component component) {
                return pattern.matcher(component.text()).find();
            }
        });
    }

    public Components texture(final int... texture) {
        return select(new Filter<Component>() {
            @Override
            public boolean accept(Component component) {
                for (int i : texture) {
                    if (component.textureId() == i) {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public Components itemId(final int... ids) {
        return select(new Filter<Component>() {
            @Override
            public boolean accept(Component component) {
                for (int i : ids) {
                    if (i == component.itemId()) {
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
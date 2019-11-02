package vista;

import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;

public class IconListRenderer
        extends DefaultListCellRenderer {

    static URL resource = Icon.class.getResource("/resources/online.png");
    static Image image = Toolkit.getDefaultToolkit().getImage(resource);

    private Map<Object, Icon> icons = null;

    public IconListRenderer(Map<Object, Icon> icons) {
        this.icons = icons;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        // Get the renderer component from parent class
        JLabel label
                = (JLabel) super.getListCellRendererComponent(list,
                        value, index, isSelected, cellHasFocus);

        // Get icon to use for the list item value
        Icon icon = icons.get(value);

        // Set icon to display for value
        label.setIcon(icon);
        return label;
    }

}

import javax.swing.*;
import java.awt.*;

public class DrawersColor {

    public static final Color panel_Background = new Color(0, 0, 0);
    public static final Color button_color = new Color(70, 130, 180);

    public static void applyColors(JButton d1, JButton d2, JButton d3, JButton d4, JButton d5, JPanel panel){
        applyStyle(d1, button_color, Color.WHITE);
        applyStyle(d2, button_color, Color.WHITE);
        applyStyle(d3, button_color, Color.WHITE);
        applyStyle(d4, button_color, Color.WHITE);
        applyStyle(d5, button_color, Color.WHITE);
        panel.setBackground(panel_Background);
    }
    public static void applyStyle(JButton btn, Color bg, Color fg){
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
    }
}
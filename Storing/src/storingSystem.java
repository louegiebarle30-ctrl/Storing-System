import javax.swing.*;
import java.awt.*;
import java.io.*;

public class storingSystem {

    // Data for drawers
    static DefaultListModel<String> drawer1 = new DefaultListModel<>();
    static DefaultListModel<String> drawer2 = new DefaultListModel<>();
    static DefaultListModel<String> drawer3 = new DefaultListModel<>();
    static DefaultListModel<String> drawer4 = new DefaultListModel<>();
    static DefaultListModel<String> drawer5 = new DefaultListModel<>();

    // Reference to current drawer
    static DefaultListModel<String> currentDrawer = drawer1;

    public static void main(String[] args) {

        // Load saved data
        loadFromFile();

        JFrame frame = new JFrame("Storing System");
        frame.setSize(1000, 650);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Exit options
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                        frame,
                        "Do you want to save your changes before exiting?",
                        "Save Changes?",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (choice == JOptionPane.YES_OPTION) {
                    saveToFile();
                    System.exit(0);
                } else if (choice == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }
        });
        frame.setLayout(new BorderLayout());

        // ===== LEFT PANEL =====
        JPanel leftPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        leftPanel.setPreferredSize(new Dimension(150, 0));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton drawer1Btn = new JButton("Drawer 1");
        JButton drawer2Btn = new JButton("Drawer 2");
        JButton drawer3Btn = new JButton("Drawer 3");
        JButton drawer4Btn = new JButton("Drawer 4");
        JButton drawer5Btn = new JButton("Drawer 5");

        leftPanel.add(drawer1Btn);
        leftPanel.add(drawer2Btn);
        leftPanel.add(drawer3Btn);
        leftPanel.add(drawer4Btn);
        leftPanel.add(drawer5Btn);

        DrawersColor.applyColors(drawer1Btn, drawer2Btn, drawer3Btn, drawer4Btn, drawer5Btn, leftPanel);

        frame.add(leftPanel, BorderLayout.WEST);

        // ===== CENTER LIST =====
        JList<String> itemList = new JList<>(drawer1);
        JScrollPane scrollPane = new JScrollPane(itemList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Items in Drawer 1"));

        frame.add(scrollPane, BorderLayout.CENTER);

        // ===== DRAWER SWITCHING =====
        drawer1Btn.addActionListener(e -> {
            itemList.setModel(drawer1);
            currentDrawer = drawer1;
            scrollPane.setBorder(BorderFactory.createTitledBorder("Items in Drawer 1"));
        });

        drawer2Btn.addActionListener(e -> {
            itemList.setModel(drawer2);
            currentDrawer = drawer2;
            scrollPane.setBorder(BorderFactory.createTitledBorder("Items in Drawer 2"));
        });

        drawer3Btn.addActionListener(e -> {
            itemList.setModel(drawer3);
            currentDrawer = drawer3;
            scrollPane.setBorder(BorderFactory.createTitledBorder("Items in Drawer 3"));
        });

        drawer4Btn.addActionListener(e -> {
            itemList.setModel(drawer4);
            currentDrawer = drawer4;
            scrollPane.setBorder(BorderFactory.createTitledBorder("Items in Drawer 4"));
        });

        drawer5Btn.addActionListener(e -> {
            itemList.setModel(drawer5);
            currentDrawer = drawer5;
            scrollPane.setBorder(BorderFactory.createTitledBorder("Items in Drawer 5"));
        });

        // ===== BUTTONS =====
        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton saveButton = new JButton("Save");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        // ===== ADD =====
        addButton.addActionListener(e -> new AddWindow());

        // ===== DELETE WITH CONFIRMATION =====
        deleteButton.addActionListener(e -> {
            int selectedIndex = itemList.getSelectedIndex();

            if (selectedIndex != -1) {

                String selectedItem = itemList.getSelectedValue();

                Object[] options = {"Yes, I'm sure", "No"};

                int choice = JOptionPane.showOptionDialog(
                        frame,
                        "Are you sure you want to delete \"" + selectedItem + "\"?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        options,
                        options[1]
                );

                if (choice == 0) {
                    currentDrawer.remove(selectedIndex);
                    saveToFile();
                }

            } else {
                JOptionPane.showMessageDialog(frame, "Please select an item to delete.");
            }
        });

        // ===== SAVE BUTTON =====
        saveButton.addActionListener(e -> saveToFile());

        frame.setVisible(true);
    }

    // ===== SAVE =====
    public static void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("drawers.txt"))) {

            for (int i = 0; i < drawer1.size(); i++)
                writer.println("1:" + drawer1.get(i));

            for (int i = 0; i < drawer2.size(); i++)
                writer.println("2:" + drawer2.get(i));

            for (int i = 0; i < drawer3.size(); i++)
                writer.println("3:" + drawer3.get(i));

            for (int i = 0; i < drawer4.size(); i++)
                writer.println("4:" + drawer4.get(i));

            for (int i = 0; i < drawer5.size(); i++)
                writer.println("5:" + drawer5.get(i));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ===== LOAD =====
    public static void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("drawers.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                int drawerNum = Integer.parseInt(parts[0]);
                String item = parts[1];

                switch (drawerNum) {
                    case 1 -> drawer1.addElement(item);
                    case 2 -> drawer2.addElement(item);
                    case 3 -> drawer3.addElement(item);
                    case 4 -> drawer4.addElement(item);
                    case 5 -> drawer5.addElement(item);
                }
            }

        } catch (IOException e) {
        }
    }
}

// ===== ADD WINDOW =====
class AddWindow {

    AddWindow() {
        JFrame frame = new JFrame("Add Item");
        frame.setSize(300, 150);
        frame.setLayout(new FlowLayout());

        JTextField textField = new JTextField(15);
        JButton submit = new JButton("Add");

        submit.addActionListener(e -> {
            String item = textField.getText().trim();

            if (!item.isEmpty()) {
                storingSystem.currentDrawer.addElement(item);
                storingSystem.saveToFile();
                frame.dispose();
            }
        });

        frame.add(new JLabel("Item:"));
        frame.add(textField);
        frame.add(submit);

        frame.setVisible(true);
    }
}
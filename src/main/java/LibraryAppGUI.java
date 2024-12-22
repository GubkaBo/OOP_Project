import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibraryAppGUI {
    private JFrame frame;
    private BookDAO bookDAO;

    public LibraryAppGUI(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
        initialize();
    }

    private void initialize() {
        // Создаем основное окно
        frame = new JFrame("Library Management System");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Создаем панели для кнопок и содержимого
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton addButton = new JButton("Add Book");
        JButton editButton = new JButton("Edit Book");
        JButton deleteButton = new JButton("Delete Book");
        JButton searchButton = new JButton("Search Book");

        // Добавляем кнопки на панель
        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(searchButton);

        // Добавляем панель в окно
        frame.getContentPane().add(BorderLayout.SOUTH, panel);

        // Добавляем действия для кнопок
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editBook();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBook();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBook();
            }
        });

        // Делаем окно видимым
        frame.setVisible(true);
    }

    private void addBook() {
        JOptionPane.showMessageDialog(frame, "Add Book Functionality to be implemented!");
    }

    private void editBook() {
        JOptionPane.showMessageDialog(frame, "Edit Book Functionality to be implemented!");
    }

    private void deleteBook() {
        JOptionPane.showMessageDialog(frame, "Delete Book Functionality to be implemented!");
    }

    private void searchBook() {
        JOptionPane.showMessageDialog(frame, "Search Book Functionality to be implemented!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                DatabaseConnection databaseConnection = new DatabaseConnection();
                BookDAO bookDAO = new BookDAO(databaseConnection.getConnection());
                new LibraryAppGUI(bookDAO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

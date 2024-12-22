import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class LibraryApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Library Management System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 700);

            // Подключение к базе данных
            DatabaseConnection databaseConnection;
            BookDAO bookDAO;

            try {
                databaseConnection = new DatabaseConnection();
                bookDAO = new BookDAO(databaseConnection.getConnection());
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Ошибка подключения к базе данных: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Основная панель
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

            // Панель добавления книги
            JPanel addBookPanel = new JPanel(new GridLayout(0, 2, 10, 10));
            addBookPanel.setBorder(BorderFactory.createTitledBorder("Add Book"));

            JTextField titleField = new JTextField();
            JTextField authorField = new JTextField();
            JTextField yearField = new JTextField();
            JTextField genreField = new JTextField();
            JTextField descriptionField = new JTextField();
            JButton addButton = new JButton("Add Book");

            addBookPanel.add(new JLabel("Title:"));
            addBookPanel.add(titleField);
            addBookPanel.add(new JLabel("Author:"));
            addBookPanel.add(authorField);
            addBookPanel.add(new JLabel("Year:"));
            addBookPanel.add(yearField);
            addBookPanel.add(new JLabel("Genre:"));
            addBookPanel.add(genreField);
            addBookPanel.add(new JLabel("Description:"));
            addBookPanel.add(descriptionField);
            addBookPanel.add(new JLabel());
            addBookPanel.add(addButton);

            // Панель поиска книги
            JPanel searchBookPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            searchBookPanel.setBorder(BorderFactory.createTitledBorder("Search Book"));

            JTextField searchField = new JTextField(20);
            JButton searchButton = new JButton("Search");
            searchBookPanel.add(new JLabel("Search Keyword:"));
            searchBookPanel.add(searchField);
            searchBookPanel.add(searchButton);

            // Панель удаления книги
            JPanel deleteBookPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            deleteBookPanel.setBorder(BorderFactory.createTitledBorder("Delete Book"));

            JTextField deleteField = new JTextField(10);
            JButton deleteButton = new JButton("Delete");
            deleteBookPanel.add(new JLabel("Delete Book ID:"));
            deleteBookPanel.add(deleteField);
            deleteBookPanel.add(deleteButton);

            // Панель редактирования книги
            JPanel editBookPanel = new JPanel(new GridLayout(0, 2, 10, 10));
            editBookPanel.setBorder(BorderFactory.createTitledBorder("Edit Book"));

            JTextField editIdField = new JTextField();
            JTextField editTitleField = new JTextField();
            JTextField editAuthorField = new JTextField();
            JTextField editYearField = new JTextField();
            JTextField editGenreField = new JTextField();
            JTextField editDescriptionField = new JTextField();
            JButton editButton = new JButton("Edit Book");

            editBookPanel.add(new JLabel("Book ID:"));
            editBookPanel.add(editIdField);
            editBookPanel.add(new JLabel("New Title:"));
            editBookPanel.add(editTitleField);
            editBookPanel.add(new JLabel("New Author:"));
            editBookPanel.add(editAuthorField);
            editBookPanel.add(new JLabel("New Year:"));
            editBookPanel.add(editYearField);
            editBookPanel.add(new JLabel("New Genre:"));
            editBookPanel.add(editGenreField);
            editBookPanel.add(new JLabel("New Description:"));
            editBookPanel.add(editDescriptionField);
            editBookPanel.add(new JLabel());
            editBookPanel.add(editButton);

            // Поле вывода данных
            JTextArea outputArea = new JTextArea(10, 50);
            outputArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(outputArea);

            // Добавление обработчиков кнопок
            addButton.addActionListener(e -> {
                try {
                    String title = titleField.getText();
                    String author = authorField.getText();
                    int year = Integer.parseInt(yearField.getText());
                    String genre = genreField.getText();
                    String description = descriptionField.getText();

                    bookDAO.addBook(title, author, year, genre, description);
                    outputArea.append("Book added: " + title + " by " + author + "\n");

                    // Очистка полей
                    titleField.setText("");
                    authorField.setText("");
                    yearField.setText("");
                    genreField.setText("");
                    descriptionField.setText("");

                } catch (NumberFormatException ex) {
                    outputArea.append("Error adding book: invalid year format\n");

                } catch (Exception ex) {
                    outputArea.append("Error adding book: " + ex.getMessage() + "\n");

                }
            });

            searchButton.addActionListener(e -> {
                try {
                    String keyword = searchField.getText();
                    List<Book> books = bookDAO.searchBook(keyword);
                    outputArea.append("Search results for \"" + keyword + "\":\n");
                    for (Book book : books) {
                        outputArea.append(book + "\n");
                    }

                    searchField.setText("");
                } catch (Exception ex) {
                    outputArea.append("Error searching for books: " + ex.getMessage() + "\n");
                }
            });

            deleteButton.addActionListener(e -> {
                try {
                    int id = Integer.parseInt(deleteField.getText());
                    bookDAO.deleteBook(id);
                    outputArea.append("Book with ID " + id + " deleted.\n");

                    deleteField.setText("");
                } catch (Exception ex) {
                    outputArea.append("Error deleting book: " + ex.getMessage() + "\n");
                }
            });

            editButton.addActionListener(e -> {
                try {
                    int id = Integer.parseInt(editIdField.getText());
                    String newTitle = editTitleField.getText();
                    String newAuthor = editAuthorField.getText();
                    int newYear = Integer.parseInt(editYearField.getText());
                    String newGenre = editGenreField.getText();
                    String newDescription = editDescriptionField.getText();

                    bookDAO.editBook(id, newTitle, newAuthor, newYear, newGenre, newDescription);
                    outputArea.append("Book with ID " + id + " updated.\n");

                    editIdField.setText("");
                    editTitleField.setText("");
                    editAuthorField.setText("");
                    editYearField.setText("");
                    editGenreField.setText("");
                    editDescriptionField.setText("");
                } catch (Exception ex) {
                    outputArea.append("Error editing book: " + ex.getMessage() + "\n");
                }
            });

            // Добавление всех компонентов на основную панель
            mainPanel.add(addBookPanel);
            mainPanel.add(searchBookPanel);
            mainPanel.add(deleteBookPanel);
            mainPanel.add(editBookPanel);
            mainPanel.add(scrollPane);

            frame.add(mainPanel);
            frame.setVisible(true);
        });
    }
}

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList; 
import java.util.List; 

public class BookDAO {
    private Connection connection;

    public BookDAO(Connection connection) {
        this.connection = connection;
    }

    public void addBook(String title, String author, int year, String genre, String description) {
        String query = "INSERT INTO Books (title, author, year, genre, description) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            preparedStatement.setInt(3, year);
            preparedStatement.setString(4, genre);
            preparedStatement.setString(5, description);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Книга успешно добавлена!");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении книги: " + e.getMessage());
        }
    }

    public void editBook(int id, String newTitle, String newAuthor, int newYear, String newGenre, String newDescription) {
        String sql = "UPDATE Books SET title = ?, author = ?, year = ?, genre = ?, description = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newTitle);
            statement.setString(2, newAuthor);
            statement.setInt(3, newYear);
            statement.setString(4, newGenre);
            statement.setString(5, newDescription);
            statement.setInt(6, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Книга успешно обновлена!");
            } else {
                System.out.println("Книга с указанным ID не найдена.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при обновлении книги: " + e.getMessage());
        }
    }

    public void deleteBook(int id) {
        String sql = "DELETE FROM Books WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Книга успешно удалена!");
            } else {
                System.out.println("Книга с указанным ID не найдена.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении книги: " + e.getMessage());
        }
    }

    public List<Book> searchBook(String keyword) {
        String sql = "SELECT * FROM Books WHERE title ILIKE ? OR author ILIKE ?";
        List<Book> books = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getInt("year"),
                        resultSet.getString("genre"),
                        resultSet.getString("description")
                );
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при поиске книги: " + e.getMessage());
        }
        return books;
    }
}

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        try {
            // Подключение к базе данных
            DatabaseConnection databaseConnection = new DatabaseConnection();
            BookDAO bookDAO = new BookDAO(databaseConnection.getConnection());

            // Удаление книги
            bookDAO.deleteBook(1);

            // Поиск книги
            List<Book> books = bookDAO.searchBook("Толстой");
            for (Book book : books) {
                System.out.println(book);
            }

            // Изменение информации о книге
            bookDAO.editBook(1, "Анна Каренина", "Лев Толстой", 1877, "Роман", "Трагическая история любви.");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}

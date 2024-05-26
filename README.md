# Library Management System

## Description

This project is a simple Library Management System implemented in Java. It uses JavaFX for the user interface and JDBC for database connectivity. The system allows you to add books and members to the library, search for books, lend and return books.

## Features

- Add new books and members to the library.
- Search for books by ID or name(temporarily Not working ).
- Lend books to members.
- Return books to the library.
- Display member information including borrowed books(only available on console).

## Prerequisites

- Java Development Kit (JDK) installed on your machine.
- MySQL database set up and running.
- JDBC driver for MySQL.
- An Integrated Development Environment (IDE) like IntelliJ IDEA or Eclipse.

## Getting Started

### Installation

1. **Clone the repository:**

    ```bash
    git clone https://github.com/yourusername/your-repo-name.git
    cd your-repo-name
    ```

2. **Set up your MySQL database:**

    ```sql
    CREATE DATABASE Library;

    USE Library;

    CREATE TABLE Books (
        ID INT PRIMARY KEY,
        `Book Name` VARCHAR(255),
        `Author Name` VARCHAR(255),
        Availability BOOLEAN
    );

    CREATE TABLE Members (
        ID INT PRIMARY KEY,
        Name VARCHAR(255),
        `Borrowed Books` VARCHAR(255)
    );
    ```

3. **Download and add the JDBC driver for MySQL to your project dependencies.**

### Configuration

1. **Open the `Library.java` file.**
2. **Update the database connection URL, username, and password:**

    ```java
    String url = "jdbc:mysql://localhost:3306/Library";
    String username = "root";
    String password = "";
    ```

### Running the Project

1. **Compile and run the project in your IDE or from the command line:**

    ```bash
    javac -d bin src/com/example/demoprojapp/*.java
    java -cp bin com.example.demoprojapp.HelloController
    ```

2. **Alternatively, if using an IDE, run the `HelloController` class which initializes the JavaFX application.**

## Usage

### Adding a Book

1. Enter the book ID, title, and author in the corresponding text fields.
2. Click the "Add Book" button to add the book to the library.

### Adding a Member

1. Enter the member ID and name in the corresponding text fields.
2. Click the "Add Member" button to add the member to the library.

### Searching for a Book

1. Enter the book ID or name in the search field.
2. Click the "Search" button to search for the book.
3. If found, the book details are displayed.

### Borrowing a Book

1. Search for the book you want to borrow.
2. Enter the member ID in the corresponding field.
3. Click the "Borrow Book" button to borrow the book.

### Returning a Book

1. Enter the member ID and book ID in the corresponding fields.
2. Click the "Return Book" button to return the book.

### Displaying Member Information (ONLY CONSOLE BASE METHOD AVAILABLE)

1. Enter the member ID in the corresponding field.
2. Click the "Show Member Info" button to display the member's details including borrowed books.

## Project Structure

- `Library.java`: Contains the main logic for managing books and members, interacting with the database.
- `HelloController.java`: Handles user interactions from the JavaFX interface.
- `Book.java` and `Member.java`: Model classes representing the book and member entities.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any improvements or bug fixes.

## License

No License

## Contact

If you have any questions or feedback, feel free to reach out to:

- Ameer Hamza - [ah75102@gmail.com.com](mailto:ah75102@gmail.com)
- GitHub: [@gitameerhamza](https://github.com/gitameerhamza)

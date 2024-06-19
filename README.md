# Password Vault

This JavaFX project provides a secure and user-friendly application for storing and managing passwords.

## Features

- **Add New Passwords:** Easily add new passwords with relevant details.
- **View Stored Passwords:** View your stored passwords in an organized manner.
- **Search Passwords:** Quickly search through your stored passwords.
- **Edit and Delete Passwords:** Edit or delete existing passwords.
- **Encrypt and Decrypt Passwords:** Securely encrypt and decrypt passwords.
- **Load Vault:** Load the entire vault of passwords securely.
- **Secure Storage:** Passwords are stored securely to protect your sensitive information.

## Installation

### Prerequisites

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) (Ensure you have JDK 11 or later installed)
- [JavaFX SDK](https://gluonhq.com/products/javafx/) (Download the JavaFX SDK)

### Building the Project

Clone the repository and build the project using your preferred method:

#### Using Command Line

1. Clone the repository:

    ```sh
    git clone https://github.com/Cypher-O/Password-Vault.git
    cd password-vault
    ```

2. Build and run the project using Maven:

    ```sh
    mvn clean install
    mvn javafx:run
    ```

#### Using an IDE

1. Import the project into your IDE (e.g., IntelliJ IDEA or Eclipse).
2. Configure your IDE to use the JavaFX SDK.
3. Build and run the project from the IDE.

## Usage

To run the application, use the following command from the project directory:

    ```sh
    mvn javafx:run
    ```

## How to Use

1. **Add New Password:**
    - Click the 'Add Password' button.
    - Fill in the details such as website, username, and password.
    - Click 'Save' to store the password.

2. **View Stored Passwords:**
    - Navigate to the 'Passwords' tab to see a list of stored passwords.
    - Click on a password entry to view its details.

3. **Search Passwords:**
    - Use the search bar to find specific passwords quickly by typing keywords.

4. **Edit or Delete Passwords:**
    - Select a password entry and use the 'Edit' or 'Delete' buttons to modify or remove the password.

5. **Encrypt Password:**
    - Select a password entry.
    - Click the 'Encrypt Password' button to encrypt the password.
    - The encrypted password will be displayed.

6. **Decrypt Password:**
    - Select an encrypted password entry.
    - Click the 'Decrypt Password' button to decrypt the password.
    - The decrypted password will be displayed.

7. **Load Vault:**
    - Click the 'Load Vault' button to load all stored passwords securely.
    - The entire vault of passwords will be displayed in the application.

## Contributing

Contributions are welcome! Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Screenshots

<table>
    <tr>
        <td>
            <img src="/src/resources/screenshots/screenshot1.png" alt="Register & Login" width="300">
            <p>Register & Login</p>
        </td>
        <td>
            <img src="/src/resources/screenshots/screenshot2.png" alt="Dashboard" width="300">
            <p>Dashboard</p>
        </td>
    </tr>
</table>

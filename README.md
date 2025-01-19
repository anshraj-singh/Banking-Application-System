# Banking Application System
# Banking Application System

A RESTful API for managing bank accounts with user authentication and authorization using Spring Boot, Spring Security, and MongoDB.

## Features
- User login with Basic Authentication.
- Account management with CRUD operations.
- Passwords are securely hashed using BCrypt.
- Stateless session management for secure REST APIs.

## Endpoints

### **Authentication**
- `GET /accounts/me`: Retrieve details of the authenticated user.
- `PUT /accounts/me`: Update details of the authenticated user.
- `DELETE /accounts/me`: Delete the authenticated user's account.

### **Account Management**
- `GET /accounts`: (Admin only) Retrieve all accounts.


Add Deposit and Withdraw Features with Transactions Handling

- Added `Transaction` entity to handle deposit and withdrawal operations.
- Updated `Account` entity to include a list of transactions with @DBRef.
- Created `TransactionController` for deposit, withdrawal, and transaction history endpoints.
- Secured endpoints using Spring Security with Basic Authentication.
- Implemented transaction-related logic in `TransactionService`.
- Added `TransactionRepository` for MongoDB integration.
- Updated `AccountController` and `AccountService` to support transaction features.
- Refactored code to enhance modularity and maintainability.
- Tested endpoints for deposit, withdrawal, and transaction history.

Endpoints:
- `POST /accounts/me/deposit?amount=500`
- `POST /accounts/me/withdraw?amount=300`
- `GET /accounts/me/transactions`


# Banking Application System

## Description

A Spring Boot-based banking application that allows users to create accounts, perform deposits and withdrawals, and view transaction history. The application is backed by MongoDB and secured with Spring Security using Basic Authentication.

### Features Implemented:
- **Account Management**:
  - Create a new account.
  - View account details for the authenticated user.
  - Update account details (e.g., username, password, account type, balance).
  - Delete account functionality for the authenticated user.

- **Transaction Management**:
  - **Deposit**: Add funds to an account.
  - **Withdraw**: Deduct funds from an account.
  - **Transaction History**: View the list of deposits and withdrawals made for the authenticated user.

- **Security**:
  - Basic Authentication to secure endpoints.
  - Authentication managed using Spring Security.

- **Database**:
  - MongoDB used for data persistence.
  - Transactions are linked to accounts using `@DBRef`.

## Endpoints

- **POST** `/accounts`: Create a new account (requires account details).
- **GET** `/accounts/me`: Get the authenticated user's account details.
- **PUT** `/accounts/me`: Update the authenticated user's account details.
- **DELETE** `/accounts/me`: Delete the authenticated user's account.
- **POST** `/accounts/me/deposit`: Deposit money into the authenticated user's account.
- **POST** `/accounts/me/withdraw`: Withdraw money from the authenticated user's account.


# Banking Application System

## Description

A Spring Boot-based banking application that allows users to create accounts, perform deposits and withdrawals, and view transaction history. The application is backed by MongoDB and secured with Spring Security using Basic Authentication.

### Features Implemented:
- **Account Management**:
- Create a new account.
- View account details for the authenticated user.
- Update account details (e.g., username, password, account type, balance).
- Delete account functionality for the authenticated user.

- **Transaction Management**:
- **Deposit**: Add funds to an account.
- **Withdraw**: Deduct funds from an account.
- **Transaction History**: View the list of deposits and withdrawals made for the authenticated user.
- **Security**:
- Basic Authentication to secure endpoints.
- Authentication managed using Spring Security.

- **Database**:
- MongoDB used for data persistence.
- Transactions are linked to accounts using `@DBRef`.

 ## Endpoints

 - **POST** `/accounts`: Create a new account (requires account details).
 - **GET** `/accounts/me`: Get the authenticated user's account details.
 - **PUT** `/accounts/me`: Update the authenticated user's account details.
 - **DELETE** `/accounts/me`: Delete the authenticated user's account.
 - **POST** `/accounts/me/deposit`: Deposit money into the authenticated us
 - **POST** `/accounts/me/withdraw`: Withdraw money from the authenticated user's account.
 - **GET** `/accounts/me/transactions`: View transaction history for the authenticated user's account.


## Recent Updates

### Transaction Tracking Enhancement
- All transactions are now directly linked to user accounts using `@DBRef` in the `Account` entity.
- Whenever a transaction occurs (deposit/withdrawal), it is saved in the `Transaction` collection and added to the user's account.
- This ensures that each user's transaction history is accessible via their account.

### Key Files Modified:
- `Account.java`
- Added a `List<Transaction>` field annotated with `@DBRef`.
- `TransactionService.java`
- Modified `deposit()` and `withdraw()` methods to save and link transactions to the user's account.

#### Implement role-based functionality with USER and ADMIN roles

- Added 'role' field to Account entity to differentiate between USER and ADMIN roles.
- Updated Spring Security configuration for role-based access control (RBAC).
- Modified CustomeUserDetailsService to include roles in authentication.
- Secured endpoints with role-based restrictions (USER and ADMIN).
- Added admin-specific endpoints in AccountController for managing accounts and transactions.
- Set default role as USER during account creation if not specified.
- Wrote integration tests for role-based access control on endpoints.
- Updated project documentation to reflect role-based features.

## Account Transfer Feature
Description
The Account Transfer Feature allows users to transfer funds between their accounts securely and efficiently. This feature is designed to provide a seamless user experience, enabling users to manage their finances effectively.

How it Works
User Authentication: Users must be authenticated to access the account transfer feature.
Account Selection: Users select the source and destination accounts for the transfer.
Amount Entry: Users enter the amount to be transferred.
Transfer Confirmation: Users confirm the transfer details before initiating the transaction.
Transaction Processing: The system processes the transfer, updating the account balances accordingly.

## Recent Updates

Added functionality to record transactions for both sender and recipient during money transfers.
<br>
Updated TransactionService to ensure that both accounts maintain accurate transaction histories.
<br>
Improved error handling for insufficient funds and invalid account transfers.

# User Notifications Feature

## Overview
The User Notifications feature sends email alerts to users for important events such as successful transactions and low balance alerts. This feature enhances user experience by keeping them informed about their account activities.

## Features
- **Email Notifications**: Users receive notifications via email for:
  - Successful deposits
  - Successful withdrawals
  - Successful transfers
  - Low balance alerts

## Implementation
1. **Database Changes**: The `Account` entity has been updated to include an `email` field for storing user email addresses.
2. **Email Service**: A new `EmailService` class has been created to handle sending emails using JavaMailSender.
3. **Transaction Notifications**: The `TransactionService` has been modified to send notifications upon successful transactions.

## Configuration
Ensure to configure your email settings in `application.properties`:
## properties - 
spring.mail.host=smtp.example.com
spring.mail.port=587
spring.mail.username=your-email@example.com
spring.mail.password=your-email-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true


# Banking Application System [UPDATE]

## Overview
The Banking Application System is a comprehensive platform that allows users to manage their banking activities, including account management, transactions, and notifications.

## Features
- User registration and authentication
- Account management (view, update, delete)
- Transaction management (deposit, withdrawal, transfer)
- **Account Notifications**: Alerts users about important account activities such as low balances and large transactions.

## Account Notifications
The Account Notifications feature allows users to receive alerts regarding significant account activities. Users will be notified in the following scenarios:

- **Low Balance Notification**: Users will receive a notification when their account balance falls below a specified threshold (e.g., $100).
- **Large Transaction Notification**: Users will be alerted when a transaction exceeds a defined amount (e.g., $1,000).

### Key Features
- Customizable notification settings for account activities.
- Notifications are stored in the database and can be retrieved via the API.
- Integration with email services for sending notifications (optional).

### API Endpoint
- **Get Notifications**
  - **URL**: `/api/notifications`
  - **Method**: `GET`
  - **Query Parameter**: `accountId` (string) - The ID of the account for which notifications are requested.
  - **Response**: A list of notifications in JSON format.

### Example Request
```http
GET /api/notifications?accountId=12345

[
    {
        "id": "1",
        "accountId": "12345",
        "message": "Your account balance is low: $50",
        "createdAt": "2024-12-19T10:00:00"
    },
    {
        "id": "2",
        "accountId": "12345",
        "message": "A large transaction of $1500 has been made.",
        "createdAt": "2024-12-19T11:00:00"
    }
]
```

## Recent Updates

### Account Creation Confirmation Email
- Implemented a feature to send a confirmation email to users upon successful account creation.
- Users receive a welcome email with their account details after registration.

## Account Statements Feature

### Overview
The Account Statements feature allows users to generate and download their account statements for a specified date range in PDF format. This feature enhances user experience by providing a convenient way to access transaction history in a structured format.

### API Endpoint
- **Endpoint**: `/api/statements/me`
- **Method**: `GET`
- **Query Parameters**:
  - `startDate`: The start date of the date range in `yyyy-MM-dd'T'HH:mm:ss` format (e.g., `2024-12-01T00:00:00`).
  - `endDate`: The end date of the date range in `yyyy-MM-dd'T'HH:mm:ss` format (e.g., `2024-12-31T23:59:59`).

### Example Request
```http
GET http://localhost:8080/api/statements/me?startDate=2024-12-01T00:00:00&endDate=2024-12-31T23:59:59
```

## Password Reset Functionality

### Overview

The password reset functionality allows users who forget their passwords to securely reset them. This feature includes the following components:

1. **Password Reset Request Endpoint**: Users can request a password reset by providing their account holder name.
2. **Password Reset Token Generation**: A unique token is generated for the password reset link, which is sent to the user's registered email.
3. **Email Service Update**: The email service is updated to send the password reset link to the user's email.
4. **Password Reset Endpoint**: Users can reset their password using the token provided in the email.

### Implementation Steps

1. **Account Class Update**:
  - Added new fields for password reset:
    - `private String resetToken;`
    - `private LocalDateTime resetTokenExpiration;`

2. **Email Service Update**:
  - Added a method to send password reset emails:
    ```java
    public void sendPasswordResetEmail(String toEmail, String resetLink) {
        // Implementation
    }
    ```

3. **Password Reset Controller**:
  - Created a new controller to handle password reset requests and token validation:
    ```java
    @RestController
    @RequestMapping("/api/password-reset")
    public class PasswordResetController {
        // Implementation
    }
    ```

4. **Account Service Update**:
  - Added methods to find an account by reset token and to save the account:
    ```java
    public Account findByResetToken(String resetToken) {
        // Implementation
    }
    ```

5. **Account Repository Update**:
  - Updated the repository to include a method for finding accounts by reset token:
    ```java
    Account findByResetToken(String resetToken);
    ```

### Usage

- **Request Password Reset**:
  - Endpoint: `POST /api/password-reset/request`
  - Parameters: `accountHolderName`
  - Response: A password reset link will be sent to the registered email.

- **Reset Password**:
  - Endpoint: `POST /api/password-reset/reset`
  - Parameters: `token`, `newPassword`
  - Response: The password will be reset successfully if the token is valid.

### Email Notification

After the password has been successfully updated, the user receives an email notification with the following content:

- **Subject**: Your Password Has Been Updated
- **Body**:
```
      Hello [Username],

      Your password has been successfully updated. Your new password is: [NewPassword]

       If you did not request this change, please contact support.

```

## Password Reset Functionality[Update]

To ensure that only authenticated users can reset their passwords, the `PasswordResetController` has been updated to include authentication and authorization checks. The password reset method now verifies if the user is authenticated before allowing them to reset their password.

### Key Changes:
- Added checks to confirm user authentication.
- Ensured that only the account holder can reset their password using a valid reset token.

## Recent Updates

### Account Types and Interest Rates Feature

- **Feature**: Users can now choose different account types (e.g., savings, checking, fixed deposit) when creating an account.
- **Implementation**:
  - Added a new field `accountType` to the `Account` entity to specify the type of account.
  - Added a new field `interestRate` to the `Account` entity to store the interest rate associated with the account type.
  - Implemented logic in the `AccountService` to set the default interest rate based on the selected account type during account creation.
  - Updated the `AccountController` to allow users to specify the account type when creating or updating their accounts.

## Recent Updates

### Transaction Limits and Alerts Feature

- **Feature**: Users can now set daily, weekly, and monthly transaction limits for their accounts.
- **Implementation**:
  - Added fields for `dailyTransactionLimit`, `weeklyTransactionLimit`, and `monthlyTransactionLimit` in the `Account` entity.
  - Implemented logic in the `TransactionService` to check these limits before processing deposits, withdrawals, or transfers.
  - Updated the `AccountController` to allow users to set their transaction limits when creating or updating their accounts.
  - Integrated a notification system to alert users when they are approaching their transaction limits.

## Account Comparison Tool

The Account Comparison Tool allows users to compare different types of bank accounts available in the system. This feature provides essential information about each account type, including:

- **Account Type**: The name of the account (e.g., Savings, Checking, Fixed Deposit).
- **Interest Rate**: The interest rate associated with the account type.
- **Features**: A brief description of the account's features.

### API Endpoint

- **GET** `/account-comparison`: Retrieves a list of available account types along with their interest rates and features.

### Response

The response will return a list of accounts with the following fields:

```json
[
    {
        "id": "string",
        "accountHolderName": "string",
        "accountPassword": "string",
        "accountType": "string",
        "balance": 0.0,
        "interestRate": 4.0,
        "createdAt": "2024-12-09T20:42:35.982",
        "email": "string",
        "roles": ["USER", "ADMIN"],
        "transactions": [],
        "dailyTransactionLimit": 0.0,
        "weeklyTransactionLimit": 0.0,
        "monthlyTransactionLimit": 0.0,
        "resetToken": "string",
        "resetTokenExpiration": "string",
        "features": "string"
    }
]
```

### Example Response

```json
[
{
"id": "67530a6632e56804a39a7351",
"accountHolderName": "rohan",
"accountPassword": "$2a$10$Boq0pm7OlFB1NYq/ukge0.C.ch5WvY3OL5ORYOy8kxKVHltI2xUUe",
"accountType": "Savings",
"balance": 10.0,
"interestRate": 4.0,
"createdAt": "2024-12-09T20:42:35.982",
"email": "rohan123@gmail.com",
"roles": ["USER", "ADMIN"],
"transactions": [],
"dailyTransactionLimit": 0.0,
"weeklyTransactionLimit": 0.0,
"monthlyTransactionLimit": 0.0,
"resetToken": null,
"resetTokenExpiration": null,
"features": "Low risk, interest-bearing account, suitable for saving money."
}
]
```


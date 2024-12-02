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

import java.util.*;

public class ATM {
    private List<Bank> banks;
    private List<Customer> customers;

    public ATM() {
        banks = new ArrayList<>();
        customers = new ArrayList<>();
    }

    public void addBank(Bank bank) {
        banks.add(bank);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void displayBanks() {
        System.out.println("Banks available:");
        for (Bank bank : banks) {
            System.out.println(bank.getName() + ": " + bank.getDetails());
        }
    }

    public void adminMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Display Banks");
            System.out.println("2. Add Customer");
            System.out.println("3. Exit Admin Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    displayBanks();
                    break;
                case 2:
                    addNewCustomer();
                    break;
                case 3:
                    System.out.println("Exiting Admin Menu.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 3);
    }

    public void addNewCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Adding a new customer:");
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        System.out.print("Enter account number: ");
        int accountNumber = scanner.nextInt();
        System.out.print("Enter initial balance: ");
        double balance = scanner.nextDouble();
        Customer customer = new Customer(name, accountNumber, balance);
        customers.add(customer);
        System.out.println("Customer added successfully.");
    }

    public void customerMenu(Customer customer) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\nCustomer Menu for " + customer.getName() + ":");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. Change PIN");
            System.out.println("6. Exit Customer Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Current Balance: $" + customer.getBalance());
                    break;
                case 2:
                    System.out.print("Enter amount to deposit: $");
                    double depositAmount = scanner.nextDouble();
                    customer.deposit(depositAmount);
                    break;
                case 3:
                    System.out.print("Enter amount to withdraw: $");
                    double withdrawAmount = scanner.nextDouble();
                    customer.withdraw(withdrawAmount);
                    break;
                case 4:
                    System.out.print("Enter recipient's account number: ");
                    int recipientAccount = scanner.nextInt();
                    Customer recipient = findCustomerByAccountNumber(recipientAccount);
                    if (recipient != null) {
                        System.out.print("Enter amount to transfer: $");
                        double transferAmount = scanner.nextDouble();
                        customer.transfer(recipient, transferAmount);
                    } else {
                        System.out.println("Recipient account not found.");
                    }
                    break;
                case 5:
                    System.out.print("Enter new PIN: ");
                    int newPin = scanner.nextInt();
                    customer.changePin(newPin);
                    break;
                case 6:
                    System.out.println("Exiting Customer Menu.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 6);
    }

    public Customer findCustomerByAccountNumber(int accountNumber) {
        for (Customer customer : customers) {
            if (customer.getAccountNumber() == accountNumber) {
                return customer;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ATM atm = new ATM();
        atm.addBank(new Bank("KVB Bank", "Details of KVB Bank"));
        atm.addBank(new Bank("HDFC Bank", "Details of HDFC Bank"));
        atm.addBank(new Bank("ICICI Bank", "Details of ICICI Bank"));

        while (true) {
            System.out.println("\nWelcome to ATM");
            System.out.println("1. Admin");
            System.out.println("2. Customer");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    atm.adminMenu();
                    break;
                case 2:
                    System.out.print("Enter your account number: ");
                    int accountNumber = scanner.nextInt();
                    Customer customer = atm.findCustomerByAccountNumber(accountNumber);
                    if (customer != null) {
                        atm.customerMenu(customer);
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;
                case 3:
                    System.out.println("Exiting ATM.");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}

class Bank {
    private String name;
    private String details;

    public Bank(String name, String details) {
        this.name = name;
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }
}

class Customer {
    private String name;
    private int accountNumber;
    private double balance;
    private int pin;

    public Customer(String name, int accountNumber, double balance) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposit successful. New balance: $" + balance);
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Withdrawal successful. New balance: $" + balance);
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void transfer(Customer recipient, double amount) {
        if (balance >= amount) {
            withdraw(amount);
            recipient.deposit(amount);
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Transfer failed: Insufficient funds.");
        }
    }

    public void changePin(int newPin) {
        pin = newPin;
        System.out.println("PIN changed successfully.");
    }
}

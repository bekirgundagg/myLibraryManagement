//Bekir Gündağ

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
abstract class LibraryUser {
    private String username, password;
    private static int ID = 0;

    LibraryUser(String username, String password) {
        int ID;
        this.username = username;
        this.password = password;
        ID = LibraryUser.ID;
        LibraryUser.ID++;
        System.out.println("New user is initialized with username: " + username + " and password: " + password);
    }

    abstract public void displayUserInfo();

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getID() {
        return ID;
    }

    public void setUsername(String a) {
        username = a;
        System.out.println("Username has changed as: " + a);
    }

    public void setPassword(String a) {
        password = a;
        System.out.println("Your password has changed as: " + a);
    }

}

class Book {
    String title, author, genre, ISBN;
    boolean available;
    static int bookCount = 0;

    public Book(String title, String author, String genre, String ISBN, boolean available) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.ISBN = ISBN;
        this.available = available;
        System.out.println(title + " is initialized");
        bookCount++;
    }

    public void borrowBook() {
        available = false;
    }

    public void returnBook() {
        available = true;
    }
}

class Member extends LibraryUser {
    List<Book> borrowed;

    Member(String username, String password) {
        super(username, password);
        borrowed = new ArrayList<>();
    }

    @Override
    public void displayUserInfo() {
        System.out.println("Member: " + getUsername());
        System.out.println("userID: " + getID());
    }
}


class MyLibrary extends WindowAdapter implements ActionListener {
    Frame login, signin, mainmenu;
    public static int loggedInIndex = -1, selectedBook;
    //login
    TextField tun, tpw;
    Label l1, l2;
    Button b1, b2;
    //signin
    Button sigok ,sigback;
    TextField sigun, sigpw, sigpw2;
    Label sigu,sigp,sigp2;
    //mainmenu
    Label welcome, bookav, btitle, bauthor, bgenre, bisbn, bavailable, btitles, bauthors, bgenres, bisbns, bavailables;
    Button showDetails, borrow, returnBack, baddBook, signout, accountOptions;
    List<Member> members;
    List<Book> books;
    Choice bookList;
    //addbook
    TextField addTitle, addAuthor, addGenre, addISBN;
    Button badd_addBook, bgenerateISBN;
    Frame faddBook;
    Label laddTitle, laddAuthor, laddGenre, laddISBN;
    //accountsettings
    Frame facset , fchangeUsername, fchangePassword , fdeleteAccount;
    Button bacset_back, bacset_changeUsername, bacset_changePassword, bacset_deleteAccount , bacset_signout;
    //change username
    TextField newUsername;
    Label cul;
    Button cuBack,cuOK;
    //change password
    TextField password ,newPassword,newPassword2;
    Label lcp,lcp1,lcp2;
    Button cpBack,cpOK;
    //delete account
    TextField passforDelete;
    Label dul;
    Button duBack,duOK;
    Random rand = new Random();

    MyLibrary() {
        members = new ArrayList<>();
        books = new ArrayList<>();
        //textfields
        tun = new TextField();
        tun.setBounds(50, 70, 150, 25);
        tpw = new TextField();
        tpw.setBounds(50, 130, 150, 25);
        tpw.setEchoChar('*');
        // labels
        l1 = new Label("Enter your username:");
        l1.setBounds(50, 50, 200, 15);
        l2 = new Label(("Enter your password:"));
        l2.setBounds(50, 110, 200, 15);
        //buttons
        b1 = new Button("Log in");
        b1.setBounds(50, 170, 150, 30);
        b2 = new Button("Sign in");
        b2.setBounds(50, 200, 150, 30);

        //login frame
        login = new Frame("Library");
        login.setSize(250, 250);
        login.setLayout(null);
        login.setLocation(200, 200);
        login.setVisible(true);
        login.add(tun);
        login.add(tpw);
        login.add(l1);
        login.add(l2);
        login.add(b1);
        login.add(b2);
        b1.addActionListener(this);
        b2.addActionListener(this);
        //register frame
        signin = new Frame("Library");
        signin.setSize(180, 250);
        signin.setLocation(250, 300);
        signin.setLayout(null);
        signin.setVisible(false);
        sigu = new Label("Username: ");
        sigu.setBounds(15,40,150,15);
        signin.add(sigu);
        sigun = new TextField();
        sigun.setBounds(15, 60, 150, 20);
        sigpw = new TextField();
        sigp = new Label("Password: ");
        sigp.setBounds(15,100,150,15);
        signin.add(sigp);
        sigpw.setBounds(15, 120, 150, 25);
        sigpw.setEchoChar('*');
        sigp2 = new Label("Password again: ");
        sigp2.setBounds(15,160,150,15);
        signin.add(sigp2);
        sigpw2 = new TextField();
        sigpw2.setBounds(15, 180, 150, 25);
        sigpw2.setEchoChar('*');
        sigok = new Button("Register");
        sigok.setBounds(95, 215, 70, 20);
        sigback = new Button("Back");
        sigback.setBounds(15,215,70,20);
        signin.add(sigback);
        sigback.addActionListener(this);

        signin.add(sigun);
        signin.add(sigpw);
        signin.add(sigpw2);
        signin.add(sigok);
        sigok.addActionListener(this);

        //mainmenu frame
        mainmenu = new Frame("Library");
        mainmenu.setSize(360, 400);
        mainmenu.setLocation(200, 200);
        mainmenu.setLayout(null);
        bookList = new Choice();
        bookList.setBounds(50, 160, 250, 30);
        mainmenu.add(bookList);
        bookav = new Label("Books in the library: ");
        bookav.setBounds(55, 130, 250, 30);
        showDetails = new Button("Show Details");
        showDetails.setBounds(100, 190, 150, 30);
        showDetails.addActionListener(this);

        btitles = new Label("");
        btitles.setBounds(75, 230, 225, 30);
        btitles.setBackground(Color.LIGHT_GRAY);
        btitles.setForeground(Color.BLUE);
        mainmenu.add(btitles);
        bauthors = new Label("");
        bauthors.setBounds(90, 250, 210, 30);
        bauthors.setBackground(Color.LIGHT_GRAY);
        bauthors.setForeground(Color.BLUE);
        mainmenu.add(bauthors);
        bgenres = new Label("");
        bgenres.setBounds(80, 270, 220, 30);
        bgenres.setBackground(Color.LIGHT_GRAY);
        bgenres.setForeground(Color.BLUE);
        mainmenu.add(bgenres);
        bisbns = new Label("");
        bisbns.setBounds(80, 290, 220, 30);
        bisbns.setBackground(Color.LIGHT_GRAY);
        bisbns.setForeground(Color.BLUE);
        mainmenu.add(bisbns);
        bavailables = new Label("");
        bavailables.setBounds(120, 310, 180, 30);
        bavailables.setBackground(Color.LIGHT_GRAY);
        bavailables.setForeground(Color.BLUE);
        mainmenu.add(bavailables);

        btitle = new Label("Title: ");
        btitle.setBounds(40, 230, 250, 30);
        btitle.setBackground(Color.LIGHT_GRAY);
        mainmenu.add(btitle);
        bauthor = new Label("Author: ");
        bauthor.setBounds(40, 250, 250, 30);
        bauthor.setBackground(Color.LIGHT_GRAY);
        mainmenu.add(bauthor);
        bgenre = new Label("genre: ");
        bgenre.setBounds(40, 270, 250, 30);
        bgenre.setBackground(Color.LIGHT_GRAY);
        mainmenu.add(bgenre);
        bisbn = new Label("ISBN: ");
        bisbn.setBounds(40, 290, 250, 30);
        bisbn.setBackground(Color.LIGHT_GRAY);
        mainmenu.add(bisbn);
        bavailable = new Label("Is available: ");
        bavailable.setBounds(40, 310, 250, 30);
        bavailable.setBackground(Color.LIGHT_GRAY);
        mainmenu.add(bavailable);

        borrow = new Button("Borrow");
        borrow.setBounds(175, 350, 130, 30);
        mainmenu.add(borrow);
        borrow.addActionListener(this);
        returnBack = new Button("Return book");
        returnBack.setBounds(30, 350, 130, 30);
        mainmenu.add(returnBack);
        returnBack.addActionListener(this);

        signout = new Button("Sign out");
        signout.setBounds(200, 50, 130, 30);
        mainmenu.add(signout);
        signout.addActionListener(this);

        welcome = new Label("");
        mainmenu.add(showDetails);

        baddBook = new Button("Add book");
        baddBook.setBounds(200, 110, 130, 30);
        accountOptions = new Button("Account options");
        accountOptions.setBounds(200, 80, 130, 30);
        mainmenu.add(baddBook);
        mainmenu.add(bookav);
        mainmenu.add(accountOptions);
        mainmenu.add(bookav);
        baddBook.addActionListener(this);

        //addbook
        faddBook = new Frame("Add Book");
        faddBook.setLayout(null);
        faddBook.setSize(300, 200);
        faddBook.setLocation(225, 300);
        addTitle = new TextField("");
        addTitle.setBounds(80, 50, 180, 20);
        addAuthor = new TextField("");
        addAuthor.setBounds(80, 80, 180, 20);
        addGenre = new TextField("");
        addGenre.setBounds(80, 110, 180, 20);
        addISBN = new TextField("");
        addISBN.setBounds(80, 140, 180, 20);
        faddBook.add(addTitle);
        faddBook.add(addAuthor);
        faddBook.add(addGenre);
        faddBook.add(addISBN);
        laddTitle = new Label("Title: ");
        laddTitle.setBounds(40, 50, 50, 20);
        faddBook.add(laddTitle);
        laddAuthor = new Label("Author: ");
        laddAuthor.setBounds(25, 80, 70, 20);
        faddBook.add(laddAuthor);
        laddGenre = new Label("Genre: ");
        laddGenre.setBounds(33, 110, 50, 20);
        faddBook.add(laddGenre);
        laddISBN = new Label("ISBN: ");
        laddISBN.setBounds(40, 140, 50, 20);
        faddBook.add(laddISBN);
        bgenerateISBN = new Button("Generate ISBN");
        bgenerateISBN.setBounds(80, 170, 100, 20);
        faddBook.add(bgenerateISBN);
        badd_addBook = new Button("Add book");
        badd_addBook.setBounds(180, 170, 80, 20);
        faddBook.add(badd_addBook);
        badd_addBook.addActionListener(this);
        bgenerateISBN.addActionListener(this);

        //accountSettings
        accountOptions.addActionListener(this);
        facset = new Frame("Account Settings");
        facset.setSize(200,220);
        facset.setLayout(null);
        facset.setLocation(360,235);
        bacset_back = new Button("Back");
        bacset_back.setBounds(25,50,150,30);
        facset.add(bacset_back);
        bacset_changeUsername = new Button("Change Username");
        bacset_changeUsername.setBounds(25,80,150,30);
        facset.add(bacset_changeUsername);
        bacset_changePassword = new Button("Change Password");
        bacset_changePassword.setBounds(25,110,150,30);
        facset.add(bacset_changePassword);
        bacset_signout = new Button("Sign out");
        bacset_signout.setBounds(25,140,150,30);
        facset.add(bacset_signout);
        bacset_deleteAccount = new Button("Delete Account");
        bacset_deleteAccount.setBounds(25,170,150,30);
        facset.add(bacset_deleteAccount);
        bacset_back.addActionListener(this);
        bacset_changeUsername.addActionListener(this);
        bacset_changePassword.addActionListener(this);
        bacset_signout.addActionListener(this);
        bacset_deleteAccount.addActionListener(this);
        //change username
        fchangeUsername = new Frame("Change Username");
        fchangeUsername.setSize(180,120);
        fchangeUsername.setLocation(560,260);
        fchangeUsername.setLayout(null);
        cul = new Label("New username:");
        cul.setBounds(15,40,150,10);
        fchangeUsername.add(cul);
        newUsername = new TextField("");
        newUsername.setBounds(15,60,150,20);
        fchangeUsername.add(newUsername);
        cuBack = new Button("Back");
        cuBack.setBounds(15,85,70,20);
        fchangeUsername.add(cuBack);
        cuOK = new Button("Change");
        cuOK.setBounds(95,85,70,20);
        fchangeUsername.add(cuOK);
        cuOK.addActionListener(this);
        cuBack.addActionListener(this);
        //change password
        fchangePassword = new Frame("Change Password");
        fchangePassword.setSize(180,250);
        fchangePassword.setLocation(560,290);
        fchangePassword.setLayout(null);
        lcp = new Label("Current Password: ");
        lcp.setBounds(15,40,150,15);
        fchangePassword.add(lcp);
        password = new TextField("");
        password.setBounds(15,60,150,20);
        fchangePassword.add(password);
        lcp1 = new Label("New Password: ");
        lcp1.setBounds(15,100,150,15);
        fchangePassword.add(lcp1);
        newPassword = new TextField("");
        newPassword.setBounds(15,120,150,20);
        fchangePassword.add(newPassword);
        lcp2 = new Label("New Password Again: ");
        lcp2.setBounds(15,160,150,15);
        fchangePassword.add(lcp2);
        newPassword2 = new TextField("");
        newPassword2.setBounds(15,180,150,20);
        fchangePassword.add(newPassword2);
        cpBack = new Button("Back");
        cpBack.setBounds(15,205,70,20);
        fchangePassword.add(cpBack);
        cpOK = new Button("Change");
        cpOK.setBounds(95,205,70,20);
        fchangePassword.add(cpOK);
        cpBack.addActionListener(this);
        cpOK.addActionListener(this);

        //delete account
        fdeleteAccount = new Frame("Delete Username");
        fdeleteAccount.setSize(180,120);
        fdeleteAccount.setLocation(560,350);
        fdeleteAccount.setLayout(null);
        dul = new Label("Enter your Password: ");
        dul.setBounds(15,40,150,15);
        fdeleteAccount.add(dul);
        passforDelete = new TextField("");
        passforDelete.setBounds(15,60,150,20);
        fdeleteAccount.add(passforDelete);
        duBack = new Button("Back");
        duBack.setBounds(15,85,70,20);
        fdeleteAccount.add(duBack);
        duOK = new Button("Delete");
        duOK.setBounds(95,85,70,20);
        fdeleteAccount.add(duOK);
        duBack.addActionListener(this);
        duOK.addActionListener(this);


        //dispose
        login.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                login.dispose();
            }
        });
        mainmenu.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                mainmenu.dispose();
            }
        });
        signin.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                signin.dispose();
            }
        });
        faddBook.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                faddBook.dispose();
            }
        });
        facset.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                facset.dispose();
            }
        });
        fchangeUsername.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                fchangeUsername.dispose();
            }
        });
        fchangePassword.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                fchangePassword.dispose();
            }
        });
        fdeleteAccount.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                fdeleteAccount.dispose();
            }
        });



    }

    public void reloadBookList() {
        bookList.removeAll();
        for (int i = 0; i < books.size(); i++) {
            bookList.add(books.get(i).title);
        }
        System.out.println("BookList is reloaded!");
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b2) {
            login.dispose();
            signin.setVisible(true);
        } else if(e.getSource() == sigback){
            login.setVisible(true);
            signin.dispose();

        }


        else if (e.getSource() == bgenerateISBN) {
            addISBN.setText("");
            for (int i = 1; i < 14; i++) {
                if (i == 4) addISBN.setText(addISBN.getText() + "-");
                int random = rand.nextInt(10);
                addISBN.setText(addISBN.getText() + Integer.toString(random));
            }
        } else if(e.getSource() == accountOptions){
            mainmenu.dispose();
            facset.setVisible(true);
        } else if(e.getSource() == duBack) {
            fdeleteAccount.dispose();
        } else if(e.getSource() == duOK){
            if(Objects.equals(members.get(loggedInIndex).getPassword(), passforDelete.getText())){
                if(members.get(loggedInIndex).borrowed.isEmpty()){
                    fdeleteAccount.dispose();
                    facset.dispose();
                    System.out.println("Your account has successfully deleted");
                    members.remove(loggedInIndex);
                    login.setVisible(true);
                }
                else{
                    System.out.println("You cannot delete account with borrowed books");
                }

            }
            else{
                System.out.println("Wrong password");
            }
        } else if(e.getSource() == bacset_deleteAccount){
            fdeleteAccount.setVisible(true);
        }

        else if(e.getSource() == bacset_back){
            facset.dispose();
            mainmenu.setVisible(true);
        } else if(e.getSource() == bacset_changeUsername){
            fchangeUsername.setVisible(true);
        } else if(e.getSource() == cuBack){
            fchangeUsername.dispose();
        } else if(e.getSource() == cuOK){
            int go = 0;
            for(int i = 0; i< members.size(); i++){
                if(Objects.equals(members.get(i).getUsername(), newUsername.getText())){
                    System.out.println("This username is already taken.");
                    go = -1;
                }
            }
            if(go == 0){
                members.get(loggedInIndex).setUsername(newUsername.getText());
                System.out.println("Your username has successfully changed!");
                welcome.setText("Welcome: " + members.get(loggedInIndex).getUsername());
                fchangeUsername.dispose();
            }
        }else if(e.getSource() == bacset_changePassword){
            fchangePassword.setVisible(true);
        }

        else if(e.getSource() == cpBack) {
            fchangePassword.dispose();
        } else if(e.getSource() == cpOK){
            if(Objects.equals(members.get(loggedInIndex).getPassword(), password.getText())){
                if(Objects.equals(newPassword.getText(), newPassword2.getText())){
                    members.get(loggedInIndex).setPassword(newPassword.getText());
                    System.out.println("Your password hsa successfully changed");
                    fchangePassword.dispose();
                }
                else{
                    System.out.println("Passwords are not equal");
                }
            }
            else{
                System.out.println("Wrong Password");
            }
        }

        else if(e.getSource() == bacset_signout){
            System.out.println(members.get(loggedInIndex).getUsername() + " has successfully signed out");
            loggedInIndex = -1;
            facset.dispose();
            login.setVisible(true);
        }


        else if (e.getSource() == badd_addBook) {
            int go = 0;
            if (!addTitle.getText().isEmpty() && !addAuthor.getText().isEmpty() && !addGenre.getText().isEmpty() && !addISBN.getText().isEmpty()) {
                for (int i = 0; i < books.size(); i++) {
                    if (Objects.equals(addTitle.getText(), books.get(i).title) || Objects.equals(addISBN.getText(), books.get(i).ISBN)) {
                        go = -1;
                    }
                }
                if (go == 0) {
                    String title = addTitle.getText();
                    String author = addAuthor.getText();
                    String genre = addGenre.getText();
                    String ISBN = addISBN.getText();
                    Book b = new Book(title, author, genre, ISBN, true);
                    books.add(b);
                    faddBook.dispose();
                    reloadBookList();
                } else {
                    System.out.println("This book is already added to the library");
                }
            } else {
                System.out.println("All textbox should be filled");
            }
        } else if (e.getSource() == baddBook) {
            faddBook.setVisible(true);
        } else if (e.getSource() == signout) {
            System.out.println(members.get(loggedInIndex).getUsername() + " has successfully signed out");
            loggedInIndex = -1;
            mainmenu.dispose();
            login.setVisible(true);
        } else if (e.getSource() == returnBack) {
            int test = 0;
            for (int i = 0; i < members.get(loggedInIndex).borrowed.size(); i++) {
                if (books.get(selectedBook) == members.get(loggedInIndex).borrowed.get(i)) {
                    books.get(selectedBook).returnBook();
                    members.get(loggedInIndex).borrowed.remove(books.get(selectedBook));
                    bavailables.setText(String.valueOf(books.get(selectedBook).available));
                    System.out.println(btitles.getText() + " is returned. And book's detail refreshed.");
                    test = 1;
                }
            }
            if (test == 0) {
                System.out.println("This book was not borrowed by you.");
            }
        } else if (e.getSource() == borrow) {
            if (books.get(selectedBook).available) {
                books.get(selectedBook).borrowBook();
                members.get(loggedInIndex).borrowed.add(books.get(selectedBook));
                bavailables.setText(String.valueOf(books.get(selectedBook).available));
                System.out.println(btitles.getText() + " is borrowed by: " + members.get(loggedInIndex).getUsername() + ". And book's detail refreshed.");
            } else System.out.println(btitles.getText() + " is already borrowed.");
        } else if (e.getSource() == showDetails) {
            for (int i = 0; i < books.size(); i++) {
                if (Objects.equals(books.get(i).title, bookList.getSelectedItem())) {
                    System.out.println(books.get(i).title + "'s details are listed");
                    btitles.setText(books.get(i).title);
                    bauthors.setText(books.get(i).author);
                    bgenres.setText(books.get(i).genre);
                    bisbns.setText(books.get(i).ISBN);
                    bavailables.setText(String.valueOf(books.get(i).available));
                    selectedBook = i;
                }
            }
        } else if (e.getSource() == b1) {
            for (int i = 0; i < members.size(); i++) {
                if (Objects.equals(members.get(i).getUsername(), tun.getText()) && Objects.equals(members.get(i).getPassword(), tpw.getText())) {
                    login.dispose();
                    mainmenu.setVisible(true);
                    System.out.println("Logged in. welcome: " + members.get(i).getUsername());
                    MyLibrary.loggedInIndex = i;
                    welcome.setText("Welcome: " + members.get(i).getUsername());
                    welcome.setBounds(40, 50, 300, 30);
                    mainmenu.add(welcome);
                    break;
                } else if (i == members.size() - 1) {
                    System.out.println("Wrong information");
                }
            }
        } else if (e.getSource() == sigok) {
            String username = sigun.getText();
            String password = sigpw.getText();
            String confirmPassword = sigpw2.getText();
            if(username.isEmpty()){
                System.out.println("Username cannot be empty");
            } else if(password.isEmpty()){
                System.out.println("Password cannot be empty");
            }
            else if (Objects.equals(password, confirmPassword)) {
                boolean go = true;
                for (int i = 0; i < members.size(); i++) {
                    if (Objects.equals(username, members.get(i).getUsername())) {
                        System.out.println("This username is already taken");
                        go = false;
                    }
                }
                if (go) {
                    Member newMember = new Member(username, password);
                    members.add(newMember);
                    System.out.println("New member created: ");
                    newMember.displayUserInfo();
                    signin.dispose();
                    login.setVisible(true);
                }
            } else {
                System.out.println("Passwords do not match.");
            }
        }
    }


    public static void main(String[] args) {
        Member member1 = new Member("bekir2", "123");
        // Creating book objects with real book data
        Book book1 = new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", "978-0061120084", true);
        Book book2 = new Book("1984", "George Orwell", "Dystopian", "978-0451524935", true);
        Book book3 = new Book("Pride and Prejudice", "Jane Austen", "Romance", "978-0141439518", false);
        Book book4 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Classic", "978-0743273565", true);
        Book book5 = new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy", "978-0547928227", false);
        MyLibrary m1 = new MyLibrary();

        // Adding books to the library's book list
        m1.books.add(book1);
        m1.books.add(book2);
        m1.books.add(book3);
        m1.books.add(book4);
        m1.books.add(book5);
        m1.members.add(member1);
        m1.reloadBookList();
    }
}
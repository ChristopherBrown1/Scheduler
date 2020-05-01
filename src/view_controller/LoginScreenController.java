/*
    CHRISTOPHER BROWN
    C195 ADVANCED JAVA CONCEPTS
 */
package view_controller;

import DAO.UserDao;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;
import utilities.LanguageConversion;

/**
 *
 * @author brown
 */
public class LoginScreenController implements Initializable {

    @FXML
    private TextField username_text;
    @FXML
    private TextField password_text;
    @FXML
    private Button login_button;
    @FXML
    private Label label_text;
    @FXML
    private Label invalid_label;

    private User loggedInUser;

    @FXML
    private Label signInLabel;
    @FXML
    private Label userNameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label welcomeLabel;

    LanguageConversion convert = (wordToConvert, englishPhrase) -> {
        if (Locale.getDefault().getLanguage().equals("fr")) {
            try {
                ResourceBundle bundle = ResourceBundle.getBundle("utilities/Language", Locale.getDefault());

                // look in Language_fr.properties for the conversion phrase
                if (Locale.getDefault().getLanguage().equals("fr")) {
                    String convertedWord = bundle.getString(wordToConvert);
                    return convertedWord;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        // if it isnt French return the english phrase
        return englishPhrase;
    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO        

        setText();
    }

    private void setText() {
        welcomeLabel.setText(convert.LanguageConversion("Welcome", "Welcome to"));
        signInLabel.setText(convert.LanguageConversion("SignIn", "Sign In"));
        userNameLabel.setText(convert.LanguageConversion("Username", "Username"));
        passwordLabel.setText(convert.LanguageConversion("Password", "Password"));
        login_button.setText(convert.LanguageConversion("Login", "Log In"));
        if (Locale.getDefault().getLanguage().equals("fr")) {
            label_text.setText(convert.LanguageConversion("MainLabel", ""));
        }
    }

    private boolean isCorrectLoginInfo() {

        boolean correctPassword = false;
        ObservableList<User> list = UserDao.getUsernamesPasswords();
        for (int i = 0; i < list.size(); i++) {
            User userDB = list.get(i);
            String userNameDB = userDB.getUserName();
            String userPasswordDB = userDB.getPassword();
            String userNameField = username_text.getText();
            String userPasswordField = password_text.getText();

            if (userNameDB.equals(userNameField)) {
                // get i and check if username for i matches
                correctPassword = true;

                if (userPasswordDB.equals(userPasswordField)) {
                    //System.out.println("Go to next page");

// -------------------------------------
                    loggedInUser = UserDao.getLoggedInUser(userNameField, userPasswordField);
                    //System.out.println(loggedInUser.getUserId());

// ----------------------------------                            
                    return true;

                } else {
                    String correctUincorrectPLabel = "Invalid password...";
                    String correctUincorrectPText = "            Correct username but\n"
                            + "                 password is incorrect.";
                    invalid_label.setVisible(true);
                    invalid_label.setText(convert.LanguageConversion("InvalidPassword", correctUincorrectPLabel));
                    label_text.setText(convert.LanguageConversion("CorrectUWrongP", correctUincorrectPText));
                    return false;
                }

            } else {
                if (i == (list.size() - 1) && !correctPassword) {
                    String incorrectULabel = "Invalid username...";
                    String incorrectUText = "            Username not found...";
                    invalid_label.setVisible(true);
                    invalid_label.setText(convert.LanguageConversion("incorrectU", incorrectULabel));
                    label_text.setText(convert.LanguageConversion("incorrectUText", incorrectUText));
                    return false;
                }
            }
        }
        return false;
    }

    private boolean isFilledUsernamePassword() {
        String invalidUPLabel = "Empty username and password...";
        String invalidUPText = "            Enter your username and\n"
                + "                  password to begin.";
        String invalidULabel = "Empty username...";
        String invalidUText = "            You must enter valid\n"
                + "                   username to begin...";
        String invalidPLabel = "Empty password...";
        String invalidPText = "            You must enter valid\n"
                + "                   password to begin...";

        if (username_text.getText().isEmpty() && password_text.getText().isEmpty()) {
            invalid_label.setVisible(true);
            invalid_label.setText(convert.LanguageConversion("invalidUPLabel", invalidUPLabel));
            label_text.setText(convert.LanguageConversion("invalidUPText", invalidUPText));
            return false;
        } else if (username_text.getText().isEmpty() && !password_text.getText().isEmpty()) {
            invalid_label.setVisible(true);
            invalid_label.setText(convert.LanguageConversion("invalidULabel", invalidULabel));
            label_text.setText(convert.LanguageConversion("invalidUText", invalidUText));
            return false;
        } else if (!username_text.getText().isEmpty() && password_text.getText().isEmpty()) {
            invalid_label.setVisible(true);
            invalid_label.setText(convert.LanguageConversion("invalidPLabel", invalidPLabel));
            label_text.setText(convert.LanguageConversion("invalidPText", invalidPText));
            return false;
        } else {
            return true;
        }
    }

    @FXML
    private void loginButtonClicked(MouseEvent event) throws IOException {

        // if the username and password match it loads the next window
        if (isFilledUsernamePassword() && isCorrectLoginInfo()) {

            Stage stage;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view_controller/MainScreen.fxml")); // loads our main screen.
            view_controller.MainScreenController controller = new view_controller.MainScreenController(loggedInUser.getUserId(), username_text.getText(), true);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();

        }

    }

}

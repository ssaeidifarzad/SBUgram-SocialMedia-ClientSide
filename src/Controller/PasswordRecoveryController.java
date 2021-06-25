package Controller;

import Model.Connection;
import Model.Messages.ClientMessages.PasswordRecoveryRequest;
import Model.Messages.ClientMessages.SecurityQuestionsRequest;
import Model.Messages.ServerMessages.PasswordRecoveryResponse;
import Model.Messages.ServerMessages.SecurityQuestionsResponse;
import Model.PageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;

public class PasswordRecoveryController {
    public TextField username;
    public Label userNotFound;
    public Pane passwordRecovery;
    public TextField Q1Answer;
    public Text Q2Text;
    public TextField Q2Answer;
    public PasswordField password;
    public PasswordField repeatedPassword;
    public Label wrongPasswordFormat;
    public Text Q1Text;
    public Label incorrectAnswer;
    public Label passwordNotMatch;
    private String user;

    public void loadLoginPage(MouseEvent mouseEvent) {
        try {
            new PageLoader().load("Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void confirmNewPassword(ActionEvent actionEvent) {
        incorrectAnswer.setVisible(false);
        wrongPasswordFormat.setVisible(false);
        passwordNotMatch.setVisible(false);
        if (!password.getText().equals(repeatedPassword.getText())) {
            passwordNotMatch.setVisible(true);
            return;
        }
        ArrayList<String> answers = new ArrayList<>();
        answers.add(Q1Answer.getText());
        answers.add(Q2Answer.getText());
        Connection.sendMessage(new PasswordRecoveryRequest(user, answers, password.getText()));
        PasswordRecoveryResponse passwordRecoveryResponse = ((PasswordRecoveryResponse) Connection.receiveMessage());
        checkConfirmation(passwordRecoveryResponse);
    }

    public void checkUsername(ActionEvent actionEvent) {
        userNotFound.setVisible(false);
        passwordRecovery.setVisible(false);
        Connection.sendMessage(new SecurityQuestionsRequest(username.getText()));
        SecurityQuestionsResponse securityQuestionsResponse = ((SecurityQuestionsResponse) Connection.receiveMessage());
        if (securityQuestionsResponse.getResponses().get(0).equals("no_user")) {
            userNotFound.setVisible(true);
            return;
        }
        user = username.getText();
        username.setText("");
        setQuestions(securityQuestionsResponse);
    }

    private void setQuestions(SecurityQuestionsResponse securityQuestionsResponse) {
        incorrectAnswer.setVisible(false);
        wrongPasswordFormat.setVisible(false);
        passwordNotMatch.setVisible(false);
        Q1Text.setText(securityQuestionsResponse.getQuestions().get(0));
        Q2Text.setText(securityQuestionsResponse.getQuestions().get(1));
        passwordRecovery.setVisible(true);
    }

    private void checkConfirmation(PasswordRecoveryResponse passwordRecoveryResponse) {
        if (passwordRecoveryResponse.getResponses().get(0).equals("success")) {
            new Alert(Alert.AlertType.CONFIRMATION, "Your password changed successfully").showAndWait();
            try {
                new PageLoader().load("Login");
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (String s : passwordRecoveryResponse.getResponses()) {
            switch (s) {
                case "wrong_answers" -> incorrectAnswer.setVisible(true);
                case "wrong_password_format" -> wrongPasswordFormat.setVisible(true);
            }
        }
    }
}

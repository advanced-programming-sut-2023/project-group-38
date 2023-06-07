package view;

import controller.CommandParser;
//import controller.ProfileController;
import controller.Controller;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.ObservableFaceArray;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.User;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ProfileMenu extends Application {
    //    private final ProfileController profileController;
//    private final CommandParser commandParser;
    public Stage stage;
    private Style style;
    private Pane mainPane;
    private User currentUser;
    public ProfileMenu() {
        this.style = new Style();
//        this.profileController = profileController;
//        commandParser = new CommandParser();
    }


    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        currentUser = Controller.currentUser;
        stage.setResizable(false);
        Pane pane = new Pane();
        this.mainPane = pane;
        BackgroundSize backgroundSize = new BackgroundSize(1920, 1080, false, false, false, false);
        Image image = new Image(Objects.requireNonNull(LoginMenu.class.getResource("/images/background/loginBack.jpg")).toExternalForm());
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        pane.setBackground(new Background(backgroundImage));
        profileView(pane);
        stage.getScene().setRoot(pane);
        stage.setTitle("Login Menu");
        stage.show();
    }

    public void profileView(Pane pane) throws MalformedURLException {
        Pane profilePane = new Pane();
        profilePane.setPrefSize(550, 700);
        profilePane.setLayoutX(850);
        profilePane.setLayoutY(35);
//        profilePane.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderStroke.THIN)));
        pane.getChildren().add(profilePane);
        Circle avatar = new Circle(250, 150, 70);
        avatar.setFill(new ImagePattern(new Image(currentUser.getAvatar())));
        profilePane.getChildren().add(avatar);
        TextField sloganField = createTextField(currentUser.getSlogan(),-10, 790, 80, 1560, pane);
        sloganField.setOpacity(1.0);sloganField.setAlignment(Pos.CENTER);sloganField.setFont(style.Font0(30));
        createText("Username:", 45, 260, 25, Color.BLACK, profilePane);
        TextField usernameField = createTextField(currentUser.getUserName(), 75, 275, 70, 390, profilePane);
        createText("Nick Name:", 45, 375, 25, Color.BLACK, profilePane);
        TextField nickNameField = createTextField(currentUser.getNickName(), 75, 390, 70, 390, profilePane);
        createText("Email Address:", 45, 490, 25, Color.BLACK, profilePane);
        TextField emailField = createTextField(currentUser.getEmail(), 75, 505, 70, 390, profilePane);
        ImageView editImageForName = createEdit(430, 295, profilePane);
        ImageView editImageForNickName = createEdit(430, 410, profilePane);
        ImageView editImageForEmail = createEdit(430, 525, profilePane);
        ImageView editImageForSlogan = createEdit(625, 785, profilePane);
        editImageForSlogan.setFitWidth(30);editImageForSlogan.setFitHeight(30);
        Button scoreBordButton = createButton("ScoreBord", 285, 610, 60, 200, 21, profilePane);
        Button changePassword = createButton("Change password", 55,610, 60, 200, 21, profilePane);
        Button back = createButton("Back", 200, 685, 60, 150, 25, profilePane);
        scoreBordButton.setTextFill(Color.BLACK);
        changePassword.setTextFill(Color.BLACK);
        back.setTextFill(Color.BLACK);
        Pane changePasswordPane = changePassword(profilePane);
        Pane showAvatars = showAvatars(profilePane, avatar);
//        ScrollPane scoreBordPane = scoreBord(profilePane);

        editImageForName.setOnMouseClicked(mouseEvent -> editName(usernameField, profilePane));
        editImageForNickName.setOnMouseClicked(mouseEvent -> editNickName(nickNameField, profilePane));
        editImageForEmail.setOnMouseClicked(mouseEvent -> editEmail(emailField, profilePane));
        editImageForSlogan.setOnMouseClicked(mouseEvent -> editSlogan(sloganField, profilePane));
        avatar.setOnMouseClicked(mouseEvent -> showAvatars.setVisible(true));
        scoreBordButton.setOnMouseClicked(mouseEvent -> scoreBord(profilePane));
        changePassword.setOnMouseClicked(mouseEvent -> changePasswordPane.setVisible(true));
        back.setOnMouseClicked(mouseEvent -> new MainMenu().start(stage));
    }

    public Text createText(String containText,int x, int y, int font, Color color, Pane pane) {
        Text text = new Text(x,y, containText);
        text.setFont(style.Font0(font));
        text.setFill(color);
        pane.getChildren().add(text);
        return text;
    }

    public TextField createTextField(String containText, int x, int y, int height, int width, Pane pane){
        TextField textField = new TextField();
        textField.setLayoutX(x);textField.setLayoutY(y);
        textField.setFont(style.Font0(25));
        textField.setOpacity(1.0);
        textField.setDisable(true);
        textField.setOpacity(1.0);
        style.textFiled0(textField, containText,width, height);
        pane.getChildren().add(textField);
        return textField;
    }

    public Button createButton(String containText, int x, int y, int height, int width, int fontSize, Pane pane){
        Button button = new Button();
        button.setLayoutX(x);button.setLayoutY(y);
        style.button0(button, containText,width, height);
        button.setFont(style.Font0(fontSize));
        pane.getChildren().add(button);
        return button;
    }

    public ImageView createEdit(int x, int y, Pane pane){
        ImageView imageView = new ImageView(new Image(ProfileMenu.class.getResource("/images/buttons/edit.png").toExternalForm()));
        imageView.setLayoutX(x);imageView.setLayoutY(y);
        imageView.setFitHeight(25);imageView.setFitWidth(25);
        pane.getChildren().add(imageView);
        return imageView;
    }

    private ScrollPane scoreBord(Pane profilePane) {
        TableView<User> tableView = new TableView<>();
        tableView.setPrefWidth(405);
        tableView.setFixedCellSize(60.0);
        tableView.setPrefHeight(655);
        TableColumn<User, Integer> numberColumn = new TableColumn<>("Rank");
        //این پایین چی نوشتم
        numberColumn.setCellValueFactory(cellData -> {
            int rowIndex = tableView.getItems().indexOf(cellData.getValue()) + 1;
            return new ReadOnlyObjectWrapper<>(rowIndex); // return the row index for other rows
        });
        numberColumn.setPrefWidth(100);
        numberColumn.setStyle("-fx-font-family: 'Comic Sans MS'; -fx-alignment: center; -fx-font-size: 25;");
        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        usernameColumn.setStyle("-fx-font-family: 'Comic Sans MS'; -fx-alignment: center; -fx-font-size: 25;");
        usernameColumn.setPrefWidth(150);
        TableColumn<User, String> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreColumn.setStyle("-fx-font-family: 'Comic Sans MS'; -fx-alignment: center; -fx-font-size: 25;");
        scoreColumn.setPrefWidth(150);
        tableView.getColumns().add(numberColumn);
        tableView.getColumns().add(usernameColumn);
        tableView.getColumns().add(scoreColumn);
        ObservableList<User> data = FXCollections.observableArrayList(User.users);
        tableView.setItems(data);

        ScrollPane scoreBordPane = new ScrollPane(tableView);
        scoreBordPane.setLayoutX(-425);scoreBordPane.setLayoutY(20);
        scoreBordPane.setFitToHeight(true);
        scoreBordPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scoreBordPane.setFitToWidth(true);
        scoreBordPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        profilePane.getChildren().add(scoreBordPane);
//        scoreBordPane.setVisible(false);
        return scoreBordPane;
    }

    private Pane changePassword(Pane profilePane) {
        Pane changePasswordPane = new Pane();
        changePasswordPane.setPrefSize(400, 400);
        changePasswordPane.setLayoutX(-400);
        changePasswordPane.setLayoutY(300);
//        changePasswordPane.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderStroke.THIN)));
        profilePane.getChildren().add(changePasswordPane);
        createTextField("Old Password", 50, 20, 70, 300, changePasswordPane);
        createTextField("New Password", 50, 120, 70, 300, changePasswordPane);
        createTextField("Check New Password", 50, 220, 70, 300, changePasswordPane);
        Button accept = createButton("Accept", 125, 320, 50, 150, 25, changePasswordPane);
        accept.setOnMouseClicked(mouseEvent ->changePasswordPane.setVisible(false));
        changePasswordPane.setVisible(false);
        return changePasswordPane;
    }

    public Pane showAvatars(Pane profilePane, Circle mainAvatar){
        Pane showAvatar = new Pane();
        profilePane.getChildren().add(showAvatar);
        showAvatar.setPrefSize(200, 700);
        showAvatar.setLayoutX(-200);
        showAvatar.setLayoutY(0);
        Button fromFile = createButton("Select from files",0, 680, 50, 200, 20, showAvatar);
        fromFile.setTextFill(Color.BLACK);
        Circle avatar1 = new Circle(100, 50, 70);
        Circle avatar2 = new Circle(100, 160, 70);
        Circle avatar3 = new Circle(100, 270, 70);
        Circle avatar4 = new Circle(100, 380, 70);
        Circle avatar5 = new Circle(100, 490, 70);
        Circle avatar6 = new Circle(100, 600, 70);
        Image imageAvatar1 = new Image("D:\\university\\code\\project-group-3888\\src\\main\\resources\\images\\avatars\\1.jpg");
        Image imageAvatar2 = new Image("D:\\university\\code\\project-group-3888\\src\\main\\resources\\images\\avatars\\2.jpg");
        Image imageAvatar3 = new Image("D:\\university\\code\\project-group-3888\\src\\main\\resources\\images\\avatars\\3.jpg");
        Image imageAvatar4 = new Image("D:\\university\\code\\project-group-3888\\src\\main\\resources\\images\\avatars\\4.jpg");
        Image imageAvatar5 = new Image("D:\\university\\code\\project-group-3888\\src\\main\\resources\\images\\avatars\\5.jpg");
        Image imageAvatar6 = new Image("D:\\university\\code\\project-group-3888\\src\\main\\resources\\images\\avatars\\6.jpg");
        avatar1.setFill(new ImagePattern(imageAvatar1));
        avatar2.setFill(new ImagePattern(imageAvatar2));
        avatar3.setFill(new ImagePattern(imageAvatar3));
        avatar4.setFill(new ImagePattern(imageAvatar4));
        avatar5.setFill(new ImagePattern(imageAvatar5));
        avatar6.setFill(new ImagePattern(imageAvatar6));
        showAvatar.getChildren().add(avatar1);showAvatar.getChildren().add(avatar2);showAvatar.getChildren().add(avatar3);
        showAvatar.getChildren().add(avatar4);showAvatar.getChildren().add(avatar5);showAvatar.getChildren().add(avatar6);
        avatar1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                currentUser.setAvatar("D:\\university\\code\\project-group-3888\\src\\main\\resources\\images\\avatars\\1.jpg");
                mainAvatar.setFill(new ImagePattern(imageAvatar1));
                showAvatar.setVisible(false);
            }
        });
        avatar2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                currentUser.setAvatar("D:\\university\\code\\project-group-3888\\src\\main\\resources\\images\\avatars\\2.jpg");
                mainAvatar.setFill(new ImagePattern(imageAvatar2));
                showAvatar.setVisible(false);
            }
        });
        avatar3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                currentUser.setAvatar("D:\\university\\code\\project-group-3888\\src\\main\\resources\\images\\avatars\\3.jpg");
                mainAvatar.setFill(new ImagePattern(imageAvatar3));
                showAvatar.setVisible(false);
            }
        });
        avatar4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                currentUser.setAvatar("D:\\university\\code\\project-group-3888\\src\\main\\resources\\images\\avatars\\4.jpg");
                mainAvatar.setFill(new ImagePattern(imageAvatar4));
                showAvatar.setVisible(false);
            }
        });
        avatar5.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                currentUser.setAvatar("D:\\university\\code\\project-group-3888\\src\\main\\resources\\images\\avatars\\5.jpg");
                mainAvatar.setFill(new ImagePattern(imageAvatar5));
                showAvatar.setVisible(false);
            }
        });
        avatar6.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                currentUser.setAvatar("D:\\university\\code\\project-group-3888\\src\\main\\resources\\images\\avatars\\6.jpg");
                mainAvatar.setFill(new ImagePattern(imageAvatar6));
                showAvatar.setVisible(false);
            }
        });
        fromFile.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(stage);
                try {
                    currentUser.setAvatar(file.toURI().toString());
                } catch (NullPointerException nullPointerException){}
                Image ownImage = new Image(file.toURI().toString());
                mainAvatar.setFill(new ImagePattern(ownImage));
                System.out.println(fileChooser.getInitialFileName());
                showAvatar.setVisible(false);
            }
        });
        showAvatar.setVisible(false);
        return showAvatar;
    }

    private void editName(TextField textField, Pane profilePane){
        textField.setDisable(false);
        profilePane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().getName().equals("Enter")) {
                    currentUser.setUserName(textField.getText());
                    textField.setPromptText(textField.getText());
                    textField.setText("");
                    textField.setDisable(true);
                }
            }
        });
    }
    private void editNickName(TextField textField, Pane profilePane){
        textField.setDisable(false);
        profilePane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().getName().equals("Enter")) {
                    currentUser.setNickName(textField.getText());
                    textField.setPromptText(textField.getText());
                    textField.setText("");
                    textField.setDisable(true);
                }
            }
        });
    }

    private void editEmail(TextField textField, Pane profilePane){
        textField.setDisable(false);
        profilePane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().getName().equals("Enter")) {
                    currentUser.setEmail(textField.getText());
                    textField.setPromptText(textField.getText());
                    textField.setText("");
                    textField.setDisable(true);
                }
            }
        });
    }

    private void editSlogan(TextField textField, Pane profilePane){
        textField.setDisable(false);
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().getName().equals("Enter")) {
                    currentUser.setSlogan(textField.getText());
                    textField.setPromptText(textField.getText());
                    textField.setText("");
                    textField.setDisable(true);
                }
            }
        });
    }




    public String run() {
//        HashMap<String, String> optionPass;
//        String command;
//        while (true) {
//            command = CommandParser.getScanner().nextLine();
//            if (commandParser.validate(command,"back",null) != null) return "back";
//            if (commandParser.validate(command,"show current menu",null) != null) System.out.println("Profile Menu");
//            else if ((optionPass = commandParser.validate(command,"profile change","u|username/n|nickname/e|email/s|slogan")) != null)
//                System.out.println(profileController.profileChange(optionPass));
//            else if ((optionPass = commandParser.validate(command,"profile change password","o|oldPassword/n|newPassword")) != null)
//                System.out.println(profileController.changePassword(optionPass));
//            else if (commandParser.validate(command,"profile remove slogan",null) != null)
//                System.out.println(profileController.removeSlogan());
//            else if (commandParser.validate(command,"profile display highscore",null) != null)
//                System.out.println(profileController.displayInfoSeparately(true,false,false));
//            else if (commandParser.validate(command,"profile display rank",null) != null)
//                System.out.println(profileController.displayInfoSeparately(false,true,false));
//            else if (commandParser.validate(command,"profile display slogan",null) != null)
//                System.out.println(profileController.displayInfoSeparately(false,false,true));
//            else if (commandParser.validate(command,"profile display",null) != null)
//                System.out.println(profileController.displayAllInfo());
//            else System.out.println("Invalid command!");
//        }
        return null;
    }

}

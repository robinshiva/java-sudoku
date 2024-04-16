module roblabs {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;

    opens roblabs to javafx.fxml;
    exports roblabs;
}

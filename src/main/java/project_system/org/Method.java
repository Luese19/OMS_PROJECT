package project_system.org;

import java.io.File;

import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class Method {

    
    public void showAlert(String title, String message) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}

    public void createFolder(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public void createFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    public void deleteFolder(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    public void renameFile(String path, String newName) {
        File file = new File(path);
        if (file.exists()) {
            File newFile = new File(newName);
            file.renameTo(newFile);
        }
    }

    public void renameFolder(String path, String newName) {
        File file = new File(path);
        if (file.exists()) {
            File newFile = new File(newName);
            file.renameTo(newFile);
        }
    }

    public void copyFile(String source, String destination) {
        File file = new File(source);
        if (file.exists()) {
            File newFile = new File(destination);
            file.renameTo(newFile);
        }
    }

    public void copyFolder(String source, String destination) {
        File file = new File(source);
        if (file.exists()) {
            File newFile = new File(destination);
            file.renameTo(newFile);
        }
    }

    public void moveFile(String source, String destination) {
        File file = new File(source);
        if (file.exists()) {
            File newFile = new File(destination);
            file.renameTo(newFile);
        }
    }

    public void moveFolder(String source, String destination) {
        File file = new File(source);
        if (file.exists()) {
            File newFile = new File(destination);
            file.renameTo(newFile);
        }
    }

    public void openFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            try {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void openFolder(String path) {
        File file = new File(path);
        if (file.exists()) {
            try {
                Runtime.getRuntime().exec("explorer.exe /select," + path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void printFile(File file) {
    if (file == null || !file.exists()) {
        showAlert("Error", "File does not exist");
        return;
    }

    PrinterJob printerJob = PrinterJob.createPrinterJob();
    if (printerJob != null) {
        boolean success = printerJob.printPage(new ImageView(new Image(file.toURI().toString())));
        if (success) {
            printerJob.endJob();
        } else {
            showAlert("Error", "Failed to print the file");
        }
    } else {
        showAlert("Error", "Could not create a printer job");
    }
}

    public void printFile(String path) {
        File file = new File(path);

}
}
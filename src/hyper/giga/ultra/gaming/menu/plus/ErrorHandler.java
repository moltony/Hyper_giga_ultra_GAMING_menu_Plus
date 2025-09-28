package hyper.giga.ultra.gaming.menu.plus;

import javax.swing.JOptionPane;

public class ErrorHandler
{
    public static void handleError(Exception exc, String message)
    {
        JOptionPane.showMessageDialog(null, String.format("%s: %s", message, exc.toString()), GamingMenu.SOFTWARE_NAME, JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
}

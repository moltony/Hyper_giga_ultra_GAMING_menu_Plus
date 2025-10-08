package hyper.giga.ultra.gaming.menu.plus;

public class PathUtils
{
    public static String expandHome(String path)
    {
        return path.replace("~", System.getProperty("user.home"));
    }
}

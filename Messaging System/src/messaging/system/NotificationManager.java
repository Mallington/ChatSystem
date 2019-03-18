package messaging.system;

/**
 * Need to document
 */
public class NotificationManager {
    private Device OSType;
    public enum Device{
        OSX, LINUX, WINDOWS, UN_SUPPORTED
    }
    public NotificationManager(){
        String OS;
        ProcessBuilder proc = new ProcessBuilder("osascript", "script.scpt");


        switch(OS = System.getProperty("os.name").toLowerCase()){
            case "mac os x":
                OSType = Device.OSX;
                break;

            default:
                OSType = Device.UN_SUPPORTED;
                System.out.println(OS+" not supported for notifications");
                break;
        }
    }

    public boolean sendNotification(String title, String subTitle, String message){
        try {
            switch (OSType) {
                case OSX:
                    String script = "display notification \"" + message + "\" with title \"" + title + "\" subtitle \"" + subTitle+"\"";
                    ProcessBuilder proc = new ProcessBuilder("osascript", "-e", script);
                    proc.start();
                    return true;
            }
        } catch(Exception e){
            return false;
        }
        return false;
    }

    public static void main(String[] args){
        NotificationManager notifications = new NotificationManager();

        System.out.println(notifications.sendNotification("Title","Subtitle", "Message"));
    }

}

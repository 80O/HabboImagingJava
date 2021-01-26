package pw.arcturus;

import org.restlet.Component;
import org.restlet.data.Protocol;
import pw.arcturus.database.Database;
import pw.arcturus.imaging.AvatarParts;
import pw.arcturus.imaging.GuildParts;
import pw.arcturus.imaging.imagers.AvatarImager;
import pw.arcturus.routes.AvatarImagerRequest;
import pw.arcturus.routes.FourOhFourRequest;
import pw.arcturus.routes.GuildBadgeRequest;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Imaging
{
    public static ConfigurationManager  configurationManager;
    public static Database              database;
    public static GuildParts            guildParts;
    public static AvatarParts           avatarParts;
    public static List<String>          whitelistedDomains;

    public static void main(String[] args) throws Exception
    {
        System.out.println("Habbo Imaging!");

        String[] folders = new String[]{"cache/avatar/", "cache/badge/", "resources/avatar/", "resources/badge/", "resources/xml/assets/", "resources/xml/fx/"};
        for (String s : folders)
        {
            File folder = new File(s);
            folder.mkdirs();
        }

        configurationManager = new ConfigurationManager("config_imager.ini");
        database = new Database(configurationManager);
        guildParts = new GuildParts();
        avatarParts = new AvatarParts();

        AvatarImager.defaultBytes =  Files.readAllBytes(new File("cache/avatar/" + AvatarImager.defaultLook + ".png").toPath());

        Component component = new Component();
        component.getServers().add(Protocol.HTTP, configurationManager.value("host"), configurationManager.integer("port"));

        component.setLogService(new org.restlet.service.LogService(false));
        component.getDefaultHost().attachDefault(FourOhFourRequest.class);
        component.getDefaultHost().attach("/habbo-imaging/avatar/", AvatarImagerRequest.class);
        component.getDefaultHost().attach("/habbo-imaging/badge/{badge}", GuildBadgeRequest.class);

        whitelistedDomains = new ArrayList<>();
        whitelistedDomains.add("localhost");
        whitelistedDomains.add("example.com");
        Runtime.getRuntime().gc();
        component.start();
    }

    public static boolean validDomain(String domain)
    {
        return whitelistedDomains.contains(domain);
    }
}

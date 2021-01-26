package pw.arcturus.imaging;

import org.restlet.data.Parameter;

public enum AvatarGesture
{
    NORMAL("std"),
    SMILE("sml"),
    SAD("sad"),
    ANGRY("agr"),
    SURPRISED("srp"),
    EYEBLINK("eyb"),
    SPEAK("spk");

    public final String key;
    private AvatarGesture(String key)
    {
        this.key = key;
    }

    public static AvatarGesture fromParameter(Parameter parameter)
    {
        if (parameter == null)
        {
            return NORMAL;
        }

        for (AvatarGesture gesture : AvatarGesture.values())
        {
            if (gesture.key.equalsIgnoreCase(parameter.getValue()))
            {
                return gesture;
            }
        }

        return NORMAL;
    }
}

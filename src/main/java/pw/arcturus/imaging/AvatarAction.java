package pw.arcturus.imaging;

import org.restlet.data.Parameter;

import java.util.ArrayList;
import java.util.List;

public enum AvatarAction
{
    CARRY("crr"),
    DRINK("drk"),
    STAND("std"),
    SIT("sit"),
    LAY("lay"),
    WALK("wlk"),
    WAVE("wav");

    public final String key;
    public final List<AvatarAction> illegalWith = new ArrayList<>();

    static
    {
        STAND.illegalWith.add(SIT);
        STAND.illegalWith.add(LAY);
        STAND.illegalWith.add(WALK);
        SIT.illegalWith.add(STAND);
        SIT.illegalWith.add(LAY);
        SIT.illegalWith.add(WALK);
        LAY.illegalWith.add(STAND);
        LAY.illegalWith.add(SIT);
        LAY.illegalWith.add(WALK);
        LAY.illegalWith.add(WAVE);
        WALK.illegalWith.add(STAND);
        WALK.illegalWith.add(SIT);
        WALK.illegalWith.add(LAY);
        WAVE.illegalWith.add(LAY);
    }

    private AvatarAction(String key)
    {
        this.key = key;
    }

    public static AvatarAction fromParameter(Parameter parameter)
    {
        if (parameter == null)
        {
            return STAND;
        }

        for (AvatarAction action : AvatarAction.values())
        {
            if (action.key.equalsIgnoreCase(parameter.getValue()))
            {
                return action;
            }
        }

        return STAND;
    }

    public static AvatarAction fromString(String key)
    {
        for (AvatarAction action : values())
        {
            if (action.key.equals(key))
            {
                return action;
            }
        }

        return null;
    }

}

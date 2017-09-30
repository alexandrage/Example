package ru.minecraft.util;

public class MathHelper
{
    /**
     * Returns the greatest integer less than or equal to the double argument
     */
    public static int floor_double(double p_76128_0_)
    {
        int i = (int)p_76128_0_;
        return p_76128_0_ < (double)i ? i - 1 : i;
    }

    /**
     * Long version of floor_double
     */
    public static long floor_double_long(double p_76124_0_)
    {
        long i = (long)p_76124_0_;
        return p_76124_0_ < (double)i ? i - 1L : i;
    }
    
    /**
     * Returns the greatest integer less than or equal to the float argument
     */
    public static int floor_float(float p_76141_0_)
    {
        int i = (int)p_76141_0_;
        return p_76141_0_ < (float)i ? i - 1 : i;
    }
}
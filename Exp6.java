public class WrapperClass 
{
    public static void main(String[] args)
    {
        int primitiveInt = 100;
        float primitiveFloat = 25.75f;
        char primitiveChar = 'A';
        boolean primitiveBoolean = true;

        Integer wrappedInt = primitiveInt;
        Float wrappedFloat = primitiveFloat;
        Character wrappedChar = primitiveChar;
        Boolean wrappedBoolean = primitiveBoolean;

        System.out.println("Autoboxed Integer:" + wrappedInt);
        System.out.println("Autoboxed Float: " + wrappedFloat);
        System.out.println("Autoboxed Character: " + wrappedChar);
        System.out.println("Autoboxed Boolean:" + wrappedBoolean);

        int unboxedInt = wrappedInt;
        float unboxedFloat = wrappedFloat;
        char unboxedChar = wrappedChar;
        boolean unboxedBoolean = wrappedBoolean;

        System.out.println("Unboxed int:" + unboxedInt);
        System.out.println("Unboxed float:" + unboxedFloat);
        System.out.println("Unboxed char:" + unboxedChar);
        System.out.println("Unboxed  boolean:" + unboxedBoolean);

        Integer parsedInt = Integer.valueOf("300");
        Float parsedFloat = Float.valueOf("75.25");
        Character parsedChar = Character.valueOf('C');
        Boolean parsedBoolean = Boolean.valueOf("tr");

        System.out.println("parsed and autoboxed Integer:" + parsedInt);
        System.out.println("parsed and autoboxed Float:" + parsedFloat);
        System.out.println("parsed and autoboxed Character:" + parsedChar);
        System.out.println("parsed and autoboxed Boolean:tr");
    }
}


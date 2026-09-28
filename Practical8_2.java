import java.lang.reflect.Field;

class AnnotationValidator {

    public static String[] validate(Object obj) {

        java.util.ArrayList<String> errors =
                new java.util.ArrayList<>();

        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {

            field.setAccessible(true);

            try {

                if (field.isAnnotationPresent(Positive.class)) {

                    Object value = field.get(obj);

                    if (value instanceof Number) {

                        double number =
                                ((Number) value).doubleValue();

                        if (number <= 0) {
                            errors.add(
                                field.getName() +
                                " must be positive"
                            );
                        }
                    }
                }

                if (field.isAnnotationPresent(MaxLength.class)) {

                    Object value = field.get(obj);

                    if (value instanceof String) {

                        MaxLength annotation =
                                field.getAnnotation(MaxLength.class);

                        int maxLength = annotation.value();

                        String text = (String) value;

                        if (text.length() > maxLength) {
                            errors.add(
                                field.getName() +
                                " exceeds maximum length of " +
                                maxLength
                            );
                        }
                    }
                }

            } catch (IllegalAccessException e) {
                errors.add("Cannot access " + field.getName());
            }
        }

        return errors.toArray(new String[0]);
    }

    public static void main(String[] args) {

        Account account =
                new Account(-5000, "Rudra");

        String[] errors =
                AnnotationValidator.validate(account);

        for (String error : errors) {
            System.out.println(error);
        }
    }
}
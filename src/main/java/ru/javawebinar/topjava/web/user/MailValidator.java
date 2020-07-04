package ru.javawebinar.topjava.web.user;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.to.UserTo;

import static ru.javawebinar.topjava.web.ExceptionInfoHandler.duplicateMessages;

@Component
public class MailValidator implements Validator {

    private final UserRepository repository;

    public MailValidator(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserTo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserTo userTo = (UserTo) target;
        String email = userTo.getEmail();
        if (email != null && repository.getByEmail(email) != null) {
            errors.rejectValue("email", "Valid.userTo.email", duplicateMessages.get("email"));
        }
    }
}

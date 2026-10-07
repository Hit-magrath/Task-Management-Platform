package org.example.backend.user;

import com.google.common.base.MoreObjects;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Locale;
import java.util.Objects;

public class Person {

    private final String email;

    public Person(String email){
        if (!EmailValidator.getInstance().isValid(email)) throw new TmpException("Bad email address" );
        this.email = email;
    }

    public String getEmail(){return email;}

    public String getName(){
        String preName = this.email.split("@")[0];
        return preName.substring(0,1).toUpperCase() + preName.substring(1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o ;
        return Objects.equals(email,person.email);
    }

    @Override
    public int hashCode(){return Objects.hashCode(email);}

    @Override
    public String toString(){
        return MoreObjects.toStringHelper(this).add("email", email).toString();
    }
}

package com.burgas.thread.notify;

import java.util.Objects;

public record Message(String data) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return Objects.equals(data, message1.data);
    }

    @Override
    public String toString() {
        //noinspection preview
        return STR."Message{message='\{data}\{'\''}\{'}'}";
    }
}

package com.example.mbsedemo1.Entity;

import com.example.mbsedemo1.Function;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileContent {
    private String file1;
    private String file2;
    public boolean checked() {

        Function function = new Function();
        boolean result = function.check(file1, file2);
        return result;
    }

}

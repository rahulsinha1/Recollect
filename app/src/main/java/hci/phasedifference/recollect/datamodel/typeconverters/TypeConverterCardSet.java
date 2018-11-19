package hci.phasedifference.recollect.datamodel.typeconverters;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TypeConverterCardSet {

    @TypeConverter
    public static List<String> stringToList(String data) {
        Gson gson = new Gson();
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<String>>() {
        }.getType();

        try {
            return gson.fromJson(data, listType);
        } catch (Exception e) {
            return new ArrayList<String>(Arrays.asList("", ""));
        }
    }

    @TypeConverter
    public static String listToString(List<String> someObjects) {
        Gson gson = new Gson();
        return gson.toJson(someObjects);
    }
}

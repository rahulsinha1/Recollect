package hci.phasedifference.recollect.datamodel.typeconverters;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import hci.phasedifference.recollect.datamodel.datarepresentaion.CardSetImpl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TypeConverterCardSet {

    @TypeConverter
    public static List<CardSetImpl> stringToList(String data) {
        Gson gson = new Gson();
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<CardSetImpl>>() {
        }.getType();

        try {
            return gson.fromJson(data, listType);
        } catch (Exception e) {
            return new ArrayList<CardSetImpl>();
        }
    }

    @TypeConverter
    public static String listToJson(List<CardSetImpl> someObjects) {
        Gson gson = new Gson();
        return gson.toJson(someObjects);
    }
}

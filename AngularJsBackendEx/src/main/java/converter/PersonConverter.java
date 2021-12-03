package converter;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import model.Person;
import org.bson.types.ObjectId;

public class PersonConverter {

    public static DBObject toDBObject(Person p) {
        BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
                .append("name", p.getName())
                .append("country", p.getCountry());
        if (p.getId() != null) {
            builder = builder.append("_id", new ObjectId(p.getId()));
        }
        return builder.get();
    }

    public static Person toPerson(DBObject doc) {
        Person p = new Person();
        p.setName((String) doc.get("name"));
        p.setCountry((String) doc.get("country"));
        ObjectId id = (ObjectId) doc.get("_id");
        p.setId(id.toString());
        return p;
    }
}

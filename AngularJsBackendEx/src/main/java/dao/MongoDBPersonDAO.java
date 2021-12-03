package dao;

import com.mongodb.*;
import converter.PersonConverter;
import model.Person;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class MongoDBPersonDAO {

    private DBCollection col;

    public MongoDBPersonDAO(MongoClient mongo) {
        this.col = mongo.getDB("quynh").getCollection("Persons");
    }

    public Person createPerson(Person p) {
        DBObject doc = PersonConverter.toDBObject(p);
        this.col.insert(doc);
        ObjectId id = (ObjectId) doc.get("_id");
        p.setId(id.toString());
        return p;
    }

    public void updatePerson(Person p) {
        DBObject query = BasicDBObjectBuilder.start()
                .append("_id", new ObjectId(p.getId())).get();
        this.col.update(query, PersonConverter.toDBObject(p));
    }

    public List<Person> readAllPerson() {
        List<Person> data = new ArrayList<Person>();
        DBCursor cursor = col.find();
        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            Person p = PersonConverter.toPerson(doc);
            data.add(p);
        }
        return data;
    }

    public void deletePerson(Person p) {
        DBObject query = BasicDBObjectBuilder.start()
                .append("_id", new ObjectId(p.getId())).get();
        this.col.remove(query);
    }

    public Person readPerson(Person p) {
        DBObject query = BasicDBObjectBuilder.start()
                .append("_id", new ObjectId(p.getId())).get();
        DBObject data = this.col.findOne(query);
        return PersonConverter.toPerson(data);
    }

}

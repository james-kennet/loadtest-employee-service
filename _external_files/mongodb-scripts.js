
db = db.getSiblingDB('admin');
db.auth("root", "password");
db = db.getSiblingDB('local_mongo_db');
db.createCollection('employees');
db.employees.createIndex( {firstName: 1});
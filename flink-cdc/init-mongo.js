// init-mongo.js
const dbName = `inventory`;
const db = db.getSiblingDB(dbName);

// 这里插入你的初始化命令，例如创建集合、索引或插入数据
db.getCollection('products').insertMany([
    {"_id": ObjectId("100000000000000000000101"), "name": "scooter", "description": "Small 2-wheel scooter", "weight": 3.14},
    {"_id": ObjectId("100000000000000000000102"), "name": "car battery", "description": "12V car battery", "weight": 8.1},
    {"_id": ObjectId("100000000000000000000103"), "name": "12-pack drill bits", "description": "12-pack of drill bits with sizes ranging from #40 to #3", "weight": 0.8},
    {"_id": ObjectId("100000000000000000000104"), "name": "hammer", "description": "12oz carpenter''s hammer", "weight": 0.75},
    {"_id": ObjectId("100000000000000000000105"), "name": "hammer", "description": "12oz carpenter''s hammer", "weight": 0.875},
    {"_id": ObjectId("100000000000000000000106"), "name": "hammer", "description": "12oz carpenter''s hammer", "weight": 1.0},
    {"_id": ObjectId("100000000000000000000107"), "name": "rocks", "description": "box of assorted rocks", "weight": 5.3},
    {"_id": ObjectId("100000000000000000000108"), "name": "jacket", "description": "water resistent black wind breaker", "weight": 0.1},
    {"_id": ObjectId("100000000000000000000109"), "name": "spare tire", "description": "24 inch spare tire", "weight": 22.2}
]);

// 更多的数据库操作...

db1 = db.getSiblingDB("db_catalogs");

// Crear una colección y agregarle un documento para garantizar que la base de datos se cree
db1.createCollection("movies");

// Insertar un documento para asegurarte de que la base de datos y la colección estén configuradas
db1.movies.insertOne({ name: "Inception", genre: "Sci-Fi" });
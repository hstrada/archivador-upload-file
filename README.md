# archivador-upload-file

Suppose you want to upload a file to your system, but you don't know the entities that are available.

For example, you have cities and students.

There are two main resources that helps you to decide on which entity you are going to choose.

First:

```GET /entities```

Retrieves the entities in the system and the declared fields.
 
For example: *City - with id, name, numberInhabitants*

```POST /upload``` 

When you choose the file, you just have to sent the entity that you want to load and the correspondent fields.
And of course, if the first line has a header or not. If it has, you don't need to send this information.

For example:
file - test.csv
entity - City
fields - id, name

Done!
